package io.siv.support.annotation;

import static io.siv.support.annotation.SourceWriter.write;

import java.io.IOException;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Name;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

import com.google.auto.service.AutoService;

@SupportedAnnotationTypes({ "io.siv.support.annotation.GizmoStratagem", "io.siv.support.annotation.Gizmo" })
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class GizmoProcessor extends AbstractProcessor {

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		Set<? extends Element> elements = roundEnv
				.getElementsAnnotatedWithAny(Set.of(GizmoStratagem.class, Gizmo.class));

		System.out.println("@Override process() template: " + elements);

		if (elements.size() > 0) {
			int processed = 0;
			int created = 0;

			for (Element e : elements) {
				System.out.println("element: " + e);
				if (e.getKind() == ElementKind.CLASS) {
					TypeElement classElement = (TypeElement) e;
					PackageElement packageElement = (PackageElement) classElement.getEnclosingElement();

					System.out.println("Processing " + classElement.getSimpleName() + " as model Runner");
					System.out.println("Runners packaged in: " + packageElement.getQualifiedName());

					for (Gizmo g : e.getAnnotationsByType(Gizmo.class)) {
						createSource(classElement.getSimpleName(), packageElement.getQualifiedName(), g);
						created++;
					}

					processed++;
				}
			}

			System.out.println(processed + "(s) files processed and " + created + "(s) files created by SIV");
			return true;
		}

		return false;
	}

	private void createSource(Name className, Name packageName, Gizmo gizmo) {
		String prefix = useFirstPartOfCamelCaseName(className);
		String name = prefix + className(gizmo);
		System.out.println("Generating: ~>> " + name);
		JavaFileObject jfo = createSourceFile(packageName + "." + name);
		write(jfo, name, packageName, gizmo, className.toString());
	}

	private String useFirstPartOfCamelCaseName(Name name) {
		String[] r = name.toString().split("(?=\\p{Lu})");
		return r != null && r.length > 0 ? r[0] : "Generic";
	}

	private JavaFileObject createSourceFile(String name) {
		JavaFileObject jfo = null;

		try {
			jfo = processingEnv.getFiler().createSourceFile(name);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return jfo;
	}

	private String className(Gizmo t) {
		return String.format("%s%s%s%s%s%s%sRunner", legalizeName(t.os()), legalizeName(t.device()),
				legalizeName(t.osVersion()), legalizeName(t.browserName()), legalizeName(t.browser()),
				legalizeName(t.browserVersion()), t.realMobile() ? "RealMobile" : "");
	}

	private String legalizeName(String v) {
		return "".contentEquals(v) ? v : capFirstChar(v.replaceAll("[^a-zA-Z0-9]", ""));
	}

	private String capFirstChar(String v) {
		return v.substring(0, 1).toUpperCase() + v.substring(1);
	}
}
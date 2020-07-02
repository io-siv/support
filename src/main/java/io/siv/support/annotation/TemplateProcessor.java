/*
 * Inspired by ->
 * 		https://www.baeldung.com/java-annotation-processing-builder
 * 		https://www.youtube.com/watch?v=xswPPwYPAFM
 */
package io.siv.support.annotation;

import static io.siv.support.annotation.SourceWriter.write;

import java.io.IOException;
import java.util.Properties;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
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

import io.siv.support.util.Loader;
import com.google.auto.service.AutoService;

@SupportedAnnotationTypes("io.siv.support.annotation.RunTemplate")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
class TemplateProcessor extends AbstractProcessor {

	@Override
	public synchronized void init(ProcessingEnvironment processingEnv) {
		super.init(processingEnv);
		Properties p = Loader.propertiesForFileKey("support");
		String title = p.getProperty("studio.log.title");
		String warning = p.getProperty("studio.log.warning");
		System.out.println(title + warning);
	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		Set<? extends Element> templates = roundEnv.getElementsAnnotatedWith(RunTemplate.class);

		if (templates.size() > 0) {
			int processed = 0;
			int created = 0;
			for (Element e : roundEnv.getElementsAnnotatedWith(RunTemplate.class)) {
				if (e.getKind() == ElementKind.INTERFACE || e.getKind() == ElementKind.CLASS) {
					processed++;

					TypeElement classElement = (TypeElement) e;
					PackageElement packageElement = (PackageElement) classElement.getEnclosingElement();
					String extender = e.getKind() == ElementKind.INTERFACE ? "CucumberConfig"
							: classElement.getSimpleName().toString();

					System.out.println("@RunTemplate found as: " + classElement.getSimpleName());
					System.out.println("Package for generated source: " + packageElement.getQualifiedName());

					for (Target t : targets(e)) {
						createSource(classElement.getSimpleName(), packageElement.getQualifiedName(), t, extender);
						created++;
					}
				}
			}

			System.out.println(processed + "(s) files processed and " + created + "(s) files created by SIV");
			return true;
		}

		return false;
	}

	private Target[] targets(Element e) {
		return e.getAnnotation(RunTemplate.class).value();
	}

	private void createSource(Name template, Name pack, Target t, String extender) {
		String prefix = pack.toString().contains("remote") ? "Remote" : "Domestic";
		String name = prefix + className(t);
		System.out.println("Generating: ~>>." + name);
		JavaFileObject jfo = createSourceFile(pack + "." + name);
		write(jfo, name, pack, t, extender);
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

	private String className(Target t) {
		return String.format("%s%s%s%s%s%s%sTest", legalizeName(t.os()), legalizeName(t.device()),
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
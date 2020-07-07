package io.siv.support.annotation;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.function.BiFunction;

import javax.lang.model.element.Name;
import javax.tools.JavaFileObject;

class SourceWriter {

	private static final BiFunction<Integer, String, String> spacing = (n, s) -> String.format("%" + n + "s%s", "", s);

	public static void write(JavaFileObject jfo, String className, Name packageName, Gizmo gizmo, String extender) {
		if (null != jfo) {
			try {
				BufferedWriter bw = new BufferedWriter(jfo.openWriter());
				write(bw, className, packageName, gizmo, extender);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static void write(BufferedWriter bw, String clazz, Name pack, Gizmo t, String extender) throws IOException {
		bw.append("package ").append(pack).append(";");
		bw.newLine();
		bw.newLine();

		bw.append("import org.junit.BeforeClass;");
		bw.newLine();
		bw.append("import ").append(pack).append(".").append(extender).append(";");
		bw.newLine();
		bw.newLine();
		bw.newLine();

		bw.append("public class ").append(clazz).append(" extends ").append(extender).append(" {");
		bw.newLine();
		bw.newLine();
		bw.append(spacing.apply(4, "@BeforeClass"));
		bw.newLine();
		bw.append(spacing.apply(4, "public static void before() {"));
		bw.newLine();

		bw.append(spacing.apply(8, "System.setProperty(\"studio.gizmo.acceptSslCerts\", \"")).append("" + t.acceptSslCerts()).append("\");");
		bw.newLine();
		
		bw.append(spacing.apply(8, "System.setProperty(\"studio.gizmo.name\", \"")).append(clazz).append("\");");
		bw.newLine();

		bw.append(spacing.apply(8, "System.setProperty(\"studio.gizmo.os\", \"")).append(t.os()).append("\");");
		bw.newLine();

		bw.append(spacing.apply(8, "System.setProperty(\"studio.gizmo.osVersion\", \"")).append(t.osVersion()).append("\");");
		bw.newLine();

		bw.append(spacing.apply(8, "System.setProperty(\"studio.gizmo.browser\", \"")).append(t.browser()).append("\");");
		bw.newLine();
		
		bw.append(spacing.apply(8, "System.setProperty(\"studio.gizmo.browserName\", \"")).append(t.browserName()).append("\");");
		bw.newLine();

		bw.append(spacing.apply(8, "System.setProperty(\"studio.gizmo.browserVersion\", \"")).append(t.browserVersion()).append("\");");
		bw.newLine();
		
		bw.append(spacing.apply(8, "System.setProperty(\"studio.gizmo.device\", \"")).append(t.device()).append("\");");
		bw.newLine();

		bw.append(spacing.apply(8, "System.setProperty(\"studio.gizmo.realMobile\", \"")).append(t.realMobile() ? "true" : "false").append("\");");
		bw.newLine();
		
		bw.append(spacing.apply(8, "System.setProperty(\"studio.gizmo.appium\", \"")).append(t.appium()).append("\");");
		bw.newLine();
		
		StringBuilder b = new StringBuilder("");
		if (t.custom().length > 0) {
			for (String s : t.custom()) {
				b.append(b.length() > 0 ? ";" : "").append(s);
			}
		}
		
		bw.append(spacing.apply(8, "System.setProperty(\"studio.custom\", \"")).append(b.toString()).append("\");");
		bw.newLine();
		bw.newLine();
		
		bw.append(spacing.apply(8, "System.out.println(\"[SIV] run with: ")).append(clazz).append("\");");
		bw.newLine();

		bw.append(spacing.apply(4, "}"));
		bw.newLine();
		bw.append("}");
		bw.newLine();
		bw.close();
	}
}
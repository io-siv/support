package io.siv.support.service;

import java.util.Properties;

import io.siv.support.util.Loader;

/**
 * Used to obtain a Connection object.
 */
public final class ConnectionFactory {
	private ConnectionFactory() {}

	/**
	 * @param clazz The class of the calling object. Typically this would be your step class.
	 * @return A Connection object.
	 */
	public static Connection instanceOf(Class<?> clazz) {
		
		String className = clazz.getSimpleName();
		Properties p = Loader.propertiesForFileKey(clazz, "authoring");
		return new ConnectionBuilder()

				// Passed in by the step class
				.withProperty("studio.step.class.name", className)

				// Automagically loaded by Maven and or combination of properties file
				.withProperty("studio.driver.path", System.getProperty("user.dir") + p.getProperty("studio.driver.path"))
				.withProperty("studio.host.os", System.getProperty("os.name").toLowerCase())

				// Configured with .pom
				.withProperty("studio.profileId", System.getProperty("studio.profileId"))
				.withProperty("studio.local.force", System.getProperty("studio.local.force"))
				.withProperty("browserstack.local", System.getProperty("studio.local"))
				.withProperty("browserstack.debug", System.getProperty("studio.local.debug"))

				// These come from the environment. @see README.md for more information
				.withProperty("studio.browserstack.key", System.getenv("BROWSERSTACK_ACCESS_KEY"))
				.withProperty("studio.browserstack.user", System.getenv("BROWSERSTACK_USERNAME"))

				/*
				 * Configured with @RunTemplate and @Target. See TargetConfig.java
				 * Annotations are used to hard code properties to a specific class
				 * file for parallel processing. Otherwise, it's a nightmare keeping
				 * the pom.xml <forkCount>, .json or .properties, and any manually
				 * created @RunWith files all in sync.
				 */
				.withProperty("device", System.getProperty("studio.gizmo.device"))
				.withProperty("browser", System.getProperty("studio.gizmo.browser"))
				.withProperty("browserName", System.getProperty("studio.gizmo.browserName"))
				.withProperty("browser_version", System.getProperty("studio.gizmo.browserVersion"))
				.withProperty("os", System.getProperty("studio.gizmo.os"))
				.withProperty("os_version", System.getProperty("studio.gizmo.osVersion"))
				.withProperty("acceptSslCerts", System.getProperty("studio.gizmo.acceptSslCerts"))
				.withProperty("real_mobile", System.getProperty("studio.gizmo.realMobile"))
				.withProperty("browserstack.appium_version", System.getProperty("stydio.gizmo.appium"))
				.withProperty("browserstack.selenium_version", System.getProperty("studio.gizmo.seleniumVersion"))
				/*TODO finish parsing .withProperty("xxx", System.getProperty("studio.gizmo.custom"))*/

				// Automagically loaded by Studio * Important !! Do not change this unless you know why
				.withProperty("browserstack.localIdentifier", System.getProperty("studio.gizmo.name"))

				// Configured with from src/test/resources/studio.properties
				.withProperty("studio.browserstack.context", p.getProperty("studio.browserstack.context"))
				.withProperty("project", p.getProperty("studio.browserstack.project"))
				.withProperty("build", p.getProperty("studio.browserstack.build"))
				.withProperty("name", p.getProperty("studio.browserstack.name"))
				.withProperty("studio.test.home", p.getProperty("studio.test.home"))

				.build();
	}
}
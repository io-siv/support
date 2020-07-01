package io.siv.support.annotation;

/**
 * Defines an environment for running cucumber tests against.
 */
public @interface Target {

	/**
	 * Use this capability to ignore invalid certificate error in your test.
	 * @return boolean
	 */
	boolean acceptSslCerts() default true;

	/**
	 * Specifies what browser to use.
	 * <p>
	 * e.g Chrome, Firefox, Safari, Edge, IE
	 * @return String
	 */
	String browser() default "";

	/**
	 * Specifies what browser to use. Only has effect when device is set.
	 * <p>
	 * e.g iPhone, android
	 * <p>
	 * @return String
	 */
	String browserName() default "";

	/**
	 * Specifies what browser version to use. When empty, BrowerStack picks for us.
	 * @return String
	 */
	String browserVersion() default "";

	/**
	 * Specifies what operating system to use.
	 * <p>
	 * e.g. Windows, OS X
	 * 
	 * @return String
	 */
	String os() default "";

	/**
	 * Specifies what variant of the operating system to use. When empty, BrowerStack picks for us.
	 * @return String
	 */
	String osVersion() default "";

	/**
	 * Specifies a particular mobile device for the test environment.
	 * @return String
	 */
	String device() default "";

	/**
	 * Use this flag to test on a physical mobile device. Only has effect when device is set.
	 * @return boolean
	 */
	boolean realMobile() default false;

	/**
	 * Specifies what version of appium to use. Only has effect when device is set.
	 * <p>
	 * In most cases, you should leave this empty and let BrowserStack pick the version.
	 * @return The appium version
	 */
	String appium() default "";

	/**
	 * Use this to add properties not directly named by Target.
	 * <p>
	 * custom = "someBrowserStackProp=Xxx" or custom = { "someBrowserStackProp=Xxx",
	 * "someOtherBrowserStackProp=Xxx" }
	 * @return Array of Strings
	 */
	String[] custom() default "";
}
package io.siv.support.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Contains one or more @Target which define environments. The @RunTemplate
 * class is extended by a new, dynamically created class. One for each @Target.
 * The annotation processor handles this behind the scenes.
 */
@Target(TYPE)
@Retention(SOURCE)
public @interface RunTemplate {

	/**
	 * @return Array of @Target
	 */
	public io.siv.support.annotation.Target[] value();
}

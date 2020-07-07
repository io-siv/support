package io.siv.support.annotation;

import static java.lang.annotation.ElementType.TYPE;
import java.lang.annotation.Target;

/**
 * Do not use this class directly. javac handles this for us as @Gizmo is
 * repeatable.
 * <p>
 * Contains one or more @Gizmo which define environments and or device. The
 * class having one @Gizmo is extended by a new, dynamically created class. One
 * for each @Gizmo. The annotation processor handles this behind the scenes.
 */
@Target(TYPE)
public @interface GizmoStratagem {
	Gizmo[] value();
}

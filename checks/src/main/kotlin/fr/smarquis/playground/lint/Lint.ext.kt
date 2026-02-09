package fr.smarquis.playground.lint

import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Scope
import java.util.EnumSet

internal inline fun <reified T : Detector> implementation(
    scope: EnumSet<Scope>,
    vararg scopes: EnumSet<Scope>,
): Implementation = Implementation(T::class.java, scope, *scopes)

internal inline fun <reified T : Any> Any?.cast(): T = this as T
internal inline fun <reified T : Any> Any?.safeCast(): T? = this as? T

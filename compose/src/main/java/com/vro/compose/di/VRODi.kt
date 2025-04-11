package com.vro.compose.di

import com.vro.compose.navigator.VROTemplateNav
import com.vro.compose.template.VROTemplate
import com.vro.compose.template.VROTemplateMapper
import com.vro.navigation.VRODestination
import org.koin.core.qualifier.named

/**
 * Generates a unique [Qualifier] for a Koin scope based on the class name of the given type [T].
 * Useful for creating or retrieving named scopes for templates or screens.
 *
 * Example usage:
 * ```
 * val scope = get().createScope("SomeScope", vroScopeName<MyClass>())
 * ```
 */
inline fun <reified T : Any> vroScopeName() = named(T::class.toString())

/**
 * Retrieves a scoped navigator instance for the current [VROTemplate] from its Koin scope.
 *
 * @return A [VROTemplateNav] of type [D], resolved from the template's scope.
 * @throws org.koin.core.error.NoBeanDefFoundException if the navigator is not defined in the scope.
 */
fun <D : VRODestination> VROTemplate<*, *, D, *, *, *>.injectNavigator(): VROTemplateNav<D> {
    return scope.get()
}

/**
 * Retrieves a scoped mapper instance for the current [VROTemplate] from its Koin scope.
 *
 * @return A mapper of type [M], resolved from the template's scope.
 * @throws org.koin.core.error.NoBeanDefFoundException if the mapper is not defined in the scope.
 */
inline fun <reified M : VROTemplateMapper> VROTemplate<*, *, *, *, *, *>.injectMapper(): M {
    return scope.get()
}
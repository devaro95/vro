package com.vro.compose.di

import com.vro.compose.template.*
import org.koin.core.context.GlobalContext.get
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import java.util.UUID

/**
 * Generates a unique named for a Koin scope based on the runtime class name of the given type [T].
 *
 * This is useful when defining or retrieving named scopes dynamically for screens, templates,
 * or components that require isolated dependency containers.
 *
 * Example usage:
 * ```
 * val scope = getKoin().createScope("some-id", vroScopeName<MyTemplate>())
 * ```
 *
 * @return A named created from the fully qualified class name of [T].
 */
inline fun <reified T : Any> vroScopeName() = named(T::class.toString())

/**
 * Lazily retrieves a scoped mapper instance for the current [VROTemplate] from its Koin scope.
 *
 * This function is intended to be used with property delegation:
 * `override val mapper: MyTemplateMapper by injectMapper()`
 *
 * @return A lazy delegate that provides a mapper of type [M], resolved from the template's scope.
 */
inline fun <reified M : VROTemplateMapper> VROTemplateContent<*, *, *, *>.injectMapper(): Lazy<M> {
    return lazy { scope.get() }
}

/**
 * Lazily retrieves a scoped ViewModel instance for the current [VROTemplate] from its Koin scope.
 *
 * This function is intended to be used with property delegation:
 * `override val viewModel: MyTemplateViewModel by injectViewModel()`
 *
 * @return A lazy delegate that provides a ViewModel of type [VM], resolved from the template's scope.
 */
inline fun <reified VM : VROTemplateViewModel<*, *, *>> VROTemplate<*, *, *, *>.injectViewModel(): Lazy<VM> {
    return lazy { scope.get<VM>() }
}

inline fun <reified T : Any> createTemplateScope(): Scope {
    val name = T::class.simpleName ?: T::class.qualifiedName ?: "UnknownTemplate"
    val id = "$name-${UUID.randomUUID()}"
    return get().createScope(id, named(name))
}
package com.vro.compose.di

import com.vro.compose.template.*
import org.koin.core.context.GlobalContext.get
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import java.util.UUID

inline fun <reified T : Any> vroScopeName() = named(T::class.toString())

inline fun <reified M : VROTemplateMapper> VROTemplateContent<*, *, *, *>.injectMapper(): Lazy<M> {
    return lazy { scope.get() }
}

inline fun <reified VM : VROTemplateViewModel<*, *, *>> VROTemplate<*, *, *, *>.injectViewModel(): Lazy<VM> {
    return lazy { scope.get<VM>() }
}

inline fun <reified T : Any> createTemplateScope(): Scope {
    val name = T::class.simpleName ?: T::class.qualifiedName ?: "UnknownTemplate"
    val id = "$name-${UUID.randomUUID()}"
    return get().createScope(id, named(name))
}

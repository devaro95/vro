package com.vro.core_android.injection

import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import org.koin.java.KoinJavaComponent

inline fun <reified T> injectViewModel(
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null
) = KoinJavaComponent.getKoin().get<T>(T::class, qualifier = qualifier, parameters = parameters)

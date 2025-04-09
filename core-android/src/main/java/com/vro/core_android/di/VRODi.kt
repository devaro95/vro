package com.vro.core_android.di

import org.koin.core.qualifier.named
import kotlin.reflect.KClass

fun vroScopeName(clazz: KClass<*>) = named(clazz.qualifiedName!!)
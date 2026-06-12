package com.vro.compose.preview

/**
 * iOS actual — no-op since Compose Multiplatform previews run via Android Studio.
 */
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.BINARY)
actual annotation class VROLightMultiDevicePreview

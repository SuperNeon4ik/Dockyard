package io.github.dockyardmc.extentions

import io.netty.util.CharsetUtil
import java.util.*

fun String.byteSize(): Int {
    return this.toByteArray(CharsetUtil.UTF_8).size
}

fun String.properStrictCase(): String {
    return this.lowercase().replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
}
package com.sayproj.memorynotes.framework

import android.os.Bundle
import android.os.Parcelable

@Suppress("IMPLICIT_CAST_TO_ANY")
inline fun <reified T : Any> Bundle.getValue(key: String): T {
    return when (T::class) {
        Byte::class -> getByte(key)
        Char::class -> getChar(key)
        Short::class -> getShort(key)
        CharSequence::class -> getCharSequence(key)
        Bundle::class -> getBundle(key)
        Boolean::class -> getBoolean(key)
        String::class -> getString(key)
        Int::class -> getInt(key)
        Float::class -> getFloat(key)
        Long::class -> getLong(key)
        Parcelable::class -> getParcelable(key)
        else -> throw IllegalArgumentException("value type is not supported")
    } as T
}

inline fun <reified T> Bundle.getOrNull(key: String): T? {
    if (containsKey(key)) {
        return getValue(key)
    }
    return null
}
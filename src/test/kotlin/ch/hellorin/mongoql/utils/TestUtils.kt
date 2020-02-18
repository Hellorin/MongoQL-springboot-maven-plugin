package ch.hellorin.mongoql.utils

internal fun cleanStringForAssertion(input : String) : String = input
        .replace("\n", "")
        .replace("\r", "")
        .replace("\t", "")
        .replace(" ", "")
        .trim()
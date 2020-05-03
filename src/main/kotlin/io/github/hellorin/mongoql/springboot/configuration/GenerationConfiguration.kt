package io.github.hellorin.mongoql.springboot.configuration

import java.lang.UnsupportedOperationException

class GenerationConfiguration private constructor(val language: Language) {

    data class Builder constructor(var language: Language= KotlinLanguage) {

        fun language(language: String) = apply { this.language = Language.valueOf(language) }

        fun build() = GenerationConfiguration(
                language = language);
    }
}

sealed class Language {
    companion object {
        fun valueOf(language: String): Language {
            return when (language.toLowerCase().trim()) {
                "java" -> JavaLanguage
                "kotlin" -> KotlinLanguage
                else -> throw UnsupportedOperationException()
            }
        }
    }

    abstract fun name() : String

    open fun folderName() : String = name()

    abstract fun extension() : String
}

object JavaLanguage : Language() {
    override fun name() = "java"

    override fun extension() = "java"
}

object KotlinLanguage : Language() {
    override fun name() = "kotlin"

    override fun extension() = "kt"
}


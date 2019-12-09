package ch.hellorin.mongoql.springboot.templating.freemarker.configuration

import freemarker.template.Configuration
import freemarker.template.TemplateExceptionHandler
import freemarker.template.Version
import java.util.*


object FreemarkerConfiguration {
    var cfg = Configuration(Version("2.3.23"))

    init {
        cfg.setClassForTemplateLoading(FreemarkerConfiguration::class.java, "/")

        // Some other recommended settings:
        cfg.incompatibleImprovements = Version(2, 3, 20)
        cfg.defaultEncoding = "UTF-8"
        cfg.locale = Locale.ENGLISH

        cfg.templateExceptionHandler = TemplateExceptionHandler.RETHROW_HANDLER
    }

    fun getTemplate(template: String) = cfg.getTemplate(template)

}
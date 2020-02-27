package io.github.hellorin.mongoql.springboot

import org.apache.maven.plugin.AbstractMojo
import org.apache.maven.plugins.annotations.Mojo
import java.io.File

@Mojo(name = "cleanMongoqlClasses")
class SpringBootClassesCleaner : AbstractMojo() {
    override fun execute() {
        log.info("Cleaning all folders")
        File("./generated-sources/src/main/kotlin").deleteRecursively()
    }

}
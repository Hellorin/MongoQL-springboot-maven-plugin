package ch.hellorin.mongoql.springboot.templating

import com.hellorin.mongoql.Attribute
import com.hellorin.mongoql.Type
import org.junit.Assert.*
import org.junit.Test
import java.io.File

internal class TypesSDLGeneratorTest() {
    @Test
    fun `test`() {
        // Given
        val attributes1 = listOf<Attribute>(
                Attribute("name", setOf("String"), false),
                Attribute("age", setOf("Int"), true)
        )
        val type1 = Type("Person", attributes1)

        val types = listOf(
                type1
        )

        // When
        TypesSDLGenerator().generate(types)

        // Then
        File("./generated-ressources/graphql/")
    }
}
package ch.hellorin.mongoql.springboot

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection= "myCollection")
data class Person (
    @Id
    val _id : String,
        val _id : ID
        val name : String
        val age : Int?
        val child : Child?
) {
    constructor() : this(
        "",
        "",
        null,
        null)
}

data class Child (
) {
    constructor(): this(
    )
}

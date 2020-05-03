package ${packageName}

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection= "${collectionName}")
data class ${rootType} (
    @Id
    val _id : String,
    <#list attributes as attribute>
        <#if attribute.name != "_id">
    val ${attribute.name} : ${attribute.types[0]}<#if attribute.nullable>?</#if><#if attribute?index != attributes?size-1>,</#if>
        </#if>
    </#list>
) {
    constructor() : this(
        "",
        <#list attributes as attribute>
        <#if attribute.name != "_id">
        <#if attribute.nullable>
        null<#if attribute?index != attributes?size-1>,</#if>
        <#elseif attribute.types[0] == "String">
        ""<#if attribute?index != attributes?size-1>,</#if>
        <#elseif attribute.types[0] == "Int">
        0<#if attribute?index != attributes?size-1>,</#if>
        </#if>
        </#if>
        </#list>
    )
}

<#list types as type>
data class ${type.typeName?cap_first} (
    <#list type.attributes as attribute>
    <#if attribute.name != "_id">
    val ${attribute.name} : ${attribute.types[0]}<#if attribute.nullable>?</#if><#if attribute?index != type.attributes?size-1>,</#if>
    </#if>
    </#list>
) {
    constructor(): this(
    <#list type.attributes as attribute>
    <#if attribute.name != "_id">
    <#if attribute.nullable>
    null<#if attribute?index != type.attributes?size-1>,</#if>
    <#elseif attribute.types[0] == "String">
    ""<#if attribute?index != type.attributes?size-1>,</#if>
    <#elseif attribute.types[0] == "Int">
    0<#if attribute?index != type.attributes?size-1>,</#if>
    </#if>
    </#if>
    </#list>
    )
}
</#list>
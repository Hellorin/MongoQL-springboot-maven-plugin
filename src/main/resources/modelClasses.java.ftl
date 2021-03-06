package ${packageName};

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

public class Models {
}

@Document(collection= "${collectionName}")
class ${rootType} {
    @Id
    private String _id;

    <#list attributes as attribute>
        <#if attribute.name != "_id">
            <#if attribute.types[0] == "Int">
    private Integer ${attribute.name};
            <#else>
    private ${attribute.types[0]} ${attribute.name};
            </#if>
        </#if>
    </#list>

    public ${rootType}(String _id,
        <#list attributes as attribute>
            <#if attribute.name != "_id">
                <#if attribute.types[0] == "Int">
        Integer ${attribute.name}<#if attribute?index != attributes?size-1>,</#if>
                <#else>
        ${attribute.types[0]} ${attribute.name}<#if attribute?index != attributes?size-1>,</#if>
                </#if>
            </#if>
        </#list>) {
        this._id = _id;
        <#list attributes as attribute>
            <#if attribute.name != "_id">
        this.${attribute.name} = ${attribute.name};
            </#if>
        </#list>
    }

    public ${rootType}() { this(
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
        );
    }

    public String get_Id() {
        return this._id;
    }

    public void set_Id(String _id) {
        this._id = _id;
    }

    <#list attributes as attribute>
        <#if attribute.name != "_id">
            <#if attribute.types[0] == "Int">
    public Integer get${attribute.name}() {
        return this.${attribute.name};
    }
            <#else>
    public ${attribute.types[0]} get${attribute.name?cap_first}() {
        return this.${attribute.name};
    }
            </#if>

            <#if attribute.types[0] == "Int">
    public void set${attribute.name?cap_first}(Integer ${attribute.name?cap_first}) {
        this.${attribute.name} = ${attribute.name};
    }
            <#else>
    public void set${attribute.name?cap_first}(${attribute.types[0]} ${attribute.name}) {
        this.${attribute.name} = ${attribute.name};
    }
            </#if>
        </#if>
    </#list>
}

<#list types as type>
class ${type.typeName?cap_first} {
    <#list type.attributes as attribute>
        <#if attribute.name != "_id">
            <#if attribute.types[0] == "Int">
    private Integer ${attribute.name};
            <#else>
    private ${attribute.types[0]} ${attribute.name};
            </#if>
        </#if>
    </#list>

    public ${type.typeName?cap_first}(
    <#list type.attributes as attribute>
        <#if attribute.name != "_id">
            <#if attribute.types[0] == "Int">
                Integer ${attribute.name}<#if attribute?index != type.attributes?size-1>,</#if>
            <#else>
                ${attribute.types[0]} ${attribute.name}<#if attribute?index != type.attributes?size-1>,</#if>
            </#if>
        </#if>
    </#list>
    ) {
    <#list type.attributes as attribute>
        <#if attribute.name != "_id">
            this.${attribute.name} = ${attribute.name};
        </#if>
    </#list>
    }

    public ${type.typeName?cap_first} () {
        this(
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
        );
    }

    <#list type.attributes as attribute>
        <#if attribute.name != "_id">
            <#if attribute.types[0] == "Int">
    public Integer get${attribute.name?cap_first}(Integer ${attribute.name?cap_first}) {
        return this.${attribute.name};
    }
            <#else>
    public ${attribute.types[0]} get${attribute.name?cap_first}() {
        return this.${attribute.name};
    }
            </#if>

            <#if attribute.types[0] == "Int">
    public void set${attribute.name?cap_first}(Integer ${attribute.name}) {
        this.${attribute.name} = ${attribute.name};
    }
            <#else>
    public void set${attribute.name?cap_first}(${attribute.types[0]} ${attribute.name}) {
        this.${attribute.name} = ${attribute.name};
    }
            </#if>
        </#if>
    </#list>
}
</#list>
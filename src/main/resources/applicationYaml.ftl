server:
    address: 0.0.0.0
    port: ${r"${PORT}"}

spring:
    data:
        mongodb:
            database: ${databaseName}
            <#if authenticationDatabaseConfig??>
            ${authenticationDatabaseConfig}
            </#if>
            <#if authenticationMechanismConfig??>
            ${authenticationMechanismConfig}
            </#if>
            <#if useURI!false == true>
            uri: mongodb+srv://${username}:${password}@${clusterHost}/${databaseName}?retryWrites=true&w=majority
            <#else>
            <#if singleHost??>
            ${singleHost}
            </#if>
            <#if port??>
            ${portConfig}
            </#if>
            </#if>

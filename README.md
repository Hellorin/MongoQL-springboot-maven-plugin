# MongoQL-springboot-maven-plugin
[![Build Status](https://travis-ci.com/Hellorin/MongoQL-springboot-maven-plugin.svg?branch=master)](https://travis-ci.com/Hellorin/MongoQL-springboot-maven-plugin)
[![codecov](https://codecov.io/gh/Hellorin/MongoQL-springboot-maven-plugin/branch/master/graph/badge.svg)](https://codecov.io/gh/Hellorin/MongoQL-springboot-maven-plugin)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=Hellorin_MongoQL-springboot-maven-plugin&metric=alert_status)](https://sonarcloud.io/dashboard?id=Hellorin_MongoQL-springboot-maven-plugin)

## Purpose
In a nutshell, the purpose of this tool is to generate at *compile-time* the nut and bolt to easily explore a MongoDB database/collection.

## How to use
Since this maven plugin uses **MongoQL-core**, please refer to [its readme](https://github.com/Hellorin/MongoQL-core) for the initial setup of MongoDB data.

The mojo holds almost all required dependencies needed to run SpringBoot and GraphQL. Therefore it must be declared as dependencies:
```xml
<!-- Used to gather all dependencies required to run springboot and graphql -->
<dependency>
        <groupId>io.github.hellorin.mongoql</groupId>
        <artifactId>mongoql-springboot-maven-plugin</artifactId>
        <version>${mongoql-springboot-maven-plugin.version}</version>
        <classifier>jar-with-dependencies</classifier>
</dependency>
```
As for the setup of this plugin, it can be used like that:
```xml
<!-- 1. Generate MongoQL sources-->
<plugin>
    <groupId>io.github.hellorin.mongoql</groupId>
    <artifactId>mongoql-springboot-maven-plugin</artifactId>
    <version>${mongoql-springboot-maven-plugin.version}</version>
    <configuration>
        <packageName>io.github.hellorin.graphqlmongoexplorer</packageName>
        <databaseName>myDatabase</databaseName>
        <collectionName>myCollection</collectionName>
        <rootTypeName>Person</rootTypeName>
        <username>user</username>
        <password>${mongodbUserPwd}</password>
        <host>mongoql-shard-00-01-sp4v4.mongodb.net</host>
        <useURI>true</useURI>
        <clusterHost>mongoql-sp4v4.mongodb.net</clusterHost>
        <port>27017</port>
        <isUsingTLS>true</isUsingTLS>
        <authenticationDatabase>admin</authenticationDatabase>
        <authenticationMechanism>SCRAM-SHA-1</authenticationMechanism>

        <!-- only required for Travis-ci to avoid failure -->
        <skip>${generation-skipped}</skip>
    </configuration>
    <executions>
        <execution>
            <id>generateMongoQLClasses</id>
            <phase>generate-sources</phase>
            <goals>
                <goal>generateMongoqlClasses</goal>
            </goals>
        </execution>
    </executions>
 </plugin>

<!-- 2. Copy MongoQL generated sources -->
<plugin>
    <groupId>org.codehaus.mojo</groupId>
    <artifactId>build-helper-maven-plugin</artifactId>
    <version>3.0.0</version>
    <executions>
        <execution>
            <phase>generate-sources</phase>
            <goals>
                <goal>add-source</goal>
            </goals>
            <configuration>
                <sources>
                    <source>target/generated-sources/src/main/kotlin</source>
                </sources>
            </configuration>
        </execution>
    </executions>
</plugin>

<!-- 3. Copy MongoQL generated resources -->
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-resources-plugin</artifactId>
    <version>3.0.2</version>
    <executions>
        <execution>
            <id>copy-resources-post-compile</id>
            <phase>process-resources</phase>
            <goals>
                <goal>copy-resources</goal>
            </goals>
            <configuration>
                <outputDirectory>${project.build.outputDirectory}</outputDirectory>
                <resources>
                    <resource>
                        <directory>${project.basedir}/target/generated-resources</directory>
                    </resource>
                </resources>
            </configuration>
        </execution>
    </executions>
</plugin>
```

Please note that you can refer to [my MongoQL kotlin explorer project](https://github.com/Hellorin/MongoQL-kotlin-explorer) for a simple overview on how to use this plugin.

## Technologies
- Kotlin
- Mockito
- JUnit
- Apache Freemarker

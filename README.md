# MongoQL-springboot-maven-plugin

## Purpose
In a nutshell, the purpose of this tool is to generate at *compile-time* the nut and bolt to easily explore a MongoDB database/collection.

## How to use
Since this maven plugin uses **MongoQL-core**, please refer to [its readme](https://github.com/Hellorin/MongoQL-core) for the initial setup of MongoDB data.

As for the setup of this plugin, it can be used like that:
```xml
<plugin>
    <groupId>ch.hellorin.mongoql</groupId>
    <artifactId>mongoql-springboot-maven-plugin</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <configuration>
        <packageName>yourBasePackageForClassesGeneration</packageName>
        <databaseName>yourMongoDBDatabaseName</databaseName>
        <collectionName>yourMongoDBCollectionName</collectionName>
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
<!-- Required to copy resources after generation -->
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
                        <directory>${project.basedir}/generated-resources</directory>
                    </resource>
                </resources>
            </configuration>
        </execution>
    </executions>
</plugin>
```

## Technologies
- Kotlin
- Mockito
- JUnit
- Apache Freemarker

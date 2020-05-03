package ${packageName};

import ${packageName}.MongoPersonDao;
import ${packageName}.QueryResolver;
import com.coxautodev.graphql.tools.SchemaParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@org.springframework.context.annotation.Configuration
public class GraphQLConfiguration {
    @Bean
    public SchemaParser schema(MongoPersonDao dao) {
        return SchemaParser.newParser()
            .files(
                "graphql/query.graphqls",
                "graphql/types.graphqls"
            ).resolvers(new QueryResolver(dao)).build();
    }
}
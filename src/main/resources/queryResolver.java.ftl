package ${packageName};

import java.util.List;
import org.springframework.stereotype.Component;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;

@Component
class QueryResolver implements GraphQLQueryResolver {
    private final Mongo${rootType}Dao dao;

    public QueryResolver(Mongo${rootType}Dao dao) {
        this.dao = dao;
    }

    public List<${rootType}> all${rootType}() {
        return dao.findAll();
    }
}
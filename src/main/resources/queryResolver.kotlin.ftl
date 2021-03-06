package ${packageName}

import org.springframework.stereotype.Component
import com.coxautodev.graphql.tools.GraphQLQueryResolver

@Component
class QueryResolver(private val dao : Mongo${rootType}Dao) : GraphQLQueryResolver {
    fun all${rootType}() :List<${rootType}> = dao.findAll()
}
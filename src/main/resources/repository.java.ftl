package ${packageName};

import ${packageName}.${rootType};
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
@Primary
interface Mongo${rootType}Dao extends MongoRepository<${rootType}, String> {
}
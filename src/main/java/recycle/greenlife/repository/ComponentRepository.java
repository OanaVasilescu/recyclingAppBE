package recycle.greenlife.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import recycle.greenlife.model.Component;

import java.util.UUID;

@Repository
public interface ComponentRepository extends MongoRepository<Component, UUID> {//TODO: is it string?
}

package recycle.greenlife.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import recycle.greenlife.model.Tip;

import java.util.UUID;

@Repository
public interface TipRepository extends MongoRepository<Tip, UUID> {
}

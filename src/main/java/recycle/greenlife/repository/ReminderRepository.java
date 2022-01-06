package recycle.greenlife.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import recycle.greenlife.model.Reminder;

import java.util.UUID;

@Repository
public interface ReminderRepository extends MongoRepository<Reminder, UUID> {
}

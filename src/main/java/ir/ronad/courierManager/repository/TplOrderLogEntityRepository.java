package ir.ronad.courierManager.repository;

import ir.ronad.courierManager.domain.TplOrderLogEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TplOrderLogEntityRepository extends MongoRepository<TplOrderLogEntity, String> {
}

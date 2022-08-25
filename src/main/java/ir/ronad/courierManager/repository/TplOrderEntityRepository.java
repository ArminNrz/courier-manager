package ir.ronad.courierManager.repository;

import ir.ronad.courierManager.domain.TplOrderEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TplOrderEntityRepository extends MongoRepository<TplOrderEntity, String> {
    Optional<TplOrderEntity> findTplOrderEntitiesById(String id);
}

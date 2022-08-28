package ir.ronad.courierManager.repository;

import ir.ronad.courierManager.domain.TplOrderEntity;
import ir.ronad.courierManager.domain.TplOrderSchedulerEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TplOrderSchedulerEntityRepository extends MongoRepository<TplOrderSchedulerEntity, String> {
    Optional<TplOrderSchedulerEntity> findByTplOrder(TplOrderEntity tplOrderEntity);
}

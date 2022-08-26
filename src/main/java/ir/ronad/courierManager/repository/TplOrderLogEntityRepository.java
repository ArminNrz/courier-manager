package ir.ronad.courierManager.repository;

import ir.ronad.courierManager.domain.TplOrderLogEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TplOrderLogEntityRepository extends MongoRepository<TplOrderLogEntity, String> {
    List<TplOrderLogEntity> findAllByTplOrderId(String tplOrderId);
}

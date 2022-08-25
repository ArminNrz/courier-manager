package ir.ronad.courierManager.service.entity;

import ir.ronad.courierManager.domain.TplOrderEntity;
import ir.ronad.courierManager.dto.tplOrder.TplOrderQueryDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class TplOrderQueryHandler {

    private final MongoTemplate mongoTemplate;

    public List<TplOrderEntity> getTplOrderEntities(TplOrderQueryDTO queryDTO, Pageable pageable) {
        log.trace("Enter: TplOrderQueryHandler.getTplOrderEntities(queryDTO: {}, pageable: {})", queryDTO, pageable);
        Query query = new Query();
        Criteria criteria = new Criteria();

        if (queryDTO.getId() != null) {
            query.addCriteria(criteria.and("id").is(queryDTO.getId()));
        }
        if (queryDTO.getCreated() != null) {
            query.addCriteria(criteria.and("created").gte(queryDTO.getCreated()));
        }

        if (queryDTO.getTplCode() != null) {
            query.addCriteria(criteria.and("tplCode").is(queryDTO.getTplCode()));
        } else if (queryDTO.getTplCodes() != null && queryDTO.getTplCodes().size() != 0) {
            query.addCriteria(criteria.and("tplCode").in(queryDTO.getTplCodes()));
        }

        if (queryDTO.getOrderId() != null) {
            query.addCriteria(criteria.and("orderId").is(queryDTO.getOrderId()));
        } else if (queryDTO.getOrderIds() != null && queryDTO.getOrderIds().size() != 0) {
            query.addCriteria(criteria.and("orderId").in(queryDTO.getOrderIds()));
        }

        if (queryDTO.getStatus() != null) {
            query.addCriteria(criteria.and("status").is(queryDTO.getStatus()));
        } else if (queryDTO.getStatuses() != null && queryDTO.getStatuses().size() != 0) {
            query.addCriteria(criteria.and("status").in(queryDTO.getStatuses()));
        }

        if (queryDTO.getTplTrackingCode() != null) {
            query.addCriteria(criteria.and("tplTrackingCode").is(queryDTO.getTplTrackingCode()));
        }

        query.with(pageable);
        List<TplOrderEntity> entities = mongoTemplate.find(query, TplOrderEntity.class);
        log.debug("Found tplOrderEntities: {}", entities);
        return entities;
    }
}

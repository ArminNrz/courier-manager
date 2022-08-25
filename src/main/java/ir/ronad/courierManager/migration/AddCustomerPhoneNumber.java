package ir.ronad.courierManager.migration;

import com.mongodb.client.result.UpdateResult;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

@ChangeUnit(order = "1", id = "addCustomerPhoneNumber", author = "Armin")
@RequiredArgsConstructor
@Slf4j
public class AddCustomerPhoneNumber {

    private final MongoTemplate mongoTemplate;

    @Execution
    public void changeSet() {
        Query query = new Query();
        query.addCriteria(Criteria.where("customerMobileNumber").exists(false));
        Update update = new Update();
        update.set("customerMobileNumber", "09121111234");
        UpdateResult updateResult = mongoTemplate.updateMulti(query, update, "tpl_order");
        log.info("Matched {} | Modified {}",
                updateResult.getMatchedCount(), updateResult.getModifiedCount());
    }

    @RollbackExecution
    public void rollback() {
    }
}

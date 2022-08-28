package ir.ronad.courierManager.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("tpl_order_scheduler")
@Data
@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor
public class TplOrderSchedulerEntity extends BaseDomainEntity {

    private static final long serialVersionUID = -1L;

    @Id
    private String id;

    @DBRef
    @Indexed(unique = true)
    private TplOrderEntity tplOrder;
}

package ir.ronad.courierManager.domain;

import ir.ronad.courierManager.domain.enumartion.TplOrderStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("tpl_order_log")
@Data
@EqualsAndHashCode(callSuper = true)
public class TplOrderLogEntity extends BaseDomainEntity {

    private static final long serialVersionUID = -1L;

    @Id
    private String id;

    private String tplOrderId;

    private TplOrderStatus lastStatus;

    private TplOrderStatus newStatus;

    @Field("description")
    private String description;

    private Long orderId;

    private String tplOrderUUID;
}

package ir.ronad.courierManager.dto.commonEvent.event.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProductEvent extends BaseEntityEvent {

    public ProductEvent() {
        setEntity(Entity.PRODUCT);
    }

    private String code;
}

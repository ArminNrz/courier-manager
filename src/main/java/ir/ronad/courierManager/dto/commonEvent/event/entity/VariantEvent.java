package ir.ronad.courierManager.dto.commonEvent.event.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class VariantEvent extends BaseEntityEvent {

    public VariantEvent() {
        setEntity(Entity.VARIANT);
    }

    private String productId;
    private String productCode;
    private String code;
}

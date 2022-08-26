package ir.ronad.courierManager.dto.commonEvent.event.entity;

import ir.ronad.courierManager.dto.commonEvent.status.InventoryItemStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class InventoryItemEvent extends BaseStatusEntityEvent<InventoryItemStatus> {

    public InventoryItemEvent() {
        setEntity(Entity.INVENTORY_ITEM);
    }

    private Long variantId;

    private Double amount;
}

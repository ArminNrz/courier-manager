package ir.ronad.courierManager.dto.commonEvent.event.entity;

import ir.ronad.courierManager.dto.commonEvent.event.EventAction;
import ir.ronad.courierManager.dto.commonEvent.status.ParcelItemStatus;
import ir.ronad.courierManager.dto.commonEvent.type.ParcelType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ParcelItemEvent extends BaseStatusEntityEvent<ParcelItemStatus> {

    public ParcelItemEvent() {
        setEntity(Entity.PARCEL_ITEM);
    }

    private Long parcelId;
    private Long variantId;

    private Double amount;
    private ParcelType parcelType;

    public ParcelItemEvent(Long variantId, Double amount, Long warehouseId, EventAction eventAction, Long parcelId, ParcelType parcelType) {

        this.variantId = variantId;
        this.amount = amount;
        this.parcelId = parcelId;
        this.parcelType = parcelType;
        this.setWarehouseId(warehouseId);
        this.setEventAction(eventAction);
    }
}

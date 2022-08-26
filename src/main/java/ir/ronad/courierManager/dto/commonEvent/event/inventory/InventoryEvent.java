package ir.ronad.courierManager.dto.commonEvent.event.inventory;

import ir.ronad.courierManager.dto.commonEvent.event.BaseEvent;
import ir.ronad.courierManager.dto.commonEvent.type.OrderType;
import ir.ronad.courierManager.dto.commonEvent.type.ParcelType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class InventoryEvent extends BaseEvent {

    private InventoryEventType type;

    private Long variantId;
    private String variantCode;
    private String productCode;

    private Double locatingAmount;
    private Double locatingDiff;

    private Double locatedAmount;
    private Double locatedDiff;

    private Double supplyingAmount;
    private Double supplyingDiff;

    private Double suppliableAmount;
    private Double suppliableDiff;

    private Double assignedAmount;
    private Double assignedDiff;

    private Double orderableAmount;
    private Double orderableDiff;

    private Double availableAmount;
    private Double availableDiff;

    private Double reservedAmount;
    private Double reservedDiff;

    private Double minimumAmount;
    private Double minimumDiff;

    private Double wastageAmount;
    private Double wastageDiff;

    private Boolean isValid;

    // parcelInfo
    private Long parcelId;
    private ParcelType parcelType;

    // orderInfo
    private Long orderId;
    private OrderType orderType;
    private String orderTrackingCode;

}

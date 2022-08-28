package ir.ronad.courierManager.dto.commonEvent.status;

public enum OrderItemStatus {
    NOT_DELIVERED,
    // outbound
    PICKED,
    PACKAGED,
    // delivery
    DELIVERED,
    // canceling
    CANCELLED,
    LOCATED
}

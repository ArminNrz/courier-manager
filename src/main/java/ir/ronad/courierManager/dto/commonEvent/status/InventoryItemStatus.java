package ir.ronad.courierManager.dto.commonEvent.status;

public enum InventoryItemStatus {
    NOT_REGISTERED,
    DELETED_BY_OPERATOR,
    // inbound
    REGISTERED,
    LOCATED,
    UNLOADED,
    // outbound
    PICKED,
    CHECKED,
    WASTED
}

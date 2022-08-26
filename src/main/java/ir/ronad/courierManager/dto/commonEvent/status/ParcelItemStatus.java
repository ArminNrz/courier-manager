package ir.ronad.courierManager.dto.commonEvent.status;

public enum ParcelItemStatus {
    NOT_REGISTERED,
    // inbound
    UNLOADED, CONFLICTED,
    REGISTERED, LOCATED,
    //outbound
    PICKED, PACKAGED,
    // canceling
    CANCELLED
}

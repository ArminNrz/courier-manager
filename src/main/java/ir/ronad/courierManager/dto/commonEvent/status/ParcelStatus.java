package ir.ronad.courierManager.dto.commonEvent.status;

/**
 * The ParcelStatus enumeration.
 */
public enum ParcelStatus {
    SUBMITTED,
    // delivery
    SHIPPED, DELIVERED,
    // inbound
    UNLOADING,
    ASSIGNED, UNLOADED, REGISTERED, LOCATED,
    CONFLICTED, RETURNING,
    // canceling
    CANCELLED,
    PICKED, PACKAGED
}

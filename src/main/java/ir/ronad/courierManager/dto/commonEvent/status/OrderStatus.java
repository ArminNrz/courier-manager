package ir.ronad.courierManager.dto.commonEvent.status;

/**
 * The OrderStatus enumeration.
 */
public enum OrderStatus {
    ORDERED,
    // outbound
    ASSIGNED, PICKED, PACKAGED,
    PROCESSING,
    PROCESSED,
    // fleet
    BUNDLED, LOADED, ARRIVED,
    // courier
    DELIVERED_TO_COURIER,
    // post
    DELIVERED_TO_POST,
    DELIVERING_TO_POST,
    // 3pl
    REGISTERED_IN_3PL,
    REGISTERING_IN_3PL,
    DELIVERING,
    DELIVERED_TO_3PL,
    DELIVERED_TO_3PL_COURIER,
    CANCELED_FROM_3PL,
    AWAITING_REVIEW,
    // distribution center
    DELIVERED_TO_CENTER, SUPPLIED_IN_CENTER,
    // customer
    DELIVERED, NOT_DELIVERED,
    // canceling
    CANCELLED, REQUESTED_TO_RETURN, RETURNED_TO_WAREHOUSE, REQUESTED_TO_RESEND, RETURNED,
    INVENTORY_ITEM_ASSIGNED,
    SUPPLYING,
    //exchange
    SCANNED
}

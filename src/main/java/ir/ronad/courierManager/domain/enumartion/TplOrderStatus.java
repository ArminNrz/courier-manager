package ir.ronad.courierManager.domain.enumartion;

public enum TplOrderStatus {
    NOT_REGISTERED,

    REGISTERED_IN_3PL,

    REJECTED_BY_3PL,

    SEARCHING_COURIER,
    SEARCHING_COURIER_EXPIRED,
    COURIER_ACCEPTED,

    DELIVERED_TO_3PL,
    DELIVERED_TO_3PL_COURIER,
    DELIVERED_TO_POST,

    NOT_DELIVERED,
    DELIVERED,

    REQUESTED_TO_CANCEL,
    CANCELLED,

    NONE
}

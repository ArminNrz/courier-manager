package ir.ronad.courierManager.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorKey {

    public interface common {
        String INTERNAL_ERROR = "internal.exception";
    }
    public interface tplOrderValidation {
        String TPL_ORDER_NOT_FOUND = "tplOrder.notFound";
        String TPL_ORDER_HAS_NOT_REGISTERED = "tplOrder.id.hasNotRegistered";
    }
    public interface courierManager {
        String COURIER_HAS_NOT_IMPL = "Courier.notImpl";
    }
    public interface linkExpress {
        String LINK_EXPRESS_SERVICE_VALIDATION_ERROR = "courier.linkExpress.internalValidation.error";
        String LINK_EXPRESS_SERVICE_AUTH_ERROR = "courier.linkExpress.auth.error";
        String LINK_EXPRESS_SERVICE_ERROR = "courier.linkExpress.unknown.error";
    }
}

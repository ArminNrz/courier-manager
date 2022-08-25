package ir.ronad.courierManager.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorMessages {

    public interface common {
        String INTERNAL_ERROR = "courier service has internal error";
    }
    public interface tplOrderValidation {
        String TPL_ORDER_ORDER_ID_NOT_EMPTY = "order id not allowed to be empty";
        String TPL_ORDER_TPL_CODE_NOT_EMPTY = "tpl code not allowed to be empty";
        String TPL_ORDER_NOT_FOUND = "tpl order not found";
    }
    public interface courierManager {
        String COURIER_HAS_NOT_IMPL = "Courier has not implemented yet!";
    }
    public interface linkExpress {
        String LINK_EXPRESS_SERVICE_AUTH_ERROR = "link express service auth exception";
        String LINK_EXPRESS_SERVICE_ERROR = "link express send unknown error";
    }
}

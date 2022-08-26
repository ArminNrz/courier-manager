package ir.ronad.courierManager.service.higlevel.handler;

import ir.ronad.courierManager.common.ErrorKey;
import ir.ronad.courierManager.common.ErrorMessages;
import ir.ronad.courierManager.common.errors.BadRequestAlertException;
import ir.ronad.courierManager.domain.TplOrderEntity;
import ir.ronad.courierManager.domain.enumartion.TplOrderStatus;
import ir.ronad.courierManager.service.data.DeliveryResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DeliveryServiceValidationHandler {

    private static final String ENTITY = "tplOrder";

    public boolean isTplOrderRegisterInCourier(TplOrderEntity tplOrder) {
        if (tplOrder.getTplTrackingCode() == null) {
            log.error("TplOrder with id: {}, is not register in courier: {}", tplOrder.getId(), tplOrder.getTplCode());
            throw new BadRequestAlertException(ErrorMessages.tplOrderValidation.TPL_ORDER_HAS_NOT_REGISTERED, ENTITY, ErrorKey.tplOrderValidation.TPL_ORDER_HAS_NOT_REGISTERED);
        } else {
            return true;
        }
    }

    public boolean isTplOrderStatusChange(DeliveryResponse deliveryResponse) {
        log.trace("Enter: DeliveryServiceValidationHandler.isTplOrderStatusChange({})", deliveryResponse);
        TplOrderEntity tplOrder = deliveryResponse.getTplOrderEntity();

        if (deliveryResponse.getStatus() == TplOrderStatus.NONE) {
            log.warn("Unknown status coming from courier: {}, tplOrderId: {}, Status: {}", tplOrder.getTplCode(), tplOrder.getId(), tplOrder.getStatus());
            return false;
        }

        if (tplOrder.getStatus() == deliveryResponse.getStatus()) {
            log.debug("tplOrderId: {}, has not any change in status: {}, after fetching from courier: {}", tplOrder.getId(), tplOrder.getStatus(), tplOrder.getTplCode());
            return false;
        } else {
            log.debug("tplOrderId: {}, has change in status from: {}, to: {}, after fetch from courier: {}", tplOrder.getId(), tplOrder.getStatus(), deliveryResponse.getStatus(), tplOrder.getTplCode());
            return true;
        }
    }
}

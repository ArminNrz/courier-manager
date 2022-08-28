package ir.ronad.courierManager.service.higlevel.delivery;

import ir.ronad.courierManager.common.ErrorKey;
import ir.ronad.courierManager.common.ErrorMessages;
import ir.ronad.courierManager.common.errors.NotFoundException;
import ir.ronad.courierManager.domain.TplOrderEntity;
import ir.ronad.courierManager.dto.tplOrder.TplOrderLimitDTO;
import ir.ronad.courierManager.mapper.TplOrderMapper;
import ir.ronad.courierManager.service.data.DeliveryResponse;
import ir.ronad.courierManager.service.entity.tplOrder.TplOrderService;
import ir.ronad.courierManager.service.higlevel.delivery.handler.DeliveryServiceCommonHandler;
import ir.ronad.courierManager.service.higlevel.delivery.handler.DeliveryServiceSchedulerHandler;
import ir.ronad.courierManager.service.higlevel.delivery.handler.DeliveryServiceValidationHandler;
import ir.ronad.courierManager.service.higlevel.delivery.manager.CourierManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {

    private final TplOrderService tplOrderService;
    private final CourierManager courierManager;
    private final TplOrderMapper tplOrderMapper;
    private final DeliveryServiceCommonHandler commonHandler;
    private final DeliveryServiceValidationHandler validationHandler;
    private final DeliveryServiceSchedulerHandler schedulerHandler;

    private static final String ENTITY = "tplOrder";

    @Override
    public TplOrderLimitDTO createOrder(String tplOrderId) {
        log.trace("Enter: deliveryService.createOrder({})", tplOrderId);
        return tplOrderService.getById(tplOrderId)
                .stream()
                .map(courierManager::createOrder)
                .map(commonHandler::updateByDeliveryResponse)
                .peek(schedulerHandler::saveScheduler)
                .map(deliveryResponse -> tplOrderMapper.toLimitDTO(deliveryResponse.getTplOrderEntity()))
                .peek(tplOrderLimitDTO -> log.debug("create Order for tplOrderId: {}, limitDTO: {}", tplOrderId, tplOrderLimitDTO))
                .findAny()
                .orElseThrow(() -> new NotFoundException(ErrorMessages.tplOrderValidation.TPL_ORDER_NOT_FOUND, ENTITY, ErrorKey.tplOrderValidation.TPL_ORDER_NOT_FOUND));
    }

    @Override
    public TplOrderLimitDTO getOrder(String tplOrderId) {
        log.trace("Enter: deliveryService.getOrder({})", tplOrderId);
        return tplOrderService.getById(tplOrderId)
                .stream()
                .peek(tplOrder -> log.debug("tplOrder with id: {}, tplTrackingCode: {}", tplOrder.getId(), tplOrder.getTplTrackingCode()))
                .filter(validationHandler::isTplOrderRegisterInCourier)
                .map(this::getOrder)
                .map(deliveryResponse -> tplOrderMapper.toLimitDTO(deliveryResponse.getTplOrderEntity()))
                .peek(tplOrderLimitDTO -> log.debug("create Order for tplOrderId: {}, limitDTO: {}", tplOrderId, tplOrderLimitDTO))
                .findAny()
                .orElseThrow(() -> new NotFoundException(ErrorMessages.tplOrderValidation.TPL_ORDER_NOT_FOUND, ENTITY, ErrorKey.tplOrderValidation.TPL_ORDER_NOT_FOUND));
    }

    @Override
    public DeliveryResponse getOrder(TplOrderEntity tplOrderEntity) {
        DeliveryResponse deliveryResponse = courierManager.getOrder(tplOrderEntity);
        commonHandler.checkAndUpdateByDeliveryResponse(deliveryResponse);
        schedulerHandler.deleteScheduler(deliveryResponse);
        return deliveryResponse;
    }

    @Override
    public void getOrderPrice(String tplOrderId) {

    }
}

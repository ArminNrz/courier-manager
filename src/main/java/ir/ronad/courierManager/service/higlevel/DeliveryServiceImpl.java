package ir.ronad.courierManager.service.higlevel;

import ir.ronad.courierManager.common.ErrorKey;
import ir.ronad.courierManager.common.ErrorMessages;
import ir.ronad.courierManager.common.errors.NotFoundException;
import ir.ronad.courierManager.dto.tplOrder.TplOrderLimitDTO;
import ir.ronad.courierManager.mapper.TplOrderMapper;
import ir.ronad.courierManager.service.entity.TplOrderService;
import ir.ronad.courierManager.service.higlevel.manager.CourierManager;
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

    private static final String ENTITY = "tplOrder";

    @Override
    public TplOrderLimitDTO createOrder(String tplOrderId) {
        log.trace("Enter: deliveryService.createOrder({})", tplOrderId);
        return tplOrderService.getById(tplOrderId).stream()
                .map(courierManager::createOrder)
                .map(tplOrderService::updateStatus)
                .map(tplOrderService::updateByDeliveryResponse)
                .map(deliveryResponse -> tplOrderMapper.toLimitDTO(deliveryResponse.getTplOrderEntity()))
                .peek(tplOrderLimitDTO -> log.debug("create Order for tplOrderId: {}, limitDTO: {}", tplOrderId, tplOrderLimitDTO))
                .findAny()
                .orElseThrow(() -> new NotFoundException(ErrorMessages.tplOrderValidation.TPL_ORDER_NOT_FOUND, ENTITY, ErrorKey.tplOrderValidation.TPL_ORDER_NOT_FOUND));
    }

    @Override
    public void getOrder(String tplOrderId) {

    }

    @Override
    public void getOrderPrice(String tplOrderId) {

    }
}
package ir.ronad.courierManager.service.higlevel.delivery.manager;

import ir.ronad.courierManager.common.ErrorKey;
import ir.ronad.courierManager.common.ErrorMessages;
import ir.ronad.courierManager.common.errors.BadRequestAlertException;
import ir.ronad.courierManager.domain.TplOrderEntity;
import ir.ronad.courierManager.domain.enumartion.TplCode;
import ir.ronad.courierManager.service.data.DeliveryResponse;
import ir.ronad.courierManager.service.higlevel.delivery.manager.handler.BaseCourierHandler;
import ir.ronad.courierManager.service.higlevel.delivery.manager.handler.linkExpress.LinkExpressCourierHandler;
import ir.ronad.courierManager.service.higlevel.delivery.manager.handler.speed.SpeedCourierHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

import static ir.ronad.courierManager.domain.enumartion.TplCode.LINK_EXPRESS;
import static ir.ronad.courierManager.domain.enumartion.TplCode.SPEED;

@Service
@Slf4j
@RequiredArgsConstructor
public class CourierManager {

    private final Map<TplCode, BaseCourierHandler> courierCache = new HashMap<>();

    private final LinkExpressCourierHandler linkExpressCourierHandler;
    private final SpeedCourierHandler speedCourierHandler;

    private static final String ENTITY = "courierManager";

    @PostConstruct
    private void init() {
        registerCourierHandler();
    }

    private void registerCourierHandler() {
        courierCache.put(LINK_EXPRESS, linkExpressCourierHandler);
        courierCache.put(SPEED, speedCourierHandler);
    }

    private BaseCourierHandler getCourierHandler(TplCode tplCode) {
        log.trace("Enter: courierManager.getCourierHandler({})", tplCode);
        if (!courierCache.containsKey(tplCode)) {
            log.error("Courier: {}, has not implemented yet", tplCode);
            throw new BadRequestAlertException(ErrorMessages.courierManager.COURIER_HAS_NOT_IMPL, ENTITY, ErrorKey.courierManager.COURIER_HAS_NOT_IMPL);
        }
        return courierCache.get(tplCode);
    }

    public DeliveryResponse createOrder(TplOrderEntity tplOrder) {
        log.trace("Enter: courierManager.createOrder({})", tplOrder);
        BaseCourierHandler baseCourierHandler = this.getCourierHandler(tplOrder.getTplCode());
        return baseCourierHandler.createOrder(tplOrder);
    }

    public DeliveryResponse getOrder(TplOrderEntity tplOrder) {
        log.trace("Enter: courierManager.getOrder({})", tplOrder);
        BaseCourierHandler baseCourierHandler = this.getCourierHandler(tplOrder.getTplCode());
        return baseCourierHandler.getOrder(tplOrder);
    }
}

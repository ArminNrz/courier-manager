package ir.ronad.courierManager.service.higlevel;

import ir.ronad.courierManager.common.ErrorMessages;
import ir.ronad.courierManager.common.errors.NotFoundException;
import ir.ronad.courierManager.domain.TplOrderEntity;
import ir.ronad.courierManager.domain.TplOrderLogEntity;
import ir.ronad.courierManager.domain.TplOrderSchedulerEntity;
import ir.ronad.courierManager.domain.enumartion.NotificationType;
import ir.ronad.courierManager.domain.enumartion.TplOrderStatus;
import ir.ronad.courierManager.domain.extraInfo.LinkExpressExtraInfo;
import ir.ronad.courierManager.dto.tplOrder.TplOrderLimitDTO;
import ir.ronad.courierManager.repository.TplOrderEntityRepository;
import ir.ronad.courierManager.repository.TplOrderLogEntityRepository;
import ir.ronad.courierManager.repository.TplOrderSchedulerEntityRepository;
import ir.ronad.courierManager.service.data.DeliveryResponse;
import ir.ronad.courierManager.service.higlevel.delivery.DeliveryService;
import ir.ronad.courierManager.service.higlevel.delivery.manager.CourierManager;
import ir.ronad.courierManager.utility.TplOrderUtility;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@Slf4j
class DeliveryServiceImplTest {

    @Autowired
    TplOrderUtility tplOrderUtility;
    @Autowired
    TplOrderEntityRepository tplOrderEntityRepository;
    @Autowired
    DeliveryService deliveryService;
    @Autowired
    TplOrderLogEntityRepository logEntityRepository;
    @Autowired
    TplOrderSchedulerEntityRepository schedulerRepository;
    @MockBean
    CourierManager courierManager;

    private TplOrderEntity tplOrder;

    private final String trackingCode = "91114";
    private final String createMessage = "order item successfully registered.";

    private TplOrderLimitDTO createTplOrder(String message) {
        log.debug("start registering");
        this.tplOrder = tplOrderUtility.buildLinkExpressTplOrder();
        tplOrderEntityRepository.insert(tplOrder);
        LinkExpressExtraInfo extraInfo = LinkExpressExtraInfo.builder()
                .message(message)
                .build();

        DeliveryResponse expectedDeliveryResponse = DeliveryResponse.builder()
                .tplOrderEntity(tplOrder)
                .tplTrackingCode(trackingCode)
                .status(TplOrderStatus.REGISTERED_IN_3PL)
                .extraInfo(extraInfo)
                .notificationType(NotificationType.SCHEDULER)
                .build();
        Mockito.when(courierManager.createOrder(Mockito.any()))
                .thenReturn(expectedDeliveryResponse);

        return deliveryService.createOrder(this.tplOrder.getId());
    }

    private TplOrderLimitDTO getTplOrderLimitDTO(TplOrderStatus mockReceivedStatus) {
        log.debug("get order");
        LinkExpressExtraInfo extraInfo = LinkExpressExtraInfo.builder()
                .actualReceiverName("User-test")
                .message("Message received!")
                .build();
        DeliveryResponse deliveryResponse = DeliveryResponse.builder()
                .tplOrderEntity(this.tplOrder)
                .tplTrackingCode(trackingCode)
                .status(mockReceivedStatus)
                .extraInfo(extraInfo)
                .notificationType(NotificationType.SCHEDULER)
                .build();
        Mockito.when(courierManager.getOrder(Mockito.any()))
                .thenReturn(deliveryResponse);

        return deliveryService.getOrder(this.tplOrder.getId());
    }

    @Test
    @DisplayName("create order for link express success")
    void createOrderLinkExpress() {

        TplOrderLimitDTO actualResponse = createTplOrder(createMessage);

        Assertions.assertNotNull(actualResponse);
        Assertions.assertEquals(TplOrderStatus.REGISTERED_IN_3PL, actualResponse.getStatus());
        Assertions.assertNotNull(actualResponse.getTplTrackingCode());
        Assertions.assertEquals(trackingCode, actualResponse.getTplTrackingCode());
        Assertions.assertEquals(this.tplOrder.getTrackingCode(), actualResponse.getTrackingCode());
        LinkExpressExtraInfo linkExpressExtraInfo = (LinkExpressExtraInfo) tplOrder.getExtraInfo();
        Assertions.assertNotNull(linkExpressExtraInfo);
        Assertions.assertEquals(createMessage, linkExpressExtraInfo.getMessage());
    }

    @Test
    @DisplayName("create order for link express when tpl order not found")
    void createOrderWithError1() {
        try {
            deliveryService.createOrder(UUID.randomUUID().toString());
        } catch (NotFoundException notFoundException) {
            Assertions.assertEquals(ErrorMessages.tplOrderValidation.TPL_ORDER_NOT_FOUND, notFoundException.getMessage());
        }
    }

    @Test
    @DisplayName("register order in Link express courier and get register in 3pl status")
    void getOrder() {
        createTplOrder(createMessage);

        TplOrderLimitDTO actualResponse = getTplOrderLimitDTO(TplOrderStatus.REGISTERED_IN_3PL);

        Assertions.assertNotNull(actualResponse);
        Assertions.assertEquals(TplOrderStatus.REGISTERED_IN_3PL, actualResponse.getStatus());
        Assertions.assertNotNull(actualResponse.getTrackingCode());
        Assertions.assertNotNull(actualResponse.getTplTrackingCode());
        Assertions.assertEquals(trackingCode, actualResponse.getTplTrackingCode());

        List<TplOrderLogEntity> logEntities = logEntityRepository.findAllByTplOrderId(tplOrder.getId());
        Assertions.assertNotNull(logEntities);
        Assertions.assertEquals(1, logEntities.size());
        Assertions.assertEquals(TplOrderStatus.REGISTERED_IN_3PL, logEntities.get(0).getNewStatus());
        Assertions.assertEquals(TplOrderStatus.NOT_REGISTERED, logEntities.get(0).getLastStatus());
    }

    @Test
    @DisplayName("register order in Link express courier and get (first REGISTERED_IN_3PL, second DELIVERED_TO_3PL, third DELIVERED)")
    void getOrder2() {
        createTplOrder(createMessage);

        log.debug("First fetching from courier");
        TplOrderLimitDTO actualResponse = getTplOrderLimitDTO(TplOrderStatus.REGISTERED_IN_3PL);

        Assertions.assertNotNull(actualResponse);
        Assertions.assertEquals(TplOrderStatus.REGISTERED_IN_3PL, actualResponse.getStatus());
        Assertions.assertNotNull(actualResponse.getTrackingCode());
        Assertions.assertNotNull(actualResponse.getTplTrackingCode());
        Assertions.assertEquals(trackingCode, actualResponse.getTplTrackingCode());

        List<TplOrderLogEntity> logEntities = logEntityRepository.findAllByTplOrderId(tplOrder.getId());
        Assertions.assertNotNull(logEntities);
        Assertions.assertEquals(1, logEntities.size());
        Assertions.assertEquals(TplOrderStatus.REGISTERED_IN_3PL, logEntities.get(0).getNewStatus());
        Assertions.assertEquals(TplOrderStatus.NOT_REGISTERED, logEntities.get(0).getLastStatus());

        Optional<TplOrderSchedulerEntity> schedulerEntity = schedulerRepository.findByTplOrder(tplOrder);
        Assertions.assertTrue(schedulerEntity.isPresent());

        log.debug("Second fetching from courier");
        actualResponse = getTplOrderLimitDTO(TplOrderStatus.DELIVERED_TO_3PL);
        Assertions.assertNotNull(actualResponse);
        Assertions.assertEquals(TplOrderStatus.DELIVERED_TO_3PL, actualResponse.getStatus());
        Assertions.assertEquals(trackingCode, actualResponse.getTplTrackingCode());

        logEntities = logEntityRepository.findAllByTplOrderId(tplOrder.getId());
        Assertions.assertNotNull(logEntities);
        Assertions.assertEquals(2, logEntities.size());
        Assertions.assertEquals(TplOrderStatus.DELIVERED_TO_3PL, logEntities.get(1).getNewStatus());
        Assertions.assertEquals(TplOrderStatus.REGISTERED_IN_3PL, logEntities.get(1).getLastStatus());

        Optional<TplOrderSchedulerEntity> schedulerEntityAfterDELIVEREDTo3pl = schedulerRepository.findByTplOrder(tplOrder);
        Assertions.assertTrue(schedulerEntityAfterDELIVEREDTo3pl.isPresent());

        log.debug("Third fetching from courier");
        actualResponse = getTplOrderLimitDTO(TplOrderStatus.DELIVERED);
        Assertions.assertNotNull(actualResponse);
        Assertions.assertEquals(TplOrderStatus.DELIVERED, actualResponse.getStatus());
        Assertions.assertEquals(trackingCode, actualResponse.getTplTrackingCode());

        logEntities = logEntityRepository.findAllByTplOrderId(tplOrder.getId());
        Assertions.assertNotNull(logEntities);
        Assertions.assertEquals(3, logEntities.size());
        Assertions.assertEquals(TplOrderStatus.DELIVERED, logEntities.get(2).getNewStatus());
        Assertions.assertEquals(TplOrderStatus.DELIVERED_TO_3PL, logEntities.get(2).getLastStatus());

        Optional<TplOrderSchedulerEntity> schedulerEntityAfterDelivered = schedulerRepository.findByTplOrder(tplOrder);
        Assertions.assertTrue(schedulerEntityAfterDelivered.isEmpty());
    }

    @Test
    void getOrderPrice() {
    }
}
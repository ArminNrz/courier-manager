package ir.ronad.courierManager.service.higlevel;

import ir.ronad.courierManager.common.ErrorMessages;
import ir.ronad.courierManager.common.errors.NotFoundException;
import ir.ronad.courierManager.domain.TplOrderEntity;
import ir.ronad.courierManager.domain.enumartion.NotificationType;
import ir.ronad.courierManager.domain.enumartion.TplOrderStatus;
import ir.ronad.courierManager.domain.extraInfo.LinkExpressExtraInfo;
import ir.ronad.courierManager.dto.tplOrder.TplOrderLimitDTO;
import ir.ronad.courierManager.repository.TplOrderEntityRepository;
import ir.ronad.courierManager.service.data.DeliveryResponse;
import ir.ronad.courierManager.service.higlevel.manager.CourierManager;
import ir.ronad.courierManager.utility.BuildTplOrderUtility;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.UUID;

@SpringBootTest
class DeliveryServiceImplTest {

    @Autowired
    BuildTplOrderUtility buildTplOrderUtility;
    @Autowired
    TplOrderEntityRepository tplOrderEntityRepository;
    @Autowired
    DeliveryService deliveryService;
    @MockBean
    CourierManager courierManager;

    private TplOrderEntity tplOrder;

    @Test
    @DisplayName("create order for link express success")
    void createOrderLinkExpress() {
        this.tplOrder = buildTplOrderUtility.buildLinkExpressTplOrder();
        tplOrderEntityRepository.insert(tplOrder);
        String trackingCode = "91114";
        LinkExpressExtraInfo extraInfo = LinkExpressExtraInfo.builder()
                .message("order item successfully registered.")
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

        TplOrderLimitDTO actualResponse = deliveryService.createOrder(this.tplOrder.getId());

        Assertions.assertNotNull(actualResponse);
        Assertions.assertEquals(TplOrderStatus.REGISTERED_IN_3PL, actualResponse.getStatus());
        Assertions.assertNotNull(actualResponse.getTplTrackingCode());
        Assertions.assertEquals(trackingCode, actualResponse.getTplTrackingCode());
        Assertions.assertEquals(this.tplOrder.getTrackingCode(), actualResponse.getTrackingCode());
        LinkExpressExtraInfo linkExpressExtraInfo = (LinkExpressExtraInfo) tplOrder.getExtraInfo();
        Assertions.assertNotNull(linkExpressExtraInfo);
        Assertions.assertEquals(extraInfo.getMessage(), linkExpressExtraInfo.getMessage());
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
    void getOrder() {
    }

    @Test
    void getOrderPrice() {
    }
}
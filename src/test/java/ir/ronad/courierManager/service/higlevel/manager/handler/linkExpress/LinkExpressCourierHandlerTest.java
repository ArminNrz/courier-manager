package ir.ronad.courierManager.service.higlevel.manager.handler.linkExpress;

import ir.ronad.courierManager.domain.TplOrderEntity;
import ir.ronad.courierManager.domain.enumartion.TplOrderStatus;
import ir.ronad.courierManager.dto.thirdparty.linkExpress.LinkExpressCreateOrderResponse;
import ir.ronad.courierManager.service.data.DeliveryResponse;
import ir.ronad.courierManager.service.lowlevel.thirdparty.linkExpress.LinkExpressService;
import ir.ronad.courierManager.utility.BuildTplOrderUtility;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class LinkExpressCourierHandlerTest {

    @MockBean
    LinkExpressService linkExpressService;
    @Autowired
    LinkExpressCourierHandler courierHandler;
    @Autowired
    BuildTplOrderUtility buildTplOrderUtility;

    private TplOrderEntity tplOrder;
    private String trackingCode;

    @BeforeEach
    void setUp() {
        tplOrder = buildTplOrderUtility.buildLinkExpressTplOrder();
        trackingCode = "91114";
        tplOrder.setTplTrackingCode(trackingCode);
    }

    @Test
    void createOrder() {
        /*
        mock
         */
        LinkExpressCreateOrderResponse expectedResponse = new LinkExpressCreateOrderResponse("order item successfully registered.", "0", trackingCode);
        Mockito.when(linkExpressService.createOrder(Mockito.any()))
                .thenReturn(expectedResponse);

        /*
        test
         */
        DeliveryResponse actualResponse = courierHandler.createOrder(tplOrder);

        /*
        assert
         */
        Assertions.assertNotNull(actualResponse.getTplTrackingCode());
        Assertions.assertEquals(trackingCode, actualResponse.getTplTrackingCode());
        Assertions.assertEquals(TplOrderStatus.REGISTERED_IN_3PL, actualResponse.getStatus());
    }
}
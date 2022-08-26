package ir.ronad.courierManager.service.lowlevel.thirdparty.linkExpress;

import ir.ronad.courierManager.common.ErrorKey;
import ir.ronad.courierManager.common.errors.BadRequestAlertException;
import ir.ronad.courierManager.dto.thirdparty.linkExpress.create.LinkExpressCreateOrderRequest;
import ir.ronad.courierManager.dto.thirdparty.linkExpress.create.LinkExpressCreateOrderResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

@SpringBootTest
class LinkExpressServiceTest {

    @Autowired
    LinkExpressService linkExpressService;

    private LinkExpressCreateOrderRequest createOrderRequest;

    @BeforeEach
    void init() {
        createOrderRequest = new LinkExpressCreateOrderRequest();
        Random random = new Random();
        int sendDate = 3;

        createOrderRequest.setCompanyTrackingCode(String.valueOf(random.nextInt()));
        createOrderRequest.setAddress("Address-" + random.nextInt());
        createOrderRequest.setFullName("Test Name");
        createOrderRequest.setDeliveryType(1);
        createOrderRequest.setShift(3);
        createOrderRequest.setParcelType(1);
        createOrderRequest.setCity("تهران");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd ");

        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, sendDate);
        dt = c.getTime();

        createOrderRequest.setSendDate(dateFormat.format(dt));
        createOrderRequest.setGenerateQrCode(1);
    }

    @Test
    @DisplayName("create order normally")
    void createOrder() {
        LinkExpressCreateOrderResponse actualResponse = linkExpressService.createOrder(createOrderRequest);
        Assertions.assertNotNull(actualResponse);
        Assertions.assertNotNull(actualResponse.getCode());
        Assertions.assertNotNull(actualResponse.getTracking_code());
    }

    @Test
    @DisplayName("create order with exception")
    void createOrder2() {
        createOrderRequest.setCompanyTrackingCode(null);
        try {
            linkExpressService.createOrder(createOrderRequest);
        } catch (BadRequestAlertException exception) {
            Assertions.assertEquals(ErrorKey.linkExpress.LINK_EXPRESS_SERVICE_VALIDATION_ERROR, exception.getErrorKey());
        }
    }
}
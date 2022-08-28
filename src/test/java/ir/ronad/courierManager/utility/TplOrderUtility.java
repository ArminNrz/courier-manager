package ir.ronad.courierManager.utility;

import ir.ronad.courierManager.domain.TplOrderEntity;
import ir.ronad.courierManager.domain.enumartion.PaymentType;
import ir.ronad.courierManager.domain.enumartion.TplCode;
import ir.ronad.courierManager.domain.enumartion.TplOrderStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.UUID;

@Component
public class TplOrderUtility {

    public TplOrderEntity buildLinkExpressTplOrder() {
        Random random = new Random();
        TplOrderEntity tplOrder = new TplOrderEntity();
        tplOrder.setId(UUID.randomUUID().toString());
        tplOrder.setCreated(LocalDateTime.now());
        tplOrder.setUpdated(LocalDateTime.now());
        tplOrder.setOrderId(random.nextLong());
        tplOrder.setCustomerName("Test Test");
        tplOrder.setWeight(random.nextDouble());
        tplOrder.setLength(10L);
        tplOrder.setHeight(16L);
        tplOrder.setWidth(11L);
        tplOrder.setPaymentType(PaymentType.ONLINE);
        tplOrder.setDeliveryDate(LocalDate.now().plus(1, ChronoUnit.DAYS));
        tplOrder.setDeliveryStartTime(LocalTime.now());
        tplOrder.setDeliveryEndTime(LocalTime.now().plus(4, ChronoUnit.HOURS));
        tplOrder.setDescription("This is test");
        tplOrder.setStatus(TplOrderStatus.NOT_REGISTERED);
        tplOrder.setDestinationLat(random.nextDouble());
        tplOrder.setDestinationLon(random.nextDouble());
        tplOrder.setTplCode(TplCode.LINK_EXPRESS);
        tplOrder.setTrackingCode(UUID.randomUUID().toString());
        return tplOrder;
    }
}

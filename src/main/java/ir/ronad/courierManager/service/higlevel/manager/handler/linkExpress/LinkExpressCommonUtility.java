package ir.ronad.courierManager.service.higlevel.manager.handler.linkExpress;

import ir.ronad.courierManager.domain.TplOrderEntity;
import ir.ronad.courierManager.domain.enumartion.TplOrderStatus;
import ir.ronad.courierManager.domain.extraInfo.LinkExpressExtraInfo;
import ir.ronad.courierManager.dto.thirdparty.linkExpress.LinkExpressCreateOrderResponse;
import ir.ronad.courierManager.service.data.DeliveryResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static ir.ronad.courierManager.domain.enumartion.NotificationType.SCHEDULER;

@Component
@Slf4j
public class LinkExpressCommonUtility {

    public int detectParcelType(Long length, Long height, Long width) {
        log.debug("detectParcelType length: {}, height: {}, width: {}", length, height, width);

        int parcelType;

        if (length < 15L && height < 15L && width < 15L) {
            parcelType = 2;
        } else if (length < 25L && height < 25L && width < 25L) {
            parcelType = 3;
        } else if (length < 40L && height < 40L && width < 40L) {
            parcelType = 4;
        } else {
            parcelType = 5;
        }

        log.debug("parcelType is: {}", parcelType);
        return parcelType;
    }

    public DeliveryResponse buildCreateDeliveryResponse(TplOrderEntity tplOrderEntity, LinkExpressCreateOrderResponse response) {
        LinkExpressExtraInfo extraInfo = LinkExpressExtraInfo.builder()
                .message(response.getMessage())
                .build();
        DeliveryResponse returnValue = DeliveryResponse.builder()
                .tplOrderEntity(tplOrderEntity)
                .tplTrackingCode(response.getTrackingCode())
                .status(TplOrderStatus.REGISTERED_IN_3PL)
                .extraInfo(extraInfo)
                .notificationType(SCHEDULER)
                .build();
        log.debug("build link express deliveryResponse: {}", returnValue);
        return returnValue;
    }

}

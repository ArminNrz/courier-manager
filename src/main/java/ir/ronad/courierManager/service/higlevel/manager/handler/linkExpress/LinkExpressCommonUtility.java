package ir.ronad.courierManager.service.higlevel.manager.handler.linkExpress;

import ir.ronad.courierManager.domain.TplOrderEntity;
import ir.ronad.courierManager.domain.enumartion.TplOrderStatus;
import ir.ronad.courierManager.domain.extraInfo.LinkExpressExtraInfo;
import ir.ronad.courierManager.dto.thirdparty.linkExpress.create.LinkExpressCreateOrderResponse;
import ir.ronad.courierManager.dto.thirdparty.linkExpress.get.LinkExpressGetOrderResponse;
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
                .tplTrackingCode(response.getTracking_code())
                .status(TplOrderStatus.REGISTERED_IN_3PL)
                .extraInfo(extraInfo)
                .notificationType(SCHEDULER)
                .build();
        log.debug("build linkExpress deliveryResponse: {}", returnValue);
        return returnValue;
    }

    public DeliveryResponse buildGetDeliveryResponse(LinkExpressGetOrderResponse response, TplOrderEntity tplOrderEntity, TplOrderStatus newStatus) {
        LinkExpressExtraInfo extraInfo = LinkExpressExtraInfo.builder()
                .actualReceiverName(response.getResult().getActualReceiverName())
                .rejectReason(response.getResult().getFailRejectReasonTitle())
                .rejectReasonDescription(response.getResult().getFailRejectReasonDescription())
                .build();
        DeliveryResponse returnValue = DeliveryResponse.builder()
                .tplOrderEntity(tplOrderEntity)
                .tplTrackingCode(tplOrderEntity.getTplTrackingCode())
                .status(newStatus)
                .extraInfo(extraInfo)
                .notificationType(SCHEDULER)
                .build();
        log.debug("build linkExpress deliveryResponse: {}", returnValue);
        return returnValue;
    }

    public TplOrderStatus getLinkExpressOrderStatus(String stateCode) {
        log.debug("Request to get LinkExpress order status for state code: {}", stateCode);
        TplOrderStatus returnValue;

        switch (stateCode) {
            case "1":
                returnValue = TplOrderStatus.REGISTERED_IN_3PL; //Submitted
                break;
            case "2":
                returnValue = TplOrderStatus.REJECTED_BY_3PL; //Rejected
                break;
            case "3":
                returnValue = TplOrderStatus.CANCELLED; //Cancelled
                break;
            case "4":
                returnValue = TplOrderStatus.DELIVERED_TO_3PL; //Approved
                break;
            case "5":
                returnValue = TplOrderStatus.DELIVERED_TO_3PL_COURIER; //DeliveredToCourier
                break;
            case "6":
                returnValue = TplOrderStatus.DELIVERED; //DeliveredToCustomer
                break;
            case "7":
                returnValue = TplOrderStatus.NOT_DELIVERED; //Failed
                break;
            default:
                log.warn("LinkExpress service return not define state code: {}", stateCode);
                returnValue = TplOrderStatus.NONE;
        }

        log.debug("LinkExpress status: {}, is equal to tpl order status: {}", stateCode, returnValue);
        return returnValue;
    }

}

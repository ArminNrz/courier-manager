package ir.ronad.courierManager.service.higlevel.manager.handler.linkExpress;

import ir.ronad.courierManager.domain.TplOrderEntity;
import ir.ronad.courierManager.domain.enumartion.SendShift;
import ir.ronad.courierManager.dto.thirdparty.linkExpress.LinkExpressCreateOrderRequest;
import ir.ronad.courierManager.dto.thirdparty.linkExpress.LinkExpressCreateOrderResponse;
import ir.ronad.courierManager.service.data.DeliveryResponse;
import ir.ronad.courierManager.service.higlevel.manager.handler.BaseCourierHandler;
import ir.ronad.courierManager.service.lowlevel.thirdparty.linkExpress.LinkExpressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class LinkExpressCourierHandler extends BaseCourierHandler {

    private final LinkExpressService linkExpressService;
    private final LinkExpressDateTimeUtility dateTimeUtility;
    private final LinkExpressCommonUtility commonUtility;

    @Override
    public DeliveryResponse createOrder(TplOrderEntity tplOrderEntity) {
        log.trace("Enter: LinkExpressCourierHandler.createOrder({})", tplOrderEntity);
        SendShift sendShift = dateTimeUtility.detectSendShift(tplOrderEntity);
        int parcelType = commonUtility.detectParcelType(tplOrderEntity.getLength(), tplOrderEntity.getHeight(), tplOrderEntity.getWidth());
        LinkExpressCreateOrderRequest request = new LinkExpressCreateOrderRequest(tplOrderEntity, parcelType, sendShift);
        LinkExpressCreateOrderResponse response = linkExpressService.createOrder(request);
        return commonUtility.buildCreateDeliveryResponse(tplOrderEntity, response);
    }
}

package ir.ronad.courierManager.service.lowlevel.thirdparty.linkExpress;

import ir.ronad.courierManager.dto.thirdparty.linkExpress.LinkExpressCreateOrderRequest;
import ir.ronad.courierManager.dto.thirdparty.linkExpress.LinkExpressCreateOrderResponse;
import ir.ronad.courierManager.service.lowlevel.thirdparty.linkExpress.feignclient.LinkExpressFeignUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class LinkExpressService {

    private final LinkExpressFeignUtility feignUtility;

    public LinkExpressCreateOrderResponse createOrder(LinkExpressCreateOrderRequest createOrderRequest) {
        log.trace("Enter: linkExpressService.createOrder({})", createOrderRequest);
        return feignUtility.build().createOrder(createOrderRequest);
    }
}

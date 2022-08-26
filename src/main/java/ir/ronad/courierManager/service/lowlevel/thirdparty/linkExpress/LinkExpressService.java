package ir.ronad.courierManager.service.lowlevel.thirdparty.linkExpress;

import ir.ronad.courierManager.dto.thirdparty.linkExpress.create.LinkExpressCreateOrderRequest;
import ir.ronad.courierManager.dto.thirdparty.linkExpress.create.LinkExpressCreateOrderResponse;
import ir.ronad.courierManager.dto.thirdparty.linkExpress.get.LinkExpressGetOrderRequest;
import ir.ronad.courierManager.dto.thirdparty.linkExpress.get.LinkExpressGetOrderResponse;
import ir.ronad.courierManager.service.lowlevel.thirdparty.linkExpress.feignclient.LinkExpressFeignUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class LinkExpressService {

    private final LinkExpressFeignUtility feignUtility;

    public LinkExpressCreateOrderResponse createOrder(LinkExpressCreateOrderRequest request) {
        log.trace("Enter: linkExpressService.createOrder({})", request);
        log.debug("Try to send request to create Order with request: {} to linkExpress service", request);
        LinkExpressCreateOrderResponse response = feignUtility.build().createOrder(request);
        log.info("Created order in linkExpress service with response: {}", response);
        return response;
    }

    public LinkExpressGetOrderResponse getOrder(LinkExpressGetOrderRequest request) {
        log.trace("Enter: linkExpressService.getOrder({})", request);
        log.debug("Try to send request to get Order with request: {} to linkExpress service", request);
        LinkExpressGetOrderResponse response = feignUtility.build().getOrder(request);
        log.info("got order in linkExpress service with response: {}", response);
        return response;
    }
}

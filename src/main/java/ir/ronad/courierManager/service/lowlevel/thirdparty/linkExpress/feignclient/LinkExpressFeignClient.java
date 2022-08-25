package ir.ronad.courierManager.service.lowlevel.thirdparty.linkExpress.feignclient;

import feign.Headers;
import feign.RequestLine;
import ir.ronad.courierManager.dto.thirdparty.linkExpress.LinkExpressCreateOrderRequest;
import ir.ronad.courierManager.dto.thirdparty.linkExpress.LinkExpressCreateOrderResponse;

public interface LinkExpressFeignClient {

    @RequestLine("POST /order/add")
    @Headers("Content-Type: application/json")
    LinkExpressCreateOrderResponse createOrder(LinkExpressCreateOrderRequest request);
}

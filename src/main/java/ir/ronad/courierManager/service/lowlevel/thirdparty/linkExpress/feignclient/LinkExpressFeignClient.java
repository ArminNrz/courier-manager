package ir.ronad.courierManager.service.lowlevel.thirdparty.linkExpress.feignclient;

import feign.Headers;
import feign.RequestLine;
import ir.ronad.courierManager.dto.thirdparty.linkExpress.create.LinkExpressCreateOrderRequest;
import ir.ronad.courierManager.dto.thirdparty.linkExpress.create.LinkExpressCreateOrderResponse;
import ir.ronad.courierManager.dto.thirdparty.linkExpress.get.LinkExpressGetOrderRequest;
import ir.ronad.courierManager.dto.thirdparty.linkExpress.get.LinkExpressGetOrderResponse;

public interface LinkExpressFeignClient {

    @RequestLine("POST /order/add")
    @Headers("Content-Type: application/json")
    LinkExpressCreateOrderResponse createOrder(LinkExpressCreateOrderRequest request);

    @RequestLine("POST /order/status")
    @Headers("Content-Type: application/json")
    LinkExpressGetOrderResponse getOrder(LinkExpressGetOrderRequest request);
}

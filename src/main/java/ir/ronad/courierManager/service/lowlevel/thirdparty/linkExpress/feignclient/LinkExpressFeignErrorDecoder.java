package ir.ronad.courierManager.service.lowlevel.thirdparty.linkExpress.feignclient;

import feign.Response;
import feign.codec.ErrorDecoder;
import feign.gson.GsonDecoder;
import ir.ronad.courierManager.common.ErrorKey;
import ir.ronad.courierManager.common.ErrorMessages;
import ir.ronad.courierManager.common.errors.BadRequestAlertException;
import ir.ronad.courierManager.common.errors.ServiceException;
import ir.ronad.courierManager.dto.thirdparty.linkExpress.create.LinkExpressErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class LinkExpressFeignErrorDecoder implements ErrorDecoder {

    private static final String ENTITY = "linkExpress";

    @Override
    public Exception decode(String methodKey, Response response) {
        LinkExpressErrorResponse errorResponse;

        switch (response.status()) {
            case 400:
                try {
                    errorResponse = (LinkExpressErrorResponse) new GsonDecoder().decode(response, LinkExpressErrorResponse.class);
                    log.error("Error occurred in link express service with response: {}", errorResponse);
                    throw new BadRequestAlertException(errorResponse.getMessage(), ENTITY, ErrorKey.linkExpress.LINK_EXPRESS_SERVICE_VALIDATION_ERROR);
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new ServiceException(ErrorMessages.common.INTERNAL_ERROR, "courier", ErrorKey.common.INTERNAL_ERROR);
                }
            case 401:
                log.error("Error occurred in link express service: {}", ErrorMessages.linkExpress.LINK_EXPRESS_SERVICE_AUTH_ERROR);
                throw new BadRequestAlertException(ErrorMessages.linkExpress.LINK_EXPRESS_SERVICE_AUTH_ERROR, ENTITY, ErrorKey.linkExpress.LINK_EXPRESS_SERVICE_AUTH_ERROR);
            case 500:
                log.error("Error occurred in link express service: {}", ErrorMessages.linkExpress.LINK_EXPRESS_SERVICE_ERROR);
                throw new BadRequestAlertException(ErrorMessages.linkExpress.LINK_EXPRESS_SERVICE_ERROR, ENTITY, ErrorKey.linkExpress.LINK_EXPRESS_SERVICE_ERROR);
        }

        return null;
    }
}

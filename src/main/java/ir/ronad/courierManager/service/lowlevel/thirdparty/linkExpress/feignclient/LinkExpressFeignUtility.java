package ir.ronad.courierManager.service.lowlevel.thirdparty.linkExpress.feignclient;

import feign.Feign;
import feign.Logger;
import feign.auth.BasicAuthRequestInterceptor;
import feign.gson.GsonDecoder;
import feign.jackson.JacksonEncoder;
import feign.slf4j.Slf4jLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LinkExpressFeignUtility {

    private final LinkExpressFeignErrorDecoder errorDecoder;

    @Value("${ronad.courier.link-express.url}")
    private String url;
    @Value("${ronad.courier.link-express.username}")
    private String userName;
    @Value("${ronad.courier.link-express.password}")
    private String password;

    public LinkExpressFeignClient build() {
        BasicAuthRequestInterceptor interceptor = new BasicAuthRequestInterceptor(userName, password);
        return Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new GsonDecoder())
                .logger(new Slf4jLogger(LinkExpressFeignUtility.class))
                .logLevel(Logger.Level.FULL)
                .requestInterceptor(interceptor)
                .errorDecoder(errorDecoder)
                .target(LinkExpressFeignClient.class, url);
    }
}

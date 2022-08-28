package ir.ronad.courierManager.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import ir.ronad.courierManager.dto.tplOrder.TplOrderLimitDTO;
import ir.ronad.courierManager.service.higlevel.delivery.DeliveryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/courier/delivery")
public class DeliveryResource {

    private final DeliveryService deliveryService;

    @Value("${spring.application.name}")
    private String applicationName;
    private static final String ENTITY_NAME = "tplOrder";

    @PostMapping("/{tplOrderId}/register")
    @PreAuthorize("hasAnyAuthority('TPL_ORDER_REGISTER')")
    public ResponseEntity<TplOrderLimitDTO> register(@PathVariable("tplOrderId") String tplOrderId) {
        log.info("REST request to register tplOrder with id: {}, in courier", tplOrderId);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tplOrderId))
                .body(deliveryService.createOrder(tplOrderId));
    }

    @GetMapping("/{tplOrderId}/status")
    @PreAuthorize("hasAnyAuthority('TPL_ORDER_STATUS')")
    public ResponseEntity<TplOrderLimitDTO> getFromCourier(@PathVariable("tplOrderId") String tplOrderId) {
        log.info("REST request to get tplOrder with id: {} from courier", tplOrderId);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tplOrderId))
                .body(deliveryService.getOrder(tplOrderId));
    }
}

package ir.ronad.courierManager.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import ir.ronad.courierManager.common.ErrorKey;
import ir.ronad.courierManager.common.ErrorMessages;
import ir.ronad.courierManager.common.errors.NotFoundException;
import ir.ronad.courierManager.dto.tplOrder.*;
import ir.ronad.courierManager.service.entity.tplOrder.TplOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/courier/tplOrder")
public class TplOrderResource {

    private final TplOrderService tplOrderService;

    @Value("${spring.application.name}")
    private String applicationName;
    private static final String ENTITY_NAME = "tplOrder";

    @PostMapping
    @PreAuthorize("hasAnyAuthority('TPL_ORDER_CREATE')")
    public ResponseEntity<TplOrderLimitDTO> create(@Valid @RequestBody TplOrderCreateRequestDTO createRequestDTO) throws URISyntaxException {
        log.info("REST request to create Tpl-order with createRequest: {}", createRequestDTO);
        TplOrderLimitDTO result = tplOrderService.create(createRequestDTO);
        return ResponseEntity
                .created(new URI("/api/3pl-orders/"))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
                .body(result);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('TPL_ORDER_UPDATE')")
    public ResponseEntity<TplOrderDTO> update(@PathVariable("id") String id, @Valid @RequestBody TplOrderUpdateRequestDTO updateRequestDTO) {
        log.info("REST request to update Tpl-order with updateRequest: {}", updateRequestDTO);
        updateRequestDTO.setId(id);
        TplOrderDTO result = tplOrderService.update(updateRequestDTO);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
                .body(result);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('TPL_ORDER_LIST')")
    public ResponseEntity<List<TplOrderLimitDTO>> getAll(@ModelAttribute TplOrderQueryDTO queryDTO, Pageable pageable) {
        log.info("REST request to get all Tpl-orders with query: {}", queryDTO);
        Page<TplOrderLimitDTO> page = new PageImpl<>(tplOrderService.getAll(queryDTO, pageable));
        HttpHeaders headers = PaginationUtil
                .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('TPL_ORDER_LIST')")
    public ResponseEntity<TplOrderDTO> get(@PathVariable("id") String id) {
        log.info("REST request to find one Tpl-order with id: {}", id);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, id))
                .body(
                        tplOrderService.getDTOById(id)
                                .orElseThrow(() -> new NotFoundException(ErrorMessages.tplOrderValidation.TPL_ORDER_NOT_FOUND, ENTITY_NAME, ErrorKey.tplOrderValidation.TPL_ORDER_NOT_FOUND))
                );
    }
}

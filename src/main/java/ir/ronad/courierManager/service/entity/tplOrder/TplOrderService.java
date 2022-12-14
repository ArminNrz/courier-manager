package ir.ronad.courierManager.service.entity.tplOrder;

import ir.ronad.courierManager.common.ErrorKey;
import ir.ronad.courierManager.common.ErrorMessages;
import ir.ronad.courierManager.common.errors.NotFoundException;
import ir.ronad.courierManager.domain.TplOrderEntity;
import ir.ronad.courierManager.domain.enumartion.TplOrderStatus;
import ir.ronad.courierManager.dto.tplOrder.*;
import ir.ronad.courierManager.mapper.TplOrderMapper;
import ir.ronad.courierManager.repository.TplOrderEntityRepository;
import ir.ronad.courierManager.service.data.DeliveryResponse;
import ir.ronad.courierManager.service.data.TplOrderLogData;
import ir.ronad.courierManager.service.entity.tplOrder.handler.TplOrderLogHandler;
import ir.ronad.courierManager.service.entity.tplOrder.handler.TplOrderQueryHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static ir.ronad.courierManager.domain.enumartion.TplOrderStatus.NOT_REGISTERED;

@Service
@Slf4j
@RequiredArgsConstructor
public class TplOrderService {

    private final TplOrderEntityRepository repository;
    private final TplOrderMapper mapper;
    private final TplOrderQueryHandler queryHandler;
    private final TplOrderLogHandler logHandler;

    private static final String ENTITY = "tplOrder";

    public TplOrderLimitDTO create(TplOrderCreateRequestDTO createRequestDTO) {
        log.trace("Enter: tplOrderService.create({})", createRequestDTO);
        TplOrderEntity entity = mapper.fromCreateDTO(createRequestDTO);
        log.trace("entity: {}", entity);
        entity.setStatus(NOT_REGISTERED);
        repository.insert(entity);
        log.trace("saved tplOrderEntity, with id: {}, and status: {}", entity.getId(), entity.getStatus());
        return mapper.toLimitDTO(entity);
    }

    public List<TplOrderLimitDTO> getAll(TplOrderQueryDTO queryDTO, Pageable pageable) {
        log.trace("Enter: tplOrderService.get(queryDTO: {}, pageable: {})", queryDTO, pageable);
        return queryHandler.getTplOrderEntities(queryDTO, pageable).stream()
                .filter(Objects::nonNull)
                .map(mapper::toLimitDTO)
                .collect(Collectors.toList());
    }

    public Optional<TplOrderEntity> getById(String id) {
        log.trace("Enter: tplOrderService.getById(id: {})", id);
        return repository.findTplOrderEntitiesById(id);
    }

    public Optional<TplOrderDTO> getDTOById(String id) {
        log.trace("Enter: tplOrderService.getDTOById(id: {})", id);
        return this.getById(id).stream()
                .peek(tplOrderEntity -> log.trace("found with id: {} TplOrderEntity: {}", id, tplOrderEntity))
                .map(mapper::toDTO)
                .peek(tplOrderDTO -> log.debug("Found tplOrderDTO: {}", tplOrderDTO))
                .findFirst();
    }

    public List<TplOrderEntity> getByOrderId(Long orderId) {
        log.trace("Enter: tplOrderService.getByOrderId(orderId: {})", orderId);
        return repository.findAllByOrderId(orderId).stream()
                .peek(tplOrder -> log.trace("Found tplOrderId: {}, with orderId: {}", tplOrder.getId(), tplOrder.getOrderId()))
                .collect(Collectors.toList());
    }

    public TplOrderDTO update(TplOrderUpdateRequestDTO updateRequestDTO) {
        log.trace("Enter: tplOrderService.update({})", updateRequestDTO);

        if (!repository.existsById(updateRequestDTO.getId())) {
            log.warn("No such Tpl-order with id: {}", updateRequestDTO.getId());
            throw new NotFoundException(ErrorMessages.tplOrderValidation.TPL_ORDER_NOT_FOUND, ENTITY, ErrorKey.tplOrderValidation.TPL_ORDER_NOT_FOUND);
        }

        TplOrderEntity updatedEntity = repository.save(mapper.fromUpdateDTO(updateRequestDTO));
        log.debug("TplOrderEntity, with id: {}, is updated", updatedEntity.getId());
        return mapper.toDTO(updatedEntity);
    }

    public void updateStatus(DeliveryResponse deliveryResponse) {
        log.trace("Enter: tplOrderService.updateStatus(deliveryResponse: {}", deliveryResponse);

        TplOrderEntity entity = deliveryResponse.getTplOrderEntity();

        TplOrderStatus lastStatus = entity.getStatus();
        entity.setStatus(deliveryResponse.getStatus());
        TplOrderLogData logData = TplOrderLogData.builder()
                .lastStatus(lastStatus)
                .entity(entity)
                .description(deliveryResponse.getExtraInfo().toString())
                .build();

        entity = logHandler.saveLog(logData);
        repository.save(entity);
        log.debug("Updated TplOrder with id: {}, from status: {}, to status: {}", entity.getId(), lastStatus, entity.getStatus());
    }

    public void updateByDeliveryResponse(DeliveryResponse deliveryResponse) {
        log.trace("Enter: tplOrderService.updateByDeliveryResponse(deliveryResponse: {}", deliveryResponse);
        TplOrderEntity updatedEntity = mapper.fromDeliveryResponse(deliveryResponse, deliveryResponse.getTplOrderEntity());
        repository.save(updatedEntity);
        deliveryResponse.setTplOrderEntity(updatedEntity);
    }
}

package ir.ronad.courierManager.mapper;

import ir.ronad.courierManager.domain.TplOrderEntity;
import ir.ronad.courierManager.domain.TplOrderLogEntity;
import ir.ronad.courierManager.domain.TplOrderSchedulerEntity;
import ir.ronad.courierManager.dto.tplOrder.TplOrderCreateRequestDTO;
import ir.ronad.courierManager.dto.tplOrder.TplOrderDTO;
import ir.ronad.courierManager.dto.tplOrder.TplOrderLimitDTO;
import ir.ronad.courierManager.dto.tplOrder.TplOrderUpdateRequestDTO;
import ir.ronad.courierManager.service.data.DeliveryResponse;
import ir.ronad.courierManager.service.data.TplOrderLogData;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TplOrderMapper {
    @Mapping(target = "id", ignore = true)
    TplOrderEntity fromCreateDTO(TplOrderCreateRequestDTO createRequestDTO);

    TplOrderLimitDTO toLimitDTO(TplOrderEntity entity);

    TplOrderDTO toDTO(TplOrderEntity tplOrderEntity);

    TplOrderEntity fromUpdateDTO(TplOrderUpdateRequestDTO updateRequestDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "entity.id", target = "tplOrderId")
    @Mapping(source = "entity.orderId", target = "orderId")
    @Mapping(source = "entity.status", target = "newStatus")
    @Mapping(source = "lastStatus", target = "lastStatus")
    @Mapping(source = "entity.tplOrderUUID", target = "tplOrderUUID")
    TplOrderLogEntity fromLogData(TplOrderLogData logData);

    @Mapping(source = "deliveryResponse.extraInfo", target = "extraInfo")
    @Mapping(source = "deliveryResponse.tplTrackingCode", target = "tplTrackingCode")
    TplOrderEntity fromDeliveryResponse(DeliveryResponse deliveryResponse, @MappingTarget TplOrderEntity entity);

    /*
    @Mapping(source = "orderId", target = "id")
    @Mapping(source = "orderId", target = "orderId")
    @Mapping(source = "tplOrderId", target = "tplOrderId")
    @Mapping(source = "tplOrderUUID", target = "tplOrderUUID")
    @Mapping(source = "lastStatus", target = "lastStatus")
    @Mapping(source = "newStatus", target = "newStatus")
    TplOrderEvent toEvent(SendNotificationData notificationData);

     */

    @Mapping(source = "entity", target = "tplOrder")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "updated", ignore = true)
    TplOrderSchedulerEntity toScheduler(TplOrderEntity entity);
}

package ir.ronad.courierManager.mapper;

import ir.ronad.courierManager.domain.TplOrderEntity;
import ir.ronad.courierManager.service.data.DeliveryResponse;
import ir.ronad.courierManager.service.data.SendNotificationData;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface DeliveryResponseMapper {

    @Mapping(source = "deliveryResponse.tplOrderEntity.id", target = "tplOrderId")
    @Mapping(source = "deliveryResponse.tplOrderEntity.orderId", target = "orderId")
    @Mapping(source = "deliveryResponse.lastTplOrderStatus", target = "lastStatus")
    @Mapping(source = "deliveryResponse.tplOrderEntity.status", target = "newStatus")
    @Mapping(source = "otherTplOrders", target = "otherTplOrderEntities")
    @Mapping(source = "deliveryResponse.tplOrderEntity.tplOrderUUID", target = "tplOrderUUID")
    SendNotificationData toNotificationData(DeliveryResponse deliveryResponse, List<TplOrderEntity> otherTplOrders);
}

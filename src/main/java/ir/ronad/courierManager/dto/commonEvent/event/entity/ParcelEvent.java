package ir.ronad.courierManager.dto.commonEvent.event.entity;

import ir.ronad.courierManager.dto.commonEvent.status.ParcelStatus;
import ir.ronad.courierManager.dto.commonEvent.type.ParcelType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
public class ParcelEvent extends BaseNotificationEntityEvent<ParcelStatus> {

    public ParcelEvent() { setEntity(Entity.PARCEL);}
    private Long parcelId;
    private Map<Long,Double> items = new HashMap<>();
    private ParcelType parcelType;
}

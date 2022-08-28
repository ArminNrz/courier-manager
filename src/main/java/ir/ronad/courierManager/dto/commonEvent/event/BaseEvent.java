package ir.ronad.courierManager.dto.commonEvent.event;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public abstract class BaseEvent {

    // required
    private ZonedDateTime time;

    // partner-side partitioning
    private Long partnerId;
    private Long sellerId;
    private Long agencyId;
    private Long centerId;

    // warehouse-side partitioning
    private Long warehouseId;
    private Long operatorId;
}

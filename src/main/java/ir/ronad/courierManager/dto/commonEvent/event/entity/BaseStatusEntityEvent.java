package ir.ronad.courierManager.dto.commonEvent.event.entity;

import ir.ronad.courierManager.dto.commonEvent.event.EventAction;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class BaseStatusEntityEvent<S extends Enum<S>> extends BaseEntityEvent {

    // required if (type == STATUS_UPDATE)
    private S lastStatus;
    private S newStatus;

    private EventAction eventAction;
}

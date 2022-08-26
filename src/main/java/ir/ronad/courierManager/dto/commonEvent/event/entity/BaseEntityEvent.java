package ir.ronad.courierManager.dto.commonEvent.event.entity;

import ir.ronad.courierManager.dto.commonEvent.event.BaseEvent;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class BaseEntityEvent extends BaseEvent {

    private Entity entity;

    // required
    private Long id;
    private EntityEventType type;
}

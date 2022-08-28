package ir.ronad.courierManager.dto.commonEvent.event.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class BaseNotificationEntityEvent<S extends Enum<S>> extends BaseStatusEntityEvent<S> {
    private String partnerName;
    private String partnerAdminPhone;
    private String warehouseName;
    private String customerMobileNumber;
}

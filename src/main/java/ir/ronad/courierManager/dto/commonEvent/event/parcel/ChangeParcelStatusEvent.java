package ir.ronad.courierManager.dto.commonEvent.event.parcel;

import ir.ronad.courierManager.dto.commonEvent.status.ParcelStatus;
import lombok.Data;

@Data
public class ChangeParcelStatusEvent {
    private Long partnerId;
    private Long parcelId;
    private ParcelStatus parcelStatus;
}

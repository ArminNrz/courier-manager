package ir.ronad.courierManager.service.data;

import ir.ronad.courierManager.domain.enumartion.TplCode;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetOrderData {
    private String tplTrackingCode;
    private TplCode tplCode;
}

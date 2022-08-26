package ir.ronad.courierManager.dto.thirdparty.linkExpress.create;

import ir.ronad.courierManager.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class LinkExpressErrorResponse extends BaseDTO {
    private String message;
    private String code;
}

package ir.ronad.courierManager.dto.thirdparty.linkExpress;

import ir.ronad.courierManager.dto.BaseDTO;
import lombok.Data;

@Data
public class LinkExpressErrorResponse extends BaseDTO {
    private String message;
    private String code;
}

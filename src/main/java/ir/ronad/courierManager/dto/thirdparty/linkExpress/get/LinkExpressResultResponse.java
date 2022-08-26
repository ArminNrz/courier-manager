package ir.ronad.courierManager.dto.thirdparty.linkExpress.get;

import lombok.Data;

@Data
public class LinkExpressResultResponse {
    private String deliveryCode;
    private String state;
    private String doneDate;
    private String actualReceiverName;
    private String failRejectReasonTitle;
    private String failRejectReasonDescription;
    private String paymentMethod;
    private String amount;
    private String paidAt;
    private String refNumber;
    private String signatureUrl;
}

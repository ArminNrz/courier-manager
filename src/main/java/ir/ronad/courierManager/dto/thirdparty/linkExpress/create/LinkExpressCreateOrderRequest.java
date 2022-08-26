package ir.ronad.courierManager.dto.thirdparty.linkExpress.create;

import com.fasterxml.jackson.annotation.JsonProperty;
import ir.ronad.courierManager.domain.TplOrderEntity;
import ir.ronad.courierManager.domain.enumartion.PaymentType;
import ir.ronad.courierManager.domain.enumartion.SendShift;
import ir.ronad.courierManager.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import static ir.ronad.courierManager.domain.enumartion.PaymentType.ONLINE;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class LinkExpressCreateOrderRequest extends BaseDTO {
    private String companyTrackingCode;
    private String address;
    private String fullName;
    private int deliveryType;
    private int shift;
    private int parcelType;
    private String cellPhone;
    private String companyName;
    private String phone;
    private String postalCode;
    private BigDecimal amount;
    private BigDecimal parcelValue;
    private String city;
    private BigDecimal weight;
    private String sendDate;
    private BigDecimal latitude;
    private BigDecimal longitude;
    @JsonProperty("describtion")
    private String description;
    private String senderAddress;
    private int generateQrCode;
    @JsonProperty("return")
    private int isReturn;

    public LinkExpressCreateOrderRequest(TplOrderEntity tplOrder, int parcelType, SendShift sendShift) {
        this.companyTrackingCode = tplOrder.getTrackingCode();
        this.address = tplOrder.getDestinationAddress();
        this.fullName = tplOrder.getCustomerName();
        this.deliveryType = tplOrder.getPaymentType() == ONLINE ? 1 : 3;
        this.shift = sendShift.getValue();
        this.parcelType = parcelType;
        this.cellPhone = tplOrder.getCustomerMobileNumber();
        this.companyName = null;
        this.phone = tplOrder.getCustomerPhoneNumber();
        this.postalCode = tplOrder.getDestinationPostalCode();
        this.amount = BigDecimal.valueOf(tplOrder.getPaymentType().equals(PaymentType.ONLINE) ? 0.00 : tplOrder.getCodValue());
        this.parcelValue = BigDecimal.valueOf(tplOrder.getTotalValue() == null ? 0.00 : tplOrder.getTotalValue());
        this.city = "تهران";
        this.weight = BigDecimal.valueOf((tplOrder.getWeight()) / 1000);
        this.sendDate = tplOrder.getDeliveryDate().toString();
        this.latitude = tplOrder.getDestinationLat() == null ? null : BigDecimal.valueOf(Double.parseDouble(tplOrder.getDestinationLat().toString()));
        this.longitude = tplOrder.getDestinationLon() == null ? null : BigDecimal.valueOf(Double.parseDouble(tplOrder.getDestinationLon().toString()));
        this.description = tplOrder.getDescription();
        this.senderAddress = tplOrder.getSourceAddress();
        this.generateQrCode = 1;
        this.isReturn = 0;
    }
}

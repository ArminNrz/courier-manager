package ir.ronad.courierManager.dto.tplOrder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ir.ronad.courierManager.common.ErrorMessages;
import ir.ronad.courierManager.domain.enumartion.PaymentType;
import ir.ronad.courierManager.domain.enumartion.TplCode;
import ir.ronad.courierManager.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class TplOrderUpdateRequestDTO extends BaseDTO {
    @JsonIgnore
    private String id;
    @NotNull(message = ErrorMessages.tplOrderValidation.TPL_ORDER_ORDER_ID_NOT_EMPTY)
    private Long orderId;
    private String customerName;
    private String customerPhoneNumber;
    private String customerMobileNumber;
    private String sourceProvinceName;
    private String sourceCityName;
    private String sourceAddress;
    private Double sourceLat;
    private Double sourceLon;
    private String destinationProvinceName;
    private String destinationCityName;
    private String destinationAddress;
    private Double destinationLat;
    private Double destinationLon;
    private String destinationPostalCode;
    private Long length;
    private Long height;
    private Long width;
    private Double weight;
    private String description;
    @NotNull(message = ErrorMessages.tplOrderValidation.TPL_ORDER_TPL_CODE_NOT_EMPTY)
    private TplCode tplCode;
    private LocalDate deliveryDate;
    private LocalTime deliveryStartTime;
    private LocalTime deliveryEndTime;
    private Integer totalPackages;
    private Double totalWeight;
    private Double totalValue;
    private String ronadTrackingCode;
    private String tplOrderUUID;
    private PaymentType paymentType;
    private Double codValue;
}

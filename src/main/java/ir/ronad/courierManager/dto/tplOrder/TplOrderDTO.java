package ir.ronad.courierManager.dto.tplOrder;

import ir.ronad.courierManager.domain.enumartion.PaymentType;
import ir.ronad.courierManager.domain.enumartion.TplCode;
import ir.ronad.courierManager.domain.enumartion.TplOrderStatus;
import ir.ronad.courierManager.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class TplOrderDTO extends BaseDTO {
    private String id;
    private LocalDateTime created;
    private LocalDateTime updated;
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
    private TplOrderStatus status;
    private TplCode tplCode;
    private String tplTrackingCode;
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

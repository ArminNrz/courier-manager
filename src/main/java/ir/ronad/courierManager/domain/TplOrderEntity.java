package ir.ronad.courierManager.domain;

import ir.ronad.courierManager.domain.enumartion.PaymentType;
import ir.ronad.courierManager.domain.enumartion.TplCode;
import ir.ronad.courierManager.domain.enumartion.TplOrderStatus;
import ir.ronad.courierManager.domain.extraInfo.BaseExtraInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Document("tpl_order")
@Data
@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor
public class TplOrderEntity extends BaseDomainEntity {

    private static final long serialVersionUID = -1L;

    @Id
    private String id;

    @Indexed(name = "order_id")
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

    @NotNull
    private TplOrderStatus status;

    @Indexed(name = "tpl_code")
    private TplCode tplCode;

    @Indexed(name = "tpl_tracking_code")
    private String tplTrackingCode;

    private LocalDate deliveryDate;

    private LocalTime deliveryStartTime;

    private LocalTime deliveryEndTime;

    private boolean isDeleted = Boolean.FALSE;

    private Integer totalPackages;

    private Double totalWeight;

    private Double totalValue;

    private String trackingCode;

    private String tplOrderUUID;

    private PaymentType paymentType;

    private Double codValue;

    @DBRef
    private List<TplOrderLogEntity> tplOrderLogs = new ArrayList<>();

    @Field("extraInfo")
    private BaseExtraInfo extraInfo;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

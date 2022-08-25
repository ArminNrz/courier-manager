package ir.ronad.courierManager.service.data;

import ir.ronad.courierManager.domain.enumartion.SendShift;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinkExpressSendTime {
    private SendShift sendShift;
    private LocalDate sendDate;
}

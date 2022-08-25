package ir.ronad.courierManager.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class BaseDomainEntity implements Cloneable, Serializable {
    @CreatedDate
    private LocalDateTime created;

    @LastModifiedDate
    private LocalDateTime updated;
}

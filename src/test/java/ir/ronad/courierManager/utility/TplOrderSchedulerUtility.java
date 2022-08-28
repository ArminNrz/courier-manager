package ir.ronad.courierManager.utility;

import ir.ronad.courierManager.domain.TplOrderEntity;
import ir.ronad.courierManager.domain.TplOrderSchedulerEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class TplOrderSchedulerUtility {

    private final TplOrderUtility tplOrderUtility;

    public List<TplOrderSchedulerEntity> build(int count) {
        List<TplOrderSchedulerEntity> entities = new ArrayList<>();
        IntStream.range(0, count).forEach(value -> {
            TplOrderSchedulerEntity entity = buildOne();
            entities.add(entity);
        });

        return entities;
    }

    private TplOrderSchedulerEntity buildOne() {
        TplOrderSchedulerEntity entity = new TplOrderSchedulerEntity();
        TplOrderEntity tplOrderEntity = tplOrderUtility.buildLinkExpressTplOrder();
        entity.setTplOrder(tplOrderEntity);
        return entity;
    }
}

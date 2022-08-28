package ir.ronad.courierManager.service.higlevel.scheduler.handler;

import ir.ronad.courierManager.domain.TplOrderSchedulerEntity;
import ir.ronad.courierManager.service.higlevel.delivery.DeliveryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.IntStream;

@Component
@Slf4j
@RequiredArgsConstructor
public class TplOrderSchedulingHandler {

    private final TplOrderSchedulerPagingHandler pagingHandler;
    private final DeliveryService deliveryService;

    public void start() {
        log.trace("Enter: TplOrderSchedulingHandler.start()");
        int pageCount = pagingHandler.getTplOrderSchedulerPageCount();
        if (pageCount == 0)
            return;

        IntStream.range(0, pageCount - 1).forEach(this::process);
    }

    private void process(int pageNumber) {
        log.trace("Enter: TplOrderSchedulingHandler.process(pageNumber: {})", pageNumber);
        pagingHandler.getPage(pageNumber).stream()
                .filter(Objects::nonNull)
                .forEach(this::processRecord);
    }

    private void processRecord(TplOrderSchedulerEntity record) {
        log.trace("Enter: TplOrderSchedulingHandler.processRecord({record: {})", record);
        deliveryService.getOrder(record.getTplOrder());
    }

}

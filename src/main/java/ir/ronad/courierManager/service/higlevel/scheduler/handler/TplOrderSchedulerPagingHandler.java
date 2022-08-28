package ir.ronad.courierManager.service.higlevel.scheduler.handler;

import ir.ronad.courierManager.domain.TplOrderSchedulerEntity;
import ir.ronad.courierManager.service.entity.tplOrderScheduler.TplOrderSchedulerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class TplOrderSchedulerPagingHandler {

    private final TplOrderSchedulerService schedulerService;

    @Value("${ronad.scheduling.page-size}")
    private int pageSize;

    public int getTplOrderSchedulerPageCount() {
        int pageCount = calculateTplOrderSchedulerPageCount(pageSize);
        log.info("..................");
        log.info("Page size: {}", pageSize);
        log.info("Page count: {}", pageCount);
        log.info("..................");
        return pageCount;
    }

    private int calculateTplOrderSchedulerPageCount(int pageSize) {
        int schedulerRecordObjectNumber;
        int division;

        log.trace("Try to get tplOrder scheduler record object number");
        schedulerRecordObjectNumber = (int) schedulerService.count();
        log.debug("TplOrderSchedulerRecord object number is: {}", schedulerRecordObjectNumber);

        if (schedulerRecordObjectNumber == 0) {
            log.info("No TplOrderSchedulerRecord is exist");
            return 0;
        }

        division = schedulerRecordObjectNumber / pageSize;
        log.trace("division: {}", division);

        return  (schedulerRecordObjectNumber % pageSize == 0) ? division : division + 1;
    }

    public Page<TplOrderSchedulerEntity> getPage(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        log.debug("Try to process Tpl-order scheduler of page: {}", pageable);
        return schedulerService.findAll(pageable);
    }
}

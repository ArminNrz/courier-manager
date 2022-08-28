package ir.ronad.courierManager.service.higlevel.scheduler;

import ir.ronad.courierManager.service.higlevel.scheduler.handler.TplOrderSchedulingHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.locks.ReentrantLock;

@Service
@Slf4j
@RequiredArgsConstructor
public class SchedulerManager {

    private final TplOrderSchedulingHandler tplOrderSchedulingHandler;

    private final ReentrantLock mutex = new ReentrantLock();

    @Scheduled(fixedDelayString = "${ronad.scheduling.fixed-delay}", initialDelayString = "${ronad.scheduling.initial-delay}")
    public void runTplOrderScheduler() {
        log.info("Start Tpl-order scheduling.................");

        if (checkLock()) return;

        try {
            startTplOrderScheduling();
        }
        catch (Exception exception) {
            log.error("There is an error in checking TplOrder schedule mod from 3pl services");
        }
        finally {
            mutex.unlock();
        }
    }

    private void startTplOrderScheduling() {
        mutex.lock();
        log.info("Start scheduling...");
        tplOrderSchedulingHandler.start();
        log.info("Exit scheduling.");
    }

    private boolean checkLock() {
        if (mutex.isLocked()) {
            log.warn("A thread running TplOrder Scheduler in the same time");
            return true;
        }
        return false;
    }
}

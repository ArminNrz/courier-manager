package ir.ronad.courierManager.service.entity.tplOrder.handler;

import ir.ronad.courierManager.domain.TplOrderEntity;
import ir.ronad.courierManager.domain.TplOrderLogEntity;
import ir.ronad.courierManager.mapper.TplOrderMapper;
import ir.ronad.courierManager.repository.TplOrderLogEntityRepository;
import ir.ronad.courierManager.service.data.TplOrderLogData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class TplOrderLogHandler {

    private final TplOrderLogEntityRepository repository;
    private final TplOrderMapper mapper;

    public TplOrderEntity saveLog(TplOrderLogData logData) {
        log.trace("Enter: tplOrderLogHandler.saveLog({})", logData);
        TplOrderLogEntity logEntity = mapper.fromLogData(logData);
        repository.save(logEntity);
        logData.getEntity().getTplOrderLogs().add(logEntity);
        log.trace("Exit: tplOrderLogHandler.saveLog, result: {}", logData.getEntity());
        return logData.getEntity();
    }
}

package ir.ronad.courierManager.service.entity.tplOrderScheduler;

import ir.ronad.courierManager.domain.TplOrderEntity;
import ir.ronad.courierManager.domain.TplOrderSchedulerEntity;
import ir.ronad.courierManager.mapper.TplOrderMapper;
import ir.ronad.courierManager.repository.TplOrderSchedulerEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TplOrderSchedulerService {

    private final TplOrderSchedulerEntityRepository repository;
    private final TplOrderMapper tplOrderMapper;

    public void save(TplOrderEntity tplOrderEntity) {
        log.trace("Enter: TplOrderSchedulerService.save({})", tplOrderEntity);
        TplOrderSchedulerEntity entity = tplOrderMapper.toScheduler(tplOrderEntity);
        repository.save(entity);
        log.debug("Saved Tpl order scheduler for tplId: {}, orderId: {}", tplOrderEntity.getId(), tplOrderEntity.getOrderId());
    }

    public void delete(TplOrderEntity tplOrderEntity) {
        log.trace("Enter: TplOrderSchedulerService.delete({})", tplOrderEntity);
        boolean isDeleted = this.findByTplOrder(tplOrderEntity).stream()
                .peek(tplOrderSchedulerEntity -> log.debug("Found tpl order scheduler with tpl order id: {}", tplOrderSchedulerEntity.getTplOrder().getId()))
                .peek(repository::delete)
                .findAny().isPresent();

        if (isDeleted)
            log.debug("Deleted tpl order scheduler for tplOrderId: {}", tplOrderEntity.getId());
    }

    public Optional<TplOrderSchedulerEntity> findByTplOrder(TplOrderEntity tplOrderEntity) {
        log.trace("Enter: TplOrderSchedulerService.findByTplOrder({})", tplOrderEntity);
        return repository.findByTplOrder(tplOrderEntity);
    }

    public long count() {
        return repository.count();
    }

    public Page<TplOrderSchedulerEntity> findAll(Pageable pageable) {
        log.trace("Enter: TplOrderSchedulerService.findAll(pageable: {})", pageable);
        return repository.findAll(pageable);
    }
}

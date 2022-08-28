package ir.ronad.courierManager.service.higlevel.scheduler.handler;

import ir.ronad.courierManager.domain.TplOrderSchedulerEntity;
import ir.ronad.courierManager.repository.TplOrderSchedulerEntityRepository;
import ir.ronad.courierManager.utility.TplOrderSchedulerUtility;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import java.util.List;

@SpringBootTest
class TplOrderSchedulerPagingHandlerTest {

    @Autowired
    TplOrderSchedulerUtility schedulerUtility;
    @Autowired
    TplOrderSchedulerEntityRepository repository;
    @Autowired
    TplOrderSchedulerPagingHandler pagingHandler;

    private void persistSchedulerRecord(int count) {
        repository.deleteAll();
        List<TplOrderSchedulerEntity> entities = schedulerUtility.build(count);
        repository.saveAll(entities);
    }

    @Test
    @DisplayName("get tpl order scheduler for 10 record")
    void getTplOrderSchedulerPageCount() {
        persistSchedulerRecord(10);
        int actualPageCount = pagingHandler.getTplOrderSchedulerPageCount();
        Assertions.assertEquals(5, actualPageCount);
    }

    @Test
    @DisplayName("get tpl order scheduler for 1 record")
    void getTplOrderSchedulerPageCount2() {
        persistSchedulerRecord(1);
        int actualPageCount = pagingHandler.getTplOrderSchedulerPageCount();
        Assertions.assertEquals(1, actualPageCount);
    }

    @Test
    @DisplayName("get tpl order scheduler for 11 record")
    void getTplOrderSchedulerPageCount3() {
        persistSchedulerRecord(19);
        int actualPageCount = pagingHandler.getTplOrderSchedulerPageCount();
        Assertions.assertEquals(10, actualPageCount);
    }

    @Test
    @DisplayName("get page 1 from 1 page")
    void getPage() {
        persistSchedulerRecord(1);
        Page<TplOrderSchedulerEntity> page = pagingHandler.getPage(0);
        Assertions.assertEquals(1, page.getTotalPages());
        Assertions.assertEquals(1, page.stream().count());
    }

    @Test
    @DisplayName("get page 2 from 10 page")
    void getPage2() {
        persistSchedulerRecord(10);
        Page<TplOrderSchedulerEntity> page = pagingHandler.getPage(2);
        Assertions.assertEquals(5, page.getTotalPages());
        Assertions.assertEquals(2, page.stream().count());
    }

    @Test
    @DisplayName("get page 3 from 11 page")
    void getPage3() {
        persistSchedulerRecord(11);
        Page<TplOrderSchedulerEntity> page = pagingHandler.getPage(3);
        Assertions.assertEquals(6, page.getTotalPages());
        Assertions.assertEquals(2, page.stream().count());
    }
}
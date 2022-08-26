package ir.ronad.courierManager.service.lowlevel.producer;

import ir.ronad.courierManager.service.data.SendNotificationData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TplOrderNotificationService {

    public void sendToBus(SendNotificationData notificationData) {
        log.trace("Enter: TplOrderNotificationService.sendToBus({})", notificationData);
        //todo
    }
}

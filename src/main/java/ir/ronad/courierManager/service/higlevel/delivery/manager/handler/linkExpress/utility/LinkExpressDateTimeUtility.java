package ir.ronad.courierManager.service.higlevel.delivery.manager.handler.linkExpress.utility;

import ir.ronad.courierManager.domain.TplOrderEntity;
import ir.ronad.courierManager.domain.enumartion.SendShift;
import ir.ronad.courierManager.service.data.LinkExpressSendTime;
import ir.ronad.courierManager.service.lowlevel.utility.DateTimeUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

@Component
@Slf4j
@RequiredArgsConstructor
public class LinkExpressDateTimeUtility {

    private final DateTimeUtility dateTimeUtility;

    private static final LocalTime ELEVEN_FIFTY_FIVE = LocalTime.of(11, 45, 0, 0);
    private static final LocalTime SEVENTEEN = LocalTime.of(17, 0, 0, 0);
    private static final LocalTime MORNING_START_TIME = LocalTime.of(8, 0, 0, 0);
    private static final LocalTime MORNING_END_TIME = LocalTime.of(14, 0, 0, 0);
    private static final LocalTime EVENING_START_TIME = LocalTime.of(14, 0, 0, 0);
    private static final LocalTime EVENING_END_TIME = LocalTime.of(20, 0, 0, 0);

    public SendShift detectSendShift(TplOrderEntity tplOrder) {
        log.trace("Enter: LinkExpressDateTimeUtility.detectSendShift({})", tplOrder);
        LinkExpressSendTime context = compareDeliveryDate(tplOrder, detectSoonerSendTime());
        updateDeliveryTimeTplOrder(context, tplOrder);
        return context.getSendShift();
    }

    private void updateDeliveryTimeTplOrder(LinkExpressSendTime context, TplOrderEntity tplOrder) {
        log.trace("Enter: LinkExpressDateTimeUtility.updateDeliveryTimeTplOrder(LinkExpressSendTime: {}, tplOrder: {})", context, tplOrder);
        SendShift sendShift = context.getSendShift();
        LocalDate sendDate = context.getSendDate();
        LocalTime deliveryStartTime;
        LocalTime deliveryEndTime;

        if (sendShift == SendShift.MORNING) {
            deliveryStartTime = MORNING_START_TIME;
            deliveryEndTime = MORNING_END_TIME;
        } else {
            deliveryStartTime = EVENING_START_TIME;
            deliveryEndTime = EVENING_END_TIME;
        }

        /*
        update TplOrder
         */
        tplOrder.setDeliveryDate(sendDate);
        tplOrder.setDeliveryStartTime(deliveryStartTime);
        tplOrder.setDeliveryEndTime(deliveryEndTime);
        log.info("DeliveryTime for tplOrderId: {}, date is: {}, sendShift is: {}", tplOrder.getId(), sendDate, sendShift);
        log.debug("TplOrder: {}", tplOrder);
    }

    private LinkExpressSendTime detectSoonerSendTime() {
        log.trace("Enter: LinkExpressDateTimeUtility.detectSoonerSendTime()");
        LocalDate today = dateTimeUtility.getJalaliLocalDate();
        LocalDate tomorrow = today.plus(1, ChronoUnit.DAYS);
        LinkExpressSendTime context;
        LocalTime now = dateTimeUtility.getJalaliLocalTime();

        SendShift sendShift;
        LocalDate sendDate;

        if (now.isBefore(ELEVEN_FIFTY_FIVE)) {
            sendShift = SendShift.EVENING;
            sendDate = today;
        }
        else if (now.isAfter(ELEVEN_FIFTY_FIVE) && now.isBefore(SEVENTEEN)) {
            sendShift = SendShift.MORNING;
            sendDate = tomorrow;
        }
        else {
            sendShift = SendShift.EVENING;
            sendDate = tomorrow;
        }

        context = new LinkExpressSendTime(sendShift, sendDate);
        log.debug("Link express sooner date to send tplOrder with linkExpressSendTime: {}", context);
        return context;
    }

    private LinkExpressSendTime compareDeliveryDate(TplOrderEntity tplOrder, LinkExpressSendTime context) {
        log.trace("Enter: LinkExpressDateTimeUtility.compareDeliveryDate(tplOrder: {}, linkExpressSendTime: {})", tplOrder, context);
        LocalDate orderDeliveryDate = tplOrder.getDeliveryDate();
        LocalDate linkExpressSoonerDeliveryDate = context.getSendDate();

        /*
        compare tplOrder delivery date with linkExpress sooner date
         */
        if (!orderDeliveryDate.isAfter(linkExpressSoonerDeliveryDate)) {
            log.info("link express deliveryDate is: {}, before linkExpress sooner delivery date: {}", orderDeliveryDate, linkExpressSoonerDeliveryDate);
        }
        else {
            context = detectFutureParcelDeliveryDate(tplOrder);
        }

        /*
        linkExpress can not send Order in TUESDAY evening and friday, so we detect sooner time is saturday morning
         */
        return detectOffDay(context);
    }

    private LinkExpressSendTime detectFutureParcelDeliveryDate(TplOrderEntity tplOrder) {
        log.trace("Enter: LinkExpressDateTimeUtility.detectFutureParcelDeliveryDate(tplOrder: {})", tplOrder);
        LinkExpressSendTime context = new LinkExpressSendTime();
        LocalTime startTime = tplOrder.getDeliveryStartTime();

        if (startTime.isBefore(MORNING_END_TIME)) {
            context.setSendDate(tplOrder.getDeliveryDate());
            context.setSendShift(SendShift.MORNING);
        }
        else if (startTime.isBefore(EVENING_END_TIME)) {
            context.setSendDate(tplOrder.getDeliveryDate());
            context.setSendShift(SendShift.EVENING);
        }
        else {
            context.setSendDate(tplOrder.getDeliveryDate().plus(1, ChronoUnit.DAYS));
            context.setSendShift(SendShift.MORNING);
        }

        log.debug("link express detect future parcel delivery: {}", context);
        return context;
    }

    private LinkExpressSendTime detectOffDay(LinkExpressSendTime context) {
        log.trace("Enter: LinkExpressDateTimeUtility.detectOffDay(linkExpressSendTime: {})", context);
        LocalDate sendDate = context.getSendDate();
        SendShift sendShift = context.getSendShift();

        if (sendDate.getDayOfWeek() == DayOfWeek.FRIDAY) {
            sendShift = SendShift.EVENING;
            sendDate = sendDate.plus(1, ChronoUnit.DAYS);
            log.debug("sendShift: {}, sendDate: {}, is changing because today is FRIDAY", sendShift, sendDate);
        }

        context.setSendDate(sendDate);
        context.setSendShift(sendShift);
        log.debug("LinkExpressSendDateContext: {}", context);
        return context;
    }
}

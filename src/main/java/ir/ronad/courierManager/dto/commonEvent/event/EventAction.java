package ir.ronad.courierManager.dto.commonEvent.event;

// TODO refactor this
public enum EventAction {

    /*
     * incoming parcel
     */
    REGISTERED_PARCEL_ITEM_BTW_PARCEL, // نهایی شدن پردازش محموله‌ی بین انبار
    LOCATED_PARCEL_ITEM_INCOMING_PARCEL, // جایگذاری محموله‌ی ورودی
    UNLOAD_INCOMING_PARCEL, // تخلیه محموله ورودی
    INVERSE_UNLOADED_PARCEL, //تغییر وضعیت محموله از تخلیه یا رجیستر به ایجاد شده یا اختصاص داده شده

    /*
     * order
     */
    CREATED_ORDER_ORDERED, // ثبت سفارش در حالت تامین شده
    CREATED_ORDER_SUPPLYING, // ثبت سفارش در حال تامین ( سفارش در انتظار تامین است )
    SUPPLYING_TO_ORDERED, // تغییر وضعیت از در حال تامین به تامین شده
    ORDERED_TO_SUPPLYING, // تغییر وضعیت از در تامین شده به در حال تامین
    CHANGE_WAREHOUSE_FROM_SOURCE, // تغییر انبار از مبدا سفارش
    PICKED_ORDER, // پردازش سفارش
    UNPICKED_ORDER, // آن پیک کردن سفارش
    CANCEL_SUPPLYING_ORDER, // لغو سفارش در حال تامین
    CANCEL_ORDER_BEFORE_PICKED, // لغو سفارش تامین شده، پیش از جمع‌آوری
    CANCEL_ORDER_AFTER_PICKED, // لغو سفارش پس از جمع‌آوری
    LOCATED_ORDER_AFTER_CANCELED, // جایگذاری سفارش لغو شده
    DELETE_ORDER_ITEM_ORDERED, // حدف آیتم از سفارش تامین شده
    DELETE_ORDER_ITEM_SUPPLYING, // حذف آیتم از سفارش در حال تامین

    /*
     * outgoing parcel
     */
    CREATED_OUTGOING_PARCEL, // ایجاد محموله‌ی خروجی
    PACKAGED_OUTGOING_PARCEL, // پردازش محموله‌ی خروجی
    CANCELED_OUTGOING_PARCEL_BEFORE_PICKED, // لغو محموله‌ی خروجی پیش از جمع‌آوری
    RECEIPT_OUTGOING_PARCEL, // ایجاد قبض خروج
    WASTAGE_RECEIPT_OUTGOING_PARCEL, // ایجاد قبض خروج از انبار ضایعات
    RECEIPT_TO_WASTAGE_PARCEL, // ایجاد قبض خروج به ضایعات
    RECEIPT_FROM_WASTAGE_PARCEL // ایجاد قبض خروج از ضایعات

}

package ir.ronad.courierManager.dto.commonEvent.type;

/**
 * The ParcelType enumeration.
 */
public enum ParcelType {
    PARTNER, WAREHOUSE,
    RECEIPT,                 // قبض خروج از انبار اصلی به بیرون
    RECEIPT_WASTAGE,         // قبض خروج از انبار ضایعات به بیرون
    RECEIPT_TO_WASTAGE,      // قبض خروج از انبار اصلی به ضایعات
    RECEIPT_FROM_WASTAGE     // قبض خروج از انبار ضایعات به اصلی
}

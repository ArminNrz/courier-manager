package ir.ronad.courierManager.dto.commonEvent.event;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Topics {

    /*
    3pl_order
     */
    public static final String TPL_ORDER_PRE = "event_3pl_order_pre";
    public static final String TPL_ORDER = "event_3pl_order";

    /*
    parcelItem
    */
    public static final String PARCEL_ITEM = "event_parcel_item";
    public static final String PARCEL_ITEM_PRE = "event_parcel_item_pre";
    public static final String PARCEL_ITEM_COMMAND = "command_parcel_item";

    /*
    orderItem
     */
    public static final String ORDER_ITEM = "event_order_item";
    public static final String ORDER_ITEM_PRE = "event_order_item_pre";

    /*
    order
     */
    public static final String ORDER = "event_order";
    public static final String ORDER_PRE = "event_order_pre";
    public static final String ORDER_COMMAND = "command_order";

    /*
    parcel
     */
    public static final String PARCEL = "event_parcel";
    public static final String PARCEL_PRE = "event_parcel_pre";


    /*
    Inventory
     */
    public static final String INVENTORY = "event_inventory";
    public static final String INVENTORY_PRE = "event_inventory_pre";


    /*
    Warehouse Inventory
    */
    public static final String WAREHOUSE_INVENTORY = "event_warehouse_inventory";
    public static final String WAREHOUSE_INVENTORY_PRE = "event_warehouse_inventory_pre";

     /*
    Inventory Item
     */
    public static final String INVENTORY_ITEM = "event_inventory_item";

    /*
    variant
     */
    public static final String VARIANT = "event_variant";
}

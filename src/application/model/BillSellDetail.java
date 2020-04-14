package application.model;

import javafx.scene.Parent;

/**
 * 销售单明细
 */
public class BillSellDetail {
    private int id;
    private BillMain billMain;
    private Parent parent;
    private Warehouse warehouse;
    private Book book;
    private int book_quantity;
    private double sell_price;
    private double cost_price;
    private double discount_price;
    private double sell_amount;
    private double cost_amount;
    private double discount_amount;

}

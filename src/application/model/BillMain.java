package application.model;

import javafx.scene.Parent;

import java.util.Date;

/**
 * 单据表头
 */
public class BillMain {
    private int id;
    private Warehouse in_warehouse;
    private Warehouse out_warehouse;
    private Parent parent;
    private String bill_type;
    private int recores_count;
    private double bill_sell_amount;
    private double bill_amount;
    private double cash;
    private double bank;
    private String bill_state;
    private Date date_create;
    private Date date_update;
    private Date date_account;
}

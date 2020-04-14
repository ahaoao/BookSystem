package application.model;

import java.util.Date;

/**
 * 图书模型
 */
public class Book {
    private int id;
    private String name;
    private String author;
    private String category;
    private String barcode;
    private String publisher;//出版商
    private double price;
    private Date date_publish;
    private Date date_create;
    private Date date_update;
}

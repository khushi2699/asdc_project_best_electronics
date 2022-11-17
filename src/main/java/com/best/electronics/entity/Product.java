package com.best.electronics.entity;

import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;

import javax.validation.constraints.NotEmpty;

import java.io.Serializable;

@Entity
@Data
@Table(name = "products")
@NoArgsConstructor
public class Product implements Serializable {

    private static final long serialVersionUID = 4887904943282174032L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long productId;

    @NotEmpty
    private String productName;

    @NotEmpty
    private String lName;

    private float price;
    private String description;
    private int categoryId;
    private int orderItemId;

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemagestioninventario;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 *
 * @author Ancor
 */
@XmlRootElement(name = "product")
public class Product implements Serializable{

    private int id;
    private String name;
    private int quantity;
    private float price;
    private String category;

    public Product(int id, String name, int quantity, float price, String category) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.category = category;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setCategory(String category) {
        this.category = category;
    }

        @XmlElement    
    public int getId() {
        return id;
    }
    
    @XmlElement
    public String getName() {
        return name;
    }

    @XmlElement
    public int getQuantity() {
        return quantity;
    }

    @XmlElement
    public float getPrice() {
        return price;
    }

    @XmlElement
    public String getCategory() {
        return category;
    }

}

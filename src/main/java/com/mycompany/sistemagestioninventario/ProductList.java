package com.mycompany.sistemagestioninventario;


import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jorge
 */
@XmlRootElement(name = "products")
public class ProductList {

    private List<Product> products;

    public ProductList() {
        this.products = new ArrayList<>();
    }

    @XmlElement(name = "product")
    public List<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }
}

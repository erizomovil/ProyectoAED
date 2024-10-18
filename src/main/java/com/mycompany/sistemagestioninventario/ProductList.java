package com.mycompany.sistemagestioninventario;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jorge
 */
@XmlRootElement(name = "products")
public class ProductList implements Serializable{

    private List<Product> products;

    public ProductList() {
        this.products = new ArrayList<>();
    }
    
    public void addList(List<Product> x){
        products = x;
    }

    @XmlElement(name = "product")
    public List<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public Product getProductAt(int index) {
        if (index >= 0 && index < products.size()) {
            return products.get(index);
        } else {
            new ErrorHandler("Índice fuera de rango.", null);
            return null;
        }
    }

    public void updateProductAt(int index, Product updatedProduct) {
        if (index >= 0 && index < products.size()) {
            products.set(index, updatedProduct);
        } else {
            new ErrorHandler("Índice fuera de rango.", null);
        }
    }
}

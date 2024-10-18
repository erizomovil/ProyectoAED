/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemagestioninventario;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author Ancor
 */
public class TransformData {

    public void ObjectToCSV(ProductList productList, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {

            for (Product product : productList.getProducts()) {
                writer.append(String.valueOf(product.getId()))
                        .append(",")
                        .append(product.getName())
                        .append(",")
                        .append(String.valueOf(product.getQuantity()))
                        .append(",")
                        .append(String.valueOf(product.getPrice()))
                        .append(",")
                        .append(product.getCategory())
                        .append("\n");
            }
        } catch (IOException e) {
            new ErrorHandler("Error converting CSV file", e);
        }
    }

    public void ObjectToXML(ProductList productList, String filePath) {
        try {
            JAXBContext context = JAXBContext.newInstance(ProductList.class, Product.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            marshaller.marshal(productList, new File(filePath));
        } catch (JAXBException e) {
            new ErrorHandler("Error converting XML file", e);
        }
    }

    public void ObjectToJSON(ProductList productList, String filePath) {
        try {
            Gson gson = new Gson();
            FileWriter writer = new FileWriter(filePath);
            gson.toJson(productList, writer);
            writer.close();
        } catch (Exception e) {
            new ErrorHandler("Error converting JSON file", e);
        }
    }

    public void ObjectTODat(ProductList productList, String filePath) {
        try (FileOutputStream fileOut = new FileOutputStream(filePath); ObjectOutputStream out = new ObjectOutputStream(fileOut)) {

            out.writeObject(productList);

        } catch (IOException e) {
            new ErrorHandler("Error converting to DAT file ", e);
        }
    }

    public static List<Product> CSVToObject(String filePath) {
        List<Product> products = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)))) {
            String linea;

            while ((linea = br.readLine()) != null) {

                String[] valores = linea.split(",");

                if (valores.length == 5) {

                    int id = Integer.parseInt(valores[0].trim());
                    String name = valores[1].trim();
                    int quantity = Integer.parseInt(valores[2].trim());
                    float price = Float.parseFloat(valores[3].trim());
                    String category = valores[4].trim();

                    Product product = new Product(id, name, quantity, price, category);
                    products.add(product);
                }
            }
        } catch (Exception e) {
            new ErrorHandler("Error reading CSV file", e);
        }

        return products;
    }

    public static List<Product> JsonToObject(String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {

            File jsonFile = new File(filePath);
            JsonNode rootNode = objectMapper.readTree(jsonFile);
            JsonNode productsNode = rootNode.get("products");

            return objectMapper.convertValue(productsNode, new TypeReference<List<Product>>() {
            });

        } catch (IOException e) {
            new ErrorHandler("Error reading JSON file", e);
        }

        return null;
    }

    public static List<Product> XMLToObject(String filePath) {
        try {
            File xmlFile = new File(filePath);
            JAXBContext context = JAXBContext.newInstance(ProductList.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            ProductList productList = (ProductList) unmarshaller.unmarshal(xmlFile);
            return productList.getProducts();
        } catch (JAXBException e) {
            new ErrorHandler("Error reading XML file", e);
            return null;
        }
    }

    public static List<Product> DatToObject(String filePath) {
        List<Product> list = null;
        try (FileInputStream fileIn = new FileInputStream(filePath); ObjectInputStream in = new ObjectInputStream(fileIn)) {

            ProductList productList = (ProductList) in.readObject();
            list = productList.getProducts();

        } catch (IOException | ClassNotFoundException e) {
            new ErrorHandler("Error reading DAT file ", e);
        }
        return list;
    }
}

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
import java.io.ObjectInputStream;

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
            System.out.println("Conversión completa: " + filePath + " generado.");
        } catch (IOException e) {
            new ErrorHandler("Error en la conversion a CSV ", e);
        }
    }

    public void ObjectToXML(ProductList productList, String filePath) {
        try {
            JAXBContext context = JAXBContext.newInstance(ProductList.class, Product.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            marshaller.marshal(productList, new File(filePath));

            System.out.println("Conversión completa: " + filePath + " generado.");

        } catch (JAXBException e) {
            new ErrorHandler("Error en la conversion a XML ", e);
        }
    }

    public void ObjectToJSON(ProductList productList, String filePath) {
        try {
            Gson gson = new Gson();
            FileWriter writer = new FileWriter(filePath);
            gson.toJson(productList, writer);
            writer.close();
            System.out.println("Conversión de objetos a JSON completada.");
        } catch (Exception e) {
            new ErrorHandler("Error en la conversion a JSON ", e);
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
            System.out.println("Lectura de CSV completada.");
        } catch (Exception e) {
            new ErrorHandler("Error en la lectura de CSV ", e);
        }

        return products;
    }

    public static List<Product> JsonToObject(String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {

            File jsonFile = new File(filePath);
            JsonNode rootNode = objectMapper.readTree(jsonFile);
            JsonNode productsNode = rootNode.get("Product");

            return objectMapper.convertValue(productsNode, new TypeReference<List<Product>>() {
            });

        } catch (IOException e) {
            new ErrorHandler("Error en la lectura de JSON ", e);
        }

        return null;
    }

    public static List<Product> XMLToObject(String xmlFilePath) {
        try {
            File xmlFile = new File(xmlFilePath);
            JAXBContext context = JAXBContext.newInstance(ProductList.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            ProductList productList = (ProductList) unmarshaller.unmarshal(xmlFile);
            return productList.getProducts();
        } catch (JAXBException e) {
            new ErrorHandler("Error en la lectura de XML ", e);
            return null;
        }
    }
}


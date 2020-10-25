package com.detection.utils;

import java.util.HashMap;
import java.util.UUID;

/**
 * @Author vinhnp
 * @create 23/10/2020
 */
public class Broker {
    private String name;
    private String addressCity;
    private HashMap<String, Integer> products;

    public Broker(String address_city, HashMap<String, Integer> products, String name) {
        if (address_city == null) {
            addressCity = "";
        } else {
            this.addressCity = address_city;
        }
        if (products == null) {
            this.products = new HashMap<String, Integer>();
        } else {
            this.products = new HashMap<String, Integer>(products);
        }
        if (name == null || name.isEmpty()) {
            this.name = UUID.randomUUID().toString();
        } else {
            this.name = name;
        }

    }


    public String getName() {
        return name;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public HashMap<String, Integer> getProducts() {
        return products;
    }

    public void setProducts(HashMap<String, Integer> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Broker{" +
                "name='" + name + '\'' +
                ", addressCity='" + addressCity + '\'' +
                ", products=" + products.toString() +
                '}';
    }
}

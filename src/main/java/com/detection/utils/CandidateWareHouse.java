package com.detection.utils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @Author vinhnp
 * @create 23/10/2020
 */
public class CandidateWareHouse {

    private Broker customer;
    private Broker wareHouse;
    private List<Broker> combinationWarehouse;
    private boolean isSameAddressCity = false;
    private boolean isEnoughProduct = false;
    private HashMap<String, Integer> productMatching = new HashMap<String, Integer>();
    private HashMap<String, Integer> productTaking = new HashMap<String, Integer>();

    public CandidateWareHouse(Broker customer, Broker wareHouse) {
        this.customer = new Broker(customer.getAddressCity(), customer.getProducts(), customer.getName());
        this.wareHouse = new Broker(wareHouse.getAddressCity(), wareHouse.getProducts(), wareHouse.getName());
        this.selfCheck();
    }


    public void selfCheck() {
        if (wareHouse.getAddressCity().equals(customer.getAddressCity())) {
            isSameAddressCity = true;
        }
        for (Map.Entry<String, Integer> entryCustomer : customer.getProducts().entrySet()) {
            String productCustomer = entryCustomer.getKey();
            int quantityCustomer = entryCustomer.getValue();
            if (quantityCustomer > 0 && wareHouse.getProducts().containsKey(productCustomer)) {
                int quantityWareHouse = wareHouse.getProducts().get(productCustomer);
                productMatching.put(productCustomer, quantityWareHouse);
                wareHouse.getProducts().remove(productCustomer);
                if (quantityCustomer > quantityWareHouse) {
                    customer.getProducts().put(productCustomer, quantityCustomer - quantityWareHouse);
                    productTaking.put(productCustomer, quantityCustomer - quantityWareHouse);
                } else {
                    customer.getProducts().put(productCustomer, 0);
                    productTaking.put(productCustomer, quantityCustomer);
                }
            }
        }
        if (countProduct(customer.getProducts()) == 0) {
            this.isEnoughProduct = true;
        }
    }

    public void setWareHouse(Broker wareHouse) {
        this.wareHouse = wareHouse;
    }

    public HashMap<String, Integer> getProductTaking() {
        return productTaking;
    }

    public void setProductTaking(HashMap<String, Integer> productTaking) {
        this.productTaking = productTaking;
    }

    public Broker getWareHouse() {
        return wareHouse;
    }

    public void setCombinationWarehouse(List<Broker> combinationWarehouse) {
        this.combinationWarehouse = combinationWarehouse;
    }

    public LinkedList<Broker> getCombinationWarehouse() {
        return new LinkedList<Broker>(combinationWarehouse);
    }

    public int countProduct(HashMap<String, Integer> products) {
        int result = 0;
        if (products != null && !products.isEmpty()) {
            for (Map.Entry<String, Integer> entry : products.entrySet()) {
                result += entry.getValue();
            }
        }
        return result;
    }

    public int getProductRemainCount() {
        return countProduct(this.wareHouse.getProducts());
    }

    public int getProductMatchingCount() {
        return countProduct(this.productMatching);
    }

    public HashMap<String, Integer> getProductMatching() {
        return productMatching;
    }

    public void setProductMatching(HashMap<String, Integer> productMatching) {
        this.productMatching = productMatching;
    }

    public Broker getCustomer() {
        return customer;
    }

    public void setCustomer(Broker customer) {
        this.customer = customer;
    }

    public boolean isSameAddressCity() {
        return isSameAddressCity;
    }

    public void setSameAddressCity(boolean sameAddressCity) {
        isSameAddressCity = sameAddressCity;
    }

    public boolean isEnoughProduct() {
        return isEnoughProduct;
    }

    public void setEnoughProduct(boolean enoughProduct) {
        isEnoughProduct = enoughProduct;
    }

}

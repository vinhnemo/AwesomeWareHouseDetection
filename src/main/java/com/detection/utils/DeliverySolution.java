package com.detection.utils;

import java.util.LinkedList;
import java.util.List;

/**
 * @Author vinhnp
 * @create 23/10/2020
 */
public class DeliverySolution {
    private Broker customer;
    private List<CandidateWareHouse> candidateWareHouseList;
    private int warehouseAvailableProductCount;
    private int warehouseRemainProductCount;
    private boolean stopFlat = false;
    private LinkedList<MsgData> child;

    public DeliverySolution(Broker customer) {
        this.customer = customer;
    }

    public DeliverySolution(Broker customer, List<CandidateWareHouse> candidateWareHouseList) {
        this.customer = new Broker(customer.getAddressCity(), customer.getProducts(), customer.getName());
        this.candidateWareHouseList = new LinkedList<CandidateWareHouse>(candidateWareHouseList);
    }


    public LinkedList<MsgData> addChild(MsgData msgData) {
        if (child == null) {
            child = new LinkedList<MsgData>();
        }
        if (msgData != null) {
            child.addLast(msgData);
        }
        return child;
    }

    public LinkedList<MsgData> getChild() {
        return child;
    }

    public void setChild(LinkedList<MsgData> child) {
        this.child = child;
    }

    public int getWarehouseAvailableProductCount() {
        return warehouseAvailableProductCount;
    }

    public void setWarehouseAvailableProductCount(int warehouseAvailableProductCount) {
        this.warehouseAvailableProductCount = warehouseAvailableProductCount;
    }

    public int getWarehouseRemainProductCount() {
        return warehouseRemainProductCount;
    }

    public void setWarehouseRemainProductCount(int warehouseRemainProductCount) {
        this.warehouseRemainProductCount = warehouseRemainProductCount;
    }

    public boolean isStopFlat() {
        return stopFlat;
    }

    public void setStopFlat(boolean stopFlat) {
        this.stopFlat = stopFlat;
    }

    public Broker getCustomer() {
        return customer;
    }

    public void setCustomer(Broker customer) {
        this.customer = customer;
    }

    public List<CandidateWareHouse> getCandidateWareHouseList() {
        return candidateWareHouseList;
    }

    public void setCandidateWareHouseList(List<CandidateWareHouse> candidateWareHouseList) {
        this.candidateWareHouseList = candidateWareHouseList;
    }
}

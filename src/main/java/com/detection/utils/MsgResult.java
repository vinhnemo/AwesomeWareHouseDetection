package com.detection.utils;

import java.util.LinkedList;

/**
 * @Author vinhnp
 * @create 25/10/2020
 */
public class MsgResult {

    private LinkedList<DeliverySolution> deliverySolutionList = new LinkedList<>();
    private int sameCityCount = 0;
    private int warehouseCount = 0;
    private int productRemainCount = 0; // product remain after customer taking
    private int productMatching = 0; // product matching with user's order
    private int hasDiffCity = 0;

    public MsgResult(DeliverySolution deliverySolutionList) {
        addDeliverySolution(deliverySolutionList);
    }

    public MsgResult(MsgResult msgResult) {
        this.deliverySolutionList = new LinkedList<>(msgResult.getDeliverySolutionList());
        this.sameCityCount = msgResult.getSameCityCount();
        this.warehouseCount = msgResult.getWarehouseCount();
        this.productRemainCount = msgResult.getProductRemainCount();
        this.productMatching = msgResult.getProductMatching();
        this.hasDiffCity = msgResult.getHasDiffCity();
    }

    public MsgResult() {
    }

    public void addDeliverySolution(DeliverySolution deliverySolution) {
        if (deliverySolutionList == null) {
            deliverySolutionList = new LinkedList<>();
        }
        deliverySolutionList.addLast(deliverySolution);
        CandidateWareHouse candidateWareHouse = deliverySolution.getCandidateWareHouseList().get(0);
        if (candidateWareHouse.isSameAddressCity()) {
            sameCityCount++;
        }
        warehouseCount++;
        productRemainCount += candidateWareHouse.getProductRemainCount();
        productMatching += candidateWareHouse.getProductMatchingCount();
        hasDiffCity = warehouseCount - sameCityCount;
    }

    public int getHasDiffCity() {
        return hasDiffCity;
    }

    public void setHasDiffCity(int hasDiffCity) {
        this.hasDiffCity = hasDiffCity;
    }

    public int getProductMatching() {
        return productMatching;
    }

    public void setProductMatching(int productMatching) {
        this.productMatching = productMatching;
    }

    public LinkedList<DeliverySolution> getDeliverySolutionList() {
        return deliverySolutionList;
    }

    public void setDeliverySolutionList(LinkedList<DeliverySolution> deliverySolutionList) {
        this.deliverySolutionList = deliverySolutionList;
    }

    public int getSameCityCount() {
        return sameCityCount;
    }

    public int SameCityCount() {
        return ++sameCityCount;
    }

    public void setSameCityCount(int sameCityCount) {
        this.sameCityCount = sameCityCount;
    }

    public int getWarehouseCount() {
        return warehouseCount;
    }

    public void setWarehouseCount(int warehouseCount) {
        this.warehouseCount = warehouseCount;
    }

    public int getProductRemainCount() {
        return productRemainCount;
    }

    public void setProductRemainCount(int productRemainCount) {
        this.productRemainCount = productRemainCount;
    }
}

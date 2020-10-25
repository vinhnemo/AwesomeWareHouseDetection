package com.detection.utils;

import java.util.LinkedList;
import java.util.List;

/**
 * @Author vinhnp
 * @create 24/10/2020
 */
public class MsgData {

    private final Broker customer;
    private LinkedList<Broker> availableWarehouse;
    private List<CandidateWareHouse> candidateWareHouseList;
    private List<DeliverySolution> deliverySolutionList;

    public MsgData(Broker customer, LinkedList<Broker> availableWarehouse) {
        this.customer = customer;
        this.availableWarehouse = availableWarehouse;
    }

    public List<CandidateWareHouse> getCandidateWareHouseList() {
        return candidateWareHouseList;
    }

    public void setCandidateWareHouseList(List<CandidateWareHouse> candidateWareHouseList) {
        this.candidateWareHouseList = candidateWareHouseList;
    }

    public List<DeliverySolution> getDeliverySolutionList() {
        return deliverySolutionList;
    }

    public void setDeliverySolutionList(LinkedList<DeliverySolution> deliverySolutionList) {
        this.deliverySolutionList = deliverySolutionList;
    }

    public Broker getCustomer() {
        return customer;
    }

    public LinkedList<Broker> getAvailableWarehouse() {
        LinkedList<Broker> brokerArrayList = new LinkedList<Broker>();
        for (Broker broker : availableWarehouse) {
            brokerArrayList.add(new Broker(broker.getAddressCity(), broker.getProducts(), broker.getName()));
        }
        return brokerArrayList;
    }

    public LinkedList<Broker> removeAvailableWarehouse(Broker broker) {
        return removeBrokerFromList(availableWarehouse, broker);
    }

    public LinkedList<Broker> removeBrokerFromList(LinkedList<Broker> brokerArrayList, Broker broker) {
        for (Broker a : brokerArrayList) {
            if (a.getName().equals(broker.getName())) {
                brokerArrayList.remove(a);
                break;
            }
        }
        return brokerArrayList;
    }

    public void setAvailableWarehouse(LinkedList<Broker> availableWarehouse) {
        this.availableWarehouse = availableWarehouse;
    }
}

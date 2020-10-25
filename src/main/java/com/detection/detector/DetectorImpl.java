package com.detection.detector;

import com.detection.utils.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @Author vinhnp
 * @create 24/10/2020
 */
public class DetectorImpl extends BaseDetector {


    public MsgData initCandidate(MsgData msgData) {
        LinkedList<CandidateWareHouse> candidateWareHouses = new LinkedList<>();
        LinkedList<Broker> brokerArrayList = msgData.getAvailableWarehouse();
        for (int i = 0; i < brokerArrayList.size(); i++) {
            Broker broker = brokerArrayList.get(i);
            CandidateWareHouse candidateWareHouse = new CandidateWareHouse(msgData.getCustomer(), broker);
            if (candidateWareHouse.getProductMatchingCount() > 0) {
                if (candidateWareHouse.isSameAddressCity()) {
                    candidateWareHouses.addFirst(candidateWareHouse);
                    if (candidateWareHouse.isEnoughProduct()) {
                        msgData.removeAvailableWarehouse(candidateWareHouse.getWareHouse());
                    }
                } else {
                    candidateWareHouses.addLast(candidateWareHouse);
                }
            }
        }
        msgData.setCandidateWareHouseList(candidateWareHouses);
        return msgData;
    }

    public MsgData processCandidateWarehouses(MsgData msgData) {
        LinkedList<DeliverySolution> deliverySolutions = new LinkedList<>();
        for (CandidateWareHouse candidateWareHouse : msgData.getCandidateWareHouseList()) {
            DeliverySolution deliverySolution = new DeliverySolution(candidateWareHouse.getCustomer(), Arrays.asList(candidateWareHouse));
            deliverySolution.setWarehouseAvailableProductCount(candidateWareHouse.getProductMatchingCount());
            deliverySolution.setWarehouseRemainProductCount(candidateWareHouse.getProductRemainCount());
            if (candidateWareHouse.isEnoughProduct()) {
                deliverySolution.setStopFlat(true);
            } else {
                candidateWareHouse.setCombinationWarehouse(msgData.removeBrokerFromList(msgData.getAvailableWarehouse(), candidateWareHouse.getWareHouse()));
            }
            if (candidateWareHouse.isSameAddressCity()) {
                deliverySolutions.addFirst(deliverySolution);
            } else {
                deliverySolutions.addLast(deliverySolution);
            }
        }
        msgData.setDeliverySolutionList(deliverySolutions);
        return msgData;
    }

    protected MsgData combinationCandidate(MsgData msgData) {
        List<DeliverySolution> deliverySolutions = new LinkedList<>(msgData.getDeliverySolutionList());
        for (DeliverySolution deliverySolution : deliverySolutions) {
            if (!deliverySolution.isStopFlat()) {
                deliverySolution.addChild(combine(deliverySolution));
            }
        }
        return msgData;
    }

    protected MsgData combine(DeliverySolution deliverySolution) {
        MsgData msgData = null;
        LinkedList<CandidateWareHouse> candidateWareHouseList = (LinkedList<CandidateWareHouse>) deliverySolution.getCandidateWareHouseList();
        if (candidateWareHouseList != null && candidateWareHouseList.size() > 0) {
            CandidateWareHouse candidateWareHouse = candidateWareHouseList.getLast();
            if (candidateWareHouse != null) {
                List<Broker> brokerList = candidateWareHouse.getCombinationWarehouse();
                if (brokerList != null && brokerList.size() > 0) {
                    msgData = new MsgData(deliverySolution.getCustomer(), (LinkedList<Broker>) brokerList);
                    msgData = initCandidate(msgData);
                    msgData = processCandidateWarehouses(msgData);
                }
            }
        }
        return combinationCandidate(msgData);
    }

    public MsgResult takeSolutionDelivery(MsgData msgData) {

        LinkedList<DeliverySolution> deliverySolutions = (LinkedList<DeliverySolution>) msgData.getDeliverySolutionList();

        Queue<MsgResult> deliverySolutionQueue = new LinkedList<>();
        for (DeliverySolution deliverySolution : deliverySolutions) {
            deliverySolutionQueue.add(new MsgResult(deliverySolution));
        }
        MsgResult result = new MsgResult();
        while (deliverySolutionQueue.size() > 0) {
            MsgResult msgResult = deliverySolutionQueue.remove();
            DeliverySolution deliverySolution = msgResult.getDeliverySolutionList().getLast();
            if (deliverySolution.isStopFlat()) {
                if (result.getDeliverySolutionList() == null || result.getDeliverySolutionList().isEmpty()) {
                    result = msgResult;
                } else {
                    // >1 = combine , 0 != combine
                    if (result.getWarehouseCount() >= msgResult.getWarehouseCount()) {
                        if (msgResult.getHasDiffCity() < result.getHasDiffCity()) {
                            result = msgResult;
                        } else if (msgResult.getHasDiffCity() == result.getHasDiffCity()) {
                            if (msgResult.getWarehouseCount() < result.getWarehouseCount()) {
                                result = msgResult;
                            } else if (msgResult.getWarehouseCount() == result.getWarehouseCount()) {
                                if (msgResult.getProductMatching() > result.getProductMatching()) {
                                    result = msgResult;
                                } else if (msgResult.getProductMatching() == result.getProductMatching()) {
                                    if (msgResult.getProductRemainCount() >= result.getProductRemainCount()) {
                                        result = msgResult;
                                    }
                                }
                            }
                        }
                    } else {
                        // 1 or 0
                        if (msgResult.getSameCityCount() > result.getSameCityCount()) {
                                result = msgResult;
                        } else if (msgResult.getSameCityCount() == result.getSameCityCount()) {
                            if (msgResult.getWarehouseCount() < result.getWarehouseCount()) {
                                if (msgResult.getProductMatching() > result.getProductMatching()) {
                                    result = msgResult;
                                } else if (msgResult.getProductMatching() == result.getProductMatching()) {
                                    if (msgResult.getProductRemainCount() >= result.getProductRemainCount()) {
                                        result = msgResult;
                                    }
                                }
                            }
                        }

                    }

                }
            } else {
                LinkedList<MsgData> linkedList = deliverySolution.getChild();
                if (linkedList != null) {
                    for (DeliverySolution data : linkedList.getLast().getDeliverySolutionList()) {
                        MsgResult newMsgResult1 = new MsgResult(msgResult);
                        newMsgResult1.addDeliverySolution(data);
                        deliverySolutionQueue.add(newMsgResult1);
                    }
                }

            }


        }


        return result;

        // TODO validate

        // TODO: count diff city : 0 is better  *1
        // TODO: count brokers : smaller is better   *2
        // TODO: sum product matching : bigger is better  *3
        // TODO: sum remain product of each broker: smaller is better *optional

    }
}

package com.detection.detector;

import com.detection.utils.Broker;
import com.detection.utils.MsgData;
import com.detection.utils.MsgResult;

import java.util.LinkedList;
import java.util.List;

/**
 * @Author vinhnp
 * @create 23/10/2020
 */
public abstract class BaseDetector implements IDetector, ITakeDeliverySolution {

    public MsgResult detectDeliverySolution(Broker customer, List<Broker> availableWarehouse) {
        if (customer != null && (availableWarehouse != null || !availableWarehouse.isEmpty())) {
            MsgData msgData = new MsgData(customer, (LinkedList<Broker>) availableWarehouse);
            MsgData initCandidateMsgData = initCandidate(msgData);
            if (initCandidateMsgData.getCandidateWareHouseList() != null && initCandidateMsgData.getCandidateWareHouseList().size() > 0) {
                MsgData deliverySolutionsMsgData = processCandidateWarehouses(initCandidateMsgData);
                if (deliverySolutionsMsgData.getDeliverySolutionList() != null && deliverySolutionsMsgData.getDeliverySolutionList().size() > 0) {
                    return takeSolutionDelivery(deliverySolutionsMsgData);
                }
            }
        }
        return null;
    }
}

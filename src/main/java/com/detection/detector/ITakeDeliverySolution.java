package com.detection.detector;

import com.detection.utils.Broker;
import com.detection.utils.MsgResult;

import java.util.List;

/**
 * @Author vinhnp
 * @create 23/10/2020
 */
public interface ITakeDeliverySolution {

    MsgResult detectDeliverySolution(Broker customer, List<Broker> availableWarehouse);
}

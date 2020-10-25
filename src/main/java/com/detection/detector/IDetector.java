package com.detection.detector;

import com.detection.utils.MsgData;
import com.detection.utils.MsgResult;

/**
 * @Author vinhnp
 * @create 23/10/2020
 */
public interface IDetector {

    MsgData initCandidate(MsgData msgData);

    MsgData processCandidateWarehouses(MsgData msgData);

    MsgResult takeSolutionDelivery(MsgData msgData);
}

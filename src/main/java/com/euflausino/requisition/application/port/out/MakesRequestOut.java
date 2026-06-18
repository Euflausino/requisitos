package com.euflausino.requisition.application.port.out;

import com.euflausino.requisition.application.model.RequestModel;
import com.euflausino.requisition.application.model.ResponseModel;

public interface MakesRequestOut {
    ResponseModel send(RequestModel request);
}

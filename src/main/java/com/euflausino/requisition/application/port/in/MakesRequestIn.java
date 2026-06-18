package com.euflausino.requisition.application.port.in;

import com.euflausino.requisition.application.model.RequestModel;
import com.euflausino.requisition.application.model.ResponseModel;

public interface MakesRequestIn {
    ResponseModel send(RequestModel request);
}

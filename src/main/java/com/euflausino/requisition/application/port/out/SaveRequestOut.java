package com.euflausino.requisition.application.port.out;

import com.euflausino.requisition.application.model.RequestModel;

public interface SaveRequestOut {
    RequestModel save(RequestModel request);
}

package com.euflausino.requisition.application.port.in;

import com.euflausino.requisition.application.model.RequestModel;

public interface SaveRequestIn {
    RequestModel save(RequestModel requestModel);
}

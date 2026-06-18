package com.euflausino.requisition.application.usecase;


import com.euflausino.requisition.application.model.RequestModel;
import com.euflausino.requisition.application.model.ResponseModel;
import com.euflausino.requisition.application.port.in.SaveRequestIn;
import com.euflausino.requisition.application.port.in.MakesRequestIn;
import com.euflausino.requisition.application.port.out.MakesRequestOut;
import com.euflausino.requisition.application.port.out.SaveRequestOut;

public class RequestUseCase implements SaveRequestIn, MakesRequestIn {

    private final SaveRequestOut saveRequestOut;
    private final MakesRequestOut makesRequest;

    public RequestUseCase(SaveRequestOut saveRequestOut, MakesRequestOut makesRequest) {
        this.saveRequestOut = saveRequestOut;
        this.makesRequest = makesRequest;
    }

    @Override
    public RequestModel save(RequestModel requestModel) {
        return saveRequestOut.save(requestModel);
    }

    @Override
    public ResponseModel send(RequestModel request) {
        return makesRequest.send(request);
    }
}

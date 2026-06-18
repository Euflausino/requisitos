package com.euflausino.requisition.adapter.out.repository;

import com.euflausino.requisition.application.model.RequestModel;
import com.euflausino.requisition.application.port.out.SaveRequestOut;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.nio.file.Path;

@Service
public class SaveRequest implements SaveRequestOut {

    private final ObjectMapper mapper = new ObjectMapper();

    public SaveRequest(){
    }

    @Override
    public RequestModel save(RequestModel request){
         final Path file = SavePath.requestFile(request.getName());
         mapper.writerWithDefaultPrettyPrinter().writeValue(file.toFile(), request);
         return request;
    }

}

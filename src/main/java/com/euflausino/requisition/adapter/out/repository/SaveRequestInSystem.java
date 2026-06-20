package com.euflausino.requisition.adapter.out.repository;

import com.euflausino.requisition.application.model.RequestModel;
import com.euflausino.requisition.application.port.out.SaveRequestOut;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.nio.file.Path;

@Service
public class SaveRequestInSystem implements SaveRequestOut {

    private final ObjectMapper mapper = new ObjectMapper();

    public SaveRequestInSystem(){
    }

    @Override
    public RequestModel save(RequestModel request){
         final Path file = PathForSaveInSystem.requestFile(request.getName());
         mapper.writerWithDefaultPrettyPrinter().writeValue(file.toFile(), request);
         return request;
    }

}

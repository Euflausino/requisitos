package com.euflausino.requisition.adapter.out.repository;

import com.euflausino.requisition.application.model.CollectionModel;
import com.euflausino.requisition.application.port.out.SaveCollectionOut;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.nio.file.Path;

@Service
public class SaveCollection implements SaveCollectionOut {

    private final ObjectMapper mapper = new ObjectMapper();

    public SaveCollection() {
    }

    @Override
    public CollectionModel save(CollectionModel request) {
        final Path file = SavePath.collectionFile(request.getName());
        mapper.writerWithDefaultPrettyPrinter().writeValue(file.toFile(), request);
        return request;
    }
}

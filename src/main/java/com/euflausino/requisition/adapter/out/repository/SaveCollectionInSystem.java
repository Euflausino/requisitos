package com.euflausino.requisition.adapter.out.repository;

import com.euflausino.requisition.application.model.CollectionModel;
import com.euflausino.requisition.application.port.out.SaveCollectionOut;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.nio.file.Path;

@Service
public class SaveCollectionInSystem implements SaveCollectionOut {

    private final ObjectMapper mapper = new ObjectMapper();

    public SaveCollectionInSystem() {
    }

    @Override
    public CollectionModel save(CollectionModel request) {
        final Path file = PathForSaveInSystem.collectionFile(request.getName());
        mapper.writerWithDefaultPrettyPrinter().writeValue(file.toFile(), request);
        return request;
    }
}

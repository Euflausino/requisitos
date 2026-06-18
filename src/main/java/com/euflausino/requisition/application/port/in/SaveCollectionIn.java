package com.euflausino.requisition.application.port.in;

import com.euflausino.requisition.application.model.CollectionModel;

public interface SaveCollectionIn {
    CollectionModel save(CollectionModel collection);
}

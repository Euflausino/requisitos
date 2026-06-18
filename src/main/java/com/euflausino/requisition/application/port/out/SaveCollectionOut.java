package com.euflausino.requisition.application.port.out;

import com.euflausino.requisition.application.model.CollectionModel;

public interface SaveCollectionOut {
    CollectionModel save(CollectionModel collection);
}

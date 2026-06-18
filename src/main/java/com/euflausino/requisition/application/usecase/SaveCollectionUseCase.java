package com.euflausino.requisition.application.usecase;

import com.euflausino.requisition.application.model.CollectionModel;
import com.euflausino.requisition.application.port.in.SaveCollectionIn;
import com.euflausino.requisition.application.port.out.SaveCollectionOut;

public class SaveCollectionUseCase implements SaveCollectionIn {

    private final SaveCollectionOut saveCollectionOut;

    public SaveCollectionUseCase(SaveCollectionOut saveCollectionOut) {
        this.saveCollectionOut = saveCollectionOut;
    }

    @Override
    public CollectionModel save(CollectionModel collection) {
        return saveCollectionOut.save(collection);
    }
}

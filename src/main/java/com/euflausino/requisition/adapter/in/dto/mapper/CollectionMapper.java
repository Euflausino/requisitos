package com.euflausino.requisition.adapter.in.dto.mapper;

import com.euflausino.requisition.adapter.in.dto.CollectionDTO;
import com.euflausino.requisition.application.model.CollectionModel;

public class CollectionMapper {

    public static CollectionModel mapCollectionDTOToModel(CollectionDTO collectionDto) {
        return new CollectionModel(
                collectionDto.name(),
                collectionDto.requests().stream()
                        .map(RequestMapper::mapRequestDTOToModel)
                        .toList()
        );
    }
    public static CollectionDTO mapModelToCollectionDTO(CollectionModel colecao) {
        return new CollectionDTO(
                colecao.getName(),
                colecao.getRequests().stream()
                        .map(RequestMapper::mapRequestModelToDTO)
                        .toList()
        );
    }

}

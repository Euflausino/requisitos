package com.euflausino.requisition.adapter.in.controller;

import com.euflausino.requisition.adapter.in.dto.CollectionDTO;
import com.euflausino.requisition.adapter.in.dto.mapper.CollectionMapper;
import com.euflausino.requisition.application.port.in.SaveCollectionIn;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/colecao")
public class CollectionController {

    private final SaveCollectionIn saveCollection;

    public CollectionController(SaveCollectionIn saveCollection) {
        this.saveCollection = saveCollection;
    }

    @PostMapping
    public ResponseEntity<CollectionDTO> saveCollection(@RequestBody CollectionDTO collectionDto) throws IOException {

        return ResponseEntity.ok(CollectionMapper.mapModelToCollectionDTO(
                    saveCollection.save(
                            CollectionMapper.mapCollectionDTOToModel(collectionDto)
                    )
                )
        );
    }

}

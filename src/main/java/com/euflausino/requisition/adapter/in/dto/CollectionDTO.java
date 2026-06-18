package com.euflausino.requisition.adapter.in.dto;

import java.util.List;

public record CollectionDTO(

        String name,
        List<RequestDTO> requests

) {
}

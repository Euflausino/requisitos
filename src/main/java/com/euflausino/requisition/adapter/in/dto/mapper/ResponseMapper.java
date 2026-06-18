package com.euflausino.requisition.adapter.in.dto.mapper;

import com.euflausino.requisition.adapter.in.dto.ResponseDTO;
import com.euflausino.requisition.application.model.ResponseModel;

public class ResponseMapper {
    public static ResponseDTO toDTO(ResponseModel respostaEntity) {
        return new ResponseDTO(
                respostaEntity.getStatus(),
                respostaEntity.getStatusText(),
                respostaEntity.getSuccessful(),
                respostaEntity.getHeaders(),
                respostaEntity.getBody(),
                respostaEntity.getResponseTimeMs(),
                respostaEntity.getBodySizeBytes(),
                respostaEntity.getHeadersSizeBytes()
        );
    }
}

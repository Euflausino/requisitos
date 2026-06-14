package com.euflausino.requisition.dto.mapper;

import com.euflausino.requisition.dto.DTOResposta;
import com.euflausino.requisition.entity.RespostaEntity;

public class RetornoMapper {
    public static DTOResposta entidadeParaDTO(RespostaEntity respostaEntity) {
        return new DTOResposta(
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

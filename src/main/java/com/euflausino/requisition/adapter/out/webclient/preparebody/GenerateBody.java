package com.euflausino.requisition.adapter.out.webclient.preparebody;

import com.euflausino.requisition.adapter.out.webclient.dto.OutCompletedRequestBodyDTO;

import com.euflausino.requisition.adapter.out.webclient.dto.OutRequestModelDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface GenerateBody {
    OutCompletedRequestBodyDTO generate(OutRequestModelDTO outRequestModelDTO) throws JsonProcessingException;
}

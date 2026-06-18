package com.euflausino.requisition.adapter.in.dto;

import java.util.Map;

public record GraphQLBodyDTO(
         String query,
         Map<String, Object>variables
){
}

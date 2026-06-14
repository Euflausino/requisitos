package com.euflausino.requisition.dto;

import java.util.Map;

public record DTOGraphQLBody (
         String query,
         Map<String, Object>variables
){
}

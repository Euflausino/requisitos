package com.euflausino.requisition.adapter.out.webclient.dto.mapper;

import com.euflausino.requisition.adapter.out.webclient.dto.OutGraphQLBodyDTO;
import com.euflausino.requisition.adapter.out.webclient.dto.OutRequestBodyDTO;
import com.euflausino.requisition.adapter.out.webclient.dto.OutRequestModelDTO;
import com.euflausino.requisition.adapter.out.webclient.dto.enums.OutRequestBodyTypeDTO;
import com.euflausino.requisition.application.model.GraphQLBodyModel;
import com.euflausino.requisition.application.model.RequestBodyModel;
import com.euflausino.requisition.application.model.RequestModel;
import com.euflausino.requisition.application.model.ResponseModel;
import com.euflausino.requisition.application.model.enums.RequestBodyTypeModel;
import org.springframework.web.reactive.function.client.ClientResponse;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebClientMapper {

    public static ResponseModel mapClientResponseToResponseModel(ClientResponse response, String body, long start, long end) {

        int status = response.statusCode().value();
        String statusText = response.statusCode().toString();
        boolean successful = status >= 200 && status < 300;

        Map<String, List<String>> headers = new HashMap<>();
        response.headers().asHttpHeaders().forEach((k, v) -> headers.put(k, List.copyOf(v)));

        long sizeBody = body != null ? body.getBytes(StandardCharsets.UTF_8).length : 0;
        long sizeHeader = headers.toString().getBytes(StandardCharsets.UTF_8).length;
        long responseTimeMs = end - start;

        return new ResponseModel(
                status,
                statusText,
                successful,
                headers,
                body,
                responseTimeMs,
                sizeBody,
                sizeHeader
        );
    }

    public static OutRequestModelDTO mapRequestModelToDTO(RequestModel outRequestModelDTO) {
        return new OutRequestModelDTO(
                outRequestModelDTO.getName(),
                outRequestModelDTO.getMethod(),
                outRequestModelDTO.getUrl(),
                outRequestModelDTO.getHeaders(),
                mapRequestBodyTypeToDTO(outRequestModelDTO.getBodyType()),
                mapRequestBodyToDTO(outRequestModelDTO.getBody()),
                outRequestModelDTO.getQueryParams()
        );
    }

    private static OutRequestBodyTypeDTO mapRequestBodyTypeToDTO (RequestBodyTypeModel requestBodyTypeModel) {
        return OutRequestBodyTypeDTO.valueOf(requestBodyTypeModel.toString());
    }

    private static OutRequestBodyDTO mapRequestBodyToDTO (RequestBodyModel body) {
        if (body == null){
            return null;
        }
        return new OutRequestBodyDTO(
                body.getRaw(),
                body.getFormData(),
                body.getBinary(),
                mapGraphQLBodyToDTO(body.getGraphQL())
        );
    }

    private static OutGraphQLBodyDTO mapGraphQLBodyToDTO(GraphQLBodyModel graphQLBodyModel) {
        if (graphQLBodyModel == null) {
            return null;
        }
        return new OutGraphQLBodyDTO(
                graphQLBodyModel.getQuery(),
                graphQLBodyModel.getVariables()
        );
    }

}


package com.euflausino.requisition.service;

import com.euflausino.requisition.entity.ColecaoEntity;
import com.euflausino.requisition.util.CaminhoDeArmazenamento;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Path;

@Service
public class SalvarColecaoService {

    private final ObjectMapper mapper = new ObjectMapper();

    public SalvarColecaoService() throws IOException {
    }

    public ColecaoEntity salvar(ColecaoEntity request) throws IOException {
        final Path file = CaminhoDeArmazenamento.collectionFile(request.getNome());
        mapper.writerWithDefaultPrettyPrinter().writeValue(file.toFile(), request);
        return request;
    }
}

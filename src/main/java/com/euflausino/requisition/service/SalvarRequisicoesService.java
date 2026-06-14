package com.euflausino.requisition.service;

import com.euflausino.requisition.util.CaminhoDeArmazenamento;
import com.euflausino.requisition.entity.RequisicaoEntity;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Path;

@Service
public class SalvarRequisicoesService {

    private final ObjectMapper mapper = new ObjectMapper();

    public SalvarRequisicoesService() throws IOException {
    }

    public RequisicaoEntity salvar(RequisicaoEntity request) throws IOException {
         final Path file = CaminhoDeArmazenamento.requestFile(request.getNome());
         mapper.writerWithDefaultPrettyPrinter().writeValue(file.toFile(), request);
         return request;
    }

}

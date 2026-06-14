package com.euflausino.requisition.controller;

import com.euflausino.requisition.dto.DTORequisicao;
import com.euflausino.requisition.dto.DTOResposta;
import com.euflausino.requisition.dto.mapper.RequisicaoMapper;
import com.euflausino.requisition.dto.mapper.RetornoMapper;
import com.euflausino.requisition.service.EnviaRequisicaoEspecificadaWebClientService;
import com.euflausino.requisition.service.SalvarRequisicoesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/requisicao")
public class RequisicaoController {

    private final EnviaRequisicaoEspecificadaWebClientService enviaRequisicaoEspecificadaService;
    private final SalvarRequisicoesService salvarRequisicoesService;

    public RequisicaoController(EnviaRequisicaoEspecificadaWebClientService enviaRequisicaoEspecificadaService, SalvarRequisicoesService salvarRequisicoesService) {
        this.enviaRequisicaoEspecificadaService = enviaRequisicaoEspecificadaService;
        this.salvarRequisicoesService = salvarRequisicoesService;
    }

    @PostMapping("/enviar")
    public ResponseEntity<DTOResposta> enviarRequisicao(@RequestBody DTORequisicao request) throws IOException {
        return ResponseEntity.ok(
                RetornoMapper.entidadeParaDTO(
                        enviaRequisicaoEspecificadaService.enviar(RequisicaoMapper.DTORequisicaoParaEntidade(request))
                )
        );
    }

    @PostMapping("/salvar")
    public ResponseEntity<DTORequisicao> salvarRequisicao(@RequestBody DTORequisicao requisicao) throws IOException {
        return ResponseEntity.ok(
                RequisicaoMapper.entidadeRequisicaoParaDTO(
                        salvarRequisicoesService.salvar(RequisicaoMapper.DTORequisicaoParaEntidade(requisicao))
                )
        );
    }

}

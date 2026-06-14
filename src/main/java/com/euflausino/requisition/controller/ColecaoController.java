package com.euflausino.requisition.controller;

import com.euflausino.requisition.dto.DTOColecao;
import com.euflausino.requisition.dto.mapper.ColecaoMapper;
import com.euflausino.requisition.service.SalvarColecaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/colecao")
public class ColecaoController {

    private final SalvarColecaoService salvarColecaoService;

    public ColecaoController(SalvarColecaoService salvarColecaoService) {
        this.salvarColecaoService = salvarColecaoService;
    }

    @PostMapping
    public ResponseEntity<DTOColecao> salvarColecao(@RequestBody DTOColecao dtoColecao) throws IOException {

        return ResponseEntity.ok(ColecaoMapper.mapEntityDTOColecao(
                    salvarColecaoService.salvar(
                            ColecaoMapper.mapDTOColecaoParaEntity(dtoColecao)
                    )
                )
        );
    }

}

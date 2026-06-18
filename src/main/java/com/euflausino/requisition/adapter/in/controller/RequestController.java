package com.euflausino.requisition.adapter.in.controller;

import com.euflausino.requisition.adapter.in.dto.RequestDTO;
import com.euflausino.requisition.adapter.in.dto.ResponseDTO;
import com.euflausino.requisition.adapter.in.dto.mapper.RequestMapper;
import com.euflausino.requisition.adapter.in.dto.mapper.ResponseMapper;
import com.euflausino.requisition.adapter.out.repository.SaveRequest;
import com.euflausino.requisition.application.port.in.MakesRequestIn;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/requisicao")
public class RequestController {

    private final MakesRequestIn sendRequest;
    private final SaveRequest saveRequest;

    public RequestController(MakesRequestIn sendRequest, SaveRequest saveRequest) {
        this.sendRequest = sendRequest;
        this.saveRequest = saveRequest;
    }

    @PostMapping("/enviar")
    public ResponseEntity<ResponseDTO> sendRequest(@RequestBody RequestDTO request) throws IOException {
        return ResponseEntity.ok(
                ResponseMapper.toDTO(
                        sendRequest.send(RequestMapper.mapRequestDTOToModel(request))
                )
        );
    }

    @PostMapping("/salvar")
    public ResponseEntity<RequestDTO> saveRequest(@RequestBody RequestDTO requisicao) throws IOException {
        return ResponseEntity.ok(
                RequestMapper.mapRequestModelToDTO(
                        saveRequest.save(RequestMapper.mapRequestDTOToModel(requisicao))
                )
        );
    }

}

package com.euflausino.requisition.adapter.in.controller;

import com.euflausino.requisition.adapter.in.dto.RequestDTO;
import com.euflausino.requisition.adapter.in.dto.ResponseDTO;
import com.euflausino.requisition.adapter.in.dto.mapper.RequestMapper;
import com.euflausino.requisition.adapter.in.dto.mapper.ResponseMapper;
import com.euflausino.requisition.adapter.out.repository.SaveRequestInSystem;
import com.euflausino.requisition.application.port.in.MakesRequestIn;
import com.euflausino.requisition.application.port.in.SaveRequestIn;
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
    private final SaveRequestIn saveRequestInSystem;

    public RequestController(MakesRequestIn sendRequest, SaveRequestIn saveRequestInSystem) {
        this.sendRequest = sendRequest;
        this.saveRequestInSystem = saveRequestInSystem;
    }

    @PostMapping("/enviar")
    public ResponseEntity<ResponseDTO> sendRequest(@RequestBody RequestDTO request){
        return ResponseEntity.ok(
                ResponseMapper.toDTO(
                        sendRequest.send(RequestMapper.mapRequestDTOToModel(request))
                )
        );
    }

    @PostMapping("/salvar")
    public ResponseEntity<RequestDTO> saveRequest(@RequestBody RequestDTO requisicao){
        return ResponseEntity.ok(
                RequestMapper.mapRequestModelToDTO(
                        saveRequestInSystem.save(RequestMapper.mapRequestDTOToModel(requisicao))
                )
        );
    }

}

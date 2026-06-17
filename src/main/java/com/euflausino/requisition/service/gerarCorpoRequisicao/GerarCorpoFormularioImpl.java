package com.euflausino.requisition.service.gerarCorpoRequisicao;

import com.euflausino.requisition.entity.CorpoRequisicaoPreparadoEntity;
import com.euflausino.requisition.entity.RequisicaoEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Component
public class GerarCorpoFormularioImpl implements GerarCorpo {

    /**
     * Prepara corpo de formulário: form-urlencoded ou form-data (multipart).
     */
    @Override
    public CorpoRequisicaoPreparadoEntity gerar(RequisicaoEntity requisicao) {
        MultiValueMap<String, String> formulario = new LinkedMultiValueMap<>();

        if (requisicao.getBody() != null && requisicao.getBody().getFormData() != null) {
            requisicao.getBody().getFormData()
                    .forEach((chave, valor) -> formulario.add(chave, String.valueOf(valor)));
        }

        String tipoConteudo = ExtrairTipoConteudo.extrair(requisicao.getHeaders(), requisicao.getBodyType().getType());

        return new CorpoRequisicaoPreparadoEntity(formulario, tipoConteudo);
    }
}

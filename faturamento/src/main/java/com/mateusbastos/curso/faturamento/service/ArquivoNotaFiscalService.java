package com.mateusbastos.curso.faturamento.service;

import com.mateusbastos.curso.faturamento.model.Pedido;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Service
public class ArquivoNotaFiscalService { // CONTINUAR NA AULA 60
    @Value("${classpath:reports/nota-fiscal.jrxml}")
    private Resource templateNotaFiscal;

    @Value("${classpath:reports/logo.png}")
    private Resource logo;

    public byte[] gerarNota(Pedido dadosPedido) {
        try (InputStream inputStream = templateNotaFiscal.getInputStream()) {

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("NOME", dadosPedido.cliente().nome());
            parameters.put("CPF", dadosPedido.cliente().cpf());
            parameters.put("LOGRADOURO", dadosPedido.cliente().logradouro());
            parameters.put("NUMERO", dadosPedido.cliente().numero());
            parameters.put("EMAIL", dadosPedido.cliente().email());
            parameters.put("BAIRRO", dadosPedido.cliente().bairro());
            parameters.put("TELEFONE", dadosPedido.cliente().telefone());

            parameters.put("DATA_PEDIDO", dadosPedido.data());
            parameters.put("TOTAL_PEDIDO", dadosPedido.total());

            var dataSource = new JRBeanCollectionDataSource(dadosPedido.itens());

            JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            return JasperExportManager.exportReportToPdf(jasperPrint);
        }  catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

package com.lex.controller;

import com.lex.domain.Compra;
import com.lex.domain.Venda;
import com.lex.service.MercadoService;
import com.lex.to.CompraPedido;
import com.lex.to.VendaPedido;
import com.lex.to.VendaResposta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mercados")
public class MercadoController {
    @Autowired
    private MercadoService service;

    @PostMapping("/vendas")
    public ResponseEntity<VendaResposta> lancar(@RequestBody VendaPedido pedido) {
        if (pedido == null) {
            throw new RuntimeException();
        }

        Venda venda = service.lancar(pedido);

        VendaResposta resposta = VendaResposta.builder()
                .mensagem( "venda realizada " + venda.getId())
                .build();

        return new ResponseEntity<VendaResposta>( resposta, HttpStatus.OK );
    }

    @PostMapping("/compras")
    public ResponseEntity<String> comprar(@RequestBody CompraPedido pedido) {
        Compra compra = this.service.comprar(pedido);
        return new ResponseEntity<String>( "", HttpStatus.CREATED );
    }



    public void setService(MercadoService service) {
        this.service = service;
    }
}

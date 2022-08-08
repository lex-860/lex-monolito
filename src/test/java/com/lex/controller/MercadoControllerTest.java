package com.lex.controller;

import com.lex.service.MercadoService;
import com.lex.stubs.ComposicaoVenda;
import com.lex.stubs.FabricaObjetos;
import com.lex.to.VendaResposta;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static java.util.List.of;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest()
@TestPropertySource(locations = "classpath:application-test.properties")
public class MercadoControllerTest {

    @Autowired
    private MercadoController controller;

    private MercadoService service;

    @Before
    public void before() {
        this.service = Mockito.mock( MercadoService.class);
        controller.setService( this.service );
    }

    @Test(expected = RuntimeException.class)
    public void quandoPedidoVendaNulo_LancaRuntimeException() {
        this.controller.lancar(null);
    }

    @Test
    public void vendaValidaEsperado_MensagemSucessoComHttp201() {
        ComposicaoVenda com = FabricaObjetos.vendaSimples();

        Mockito.when( service.lancar( com.getPedido() )).thenReturn( com.getVenda() );
        ResponseEntity<VendaResposta> response = controller.lancar( com.getPedido() );

        assertEquals( response.getStatusCode().value(), HttpStatus.CREATED);
        VendaResposta vendaResposta = response.getBody();
        assertEquals("venda realizada " + com.getVenda().getId(), vendaResposta.getMensagem());

        Mockito.verify( service ).lancar( com.getPedido() ); // alvo aconteceu - dado o que foi preparado
    }

}
package com.lex;

import com.lex.controller.UsuarioController;
import com.lex.service.client.ApiCotacaoClient;
import com.lex.service.client.CotacaoMoeda;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJson;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest()
@AutoConfigureStubRunner(ids = "com.lex:lex-monolito:+:stubs:8085", stubsMode = StubRunnerProperties.StubsMode.LOCAL, deleteStubsAfterTest = true)
@TestPropertySource(locations = "classpath:application-test.properties")
@RunWith(SpringRunner.class)
@AutoConfigureJson
@AutoConfigureMockMvc
public class UsuarioControllerTest extends ContractVerifierBase {

    @Autowired private ApiCotacaoClient apiCotacaoClient;

    @Test public void cotacaoBitcoinSucesso() throws InterruptedException {
        CotacaoMoeda cotacao = apiCotacaoClient.moeda("BTC-USD");
        assertEquals("BTC", cotacao.getCode());
        // e por ai vai ..
    }

}
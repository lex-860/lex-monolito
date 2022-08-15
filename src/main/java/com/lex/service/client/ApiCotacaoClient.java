package com.lex.service.client;

import com.lex.configuration.ApiFeignBasicConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(url = "${app.cotacao.url}", value = "cotacao", configuration = ApiFeignBasicConfiguration.class)
public interface ApiCotacaoClient {

    // https://economia.awesomeapi.com.br/BTC-USD
    @GetMapping(value = "/{moeda}", consumes = "application/json", produces = "application/json")
    CotacaoMoeda moeda(@PathVariable("moeda") String moeda);
    
}
package com.lex;

import com.lex.domain.Moeda;
import com.lex.repository.MoedaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataloaderApplication implements ApplicationRunner {

    @Autowired MoedaRepository moedaRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Moeda moeda = new Moeda("Dollar").brazao("$").sigla("US$");
        moedaRepository.save(moeda);
    }

}

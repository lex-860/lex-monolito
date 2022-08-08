package com.lex.service;

import com.lex.domain.Moeda;
import com.lex.repository.MoedaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MoedaService {

    @Autowired private MoedaRepository repository;

    public Moeda buscarPelaSigla(String sigla) {
        return this.repository.findBySigla(sigla).orElseThrow();
    }

}
package com.lex.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
@Data
@Builder
@AllArgsConstructor
@Entity
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Moeda moeda;

    @OneToMany
    private List<Transacao> transacoes;

    public Livro() {
        this.transacoes = new ArrayList<Transacao>();
    }

    public boolean foiSalvo() {
        return ( this.getId() == null );
    }

    public Livro adicionar(Transacao tx) {
        if ( tx == null ) {
            throw new RuntimeException();
        }

        if ( this.transacoes.contains(tx) ) {
            throw new RuntimeException();
        }

        this.transacoes.add(tx);
        return this;
    }

}
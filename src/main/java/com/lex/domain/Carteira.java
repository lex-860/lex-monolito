package com.lex.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.beans.BeanProperty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("JpaAttributeTypeInspection")
@Data
@Builder
@AllArgsConstructor
@Entity
public class Carteira implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private Double saldo;

    @OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    private Moeda moeda;

    @ManyToOne(targetEntity = Transacao.class)
    private List<Transacao> transacoes;

    public Carteira() {
        this.transacoes = new ArrayList<Transacao>();
    }

    public Carteira adicionar(Transacao tx) {
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
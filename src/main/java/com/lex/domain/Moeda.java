package com.lex.domain;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Accessors(chain = true, fluent = true)
@NoArgsConstructor
@Entity
public class Moeda implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String brazao;
    private String sigla; // TODO deve ser unica

    public Moeda(@NonNull String nome) {
        this.nome = nome;
    }

}

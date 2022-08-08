package com.lex.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@SuppressWarnings("JpaAttributeTypeInspection")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double quantidade;
    private Double valor;
    private MercadoOperacao operacao;

    @ManyToOne
    private Usuario usuario;

    @ManyToOne
    private Moeda moeda;

}
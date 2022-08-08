package com.lex.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Transacao { // desnormalizado por performance

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime data;
    private Double valor;
    private Double quantidade;
    private String moeda; // sigla moeda
    private String comprador; // username
    private String vendedor; // username

    // desnormalizada por criterios de performance
    private Long idCompra;
    private Long idVenda;

}
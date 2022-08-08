package com.lex.domain;

import com.lex.domain.Carteira;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Corretora {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double taxa;

    @OneToOne
    private Carteira carteiraPrincipal; // fiduciaria

    @OneToMany
    private List<Carteira> carteiras;

}

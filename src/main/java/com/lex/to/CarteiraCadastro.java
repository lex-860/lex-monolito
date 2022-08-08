package com.lex.to;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarteiraCadastro {

    private String nome; // carteira
    private String moeda; // sigla

}
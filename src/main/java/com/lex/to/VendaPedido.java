package com.lex.to;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VendaPedido {

    private String usuario; // TODO remover e pegar do contexto spring do usuario logado
    private String nome;
    private String moeda;
    private Double quantidade;
    private Double valor;

}
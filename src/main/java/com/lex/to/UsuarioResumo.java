package com.lex.to;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResumo {

    private String nome;
    private String userName;
    private String email;

    // TODO imprimir informando
    // 1 carteira fiduciaria ativa com saldo X
    // X carteiras criptomoedas indicando abaixo
    //    moeda A com quantidade X
    //    moeda B com quantidade Y

    // TODO historico fase R3
    // indicar apenas resumo para cada carteira acima que possui historico de transacoes

}
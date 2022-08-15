package com.lex.to;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDados {

    private String nome;
    private String userName;
    private String email;
    private String pix;
    // TODO incluir resumo da carteira

}

package com.lex.to;

import com.lex.domain.Carteira;
import com.lex.domain.Moeda;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioCadastro {

    private String nome;
    private String userName;
    private String senha;
    private String email;

}
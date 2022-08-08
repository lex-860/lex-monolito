package com.lex.stubs;

import com.lex.domain.Mercado;
import com.lex.domain.Usuario;
import com.lex.domain.Venda;
import com.lex.to.VendaPedido;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComposicaoVenda {

    Usuario usuario;
    Mercado corretora;
    VendaPedido pedido;
    Venda venda;

}
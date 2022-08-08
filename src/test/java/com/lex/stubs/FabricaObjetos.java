package com.lex.stubs;

import com.lex.domain.*;
import com.lex.to.VendaPedido;

import java.util.List;

public class FabricaObjetos {

    public static Usuario usuario200LexCoin() {
        Usuario usuario = Usuario.builder().nome("Rafael").build();
        Carteira principal = Carteira.builder().build();
        Moeda lexcoin = new Moeda("lexcoin");
        Carteira carteiraLexcon = Carteira.builder()
                .nome("Lexcoin")
                .moeda(lexcoin)
                .saldo(200d)
                .build();
        return usuario;
    }

    public static Mercado corretoraLivroLexcoin() {
        Moeda lexcoin = new Moeda("lexcoin");
        Livro livro = Livro.builder().moeda(lexcoin).build();
        return Mercado.builder()
                .livros( List.of(livro) )
                .build();
    }

    public static VendaPedido vendaRequest(String usuario, String moeda, Double qtde, Double valor) {
        return VendaPedido.builder()
                .usuario(usuario)
                .moeda(moeda)
                .quantidade(qtde)
                .valor(valor)
                .build();
    }

    public static ComposicaoVenda vendaSimples() {
        Usuario usuario = FabricaObjetos.usuario200LexCoin();
        Mercado corretora = FabricaObjetos.corretoraLivroLexcoin();

        VendaPedido pedido = VendaPedido.builder()
                .nome( usuario.getNome() )
                .moeda("lexcoin")
                .valor(0.50)
                .quantidade( 40d )
                .build();

        Venda venda = Venda.builder()
                .usuario(usuario)
                .id(1L)
                .moeda( new Moeda("lexcoin") )
                .quantidade( pedido.getQuantidade())
                .valor(pedido.getValor())
                .build();

        ComposicaoVenda composicao = ComposicaoVenda.builder()
                .usuario(usuario)
                .corretora(corretora)
                .pedido(pedido)
                .venda(venda)
                .build();

        return composicao;
    }



}
package com.lex.domain;

import java.time.LocalDateTime;
import java.util.function.Supplier;

public abstract class MercadoConfirmacao {

    private Mercado mercado;
    private Livro livro;

    private Transacao transacao;

    public MercadoConfirmacao(Mercado mercado) {
        this.mercado = mercado;
    }
    abstract void registrar(Transacao transacao);
    public Transacao confirmar(Compra compra) {
        Moeda moeda = compra.getVenda().getMoeda();
        this.livro = getLivro(moeda);

        Transacao transacao = Transacao.builder()
                .data( LocalDateTime.now() )
                .valor( compra.getValor() )
                .quantidade( compra.getQuantidade() )
                .moeda( moeda.sigla() )
                .comprador( compra.getUsuario().getUsername() )
                .vendedor( compra.getVenda().getUsuario().getUsername() )
                .idCompra( compra.getId() )
                .idVenda( compra.getVenda().getId() )
                .build();



        return transacao;
    }

    private Livro getLivro(Moeda moeda) {
        Supplier<Livro> fornecedor = new Supplier<Livro>() {
            @Override
            public Livro get() {
                return Livro.builder().moeda(moeda).build();
            }
        };

        Livro livro = this.mercado.getLivros().stream()
                .filter( l -> l.getMoeda().equals(moeda) )
                .findFirst()
                .orElseGet( fornecedor );

        if ( !livro.foiSalvo() ) {
            this.mercado.adicionar(livro);
        }

        return livro;
    }

}
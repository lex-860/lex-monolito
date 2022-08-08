package com.lex.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@SuppressWarnings("ALL")
@Data
@Builder
@AllArgsConstructor
@Entity
public class Mercado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @OneToMany
    private List<Livro> livros;

    public Mercado() {
        this.livros = new ArrayList<Livro>();
    }

    // TODO avaliar salvar transacao como append independente de entidades
    public Transacao confirmar(Compra compra) {
        Moeda moeda = compra.getVenda().getMoeda();

        Livro livro = getLivro(moeda);

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

    public Mercado confirmada(Transacao transacao, Moeda moeda) {
        Livro livro = getLivro(moeda);
        livro.adicionar(transacao);
        return this;
    }

    private Livro getLivro(Moeda moeda) {
        Supplier<Livro> fornecedor = new Supplier<Livro>() {
            @Override
            public Livro get() {
                return Livro.builder().moeda(moeda).build();
            }
        };

        Livro livro = this.livros.stream()
                .filter( l -> l.getMoeda().equals(moeda) )
                .findFirst()
                .orElseGet( fornecedor );

        if ( !livro.foiSalvo() ) {
            this.adicionar(livro);
        }

        return livro;
    }

    public Mercado adicionar(Livro livro) {
        if ( livro == null ) {
            throw new RuntimeException();
        }

        if ( this.livros.contains(livro) ) {
            throw new RuntimeException();
        }

        this.livros.add(livro);
        return this;
    }

}

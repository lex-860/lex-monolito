package com.lex.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@SuppressWarnings("JpaAttributeTypeInspection")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double quantidade; // TODO estamos considerando que a compra e venda é de 1x1
    private Double valor; // idem .. por este motivo estes dados estao iguais com a venda
    private MercadoOperacao operacao;

    @ManyToOne
    private Usuario usuario;

    @OneToOne
    private Venda venda;

    public void realizar(Venda venda) {
        venda.setOperacao(MercadoOperacao.REALIZADO);
        this.operacao = MercadoOperacao.REALIZADO;
    }

}
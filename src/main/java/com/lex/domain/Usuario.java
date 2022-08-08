package com.lex.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("JpaAttributeTypeInspection")
@Data
@Builder
@AllArgsConstructor
@Entity
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String username; // atributo mapeado no spring security - sign up (cadastro)
    private String email;

    private String pix; // abc4as : server para voce mandar para alguem

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="carteira_principal_id", referencedColumnName = "id")
    private Carteira carteiraPrincipal; // fiduciaria

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name="carteira_id", referencedColumnName = "id")
    private List<Carteira> carteiras;

    public Usuario() {
        this.carteiras = new ArrayList<Carteira>();
    }

    public static Carteira novaCarteira(String nome, Moeda moeda) {
        return Carteira.builder().nome(nome)
                .moeda(moeda)
                .saldo(0d)
                .build();
    }

    public void adicionar(Carteira carteira) {
        this.carteiras.add( carteira );
    }

    public Carteira getCarteira(Moeda moeda) {
        return this.carteiras.stream()
                .filter( c -> c.getMoeda().equals(moeda))
                .findFirst().orElse( null );
    }

    public Usuario registrar(Transacao tx, Moeda moeda) {
        Carteira carteira = this.carteiras.stream()
                .filter( c -> c.getMoeda().equals(moeda))
                .findFirst().orElseThrow();

        carteira.adicionar(tx);
        return this;
    }

}

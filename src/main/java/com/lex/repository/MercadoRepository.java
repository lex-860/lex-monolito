package com.lex.repository;


import com.lex.domain.Mercado;
import com.lex.domain.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MercadoRepository extends JpaRepository<Mercado, Long> {

    @Query("SELECT l FROM Mercado m LEFT JOIN m.livros l WHERE l.moeda.nome = :moeda")
    Livro findByLivroMoeda(@Param(("moeda")) String moeda);

}

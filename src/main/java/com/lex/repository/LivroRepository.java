package com.lex.repository;

import com.lex.domain.Livro;
import com.lex.domain.Moeda;
import com.lex.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {

    Optional<Livro> findByMoeda(Moeda moeda);

}

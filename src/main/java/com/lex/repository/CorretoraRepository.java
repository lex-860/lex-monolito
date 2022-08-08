package com.lex.repository;

import com.lex.domain.Corretora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CorretoraRepository extends JpaRepository<Corretora, Long> {



}

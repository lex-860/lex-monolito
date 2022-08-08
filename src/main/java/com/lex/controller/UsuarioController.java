package com.lex.controller;

import com.lex.domain.Usuario;
import com.lex.service.ContaService;
import com.lex.to.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private ContaService service;

    @PostMapping
    public ResponseEntity<Usuario> salvar(@RequestBody UsuarioCadastro cadastro) {
        Usuario usuario = service.salvar(cadastro);
        return new ResponseEntity<Usuario>( usuario, HttpStatus.CREATED );
    } // TODO verificar porque moeda vem com cochetes vazio mas instanciada

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> detalhar(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<Usuario>( service.buscar(id), HttpStatus.OK );
    }

    @GetMapping("/{id}/resumo")
    public ResponseEntity<UsuarioResumo> resumo(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<UsuarioResumo>( service.resumo(id), HttpStatus.OK );
    }

    @PostMapping("/{id}/carteiras")
    public ResponseEntity<String> carteira(@PathVariable(value = "id") Long idUsuario, @RequestBody CarteiraCadastro cadastro) {
        this.service.salvar(idUsuario, cadastro); // TODO refatorar para pegar usuario logado do spring e remover parametro
        return new ResponseEntity<String>( "sucesso", HttpStatus.CREATED );
    }

    @GetMapping("/{id}/carteiras/{moeda}")
    public ResponseEntity<String> carteira(@PathVariable(value = "id") Long idUsuario, @PathVariable(value = "moeda") String moeda) {
        String retorno = service.obterCarteira(idUsuario, moeda);
        System.out.println( retorno ); // TODO pendente implementacao
        return new ResponseEntity<String>( retorno, HttpStatus.CREATED ); // TODO pendente
    }

}
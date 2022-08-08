package com.lex.service;

import com.lex.domain.Carteira;
import com.lex.domain.Moeda;
import com.lex.domain.Usuario;
import com.lex.repository.UsuarioRepository;
import com.lex.service.client.ApiCotacaoClient;
import com.lex.to.CarteiraCadastro;
import com.lex.to.UsuarioCadastro;
import com.lex.to.UsuarioResumo;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Random;

@Service
public class ContaService {

    @Autowired private UsuarioRepository usuarioRepository;

    @Autowired private MoedaService moedaService;

    @Autowired private ApiCotacaoClient apiCotacaoClient;

    public Usuario salvar(Usuario usuario) {
        this.usuarioRepository.save(usuario); return usuario;
    }

    public Usuario salvar(UsuarioCadastro cadastro) {

        Moeda moeda = moedaService.buscarPelaSigla("US$"); // TODO pegar fiat padrao - criar metodo

        Usuario usuario = Usuario.builder()
                .nome( cadastro.getNome() )
                .username( cadastro.getUserName() )
                .email( cadastro.getEmail() )
                .pix( gerarNovoPix() )
                .carteiraPrincipal( Usuario.novaCarteira("fiat dolar", moeda) ) // TODO pegar nome do request?
                .build();

        this.usuarioRepository.save( usuario );

        return usuario;
    }

    public Usuario buscar(Long id) {
        return usuarioRepository.findById(id).orElseThrow(); // TODO refactor para excecao especifica
    }

    public Usuario buscar(String nome) {
        return usuarioRepository.findByNome(nome).orElseThrow(); // TODO idem acima
    }

    public UsuarioResumo resumo(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(); // TODO idem acima

        UsuarioResumo resumo = UsuarioResumo.builder()
                .nome( usuario.getNome() )
                .email( usuario.getEmail())
                .userName( usuario.getUsername() )
                .build();

        return resumo;
    }

    public void salvar(Long idUsuario, CarteiraCadastro cadastro) {
        Usuario usuario = this.buscar(idUsuario);

        Moeda moeda = this.moedaService.buscarPelaSigla( cadastro.getMoeda() );

        Carteira carteira = Carteira.builder()
                .moeda( moeda )
                .nome( cadastro.getNome() )
                .build();

        usuario.adicionar( carteira );

        this.usuarioRepository.save( usuario );
    }

    public String obterCarteira(Long idUsuario, String moeda) {
        // TODO procurar a carteira dessa moeda
        String json = apiCotacaoClient.moeda(moeda);
        return json;
    }

    private String gerarNovoPix() {
        return RandomStringUtils.randomAlphanumeric(16);
    }

}
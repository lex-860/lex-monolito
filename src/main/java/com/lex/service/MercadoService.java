package com.lex.service;

import com.lex.domain.*;
import com.lex.repository.*;
import com.lex.to.CompraPedido;
import com.lex.to.VendaPedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class MercadoService {

    @Autowired private MercadoRepository mercadoRepository;

    @Autowired private VendaRepository vendaRepository;

    @Autowired private CompraRepository compraRepository;

    @Autowired private LivroRepository livroRepository;

    @Autowired private CorretoraRepository corretoraRepository;

    @Autowired private TransacaoRepository transacaoRepository;

    @Autowired private ContaService contaService;

    public Venda salvar(Venda venda) {
        return vendaRepository.save(venda);
    }

    public Venda lancar(VendaPedido pedido) {
        Usuario usuario = null; //contaService.buscar(pedido.getUsuario());

        Carteira carteira = usuario.getCarteiras().stream()
                .filter(c -> c.getMoeda().nome().equals(pedido.getMoeda()))
                .findFirst()
                .orElseThrow();

        if ( carteira.getSaldo() < pedido.getQuantidade() ) {
            throw new RuntimeException(); // TODO business exception .. exceptionHandler jogar 422
        }

        Venda venda = Venda.builder()
                .usuario(usuario)
                .moeda(carteira.getMoeda())
                .quantidade(pedido.getQuantidade())
                .valor(pedido.getValor())
                .build();

        vendaRepository.save(venda);

        return venda;
    }

    @Transactional
    public Compra comprar(CompraPedido pedido) {
        // logica toda aqui para simplificar -- refatorar a seguir
        // p1 - obtendo dados
        Usuario comprador = null; contaService.buscar(pedido.getUsuario());
        Venda venda = vendaRepository.findById( pedido.getIdPedido() ).orElseThrow();
        Corretora corretora = corretoraRepository.findById(1L).orElseThrow(); // TODO inicialmente 1 apenas
        Usuario vendedor = venda.getUsuario();
        Moeda moeda = venda.getMoeda();

        // p2 - validando se venda ainda em aberto
        if ( venda.getOperacao().equals(MercadoOperacao.ABERTO) )
            throw new RuntimeException(); // TODO business por exceptionhandler e controller 422 status

        // p3 - obtendo carteiras
        Carteira carteiraPrincipalVendedor = venda.getUsuario().getCarteiraPrincipal();
        Carteira carteiraPrincipalComprador = comprador.getCarteiraPrincipal();
        Carteira carteiraComprador = comprador.getCarteira( venda.getMoeda() );
        Carteira carteiraVendedor = venda.getUsuario().getCarteira( venda.getMoeda() );

        // p4 - validando saldo vendedor
        if ( carteiraVendedor.getSaldo() < venda.getQuantidade() )
            throw new RuntimeException(); // TODO business por exceptionhandler e controller 422 status

        // p5 - validando saldo comprador
        Double valorTotal = venda.getValor() * venda.getQuantidade();
        Double valorTaxa = valorTotal * corretora.getTaxa();
        Double valorCompra = valorTotal + valorTaxa;
        if ( carteiraPrincipalComprador.getSaldo() < valorTotal )
            throw new RuntimeException(); // TODO business por exceptionhandler e controller 422 status

        // p6 - troca fiduciaria das carteiras incluindo taxas
        Double saldoFC = carteiraPrincipalComprador.getSaldo();
        carteiraPrincipalComprador.setSaldo( saldoFC - valorTotal );
        Double saldoFV = carteiraPrincipalVendedor.getSaldo();
        carteiraPrincipalVendedor.setSaldo( saldoFV + valorCompra - valorTaxa );

        // p7 - troca criptomoedas entre carteiras
        Double saldoCC = carteiraComprador.getSaldo();
        carteiraComprador.setSaldo( saldoCC + venda.getQuantidade() );
        Double saldoCV = carteiraVendedor.getSaldo();
        carteiraVendedor.setSaldo( saldoCV - venda.getQuantidade() );

        // p8 - pagando taxa de corretagem veiculada por moeda fiduciaria de ambos = comprador + vendedor = *2
        Double saldoCorretora = corretora.getCarteiraPrincipal().getSaldo();
        corretora.getCarteiraPrincipal().setSaldo( saldoCorretora + (valorTaxa * 2) );

        // p9 - formulando a compra
        Compra compra = Compra.builder()
                .venda( venda )
                .quantidade( venda.getQuantidade() ) // TODO combinamos que o mesmo valor anunciado e nao partes disto
                .valor( venda.getValor() ) // idem acima
                .build();

        // p10 - contemplando status de ambos, compra e venda para realizados
        compra.realizar(venda);
        this.compraRepository.save(compra);
        this.vendaRepository.save(venda);

        // p11 - atualizando saldo apos pagamento taxas
        this.corretoraRepository.save(corretora);

        // p12 - registro no livro
        Mercado mercado = this.mercadoRepository.findById(1L).orElseThrow(); // TODO idem corretora .. iniciando com uma unica fixa
        Transacao transacao = mercado.confirmar( compra );
        Transacao txSalva = this.transacaoRepository.save(transacao);

        // p13 - atualizando a transacao ja incluida no livro, do passo anterior
        mercado.confirmada(txSalva, venda.getMoeda());
        this.mercadoRepository.save( mercado );

        // p14 - atualizando carteiras usuario
        this.contaService.salvar( comprador.registrar(txSalva, moeda) );
        this.contaService.salvar( vendedor.registrar(txSalva, moeda) );

        // p15 - sucesso!
        return compra;
    }

    public void setMercadoRepository(MercadoRepository mercadoRepository) {
        this.mercadoRepository = mercadoRepository;
    }

    public void setContaService(ContaService contaService) {
        this.contaService = contaService;
    }

}
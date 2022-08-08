package com.lex.to;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompraPedido {

    private String usuario; // que esta fazendo a compra
    private Long idPedido; // pedido de compra lancado
    private Double quantidade; // idealizamos que a compra sera a mesma do pedido, vende 10 e compra 10 por enquanto!

}
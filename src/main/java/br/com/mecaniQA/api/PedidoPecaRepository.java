package br.com.mecaniQA.api;

import java.util.ArrayList;
import java.util.List;

public class PedidoPecaRepository {
    private static PedidoPecaRepository instance;
    private List<PedidoPeca> pedidos;
    private long contadorId;

    private PedidoPecaRepository() {
        this.pedidos = new ArrayList<>();
        this.contadorId = 1;
    }

    public static PedidoPecaRepository getInstance() {
        if (instance == null) {
            instance = new PedidoPecaRepository();
        }
        return instance;
    }

    public PedidoPeca salvar(PedidoPeca pedido) {
        pedido.setCodigoUnico(contadorId++);
        pedidos.add(pedido);
        return pedido;
    }

    public PedidoPeca buscarPorId(Long id) {
        for (PedidoPeca pedido : pedidos) {
            if (pedido.getCodigoUnico().equals(id)) {
                return pedido;
            }
        }
        return null;
    }

    public List<PedidoPeca> listarTodos() {
        return pedidos;
    }
}
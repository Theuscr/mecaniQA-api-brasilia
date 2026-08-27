package br.com.mecaniQA.api;

import java.util.ArrayList;
import java.util.List;

public class ServicoRepository {

    // 1. Instância única (Singleton)
    private static ServicoRepository instance;

    // 2. Nossa lista em memória
    private List<Servico> servicos;

    // Variável para gerar o ID automático
    private long contadorId;

    // 3. Construtor privado para impedir o uso de "new" em outras classes
    private ServicoRepository() {
        this.servicos = new ArrayList<>();
        this.contadorId = 1;
    }

    // 4. Método exclusivo para acessar o repositório
    public static ServicoRepository getInstance() {
        if (instance == null) {
            instance = new ServicoRepository();
        }
        return instance;
    }

    // --- MÉTODOS DO CRUD ---

    // CREATE (Salvar novo serviço)
    public Servico salvar(Servico servico) {
        servico.setCodigoUnico(contadorId++);
        servicos.add(servico);
        return servico;
    }

    // READ (Listar todos os serviços)
    public List<Servico> listarTodos() {
        return servicos;
    }

    // READ (Buscar serviço específico pelo código)
    public Servico buscarPorId(long id) {
        for (Servico servico : servicos) {
            if (servico.getCodigoUnico() == id) {
                return servico;
            }
        }
        return null; // Retorna null se não encontrar
    }

    // DELETE (Remover serviço)
    public boolean deletar(long id) {
        Servico servico = buscarPorId(id);
        if (servico != null) {
            servicos.remove(servico);
            return true;
        }
        return false;
    }
}
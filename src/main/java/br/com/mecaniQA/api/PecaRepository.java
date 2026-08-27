package br.com.mecaniQA.api;

import java.util.ArrayList;
import java.util.List;

public class PecaRepository {

    // 1. O atributo static guarda a ÚNICA instância do repositório na memória do servidor.
    private static PecaRepository instance;

    // 2. Essa é a nossa "tabela" do banco de dados em memória.
    private List<Peca> pecas;

    // Variável para gerar o código único automaticamente
    private long contadorId;

    // 3. O construtor é private! Isso proíbe que outras classes usem "new PecaRepository()".
    private PecaRepository() {
        this.pecas = new ArrayList<>();
        this.contadorId = 1;
    }

    // 4. O método getInstance() é a única forma de acessar o repositório.
    // Se a instância não existe, ele cria. Se já existe, ele devolve a mesma.
    public static PecaRepository getInstance() {
        if (instance == null) {
            instance = new PecaRepository();
        }
        return instance;
    }

    // --- AQUI COMEÇAM OS MÉTODOS DO CRUD ---

    // CREATE (Salvar nova peça)
    public Peca salvar(Peca peca) {
        peca.setCodigoUnico(contadorId++); // Gera o ID automático
        pecas.add(peca);
        return peca;
    }

    // READ (Listar todas as peças)
    public List<Peca> listarTodas() {
        return pecas;
    }

    // READ (Buscar peça específica pelo código)
    public Peca buscarPorId(Long id) {
        for (Peca peca : pecas) {
            if (peca.getCodigoUnico().equals(id)) {
                return peca;
            }
        }
        return null; // Retorna null se não encontrar
    }

    // DELETE (Remover peça)
    public boolean deletar(Long id) {
        Peca peca = buscarPorId(id);
        if (peca != null) {
            pecas.remove(peca);
            return true;
        }
        return false;
    }
}
package br.com.mecaniQA.api;
import java.util.ArrayList;
import java.util.List;
public class PecaRepository {
    private static PecaRepository instance;
    private List<Peca> pecas;
    private long contadorId;
    private PecaRepository() {
        this.pecas = new ArrayList<>();
        this.contadorId = 1;
    }
    public static PecaRepository getInstance() {
        if (instance == null) {
            instance = new PecaRepository();
        }
        return instance;
    }

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
package br.com.mecaniQA.api;

import java.util.ArrayList;
import java.util.List;

public class OrdemServicoRepository {
    private static OrdemServicoRepository instance;
    private List<OrdemServico> ordens;
    private long contadorId;

    private OrdemServicoRepository() {
        this.ordens = new ArrayList<>();
        this.contadorId = 1;
    }

    public static OrdemServicoRepository getInstance() {
        if (instance == null) {
            instance = new OrdemServicoRepository();
        }
        return instance;
    }

    public OrdemServico salvar(OrdemServico os) {
        os.setCodigoUnico(contadorId++);
        ordens.add(os);
        return os;
    }

    public OrdemServico buscarPorId(Long id) {
        for (OrdemServico os : ordens) {
            if (os.getCodigoUnico().equals(id)) {
                return os;
            }
        }
        return null;
    }

    public List<OrdemServico> listarTodas() {
        return ordens;
    }
}
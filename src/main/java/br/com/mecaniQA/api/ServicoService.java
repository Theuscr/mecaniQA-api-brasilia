package br.com.mecaniQA.api;

import java.util.List;

public class ServicoService {

    private static ServicoService instance;

    private ServicoRepository repository = ServicoRepository.getInstance();

    private ServicoService() {
    }

    public static ServicoService getInstance() {
        if (instance == null) {
            instance = new ServicoService();
        }
        return instance;
    }

    public Servico cadastrar(Servico servico) {
        if (servico == null) {
            throw new DadosInvalidosException("O corpo da requisição é obrigatório.");
        }
        if (servico.getNomeServico() == null || servico.getNomeServico().isBlank()) {
            throw new DadosInvalidosException("O nome do serviço é obrigatório.");
        }
        validarTempoECusto(servico.getTempoMinutos(), servico.getCustoTabelado());
        return repository.save(servico);
    }

    public List<Servico> listarTodos() {
        return repository.findAll();
    }

    public Servico buscarPorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new ServicoNaoEncontradoException(id));
    }

    /** Atualiza apenas tempo e custo (US07), como já era na OAT 1. */
    public Servico atualizar(Long id, Servico dados) {
        if (dados == null) {
            throw new DadosInvalidosException("O corpo da requisição é obrigatório.");
        }
        Servico existente = buscarPorId(id);
        validarTempoECusto(dados.getTempoMinutos(), dados.getCustoTabelado());

        existente.setTempoMinutos(dados.getTempoMinutos());
        existente.setCustoTabelado(dados.getCustoTabelado());
        return repository.update(existente);
    }

    public void excluir(Long id) {
        if (!repository.existsById(id)) {
            throw new ServicoNaoEncontradoException(id);
        }
        repository.deleteById(id);
    }

    private void validarTempoECusto(short tempoMinutos, double custoTabelado) {
        if (tempoMinutos <= 0) {
            throw new DadosInvalidosException("O tempo do serviço deve ser maior que zero minutos.");
        }
        if (custoTabelado < 0) {
            throw new DadosInvalidosException("O custo tabelado não pode ser negativo.");
        }
    }
}
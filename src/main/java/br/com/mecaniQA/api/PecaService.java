package br.com.mecaniQA.api;

import java.util.List;

public class PecaService {

    private static PecaService instance;

    private PecaRepository repository = PecaRepository.getInstance();

    private PecaService() {
    }

    public static PecaService getInstance() {
        if (instance == null) {
            instance = new PecaService();
        }
        return instance;
    }

    public Peca cadastrar(Peca peca) {
        if (peca == null) {
            throw new DadosInvalidosException("O corpo da requisição é obrigatório.");
        }
        validarNome(peca.getNome());
        validarNumeros(peca.getPrecoCusto(), peca.getPrecoVenda(), peca.getQuantidadeEmEstoque());
        return repository.save(peca);
    }

    public List<Peca> listarTodas() {
        return repository.findAll();
    }

    public Peca buscarPorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new PecaNaoEncontradaException(id));
    }


    public Peca atualizar(Long id, Peca dados) {
        if (dados == null) {
            throw new DadosInvalidosException("O corpo da requisição é obrigatório.");
        }
        Peca existente = buscarPorId(id);
        if (dados.getPrecoCusto() == null || dados.getPrecoVenda() == null) {
            throw new DadosInvalidosException("Informe precoCusto e precoVenda.");
        }
        validarNumeros(dados.getPrecoCusto(), dados.getPrecoVenda(), dados.getQuantidadeEmEstoque());

        existente.setPrecoCusto(dados.getPrecoCusto());
        existente.setPrecoVenda(dados.getPrecoVenda());
        existente.setQuantidadeEmEstoque(dados.getQuantidadeEmEstoque());
        return repository.update(existente);
    }

    public void excluir(Long id) {
        if (!repository.existsById(id)) {
            throw new PecaNaoEncontradaException(id);
        }
        repository.deleteById(id);
    }

    private void validarNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new DadosInvalidosException("O nome da peça é obrigatório.");
        }
    }

    private void validarNumeros(Double precoCusto, Double precoVenda, int quantidade) {
        if (precoCusto != null && precoCusto < 0) {
            throw new DadosInvalidosException("O preço de custo não pode ser negativo.");
        }
        if (precoVenda != null && precoVenda < 0) {
            throw new DadosInvalidosException("O preço de venda não pode ser negativo.");
        }
        if (quantidade < 0) {
            throw new DadosInvalidosException("A quantidade em estoque não pode ser negativa.");
        }
    }
}
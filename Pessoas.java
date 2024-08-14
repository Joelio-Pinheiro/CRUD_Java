import java.io.IOException;

import Excecoes.DatabaseException;
import Excecoes.EmailInvalido;
import Excecoes.IdadeInvalida;
import Excecoes.TelefoneInvalido;

public class Pessoas extends Database<Pessoa>{
    public Pessoas(String fileName) throws IOException{
        super(fileName);
    }

    /**
     * Checa se uma pessoa existe no banco de dados
     */
    public boolean existe(String nome){
        Pessoa p = obterDado(nome);
        return p != null;
    }

    /**
     * Adiciona uma nova pessoa ao banco de dados
     */
    public void create(Pessoa dado) throws DatabaseException, IOException{
        if (existe(dado.getNome())) throw new DatabaseException("Pessoa já cadastrada no banco de dados.");

        String chave = dado.getNome();
        adicionarDado(chave, dado);
    }

    /**
     * Printa no console os dados de uma pessoa
     */
    public void read(String nome) throws DatabaseException{
        if (!existe(nome)) throw new DatabaseException("Pessoa não encontrada.");

        Pessoa p = obterDado(nome);

        System.out.println("Nome: " + p.getNome());
        System.out.println("Email: " + p.getEmail());
        System.out.println("Idade: " + p.getIdade());
        System.out.println("Telefone: " + p.getTelefone());
    }

    /**
     * Atualiza completamente uma pessoa
     */
    public void update(String nome, Pessoa pessoaAtualizada) throws DatabaseException, IOException{
        if (!existe(nome)) throw new DatabaseException("Pessoa não encontrada");

        removerDado(nome);
        adicionarDado(nome, pessoaAtualizada);
    }
    
    /**
     * Atualiza apenas o Email de uma pessoa
     * @param nome
     * @param novoEmail
     * @throws DatabaseException
     * @throws EmailInvalido
     * @throws IOException
     */
    public void updateEmail(String nome, String novoEmail) throws DatabaseException, EmailInvalido, IOException{
        if (!existe(nome)) throw new DatabaseException("Pessoa não encontrada");

        Pessoa p = obterDado(nome);
        p.setEmail(novoEmail);

        serializar();
    }

    /**
     * Atualiza apenas a Idade de uma pessoa
     * @param nome
     * @param novaIdade
     * @throws DatabaseException
     * @throws IdadeInvalida
     * @throws IOException
     */
    public void updateIdade(String nome, int novaIdade) throws DatabaseException, IdadeInvalida, IOException{
        if (!existe(nome)) throw new DatabaseException("Pessoa não encontrada");

        Pessoa p = obterDado(nome);
        p.setIdade(novaIdade);

        serializar();
    }

    /**
     * Atualiza apenas o telefone de uma pessoa
     * @param nome
     * @param novoTelefone
     * @throws DatabaseException
     * @throws TelefoneInvalido
     * @throws IOException
     */
    public void updateTelefone(String nome, int novoTelefone) throws DatabaseException, TelefoneInvalido, IOException{
        if (!existe(nome)) throw new DatabaseException("Pessoa não encontrada");

        Pessoa p = obterDado(nome);
        p.setTelefone(novoTelefone);

        serializar();
    }

    /**
     * Remove uma pessoa do banco de dados
     */
    public void delete(String nome) throws DatabaseException, IOException{
        if (!existe(nome)) throw new DatabaseException("Pessoa não encontrada");

        removerDado(nome);
    }
}
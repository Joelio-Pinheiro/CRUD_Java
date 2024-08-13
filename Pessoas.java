import java.io.IOException;

import Excecoes.DatabaseException;
import Excecoes.EmailInvalido;
import Excecoes.IdadeInvalida;
import Excecoes.TelefoneInvalido;

public class Pessoas extends Database<Pessoa>{
    public Pessoas(String fileName) throws IOException{
        super(fileName);
    }

    public boolean existe(String nome){
        Pessoa p = obterDado(nome);
        return p != null;
    }

    public void create(Pessoa dado) throws DatabaseException, IOException{
        if (existe(dado.getNome())) throw new DatabaseException("Pessoa já cadastrada no banco de dados.");

        String chave = dado.getNome();
        adicionarDado(chave, dado);
    }

    public void read(String nome) throws DatabaseException{
        if (!existe(nome)) throw new DatabaseException("Pessoa não encontrada.");

        Pessoa p = obterDado(nome);

        System.out.println("Nome: " + p.getNome());
        System.out.println("Email: " + p.getEmail());
        System.out.println("Idade: " + p.getIdade());
        System.out.println("Telefone: " + p.getTelefone());
    }

    //Esse primeiro update não é preferível, ele só está aqui para cumprir o contrato com a classe abstrata
    //Na situação em que uma pessoa deve ser completamente alterada (até seu nome), esse update pode ser de grande ajuda
    public void update(String nome, Pessoa pessoaAtualizada) throws DatabaseException, IOException{
        if (!existe(nome)) throw new DatabaseException("Pessoa não encontrada");

        removerDado(nome);
        adicionarDado(nome, pessoaAtualizada);
    }
    public void updateEmail(String nome, String novoEmail) throws DatabaseException, EmailInvalido, IOException{
        if (!existe(nome)) throw new DatabaseException("Pessoa não encontrada");

        Pessoa p = obterDado(nome);
        p.setEmail(novoEmail);

        serializar();
    }
    public void updateIdade(String nome, int novaIdade) throws DatabaseException, IdadeInvalida, IOException{
        if (!existe(nome)) throw new DatabaseException("Pessoa não encontrada");

        Pessoa p = obterDado(nome);
        p.setIdade(novaIdade);

        serializar();
    }
    public void updateTelefone(String nome, int novoTelefone) throws DatabaseException, TelefoneInvalido, IOException{
        if (!existe(nome)) throw new DatabaseException("Pessoa não encontrada");

        Pessoa p = obterDado(nome);
        p.setTelefone(novoTelefone);

        serializar();
    }

    public void delete(String nome) throws DatabaseException, IOException{
        if (!existe(nome)) throw new DatabaseException("Pessoa não encontrada");

        removerDado(nome);
    }
}
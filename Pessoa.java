import Excecoes.*;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Pessoa implements Serializable{
    private String nome;
    private String email;
    private int idade;
    private int telefone; //Um número de 9 dígitos

    public Pessoa(String nome, String email, int idade, int telefone) throws Exception{
        setNome(nome);
        setEmail(email);
        setIdade(idade);
        setTelefone(telefone);
    }
    public Pessoa(String nome, String email, int idade) throws Exception{
        this(nome, email, idade, 000000000);
    }
    public Pessoa(){}
    
    public String getNome() {return this.nome;}
    public String getEmail() {return this.email;}
    public int getIdade() {return this.idade;}
    public int getTelefone() {return this.telefone;}

    public void setNome(String nome) throws NomeInvalido{
        nome = nome.trim(); //Nome sem os whitespaces

        if (nome.length() < 4 || nome.length() > 12) throw new NomeInvalido("Nome precisa ter pelo menos 4 caracteres e no máximo 12.");

        this.nome = nome;
    }
    public void setEmail(String email) throws EmailInvalido{
        final String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"; //Expressão regular para validação do email
        final Pattern patternEmail = Pattern.compile(regex);
        
        Matcher validador = patternEmail.matcher(email);

        if (!validador.matches()) throw new EmailInvalido("Formato de email não suportado.");

        this.email = email;
    }
    public void setIdade(int idade) throws IdadeInvalida{
        if (idade < 0 || idade > 170) throw new IdadeInvalida("Indade inválida."); //Espero que sim, provavelmente ninguém viveu mais do que 170 anos... será?

        this.idade = idade;
    }
    public void setTelefone(int telefone) throws TelefoneInvalido{
        int tamanhoNumero = String.valueOf(telefone).length();

        if (tamanhoNumero < 9) throw new TelefoneInvalido("O número de telefone deve ter exatos 9 dígitos.");

        this.telefone = telefone;
    }
}
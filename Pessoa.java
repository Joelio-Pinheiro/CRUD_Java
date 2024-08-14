import Excecoes.*;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Pessoa implements Serializable{
    private String nome;
    private String email;
    private int idade;
    private int telefone; //Um número de 9 dígitos

    /** Cria uma pessoa com todas suas informações básicas
     * @param nome
     * @param email
     * @param idade
     * @param telefone
     * @throws Exception
     */
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

    /** Um nome é válido quando ele tem entre 4 a 12 caracteres não vazios
     * @param nome
     * @return True, se nome for válido
     */
    public static boolean nomeValido(String nome){return (nome.length() >= 4 && nome.length() <= 12);}

    /** Um email é válido quando ele está no formato algumacoisa@dominio.com
     * @param email
     * @return True, se o email for válido
     */
    public static boolean emailValido(String email){
        final String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"; //Expressão regular para validação do email
        final Pattern patternEmail = Pattern.compile(regex);
        
        Matcher validador = patternEmail.matcher(email);

        return validador.matches();
    }

    /** Uma idade é válida quando ela está entre 0 e 170 anos (intervalo inclusivo) 
     * @param idade
     * @return True, se idade for válida
     */
    public static boolean idadeValida(int idade){return (idade >= 0 && idade <= 170);}

    /** Um telefone é válido se ele tiver 9 dígitos
     * @param telefone
     * @return True, se telefone for válido
     */
    public static boolean telefoneValido(int telefone){return (String.valueOf(telefone).length() == 9);}

    /** Caso o parâmetro "nome" seja válido, seta nome da pessoa
     * @param nome
     * @throws NomeInvalido
     */
    public void setNome(String nome) throws NomeInvalido{
        nome = nome.trim(); //Nome sem os whitespaces

        if (!nomeValido(nome)) throw new NomeInvalido("Nome precisa ter pelo menos 4 caracteres e no máximo 12.");

        this.nome = nome;
    }

    /** Caso o parâmetro "email" seja válido, seta email da pessoa
     * @param email
     * @throws EmailInvalido
     */
    public void setEmail(String email) throws EmailInvalido{
        if (!emailValido(email)) throw new EmailInvalido("Formato de email não suportado.");

        this.email = email;
    }

    /** Caso idade seja válida, seta idade da pessoa
     * @param idade
     * @throws IdadeInvalida
     */
    public void setIdade(int idade) throws IdadeInvalida{
        if (!idadeValida(idade)) throw new IdadeInvalida("Indade inválida.");

        this.idade = idade;
    }

    /** Caso telefone seja válido, seta telefone da pessoa
     * @param telefone
     * @throws TelefoneInvalido
     */
    public void setTelefone(int telefone) throws TelefoneInvalido{
        if (!telefoneValido(telefone)) throw new TelefoneInvalido("O número de telefone deve ter exatos 9 dígitos.");

        this.telefone = telefone;
    }
}
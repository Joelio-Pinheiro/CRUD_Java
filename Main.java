import java.io.IOException;
import java.util.Scanner;

import Excecoes.DatabaseException;
import Excecoes.EmailInvalido;
import Excecoes.IdadeInvalida;
import Excecoes.NomeInvalido;
import Excecoes.TelefoneInvalido;

/** Implementação de toda a interface do banco de dados no terminal
 * @author Luiz Eduardo Teixeira Lima -- 554648
 * @author Luiz Felipe de Sousa Cordeiro -- 552566
 * @author Joélio Pinheiro -- 554968
 */
public class Main{  
    /**
     * Lida com todas as leituras de input do usuário
     */
    public static Scanner inputHandler = new Scanner(System.in);
    public static Pessoas database;

    public static void print(String msg){System.out.println(msg);}
    public static void waitEnter() throws IOException{
        inputHandler.nextLine();
    }


    /**
     * Simula uma limpagem do console ao printar várias novas linhas vazias
     */
    public static void limparConsole() {
        for (int i = 0; i < 60; i++) print("");
    }

    /**
     * @param insistir Deve entrar em loop até um nome válido ser dado como input?
     * @return Nome válido de uma pessoa dada como input ou, caso contrário, String vazia
     */
    public static String lerNome(boolean insistir){
        String nome = "";

        //Pedindo Nome
        print("Digite um nome válido (Mínimo de 4 caracteres não vazios e no máximo 12): ");
        while (nome == ""){
            try{
                nome = inputHandler.nextLine().trim();

                if (nome != "" && !Pessoa.nomeValido(nome)) throw new NomeInvalido("Nome inválido");
            }
            catch (NomeInvalido e){
                nome = "";
                if (!insistir) break;

                print("Nome inválido");
            }
        }
        limparConsole();

        return nome;
    }

    /**
     * @param insistir Deve entrar em loop até um nome válido ser dado como input?
     * @return Email válido de uma pessoa dada como input ou, caso contrário, String vazia
     */
    public static String lerEmail(boolean insistir){
        String email = "";

        //Pedindo Email
        print("Digite um email válido (xxxx@dominio.com): ");
        while (email == ""){
            try{
                email = inputHandler.nextLine().trim();

                if (email != "" && !Pessoa.emailValido(email)) throw new EmailInvalido("Email inválido");
            }
            catch (EmailInvalido e){
                email = "";
                if (!insistir) break;

                print("Email inválido");
            }
        }
        limparConsole();

        return email;
    }

    /**
     * @param insistir Deve entrar em loop até um nome válido ser dado como input?
     * @return Idade válida de uma pessoa dada como input ou, caso contrário, -1
     */
    public static int lerIdade(boolean insistir){
        int idade = -1;

        //Pedindo Email
        print("Digite uma idade válida (Entre 0 e 170 anos): ");
        while (idade == -1){
            try{
                idade = Integer.parseInt(inputHandler.nextLine());

                if (Pessoa.idadeValida(idade)) break;
            }
            catch (NumberFormatException e){}

            idade = -1;
            if (!insistir) break;

            print("Idade inválida");
        }
        limparConsole();

        return idade;
    }

    /**
     * @param insistir Deve entrar em loop até um nome válido ser dado como input?
     * @return Telefone válido de uma pessoa dada como input ou, caso contrário, -1
     */
    public static int lerTelefone(boolean insistir){
        int telefone = -1;

        //Pedindo Email
        print("Digite um número telefônico válido (9 dígitos): ");
        while (telefone == -1){
            try{
                telefone = Integer.parseInt(inputHandler.nextLine());

                if (Pessoa.telefoneValido(telefone)) break;
            }
            catch (NumberFormatException e){}

            telefone = -1;
            if (!insistir) break;

            print("Telefone inválido");
        }
        limparConsole();

        return telefone;
    }

    /** Diálogo voltado para adicionar uma pessoa no banco de dados
     * @throws Exception
     */
    public static void Dialogo_AdicionarPessoa() throws Exception{
        Pessoa p = new Pessoa();

        String nome = null;
        String email = null;
        int idade = -1;
        int telefone = -1;

        nome = lerNome(true);
        p.setNome(nome);

        if (database.existe(nome)){
            print("Pessoa de nome " + p.getNome() + " já existe no banco de dados");

            print("--- Aperte ENTER para continuar ---");
            waitEnter();

            return;
        } //Se pessoa já existir, termina aqui

        email = lerEmail(true);
        p.setEmail(email);

        idade = lerIdade(true);
        p.setIdade(idade);

        telefone = lerTelefone(true);
        p.setTelefone(telefone);

        limparConsole();

        boolean pessoaAdicionada = false;

        try{
            database.create(p);
            pessoaAdicionada = true;
        }
        catch (DatabaseException e){}

        if (pessoaAdicionada){
            print("Pessoa de nome " + p.getNome() + " adicionada com sucesso");
        } else{
            print("Algum erro ocorreu!"); //Teoricamente impossível o código chegar nesse else, mas vai que...
        }

        print("--- Aperte ENTER para continuar ---");
        waitEnter();
    }

    /** Diálogo voltado para imprimir as informações de uma pessoa específica do banco de dados (Nome, Email, Idade, Telefone)
     * @throws IOException
     */
    public static void Dialogo_ImprimirPessoa() throws IOException{
        String nome = null;
        boolean pessoaEncontrada = true;

        nome = lerNome(false);
        
        try{
            database.read(nome);
        }
        catch (DatabaseException e){ 
            pessoaEncontrada = false;
            limparConsole();
        }

        if (!pessoaEncontrada){
            print("Pessoa não encontrada");
        }

        print("--- Aperte ENTER para continuar ---");
        waitEnter();
    
        limparConsole();
    }

    public static void Dialogo_AlterarEmail(String nome) throws DatabaseException, IOException{
        String email = "";

        //Pedindo Email
        email = lerEmail(false);
        print("Digite um email válido (xxxx@dominio.com): ");
        try{
            database.updateEmail(nome, email);
        }
        catch (EmailInvalido e){
            email = "";

            limparConsole();
        }
    
        limparConsole();
        if (email == ""){
            print("Email inválido");
        }
        else{
            print("Email alterado com sucesso!");
        }

        print("--- Aperte ENTER para continuar ---");
        waitEnter();

        limparConsole();
    }
    public static void Dialogo_AlterarIdade(String nome) throws DatabaseException, IOException{
        int idade = -1;

        //Pedindo Idade
        idade = lerIdade(false);
        try{
            database.updateIdade(nome, idade);
        }
        catch (IdadeInvalida e){
            idade = -1;

            limparConsole();
        }
    
        limparConsole();
        if (idade == -1){
            print("Idade inválida");
        }
        else{
            print("Idade alterada com sucesso!");
        }

        print("--- Aperte ENTER para continuar ---");
        waitEnter();

        limparConsole();
    }
    public static void Dialogo_AlterarTelefone(String nome) throws DatabaseException, IOException{
        int telefone = -1;

        //Pedindo Telefone
        telefone = lerTelefone(false);

        try{
            database.updateTelefone(nome, telefone);
        }
        catch (TelefoneInvalido e){
            telefone = -1;

            limparConsole();
        }
    
        limparConsole();
        if (telefone == -1){
            print("Número telefônico inválido");
        }
        else{
            print("Número telefônico alterado com sucesso!");
        }

        print("--- Aperte ENTER para continuar ---");
        waitEnter();

        limparConsole();
    }
    /** Diálogo voltado para atualizar uma informação qualquer de uma pessoa contida no banco de dados
     * @throws IOException
     * @throws DatabaseException
     */
    public static void Dialogo_AtualizarPessoa() throws IOException, DatabaseException{
        String nome = "";
        boolean pessoaEncontrada = true;

        nome = lerNome(false);
        pessoaEncontrada = database.existe(nome);
        if (!pessoaEncontrada){
            print("Pessoa não encontrada");

            print("--- Aperte ENTER para continuar ---");
            waitEnter();
            limparConsole();

            return;
        } //Caso a pessoa não tenha sido encontrada, termina aqui

        while (true){
            //Parte 1 (Qual dado deve ser alterado)

            print("--- Escolha uma das seguintes operações ---");
            print("1: Alterar Email");
            print("2: Alterar Idade");
            print("3: Alterar Telefone");
            print("4: Sair");
            print("-------------------------------------------");
            print("");

            //Parte 2 (pedindo por operação)
            int operation = -1;

            while (operation == -1){
                try{
                    operation = Integer.parseInt(inputHandler.nextLine());

                    if (operation >= 1 && operation <= 5) break;
                }
                catch(NumberFormatException e){}

                operation = -1;
                print("Operação inválida");
            }

            //Parte 4 (escolhendo operação)
            limparConsole();

            switch(operation){
                case 1:
                Dialogo_AlterarEmail(nome);
                break;
                
                case 2:
                Dialogo_AlterarIdade(nome);
                break;

                case 3:
                Dialogo_AlterarTelefone(nome);
                break;

                case 4:
                return; //Sair do diálogo de alteração de dados
            }
        }
    }


    /** Diálogo voltado para remover uma pessoa do banco de dados
     * @throws IOException
     */
    public static void Dialogo_RemoverPessoa() throws IOException{
        String nome = null;
        boolean pessoaEncontrada = true;

        nome = lerNome(false);

        try{
            database.delete(nome);
        }
        catch (DatabaseException e){ 
            pessoaEncontrada = false;
            limparConsole();
        }

        if (!pessoaEncontrada){
            print("Pessoa não encontrada");
        } 
        else{
            print("Pessoa de nome " + nome + " foi removida com sucesso!");
        }

        print("--- Aperte ENTER para continuar ---");
        waitEnter();
    
        limparConsole();
    }
    public static void main(String args[]) throws Exception{
        database = new Pessoas("Pessoas.ser");

        limparConsole();

        //Parte 1
        print("**********************");
        print("");
        print("Bem vindo ao banco de pessoas!");
        print("Aqui você pode armazenar, atualizar ou remover dados de uma pessoa");
        print("");
        print("********************** Aperte ENTER para continuar!");
        waitEnter();

        while (true){
            limparConsole();

            //Parte 2
            print("--- Escolha uma das seguintes operações ---");
            print("1: Adicionar");
            print("2: Imprimir");
            print("3: Atualizar");
            print("4: Remover");
            print("5: Sair");
            print("-------------------------------------------");
            print("");

            //Parte 3 (pedindo por operação)
            int operation = -1;

            while (operation == -1){
                try{
                    operation = Integer.parseInt(inputHandler.nextLine());

                    if (operation >= 1 && operation <= 5) break;
                }
                catch(NumberFormatException e){}

                operation = -1;
                print("Operação inválida");
            }

            //Parte 4 (escolhendo operação)
            limparConsole();

            switch(operation){
                case 1:
                Dialogo_AdicionarPessoa();
                break;
                
                case 2:
                Dialogo_ImprimirPessoa();
                break;

                case 3:
                Dialogo_AtualizarPessoa();
                break;

                case 4:
                Dialogo_RemoverPessoa();
                break;

                case 5:
                inputHandler.close();
                return; //Sair da main
            }
        }
    }
}
import java.io.IOException;
import java.util.Scanner;

import Excecoes.DatabaseException;
import Excecoes.EmailInvalido;
import Excecoes.IdadeInvalida;
import Excecoes.NomeInvalido;
import Excecoes.TelefoneInvalido;

public class Main{
    public static Scanner inputHandler = new Scanner(System.in);
    public static Pessoas database;

    public static void print(String msg){System.out.println(msg);}
    public static void waitEnter() throws IOException{System.in.read();}
    public static void limparConsole() {
        for (int i = 0; i < 30; i++) print("");
    }

    public static void Dialogo_AdicionarPessoa() throws DatabaseException, IOException{
        Pessoa p = new Pessoa();

        String nome = null;
        String email = null;
        int idade = -1;
        int telefone = -1;

        //Pedindo Nome
        print("Digite um nome válido (Mínimo de 4 caracteres não vazios e no máximo 12): ");
        while (nome == null){
            try{
                if (inputHandler.hasNext()){
                    nome = inputHandler.nextLine();

                    p.setNome(nome);
                }
            }
            catch (NomeInvalido e){
                nome = null;

                limparConsole();
                print("Nome inválido");
            }
        }
        limparConsole();

        //Pedindo Email
        print("Digite um email válido (xxxx@dominio.com): ");
        while (email == null){
            try{
                if (inputHandler.hasNext()){
                    email = inputHandler.nextLine();
                    p.setEmail(email);
                }
            }
            catch (EmailInvalido e){
                email = null;

                limparConsole();
                print("Email inválido");
            }
        }
        limparConsole();

        //Pedindo Idade
        print("Digite uma idade válida (Entre 0 e 170 anos): ");
        while (idade == -1){
            try{
                if (inputHandler.hasNextInt()){
                    idade = inputHandler.nextInt();
                    p.setIdade(idade);
                }
            }
            catch (IdadeInvalida e){
                idade = -1;

                limparConsole();
                print("Idade inválida");
            }
        }
        limparConsole();

        //Pedindo Telefone
        print("Digite um número telefônico válido (9 dígitos): ");
        while (telefone == -1){
            try{
                if (inputHandler.hasNextInt()){
                    telefone = inputHandler.nextInt();
                    p.setTelefone(telefone);
                }
            }
            catch (TelefoneInvalido e){
                telefone = -1;

                limparConsole();
                print("Telefone inválido");
            }
        }
        limparConsole();
        //Fim

        boolean pessoaAdicionada = false;

        try{
            database.create(p);
            pessoaAdicionada = true;
        }
        catch (DatabaseException e){}

        if (pessoaAdicionada){
            print("Pessoa de nome " + p.getNome() + " adicionada com sucesso");
        } else{
            print("Pessoa de nome " + p.getNome() + " já existe no banco de dados");
        }

        print("--- Aperte ENTER para continuar ---");
        waitEnter();
    }

    public static void Dialogo_ImprimirPessoa() throws IOException{
        String nome = null;

        boolean pessoaEncontrada = true;

        //Pedindo Nome
        print("Digite um nome válido (Mínimo de 4 caracteres não vazios e no máximo 12): ");
        try{
            if (inputHandler.hasNext()){
                nome = inputHandler.next();
                limparConsole();

                database.read(nome);
            }
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
        String email = null;

        //Pedindo Email
        print("Digite um email válido (xxxx@dominio.com): ");
        try{
            if (inputHandler.hasNext()){
                email = inputHandler.next();
                database.updateEmail(nome, email);
            }
        }
        catch (EmailInvalido e){
            email = null;

            limparConsole();
        }
    
        limparConsole();
        if (email == null){
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
        print("Digite uma idade válida (Entre 0 e 170 anos): ");
        try{
            if (inputHandler.hasNextInt()){
                idade = inputHandler.nextInt();
                database.updateIdade(nome, idade);
            }
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
        print("Digite um número telefônico válido (9 dígitos): ");
        try{
            if (inputHandler.hasNextInt()){
                telefone = inputHandler.nextInt();
                database.updateTelefone(nome, telefone);
            }
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
    public static void Dialogo_AtualizarPessoa() throws IOException, DatabaseException{
        String nome = null;

        boolean pessoaEncontrada = true;

        //Pedindo Nome
        print("Digite um nome válido (Mínimo de 4 caracteres não vazios e no máximo 12): ");
        try{
            if (inputHandler.hasNext()){
                nome = inputHandler.next();
                limparConsole();
            }
        }
        catch (Exception e){}

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
                    if (inputHandler.hasNextInt()) operation = inputHandler.nextInt();

                    if (operation < 1 || operation > 4) {
                        operation = -1;

                        throw new Exception("Operação inválida");
                    }
                }
                catch(Exception e){
                    inputHandler.nextLine();
                    print("Operação inválida");
                }
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

    public static void Dialogo_RemoverPessoa() throws IOException{
        String nome = null;

        boolean pessoaEncontrada = true;

        //Pedindo Nome
        print("Digite um nome válido (Mínimo de 4 caracteres não vazios e no máximo 12): ");
        try{
            if (inputHandler.hasNext()){
                nome = inputHandler.next();
                limparConsole();

                database.delete(nome);
            }
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
                    if (inputHandler.hasNextInt()) operation = inputHandler.nextInt();

                    if (operation < 1 || operation > 5) {
                        operation = -1;

                        throw new Exception("Operação inválida");
                    }
                }
                catch(Exception e){
                    inputHandler.nextLine();
                    print("Operação inválida");
                }
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
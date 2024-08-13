package Excecoes;

public class EmailInvalido extends Exception{
    public EmailInvalido(String errorMessage){
        super(errorMessage);
    }
}
package Excecoes;

public class NomeInvalido extends Exception{
    public NomeInvalido(String errorMessage){
        super(errorMessage);
    }
}
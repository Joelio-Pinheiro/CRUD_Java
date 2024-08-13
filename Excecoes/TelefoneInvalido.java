package Excecoes;

public class TelefoneInvalido extends Exception{
    public TelefoneInvalido(String errorMessage){
        super(errorMessage);
    }
}
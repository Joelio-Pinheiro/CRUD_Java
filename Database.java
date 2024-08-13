import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.HashMap;

import Excecoes.DatabaseException;

//Classe que representa um banco de dados que armazena objetos serializados em formato txt
public abstract class Database<T extends Serializable>{
    private HashMap<String, T> dados = null;

    private File arquivo_serializado = null;

    private InputStream iStream = null;
    private OutputStream oStream = null;

    public Database(String fileName) throws IOException{
        inicializar(fileName);
    }
    public Database(){}

    private boolean databaseExiste(){
        if (arquivo_serializado == null) return false;
        else return arquivo_serializado.exists();
    }

    public void inicializar(String fileName) throws IOException{
        if (databaseExiste()) return;

        this.arquivo_serializado = new File(fileName);
        
        //Criação de novo arquivo, caso ainda não exista
        boolean novoArquivo = this.arquivo_serializado.createNewFile();
        
        if (novoArquivo) { 
            this.dados = new HashMap<String, T>();
            serializar();
        }
        else deserializar();
    }

    private InputStream abrirLeitor() throws FileNotFoundException{
        if (!databaseExiste()) return null;
        if (this.iStream != null) return this.iStream;

        InputStream i = new FileInputStream(this.arquivo_serializado);
        this.iStream = i;

        return i;
    }
    private void fecharLeitor() throws IOException{
        if (this.iStream == null) return;

        this.iStream.close();
        this.iStream = null;
    }

    private OutputStream abrirEscritor() throws FileNotFoundException{
        if (!databaseExiste()) return null;
        if (this.oStream != null) return this.oStream;

        OutputStream o = new FileOutputStream(this.arquivo_serializado);
        this.oStream = o;

        return o;
    }
    private void fecharEscritor() throws IOException{
        if (this.oStream == null) return;

        this.oStream.close();
        this.oStream = null;
    }

    @SuppressWarnings("unchecked")
    protected void deserializar() throws IOException{
        if (!databaseExiste()) return;

        InputStream in = abrirLeitor();
        ObjectInputStream objIn = new ObjectInputStream(in);

        try{
            this.dados = (HashMap<String, T>)objIn.readObject();
        } catch (Exception e) {}

        fecharLeitor();
    }
    protected void serializar() throws IOException{
        if (!databaseExiste()) return;

        OutputStream out = abrirEscritor();
        ObjectOutputStream objOut = new ObjectOutputStream(out);

        objOut.writeObject(this.dados);
        objOut.flush();

        fecharEscritor();
    }

    protected void adicionarDado(String chave, T dado) throws DatabaseException, IOException{
        if (!databaseExiste()) return;
        if (this.dados.get(chave) != null) throw new DatabaseException("Chave já existe.");

        this.dados.put(chave, dado);
        
        serializar(); //Atualizando banco de dados com novo dado criado
    }

    protected T obterDado(String chave){
        return this.dados.get(chave);
    }

    protected void removerDado(String chave) throws DatabaseException, IOException{
        if (this.dados.get(chave) == null) throw new DatabaseException("Chave não existe.");
        
        this.dados.remove(chave);
        
        serializar(); //Atualizando banco de dados sem o dado removido
    }

    public void drop(){
        if (!databaseExiste()) return;

        arquivo_serializado.delete();
        arquivo_serializado = null;
    }

    public abstract void create(T dado) throws DatabaseException, IOException;
    public abstract void read(String chave) throws DatabaseException;
    public abstract void update(String chave, T novoDado) throws DatabaseException, IOException;
    public abstract void delete(String chave) throws DatabaseException, IOException;

    public abstract boolean existe(String chave);
}

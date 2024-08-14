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

/** Representa um database genérico, onde os dados são armazenados em um arquivo serializado de extensão qualquer
 * 
 */
public abstract class Database<T extends Serializable>{
    /**
     * Representa o estado ativo do banco de dados, pois todos os dados são armazenados aqui enquanto o programa estiver executando
     */
    private HashMap<String, T> dados = null;

    /**
     * Referência para o arquivo onde o banco de dados está armazenado na memória secundária
     */
    private File arquivo_serializado = null;

    private InputStream iStream = null;
    private OutputStream oStream = null;

    /** Cria um banco de dados no arquivo de caminho "filename"
     * @param fileName Caminho para o arquivo onde será armazenado o banco de dados
     * @throws IOException
     */
    public Database(String fileName) throws IOException{
        inicializar(fileName);
    }
    public Database(){}

    /** Checa se existe uma instância ativa do banco de dados
     * @return
     */
    private boolean databaseExiste(){
        if (arquivo_serializado == null) return false;
        else return arquivo_serializado.exists();
    }

    /** Processo de inicialização do banco de dados na memória principal e da criação do banco de dados na memória secundária (caso ainda não tenha sido criado)
     * @param fileName
     * @throws IOException
     */
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

    /** Abre processo de leitura no banco de dados
     * @return
     * @throws FileNotFoundException
     */
    private InputStream abrirLeitor() throws FileNotFoundException{
        if (!databaseExiste()) return null;
        if (this.iStream != null) return this.iStream;

        InputStream i = new FileInputStream(this.arquivo_serializado);
        this.iStream = i;

        return i;
    }

    /** Fecha processo de leitura no banco de dados
     * @throws IOException
     */
    private void fecharLeitor() throws IOException{
        if (this.iStream == null) return;

        this.iStream.close();
        this.iStream = null;
    }

    /** Abre processo de escrita no banco de dados
     * @return
     * @throws FileNotFoundException
     */
    private OutputStream abrirEscritor() throws FileNotFoundException{
        if (!databaseExiste()) return null;
        if (this.oStream != null) return this.oStream;

        OutputStream o = new FileOutputStream(this.arquivo_serializado);
        this.oStream = o;

        return o;
    }

    /** Fecha processo de escrita no banco de dados
     * @throws IOException
     */
    private void fecharEscritor() throws IOException{
        if (this.oStream == null) return;

        this.oStream.close();
        this.oStream = null;
    }

    /** Deserializa o arquivo onde o banco de dados está armazenado e armazena os dados deserializados em HashMap
     * @throws IOException
     */
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

    /** Serializa os dados do banco de dados para o caminho especificado no construtor
     * @throws IOException
     */
    protected void serializar() throws IOException{
        if (!databaseExiste()) return;

        OutputStream out = abrirEscritor();
        ObjectOutputStream objOut = new ObjectOutputStream(out);

        objOut.writeObject(this.dados);
        objOut.flush();

        fecharEscritor();
    }

    /** Adiciona um novo dado no banco de dados
     * @param chave Identificador do dado
     * @param dado Informações
     * @throws DatabaseException
     * @throws IOException
     */
    protected void adicionarDado(String chave, T dado) throws DatabaseException, IOException{
        if (!databaseExiste()) return;
        if (this.dados.get(chave) != null) throw new DatabaseException("Chave já existe.");

        this.dados.put(chave, dado);
        
        serializar(); //Atualizando banco de dados com novo dado criado
    }

    /**
     * @param chave
     * @return Retorna um dado do banco de dados, caso exista
     */
    protected T obterDado(String chave){
        return this.dados.get(chave);
    }

    /** Retira um dado do banco de dados
     * @param chave
     * @throws DatabaseException
     * @throws IOException
     */
    protected void removerDado(String chave) throws DatabaseException, IOException{
        if (this.dados.get(chave) == null) throw new DatabaseException("Chave não existe.");
        
        this.dados.remove(chave);
        
        serializar(); //Atualizando banco de dados sem o dado removido
    }

    /**
     * Deleta o banco de dados
     */
    public void drop(){
        if (!databaseExiste()) return;

        arquivo_serializado.delete();
        arquivo_serializado = null;
    }

    /** Método abstrato voltado para uma criação específica de um novo dado no banco de dados
     * @param dado
     * @throws DatabaseException
     * @throws IOException
     */
    public abstract void create(T dado) throws DatabaseException, IOException;

    /** Método abstrato voltado para uma leitura específica de um dado no banco de dados
     * @param chave
     * @throws DatabaseException
     */
    public abstract void read(String chave) throws DatabaseException;

    /** Método abstrato voltado para uma atualização específica de um dado no banco de dados
     * @param chave
     * @param novoDado
     * @throws DatabaseException
     * @throws IOException
     */
    public abstract void update(String chave, T novoDado) throws DatabaseException, IOException;

    /** Método abstrato voltado para uma remoção específica de um dado no banco de dados
     * @param chave
     * @throws DatabaseException
     * @throws IOException
     */
    public abstract void delete(String chave) throws DatabaseException, IOException;

    /** Método abstrato voltado para uma checagem específica da existência de um dado no banco de dados
     * @param chave
     * @return
     */
    public abstract boolean existe(String chave);
}

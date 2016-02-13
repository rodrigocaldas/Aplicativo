package br.com.caldas.rodrigo.aplicativo.dao;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;
import br.com.caldas.rodrigo.aplicativo.modelo.Livro;

/**
 * Created by rodrigo on 06/02/16.
 */
/*Classe responsável pela criação e acesso ao BD.*/
public class LivroDAO extends SQLiteOpenHelper {

    public LivroDAO(Context context) {
        super(context, "Acervo", null, 1);
    }

    /*Responsável por criar o BD.*/
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Livros (id INTEGER PRIMARY KEY, titulo TEXT NOT NULL, saga TEXT, " +
                "volume TEXT, autor TEXT, categoria TEXT, inicio TEXT, progresso TEXT," +
                "final TEXT, isbn TEXT, notas TEXT);";
        db.execSQL(sql);
    }

    /*Responsável pela atualização do BD.*/
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS Livros;";
        db.execSQL(sql);
        onCreate(db);
    }

    /*Responsável por pegar todos os dados de um Livro e colocá-los em um ContentValues que será retornado.*/
    private ContentValues pegaDadosLivro(Livro livro) {
        ContentValues dados = new ContentValues();
        dados.put("titulo", livro.getTitulo());
        dados.put("saga", livro.getSaga());
        dados.put("volume", livro.getVolume());
        dados.put("autor", livro.getAutor());
        dados.put("categoria", livro.getCategoria());
        dados.put("inicio", livro.getData_inicio());
        dados.put("progresso", livro.getProgresso());
        dados.put("final", livro.getData_final());
        dados.put("isbn", livro.getIsbn());
        dados.put("notas", livro.getNotas_pessoais());

        return dados;
    }

    /*Responsável pela inserção de um Livro no BD, recebe um Livro e retorna nada.*/
    public void insere(Livro livro){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = pegaDadosLivro(livro);

        db.insert("Livros", null, dados);
    }

    /*Responsável por alterar os dados de um Livro no BD, recebe um Livro e usa seu ID para realizar
    * esta ação.*/
    public void altera(Livro livro){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = pegaDadosLivro(livro);
        String[] params = {String.valueOf(livro.getId())};
        db.update("Livros", dados, "id == ?", params);
    }

    /*Responsável por excluir um Livro no BD, recebe um Livro e usa sei ID para realizar esta ação.*/
    public void apagarLivro(Livro livro) {
        SQLiteDatabase db = getWritableDatabase();
        String[] params = {String.valueOf(livro.getId())};
        db.delete("Livros", "id = ?", params);
    }

    /*Responsável por retornar a quantidade de livros cadastrados no BD, recebe nada e retorna um int.*/
    public int quantidadeCadastrada(){
        String sql = "SELECT * FROM Livros";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);
        return c.getCount();
    }

    /*Responsável por recuperar no BD todos os exemplares ordenados pelo parametro passado pelo usuário,
    * recebe nada e retorna uma lista de Livros.*/
    public List<Livro> buscaTodosLivros(String parametro) {
        String sql;
        switch (parametro){
            case "alfabeto":
                sql = "SELECT * FROM Livros ORDER BY saga, volume, titulo";
                break;
            case "data_inicio":
                sql = "SELECT * FROM Livros ORDER BY inicio, saga, volume, titulo";
                break;
            case "data_final":
                sql = "SELECT * FROM Livros ORDER BY final, saga, volume, titulo";
                break;
            default:
                sql = "SELECT * FROM Livros ORDER BY saga, volume, titulo";
                break;
        }

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);
        List<Livro> livros = new ArrayList<Livro>();
        while (c.moveToNext()) {
            Livro livro= new Livro();

            livro.setId(c.getLong(c.getColumnIndex("id")));
            livro.setTitulo(c.getString(c.getColumnIndex("titulo")));
            livro.setSaga(c.getString(c.getColumnIndex("saga")));
            livro.setVolume(c.getString(c.getColumnIndex("volume")));
            livro.setAutor(c.getString(c.getColumnIndex("autor")));
            livro.setCategoria(c.getString(c.getColumnIndex("categoria")));
            livro.setData_inicio(c.getString(c.getColumnIndex("inicio")));
            livro.setProgresso(c.getString(c.getColumnIndex("progresso")));
            livro.setData_final(c.getString(c.getColumnIndex("final")));
            livro.setIsbn(c.getString(c.getColumnIndex("isbn")));
            livro.setNotas_pessoais(c.getString(c.getColumnIndex("notas")));

            livros.add(livro);
        }
        c.close();
        return livros;
    }
}

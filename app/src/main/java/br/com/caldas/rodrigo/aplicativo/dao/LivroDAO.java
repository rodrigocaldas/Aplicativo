package br.com.caldas.rodrigo.aplicativo.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import br.com.caldas.rodrigo.aplicativo.modelo.Livro;

/**
 * Created by rodrigo on 06/02/16.
 */
public class LivroDAO extends SQLiteOpenHelper {
    public LivroDAO(Context context) {
        super(context, "Acervo", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Livros (id INTEGER PRIMARY KEY, titulo TEXT NOT NULL, saga TEXT, " +
                "volume TEXT, autor TEXT, tipo TEXT, categoria TEXT, inicio TEXT, progresso TEXT," +
                "final TEXT, isbn TEXT, notas TEXT);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS Livros;";
        db.execSQL(sql);
        onCreate(db);
    }

    private ContentValues pegaDadosLivro(Livro livro) {
        ContentValues dados = new ContentValues();
        dados.put("titulo", livro.getTitulo());
        dados.put("saga", livro.getSaga());
        dados.put("volume", livro.getVolume());
        dados.put("autor", livro.getAutor());
        dados.put("tipo", livro.getTipo());
        dados.put("categoria", livro.getCategoria());
        dados.put("inicio", livro.getData_inicio());
        dados.put("progresso", livro.getProgresso());
        dados.put("final", livro.getData_final());
        dados.put("isbn", livro.getIsbn());
        dados.put("notas", livro.getNotas_pessoais());

        return dados;
    }

    public void insere(Livro livro){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = pegaDadosLivro(livro);

        db.insert("Livros", null, dados);
    }

    public void altera(Livro livro){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = pegaDadosLivro(livro);
        String[] params = {String.valueOf(livro.getId())};
        db.update("Livros", dados, "id == ?", params);
    }

    public void apagarAluno(Livro livro) {
        SQLiteDatabase db = getWritableDatabase();
        String[] params = {String.valueOf(livro.getId())};
        db.delete("Livros", "id = ?", params);
    }


    public List<Livro> buscaLivros() {
        String sql = "SELECT * FROM Livros;";
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
            livro.setTipo(c.getString(c.getColumnIndex("tipo")));
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

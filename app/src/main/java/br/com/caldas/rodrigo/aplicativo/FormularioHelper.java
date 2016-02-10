package br.com.caldas.rodrigo.aplicativo;

import android.app.DatePickerDialog;
import android.view.View;
import android.widget.EditText;

import java.util.Calendar;

import br.com.caldas.rodrigo.aplicativo.modelo.Livro;

/**
 * Created by rodrigo on 31/01/16.
 */
/*Classe responsável por auxiliar no manejo do Formulario.*/
public class FormularioHelper {
    private EditText titulo;
    private EditText saga;
    private EditText volume;
    private EditText autor;
    private EditText tipo;
    private EditText categoria;
    private EditText data_inicio;
    private EditText progresso;
    private EditText data_final;
    private EditText isbn;
    private EditText notas_pessoais;
    private Livro livro;

    public FormularioHelper(Formulario activity){
        this.titulo = (EditText) activity.findViewById(R.id.formulario_titulo);
        this.saga = (EditText) activity.findViewById(R.id.formulario_saga);
        this.volume = (EditText) activity.findViewById(R.id.formulario_volume_saga);
        this.autor = (EditText) activity.findViewById(R.id.formulario_autor);
        this.tipo = (EditText) activity.findViewById(R.id.formulario_tipo);
        this.categoria = (EditText) activity.findViewById(R.id.formulario_categoria);
        this.data_inicio = (EditText) activity.findViewById(R.id.formulario_data_inicio);
        this.progresso = (EditText) activity.findViewById(R.id.formulario_pagina);
        this.data_final = (EditText) activity.findViewById(R.id.formulario_data_fim);
        this.isbn = (EditText) activity.findViewById(R.id.formulario_isbn);
        this.notas_pessoais = (EditText) activity.findViewById(R.id.formulario_nota_pessoal);

        livro = new Livro();
    }

    /*Responsável por setar os dados do Livro com as informações passadas nos EditText*/
    public Livro pegaLivro(){
        livro.setTitulo(titulo.getText().toString());
        livro.setSaga(saga.getText().toString());
        livro.setVolume(volume.getText().toString());
        livro.setAutor(autor.getText().toString());
        livro.setTipo(tipo.getText().toString());
        livro.setCategoria(categoria.getText().toString());
        livro.setData_inicio(data_inicio.getText().toString());
        livro.setProgresso(progresso.getText().toString());
        livro.setData_final(data_final.getText().toString());
        livro.setIsbn(isbn.getText().toString());
        livro.setNotas_pessoais(notas_pessoais.getText().toString());

        return livro;
    }

    /*Responsável por pegar os dados do Livro e preencher o formulario com eles.*/
    public void preencheFormulario(Livro livro){
        titulo.setText(livro.getTitulo());
        saga.setText(livro.getSaga());
        volume.setText(livro.getVolume());
        autor.setText(livro.getAutor());
        tipo.setText(livro.getTipo());
        categoria.setText(livro.getCategoria());
        data_inicio.setText(livro.getData_inicio());
        progresso.setText(livro.getProgresso());
        data_final.setText(livro.getData_final());
        isbn.setText(livro.getIsbn());
        notas_pessoais.setText(livro.getNotas_pessoais());

        this.livro = livro;
    }
}

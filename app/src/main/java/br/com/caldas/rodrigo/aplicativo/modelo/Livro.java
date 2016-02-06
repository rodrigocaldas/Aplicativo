package br.com.caldas.rodrigo.aplicativo.modelo;

import java.io.Serializable;

/**
 * Created by rodrigo on 31/01/16.
 */
public class Livro implements Serializable{
    private long id;
    private String titulo;
    private String saga;
    private String volume;
    private String autor;
    private String tipo;
    private String categoria;
    private String data_inicio;
    private String progresso;
    private String data_final;
    private String isbn;
    private String notas_pessoais;

    public long getId() {return id;}

    public void setId(long id) {this.id = id;}

    public String getTitulo() {return titulo;}

    public void setTitulo(String titulo) {this.titulo = titulo;}

    public String getSaga() {return saga;}

    public void setSaga(String saga) {this.saga = saga;}

    public String getVolume() {return volume;}

    public void setVolume(String volume) {this.volume = volume;}

    public String getAutor() {return autor;}

    public void setAutor(String autor) {this.autor = autor;}

    public String getTipo() {return tipo;}

    public void setTipo(String tipo) {this.tipo = tipo;}

    public String getCategoria() {return categoria;}

    public void setCategoria(String categoria) {this.categoria = categoria;}

    public String getData_inicio() {return data_inicio;}

    public void setData_inicio(String data_inicio) {this.data_inicio = data_inicio;}

    public String getProgresso() {return progresso;}

    public void setProgresso(String progresso) {this.progresso = progresso;}

    public String getData_final() {return data_final;}

    public void setData_final(String data_final) {this.data_final = data_final;}

    public String getIsbn() {return isbn;}

    public void setIsbn(String isbn) {this.isbn = isbn;}

    public String getNotas_pessoais() {return notas_pessoais;}

    public void setNotas_pessoais(String notas_pessoais) {this.notas_pessoais = notas_pessoais;}

    @Override
    public String toString() {
        return getTitulo();
    }
}

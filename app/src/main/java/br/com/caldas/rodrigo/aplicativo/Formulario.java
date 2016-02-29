package br.com.caldas.rodrigo.aplicativo;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import br.com.caldas.rodrigo.aplicativo.dao.LivroDAO;
import br.com.caldas.rodrigo.aplicativo.modelo.Livro;

public class Formulario extends AppCompatActivity{

    private FormularioHelper helper;
    private EditText inicial, fim, anotacoes;
    private int dia, mes, ano;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        this.helper = new FormularioHelper(this);

        //Resgatando o Livro passado junto com a Intent.
        Intent intent = getIntent();
        Livro livro = (Livro) intent.getSerializableExtra("livro");

        fim = (EditText) findViewById(R.id.formulario_data_fim);
        anotacoes = (EditText) findViewById(R.id.formulario_nota_pessoal);

        //Se não foi passado um livro, desativa os campos de anotações pessoais e data de término.
        //Se foi passado um livro, preenche o formulário.
        if(livro == null){
            fim.setVisibility(View.GONE);
            anotacoes.setVisibility(View.GONE);
        }else{
            helper.preencheFormulario(livro);

            fim.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar calendario = Calendar.getInstance();
                    dia = calendario.get(Calendar.DAY_OF_MONTH);
                    mes = calendario.get(Calendar.MONTH);
                    ano = calendario.get(Calendar.YEAR);
                    DatePickerDialog fim_datePicker = new DatePickerDialog(Formulario.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            fim.setText(dayOfMonth + " / " + (monthOfYear+1) + " / " +year);
                        }
                    }, ano, mes, dia);
                    fim_datePicker.setTitle("Data de término da leitura");
                    fim_datePicker.show();
                }
            });
        }

        inicial = (EditText) findViewById(R.id.formulario_data_inicio);
        inicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendario = Calendar.getInstance();
                dia = calendario.get(Calendar.DAY_OF_MONTH);
                mes = calendario.get(Calendar.MONTH);
                ano = calendario.get(Calendar.YEAR);
                DatePickerDialog inicial_datePicker = new DatePickerDialog(Formulario.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        inicial.setText(dayOfMonth + " / " + (monthOfYear+1) + " / " +year);
                    }
                }, ano, mes, dia);
                inicial_datePicker.setTitle("Data de início da leitura");
                inicial_datePicker.show();
            }
        });
    }


    /*Responsável por inflar o menu(menu_formulario) na activity.*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_formulario, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /*Responsável por verificar o item do menu que foi pressionado.*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_formulario_ok:
                Livro livro = helper.pegaLivro();
                //Verificando se o Título do livro foi deixado em branco
                if (livro.getTitulo().equals("")){
                    Toast.makeText(Formulario.this, "Não é possível cadastrar um livro sem título", Toast.LENGTH_SHORT).show();
                }else{
                    LivroDAO dao = new LivroDAO(this);

                    //Verificando se o ID do livro é null para poder alterar ou inserir o aluno no banco.
                    if(livro.getId() != 0L){
                        dao.altera(livro);
                    }else{
                        dao.insere(livro);
                    }
                    dao.close();

                    finish();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}

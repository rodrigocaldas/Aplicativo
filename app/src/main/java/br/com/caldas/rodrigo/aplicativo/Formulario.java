package br.com.caldas.rodrigo.aplicativo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;

import java.util.List;

import br.com.caldas.rodrigo.aplicativo.dao.LivroDAO;
import br.com.caldas.rodrigo.aplicativo.modelo.Livro;

public class Formulario extends AppCompatActivity {

    private FormularioHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        this.helper = new FormularioHelper(this);

        Intent intent = getIntent();
        Livro livro = (Livro) intent.getSerializableExtra("livro");
        if(livro != null){
            helper.preencheFormulario(livro);
        }
    }

    public void acao_formulario(View view){
        switch (view.getId()){
            case R.id.formulario_botao:
                Livro livro = helper.pegaLivro();
                LivroDAO dao = new LivroDAO(this);

                //Verificando se o ID do aluno é null para poder alterar ou inserir o aluno no banco
                if(livro.getId() != 0L){
                    dao.altera(livro);
                }else{
                    dao.insere(livro);
                }
                dao.close();

                finish();
                break;
        }
    }
}

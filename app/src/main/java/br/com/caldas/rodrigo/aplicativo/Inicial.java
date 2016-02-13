package br.com.caldas.rodrigo.aplicativo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.com.caldas.rodrigo.aplicativo.dao.LivroDAO;
import br.com.caldas.rodrigo.aplicativo.modelo.Livro;

public class Inicial extends AppCompatActivity {

    TextView quant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicial);
        quant = (TextView) findViewById(R.id.inicial_quantidade);
    }

    @Override
    protected void onResume() {
        super.onResume();
        quant.setText(recuperaQuantidade());
    }

    /*Responsável por inflar o menu(menu_inicial) na activity.*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_inicial, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /*Responsável por verificar o item do menu que foi pressionado.*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.inicial_menu_consultar:
                Intent irParaDisplay = new Intent(this, Display.class);
                startActivity(irParaDisplay);
                break;
            case R.id.inicial_menu_configuracao:

                Toast.makeText(Inicial.this, "Configuração clicado", Toast.LENGTH_SHORT).show();

                break;
            case R.id.inicial_menu_facebook:

                Toast.makeText(Inicial.this, "Facebook clicado", Toast.LENGTH_SHORT).show();

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /*Responsável por tratar a ação de botões que não estão no menu inflado.*/
    public void acao_inicial(View view){
        switch (view.getId()){
            case R.id.inicial_botao_add:

                Intent irParaCadastro = new Intent(this,Formulario.class);
                irParaCadastro.putExtra("origem", "inicial");
                startActivity(irParaCadastro);
                break;
        }
    }

    private String recuperaQuantidade() {
        LivroDAO dao = new LivroDAO(this);
        int quantidade = dao.quantidadeCadastrada();
        dao.close();
        return String.valueOf(quantidade);
    }

}

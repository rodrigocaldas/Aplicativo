package br.com.caldas.rodrigo.aplicativo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class Configuracao extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracao);
    }

    /*Responsável por inflar o menu(menu_configuracao) na activity.*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_configuracao, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /*Responsável por verificar o item do menu que foi pressionado.*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Atualiza a preferência de ordenação e atualiza a lista.
        switch (item.getItemId()){
            case R.id.configuracao_confirma:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}

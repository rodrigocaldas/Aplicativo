package br.com.caldas.rodrigo.aplicativo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.caldas.rodrigo.aplicativo.dao.LivroDAO;
import br.com.caldas.rodrigo.aplicativo.modelo.Livro;

public class Inicial extends AppCompatActivity {

    ListView listaLivros; //Isto é temporário nesta tela

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicial);

        //A partir daqui é temporário nessa tela
        listaLivros = (ListView) findViewById(R.id.lista_livros);
        listaLivros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Livro livro =(Livro) listaLivros.getItemAtPosition(position);
                Intent intentFormulario = new Intent(Inicial.this, Formulario.class);

                intentFormulario.putExtra("livro",livro);
                startActivity(intentFormulario);
            }
        });
        registerForContextMenu(listaLivros);
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaLista();
    }

    private void carregaLista() {
        LivroDAO dao = new LivroDAO(this);
        List<Livro> alunos = dao.buscaLivros();
        dao.close();

        ArrayAdapter<Livro> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, alunos);
        listaLivros.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_inicial, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.inicial_menu_consultar:

                Toast.makeText(Inicial.this, "Consulta clicada", Toast.LENGTH_SHORT).show();

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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {

        MenuItem deletar = menu.add("Apagar livro");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
                Livro livroClicado = (Livro) listaLivros.getItemAtPosition(info.position);

                LivroDAO dao = new LivroDAO(Inicial.this);
                dao.apagarLivro(livroClicado);
                dao.close();

                carregaLista();
                return false;
            }
        });

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    public void acao_inicial(View view){
        switch (view.getId()){
            case R.id.inicial_botao_add:

                Intent irParaCadastro = new Intent(this,Formulario.class);
                startActivity(irParaCadastro);
                break;
        }
    }

}

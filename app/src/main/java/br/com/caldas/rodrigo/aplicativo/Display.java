package br.com.caldas.rodrigo.aplicativo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.List;
import br.com.caldas.rodrigo.aplicativo.dao.LivroDAO;
import br.com.caldas.rodrigo.aplicativo.modelo.Livro;

public class Display extends AppCompatActivity {

    ListView listaLivros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        listaLivros = (ListView) findViewById(R.id.lista_livros);
        listaLivros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Livro livro = (Livro) listaLivros.getItemAtPosition(position);
                Intent irParaFormulario = new Intent(Display.this, Formulario.class);

                //Anexa à Intent um Livro e a origem que serão usados na activity_formulario.
                irParaFormulario.putExtra("livro", livro);
                irParaFormulario.putExtra("origem", "display");
                startActivity(irParaFormulario);
            }
        });
        //Registra que o ListView possui um menu de contexto.
        registerForContextMenu(listaLivros);
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaLista();
    }

    /*Responsável por inflar o menu(menu_display) na activity.*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_display, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /*Responsável por realizar a chamada ao LivroDAO para recuperar todos os Livros cadastrados e
    * carregar a lista de Livros no ListView.*/
    private void carregaLista() {
        LivroDAO dao = new LivroDAO(this);
        List<Livro> alunos = dao.buscaTodosLivros();
        dao.close();

        ArrayAdapter<Livro> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, alunos);
        listaLivros.setAdapter(adapter);
    }

    /*Responsável por tratar o menu de contexto acionado pelo longclick em um dos itens do ListView.*/
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {

        MenuItem deletar = menu.add("Apagar livro");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
                Livro livroClicado = (Livro) listaLivros.getItemAtPosition(info.position);

                LivroDAO dao = new LivroDAO(Display.this);
                dao.apagarLivro(livroClicado);
                dao.close();

                carregaLista();
                return false;
            }
        });

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    /*Responsável por tratar a ação de botões que não estão no menu inflado.*/
    public void acao_display(View view){
        switch (view.getId()){
            case R.id.display_botao_add:
                Intent irParaCadastro = new Intent(this,Formulario.class);
                irParaCadastro.putExtra("origem", "display");
                startActivity(irParaCadastro);
                break;
        }
    }
}

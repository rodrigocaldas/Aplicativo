package br.com.caldas.rodrigo.aplicativo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import br.com.caldas.rodrigo.aplicativo.dao.LivroDAO;
import br.com.caldas.rodrigo.aplicativo.modelo.Livro;

public class Display extends AppCompatActivity {

    private ListView listaLivros;
    private EditText busca;
    private ArrayAdapter<Livro> adapter;
    private ArrayList<String> sagasList = new ArrayList<>();
    private String[] sagasArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setIcon(R.drawable.search);

        listaLivros = (ListView) findViewById(R.id.lista_livros);
        listaLivros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Livro livro = (Livro) listaLivros.getItemAtPosition(position);

                sagasArray = sagasList.toArray(new String[sagasList.size()]);

                //Anexa à Intent um Livro que será usado na activity_formulario.
                Intent irParaFormulario = new Intent(Display.this, Formulario.class);
                irParaFormulario.putExtra("livro", livro);
                irParaFormulario.putExtra("autoComplete", sagasArray);
                startActivity(irParaFormulario);
            }
        });
        //Registra que o ListView possui um menu de contexto.
        registerForContextMenu(listaLivros);

        //Adiciona um Watcher para o filtro de busca.
        busca = (EditText) findViewById(R.id.display_busca);
        busca.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        String preferencia = acessaSharedPreferences();
        if (preferencia.equals("nada")){
            carregaLista("alfabeto");
        }else{
            carregaLista(preferencia);
        }
        busca.setText("");
    }

    /*Responsável por inflar o menu(menu_display) na activity.*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_display, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /*Responsável por verificar o item do menu que foi pressionado.*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Atualiza a preferência de ordenação e atualiza a lista.
        switch (item.getItemId()){
            case R.id.display_facebook:

                break;
            //ALTERAR -> Abrir um popup com as possibilidades de ordenação
            case R.id.display_sort:
                /*case R.id.display_alfabeto:
                    salvarSharedPreferences("alfabeto");
                    carregaLista(acessaSharedPreferences());
                    break;
                case R.id.display_inicio:
                    salvarSharedPreferences("data_inicio");
                    carregaLista(acessaSharedPreferences());
                    break;
                case R.id.display_final:
                    salvarSharedPreferences("data_final");
                    carregaLista(acessaSharedPreferences());
                    break;*/
                break;
            case R.id.display_sobre:

                break;
            case R.id.display_ajuda:

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /*Responsável por realizar a chamada ao LivroDAO para recuperar todos os Livros cadastrados e
    * carregar a lista de Livros no ListView.*/
    private void carregaLista(String ordenacao) {
        LivroDAO dao = new LivroDAO(this);
        List<Livro> livros = dao.buscaTodosLivros(ordenacao);
        dao.close();

        String aux;
        for(Livro l : livros) {
            aux = l.getSaga();
            if(!(aux.equals("") || sagasList.contains(aux))){
                sagasList.add(aux);
            }
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, livros);
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

                onResume();
                //carregaLista(acessaSharedPreferences());
                return false;
            }
        });

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    /*Responsável por tratar a ação de botões que não estão no menu inflado.*/
    public void acao_display(View view){
        switch (view.getId()){
            case R.id.display_botao_add:
                Intent irParaCadastro = new Intent(this, Formulario.class);
                irParaCadastro.putExtra("autoComplete", sagasArray);
                startActivity(irParaCadastro);
                break;
        }
    }

    /*Responsável por salvar a preferência do modo de ordenação.*/
    private void salvarSharedPreferences(String preferencia){
        SharedPreferences pref = getSharedPreferences("ordenacao", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putString("ordenacao",preferencia);

        editor.commit();
    }

    /*Responsável por recuperar a preferência do modo de ordenação.*/
    private String acessaSharedPreferences(){
        SharedPreferences pref = getSharedPreferences("ordenacao", Context.MODE_PRIVATE);
        String texto = pref.getString("ordenacao", "nada");

        return texto;
    }
}

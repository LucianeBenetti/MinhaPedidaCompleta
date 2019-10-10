package com.example.minhapedidacompleta.control;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.minhapedidacompleta.R;
import com.example.minhapedidacompleta.Uteis.Constantes;
import com.example.minhapedidacompleta.dao.db.CategoriaDao;
import com.example.minhapedidacompleta.dao.db.ProdutoDao;
import com.example.minhapedidacompleta.model.Categoria;
import com.example.minhapedidacompleta.model.Produto;
import com.example.minhapedidacompleta.view.GerenciarCategoriaActivity;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GerenciarProdutoControl {

    private Activity activity;
    private Spinner spCategoria;
    private EditText nomeProduto;
    private EditText valorProduto;
    private ListView lvGerenciarProdutos;
    private List<Produto> listProdutos;
    private ArrayAdapter<Produto> adapterProduto;
    private List<Categoria> listCategoria;
    private ArrayAdapter<Categoria> adapterCategoria;
    private Produto produto;

    private ProdutoDao produtoDao;
    private CategoriaDao categoriaDao;

    public GerenciarProdutoControl(Activity activity) {
        this.activity = activity;
        produtoDao = new ProdutoDao(activity);
        categoriaDao = new CategoriaDao(activity);
        produto = new Produto();

        initComponents();
    }

    private void initComponents() {
        lvGerenciarProdutos = activity.findViewById(R.id.lvGerenciarProdutos);
        spCategoria = activity.findViewById(R.id.spCategoria);
        nomeProduto = activity.findViewById(R.id.editNomeProduto);
        valorProduto = activity.findViewById(R.id.editValor);
        configSpinner();
        configListView();
    }

    public void configSpinner() {

        try {
            listCategoria = categoriaDao.getDao().queryForAll();
            adapterCategoria = new ArrayAdapter<>(activity, android.R.layout.simple_spinner_dropdown_item, listCategoria);
            spCategoria.setAdapter(adapterCategoria);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void configListView() {

        try {
            listProdutos = produtoDao.getDao().queryForAll();
        } catch (SQLException e) {
            listProdutos = new ArrayList<>();
        }
        adapterProduto = new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1, listProdutos);
        lvGerenciarProdutos.setAdapter(adapterProduto);
        cliqueCurto();
        cliqueLongo();
    }

    private void cliqueLongo() {
        lvGerenciarProdutos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                produto = adapterProduto.getItem(i);
                dialogExcluirProduto(produto);
                return true; //executa somente clique longo
            }
        });
    }


    private void addProdutoLv(Produto p) {
        adapterProduto.add(p);
    }

    private void cliqueCurto() {
        lvGerenciarProdutos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                produto = adapterProduto.getItem(i);

                dialogAdicionarProduto(produto);
            }
        });
    }

    private void dialogAdicionarProduto(Produto p) {
        AlertDialog.Builder alerta = new AlertDialog.Builder(activity);
        alerta.setTitle("Mostrando dados");
        alerta.setMessage(produto.toString());
        alerta.setNegativeButton("Fechar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                produto = null;
            }
        });
        alerta.setPositiveButton("Editar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                carregarForm(produto);
            }
        });
        alerta.show();
    }

    private void carregarForm(Produto produto) {
        nomeProduto.setText(produto.getNome());
        valorProduto.setText(produto.getValor().toString());

    }

    private void dialogExcluirProduto(final Produto p) {
        AlertDialog.Builder alerta = new AlertDialog.Builder(activity);
        alerta.setTitle("Excluir Item");
        alerta.setIcon(android.R.drawable.ic_menu_delete);
        alerta.setMessage(p.toString());
        alerta.setNegativeButton("Fechar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                produto = null;
            }
        });
        alerta.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                try {
                    if (produtoDao.getDao().delete(produto) > 0) {
                        excluirProdutoLv(produto);

                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                produto = null;
            }
        });
        alerta.show();

    }

    private void excluirProdutoLv(Produto p) {
        adapterProduto.remove(p);
    }

    public void telaAddItemCategoriaAction() {
        Intent it = new Intent(activity, GerenciarCategoriaActivity.class);
        activity.startActivityForResult(it, Constantes.Request.ITEM);

    }

    private Produto getDadosForm() {
        Produto p = new Produto();
        p.setNome(nomeProduto.getText().toString());
        p.setValor(valorProduto.getText().toString());
        p.setCategoria((Categoria) spCategoria.getSelectedItem());
        return p;
    }

    public void salvarProdutoAction() {
        if (produto == null) {
            produto = getDadosForm();
        } else {
            Produto p = getDadosForm();
            produto.setNome(p.getNome());
            produto.setValor(p.getValor());
        }
        Dao.CreateOrUpdateStatus res;
        try {
            res = produtoDao.getDao().createOrUpdate(produto);

            if (res.isCreated()) {
                addProdutoLv(produto);
            } else if (res.isUpdated()) {

                atualizarProduto(produto);
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        produto = null;
        configSpinner();
    }

    private void atualizarProduto(Produto p) {
        produto.setNome(p.getNome());
        produto.setValor(p.getValor());
        adapterProduto.notifyDataSetChanged();
    }
}

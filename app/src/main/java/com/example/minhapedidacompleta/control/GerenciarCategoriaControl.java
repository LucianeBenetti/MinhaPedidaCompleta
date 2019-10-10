package com.example.minhapedidacompleta.control;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.minhapedidacompleta.R;
import com.example.minhapedidacompleta.dao.db.CategoriaDao;
import com.example.minhapedidacompleta.model.Categoria;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class GerenciarCategoriaControl {

    private Activity activity;
    private EditText nomeCategoria;
    private ListView lvCategorias;
    private CategoriaDao categoriaDao;
    private List<Categoria> listCategoria;
    private ArrayAdapter<Categoria> adapterCategoria;
    private Categoria categoria;

    public GerenciarCategoriaControl(Activity activity) {
        categoriaDao = new CategoriaDao(activity);
        categoria = new Categoria();
        this.activity = activity;
        initComponents();
    }

    private void initComponents() {

        nomeCategoria = activity.findViewById(R.id.editNomeCategoria);
        lvCategorias = activity.findViewById(R.id.lvCategorias);
        configListView();
    }

    private void configListView() {

        try {
            listCategoria = categoriaDao.getDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        adapterCategoria = new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1, listCategoria);
        //para spinner tem um R.layout.spinner.
        lvCategorias.setAdapter(adapterCategoria);
        cliqueCurto();
        cliqueLongo();
    }

    private void cliqueLongo() {
        lvCategorias.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                categoria = adapterCategoria.getItem(i);
                dialogExcluirCategoria(categoria);
                return true; //executa somente clique longo
            }
        });

    }

    private void dialogExcluirCategoria(Categoria c) {
        AlertDialog.Builder alerta = new AlertDialog.Builder(activity);
        alerta.setTitle("Excluir Item");
        alerta.setIcon(android.R.drawable.ic_menu_delete);
        alerta.setMessage(c.toString());
        alerta.setNegativeButton("Fechar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                categoria = null;
            }
        });
        alerta.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                try {
                    if (categoriaDao.getDao().delete(categoria) > 0) {
                        excluirCategoriaLv(categoria);

                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                categoria = null;
            }
        });
        alerta.show();
    }


    private void cliqueCurto() {
        lvCategorias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                categoria = adapterCategoria.getItem(i);

                dialogAdicionarCategoria(categoria);
            }
        });
    }

    private void dialogAdicionarCategoria(Categoria c) {
        AlertDialog.Builder alerta = new AlertDialog.Builder(activity);
        alerta.setTitle("Mostrando dados");
        alerta.setMessage(categoria.toString());
        alerta.setNegativeButton("Fechar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                categoria = null;
            }
        });
        alerta.setPositiveButton("Editar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                carregarForm(categoria);
            }
        });
        alerta.show();
    }


    public void salvarCategoriaAction() {

        if (categoria == null) {
            categoria = getDadosForm();
        } else {
            Categoria c = getDadosForm();
            categoria.setNome(c.getNome());

        }
        Dao.CreateOrUpdateStatus res;
        try {
            res = categoriaDao.getDao().createOrUpdate(categoria);

            if (res.isCreated()) {
                addCategoriaLv(categoria);
            } else if (res.isUpdated()) {

                atualizarCategoria(categoria);
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        categoria = null;

    }

    private void addCategoriaLv(Categoria c) {
        adapterCategoria.add(c);
    }

    private void excluirCategoriaLv(Categoria c) {
        adapterCategoria.remove(c);
    }

    private void atualizarCategoria(Categoria c) {
        categoria.setNome(c.getNome());
        adapterCategoria.notifyDataSetChanged();
    }

    private Categoria getDadosForm() {
        Categoria c = new Categoria();
        c.setNome(nomeCategoria.getText().toString());
        return c;
    }

    private void carregarForm(Categoria e) {
        nomeCategoria.setText(e.getNome());

    }


}

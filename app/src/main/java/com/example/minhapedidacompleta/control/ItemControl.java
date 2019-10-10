package com.example.minhapedidacompleta.control;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minhapedidacompleta.R;
import com.example.minhapedidacompleta.Uteis.Constantes;
import com.example.minhapedidacompleta.dao.db.ItemDao;
import com.example.minhapedidacompleta.model.Item;
import com.example.minhapedidacompleta.view.ProdutoActivity;

import java.sql.SQLException;
import java.util.List;

public class ItemControl {

    private Activity activity;
    private TextView tvTotal;
    private ListView lvItens;
    private List<Item> listItem;
    private ArrayAdapter<Item> adapterItem;
    private Item item;
    private ItemDao itemDao;

    public ItemControl(Activity activity) {
        this.activity = activity;
        item = new Item();
        itemDao = new ItemDao(activity);
        initComponents();
    }

    private void initComponents() {

        tvTotal = activity.findViewById(R.id.tvTotal);
        lvItens = activity.findViewById(R.id.lvItens);
        configListView();
    }

    private void configListView() {

        try {
            listItem = itemDao.getDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        adapterItem = new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1, listItem);
        //para spinner tem um R.layout.spinner.
        lvItens.setAdapter(adapterItem);
        cliqueCurto();
        cliqueLongo();
    }

    private void addItemLv(Item i) {
        adapterItem.add(i);

    }

    private void excluirItemLv(Item i) {
        adapterItem.remove(i);
    }

    private void somarItem(Item i) {
        item.setQuantidade(i.getQuantidade() + 1);
        adapterItem.notifyDataSetChanged();
        item = null;
    }

    private void cliqueLongo() {
        lvItens.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                item = adapterItem.getItem(i);
                //return false = excuta o clique curto junto
                //  return false;
                dialogExcluirItem(item);
                //return false = excuta o clique curto junto
                //  return false;
                return true; //executa somente clique longo
            }
        });
    }

    private void cliqueCurto() {
        lvItens.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                item = adapterItem.getItem(i);

                dialogAdicionarUmItem(item);
            }
        });
    }


    private void atualizarTotal() {
        Double total = 0.0;
        for (Item item : listItem) {
            total += item.getSubtotal();

        }
        tvTotal.setText("Total: " + total);
    }


    private void dialogExcluirItem(final Item i) {

        AlertDialog.Builder alerta = new AlertDialog.Builder(activity);
        alerta.setTitle("Excluir Item");
        alerta.setIcon(android.R.drawable.ic_menu_delete);
        alerta.setMessage(i.toString());
        alerta.setNegativeButton("Fechar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                item = null;
            }
        });
        alerta.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                try {
                    if (itemDao.getDao().delete(item) > 0) {
                        excluirItemLv(i);
                        atualizarTotal();
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                item = null;
            }
        });
        alerta.show();
    }

    private void dialogAdicionarUmItem(final Item i) {

        AlertDialog.Builder alerta = new AlertDialog.Builder(activity);
        alerta.setTitle("Adicionar item");
        alerta.setMessage(i.toString());
        alerta.setNegativeButton("-1", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int pos) {
                item.removeQuantidade();//chamando metodo da classe
                adapterItem.notifyDataSetChanged();
                if (item.getQuantidade() == 0) {
                    try {
                        if (itemDao.getDao().delete(item) > 0) {
                            adapterItem.remove(item);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        if (itemDao.getDao().update(item) > 0) {
                            somarItem(item);//chamando método co control
                            atualizarTotal();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }
                item = null;
            }
        });
        alerta.setPositiveButton("+1", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int pos) {
                try {
                    if (itemDao.getDao().update(item) > 0) {
                        somarItem(i);//chamando método co control
                        atualizarTotal();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        alerta.setNeutralButton("Fechar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                item = null;
            }
        });
        alerta.show();
    }

    public void adicionarProdutoAction() {
        Intent it = new Intent(activity, ProdutoActivity.class);
        activity.startActivityForResult(it, Constantes.Request.ITEM);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == activity.RESULT_OK) {
            if (requestCode == Constantes.Request.ITEM) {
                item = (Item) data.getSerializableExtra(Constantes.Parametros.ITEM);
                try {
                    if (itemDao.getDao().create(item) > 0) {
                        addItemLv(item);
                        atualizarTotal();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                item = null;
            } else if (resultCode == activity.RESULT_CANCELED) {
                Toast.makeText(activity, R.string.acao_cancelada, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void limparListaAction() {
        adapterItem.clear();

    }
}

package com.example.minhapedidacompleta.dao.db;

import android.content.Context;

import com.example.minhapedidacompleta.dao.helpers.DaoHelper;
import com.example.minhapedidacompleta.model.Comanda;


public class ComandaDao extends DaoHelper<Comanda> {

    public ComandaDao(Context c) {
        super(c, Comanda.class);
    }
}

package com.example.minhapedidacompleta.dao.db;

import android.content.Context;

import com.example.minhapedidacompleta.dao.helpers.DaoHelper;
import com.example.minhapedidacompleta.model.Categoria;


public class CategoriaDao extends DaoHelper<Categoria> {

    public CategoriaDao(Context c) {
        super(c, Categoria.class);
    }

}

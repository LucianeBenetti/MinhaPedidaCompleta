package com.example.minhapedidacompleta.dao.db;


import android.content.Context;

import com.example.minhapedidacompleta.dao.helpers.DaoHelper;
import com.example.minhapedidacompleta.model.Produto;

public class ProdutoDao extends DaoHelper<Produto> {

    public ProdutoDao(Context c) {
        super(c, Produto.class);
    }
}


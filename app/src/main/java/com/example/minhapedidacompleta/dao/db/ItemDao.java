package com.example.minhapedidacompleta.dao.db;


import android.content.Context;

import com.example.minhapedidacompleta.dao.helpers.DaoHelper;
import com.example.minhapedidacompleta.model.Item;

public class ItemDao extends DaoHelper<Item> {

    public ItemDao(Context c) {
        super(c, Item.class);
    }
}

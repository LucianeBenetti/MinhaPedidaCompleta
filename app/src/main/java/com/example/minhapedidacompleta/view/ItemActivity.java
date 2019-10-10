package com.example.minhapedidacompleta.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.minhapedidacompleta.R;
import com.example.minhapedidacompleta.control.ItemControl;

public class ItemActivity extends AppCompatActivity {

    private ItemControl control;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        control = new ItemControl(this);
    }

    public void adicionarProduto(View v) {
        control.adicionarProdutoAction();
    }

    public void limparLista(View v) {
        control.limparListaAction();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        control.onActivityResult(requestCode, resultCode, data);
    }

}

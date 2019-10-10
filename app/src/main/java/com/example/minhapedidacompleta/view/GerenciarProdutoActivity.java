package com.example.minhapedidacompleta.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.minhapedidacompleta.R;
import com.example.minhapedidacompleta.control.GerenciarProdutoControl;

public class GerenciarProdutoActivity extends AppCompatActivity {
    private GerenciarProdutoControl control;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerenciar_produto);
        control = new GerenciarProdutoControl(this);
    }

    public void telaAddItemCategoria(View v) {
        control.telaAddItemCategoriaAction();
    }

    public void salvarProduto(View v) {
        control.salvarProdutoAction();
    }

    @Override
    protected void onResume() {
        super.onResume();
        control.configSpinner();
    }
}
package com.example.minhapedidacompleta.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.minhapedidacompleta.R;
import com.example.minhapedidacompleta.control.GerenciarCategoriaControl;

public class GerenciarCategoriaActivity extends AppCompatActivity {
    private GerenciarCategoriaControl control;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerenciar_categoria);
        control = new GerenciarCategoriaControl(this);
    }

    public void salvarCategoria(View v) {
        control.salvarCategoriaAction();
    }

}
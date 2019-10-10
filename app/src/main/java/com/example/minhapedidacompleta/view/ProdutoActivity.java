package com.example.minhapedidacompleta.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.minhapedidacompleta.R;
import com.example.minhapedidacompleta.control.ProdutoControl;

public class ProdutoActivity extends AppCompatActivity {
    private ProdutoControl control;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto);
        control = new ProdutoControl(this);

    }

    public void enviar(View v) {
        control.enviarAction();

    }

    public void cancelar(View v) {
        control.cancelarAction();

    }

    public void gerenciarProduto(View v) {
        control.gerenciarProdutoAction();

    }

    @Override
    protected void onResume() {
        super.onResume();
        control.configSpinner();
    }
}

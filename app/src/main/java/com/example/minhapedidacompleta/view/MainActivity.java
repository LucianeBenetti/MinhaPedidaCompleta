package com.example.minhapedidacompleta.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.minhapedidacompleta.R;
import com.example.minhapedidacompleta.control.ComandaControl;

public class MainActivity extends AppCompatActivity {

    private ComandaControl control;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        control = new ComandaControl(this);
    }

    public void novaComanda(View v) {
        control.novaComandaAction();
    }

    @Override
    protected void onResume() {
        super.onResume();
        control.configListView();
    }

}
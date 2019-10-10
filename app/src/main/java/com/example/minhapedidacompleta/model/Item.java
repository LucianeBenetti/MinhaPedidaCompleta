package com.example.minhapedidacompleta.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "item")
public class Item implements Serializable {

    @DatabaseField(allowGeneratedIdInsert = true, generatedId = true)
    private Integer id;

    @DatabaseField(canBeNull = false, columnName = "quantidade", defaultValue = "1")
    private int quantidade;

    @DatabaseField(canBeNull = false)
    private double valor;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Produto produto;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    Comanda comanda;

    public Item(Integer id, Produto produto, int quantidade) {
        this.id = id;
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public Item() {
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Item(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
        this.valor = this.produto.getValor();
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public void setQuantidade(String quantidade) {
        try {
            this.quantidade = Integer.parseInt(quantidade);
        } catch (Exception e) {
            this.quantidade = 0;
        }
    }

    public void addQuantidade() {
        this.quantidade++;
    }//não utilizado, no control está de outra forma

    public void removeQuantidade() {
        this.quantidade--;
    }

    public Double getSubtotal() {
        try {
            return valor * quantidade;
        } catch (Exception e) {
            return 0.0;
        }
    }

    @Override
    public String toString() {
        return produto.getNome() + " - R$ " + produto.getValor()
                + " - " + quantidade + " = R$ " + getSubtotal();
    }
}
package com.example.minhapedidacompleta.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Collection;

@DatabaseTable(tableName = "categoria")
public class Categoria implements Serializable {

    @DatabaseField(allowGeneratedIdInsert = true, generatedId = true)
    private Integer id;

    @DatabaseField(canBeNull = false, columnName = "categoria", width = 40)
    private String nome;

    @ForeignCollectionField(eager = true)
    private Collection<Produto> categoriaProduto;

    public Categoria() {

    }

    public Categoria(String nome) {
        this.nome = nome;
    }

    public Categoria(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Collection<Produto> getCategoriaProduto() {
        return categoriaProduto;
    }

    public void setCategoriaProduto(Collection<Produto> categoriaProduto) {
        this.categoriaProduto = categoriaProduto;
    }

    @Override
    public String toString() {
        if (getCategoriaProduto() != null)
            return id + " - " + nome + " (" + getCategoriaProduto().size() + ")";
        else
            return id + " - " + nome + " (0)";
    }
}

package com.example.geraldobarros.projetods_android_geraldo_barros;

/**
 * Created by Geraldo Barros on 19/12/2017.
 */

public class Loja {
    String id;
    String nome;
    String telefone;
    String endComplemento;
    String endBairro;
    String endNumero;
    String endLogradouro;

    public Loja(String id, String nome, String telefone) {
        super();
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
    }

    public Loja() {
        this("","","");
    }

    public String getEndComplemento() {
        return endComplemento;
    }

    public void setEndComplemento(String endComplemento) {
        this.endComplemento = endComplemento;
    }

    public String getEndBairro() {
        return endBairro;
    }

    public void setEndBairro(String endBairro) {
        this.endBairro = endBairro;
    }

    public String getEndNumero() {
        return endNumero;
    }

    public void setEndNumero(String endNumero) {
        this.endNumero = endNumero;
    }

    public String getEndLogradouro() {
        return endLogradouro;
    }

    public void setEndLogradouro(String endLogradouro) {
        this.endLogradouro = endLogradouro;
    }


    public String getId() {
        return id;

    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }



}

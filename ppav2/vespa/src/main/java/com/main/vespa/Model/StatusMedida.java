package com.main.vespa.Model;

public enum StatusMedida {
    ATIVA("Ativa"),
    VENCIDA("Vencida"),
    DESCUMPRIDA("Descumprida");

    private final String descricao;

    StatusMedida(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
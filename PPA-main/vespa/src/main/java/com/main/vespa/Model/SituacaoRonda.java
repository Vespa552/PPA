package com.main.vespa.Model;

public enum SituacaoRonda {
    CUMPRIMENTO("Cumprimento da medida"),
    DESCUMPRIMENTO("Descumprimento da medida"),
    ACOMPANHAMENTO("Acompanhamento");

    private final String descricao;

    SituacaoRonda(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
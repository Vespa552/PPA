package com.main.vespa.Model;

public enum TipoOcorrencia {
    FISICA("Física"),
    PSICOLOGICA("Psicológica"),
    SEXUAL("Sexual"),
    PATRIMONIAL("Patrimonial"),
    MORAL("Moral"),
    VICARIA("Vicária");

    private final String descricao;

    TipoOcorrencia(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
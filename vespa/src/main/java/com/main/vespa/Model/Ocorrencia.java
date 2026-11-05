package com.main.vespa.Model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;


enum tipo {
    fisica,
    psicologica,
    sexual,
    patrimonial,
    moral,
    vicaria
}

@Entity
@Data
public class Ocorrencia {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descricao;
    private Date cpf;
    @Column(nullable = false)
    private Enum tipo;

    @ManyToOne
    @JoinColumn(name = "id_ronda")
    private Ronda ronda;
}

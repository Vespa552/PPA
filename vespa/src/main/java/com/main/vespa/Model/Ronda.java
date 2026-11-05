package com.main.vespa.Model;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

enum situacao{
        cumprimento, 
        descumprimento, 
        acompanhamento
}

@Entity
@Data
public class Ronda {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date data;
    @Column(length = 200)
    private String local;
    @Column(nullable = false)
    private Enum situacao;
    private String observacoes;

    @ManyToOne
    @JoinColumn(name = "id_vitima")
    private Vitima vitima;

    @ManyToOne
    @JoinColumn(name = "id_agente")
    private Agente agente;

    @OneToMany(mappedBy = "ronda")
    private List<Ocorrencia> ocorrencias;
}

package com.main.vespa.Model;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Ronda {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private LocalDateTime dataHora;
    @Column(length = 200)
    private String local;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SituacaoRonda situacao;
    @Column(columnDefinition = "TEXT")
    private String observacoes;

    @ManyToOne
    @JoinColumn(name = "id_vitima")
    private Vitima vitima;

    @ManyToOne
    @JoinColumn(name = "id_agente")
    private Agente agente;

    @JsonIgnore 
    @OneToMany(mappedBy = "ronda")
    private List<Ocorrencia> ocorrencias;
}
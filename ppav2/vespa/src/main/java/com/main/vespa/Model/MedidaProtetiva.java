package com.main.vespa.Model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class MedidaProtetiva {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String tipo;
    @Column(nullable = false)
    private LocalDate dataInicio;
    private LocalDate dataFim;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusMedida status;

    @ManyToOne
    @JoinColumn(name = "id_vitima", nullable = false)
    private Vitima vitima;

    @ManyToOne
    @JoinColumn(name = "id_agressor", nullable = false)
    private Agressor agressor;
}
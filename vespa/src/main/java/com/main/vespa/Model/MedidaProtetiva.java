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

enum status{
    ativa, 
    vencida, 
    descumprida
}

@Entity
@Data
public class MedidaProtetiva{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String tipo;
    @Column(nullable = false)
    private Date dataInicio;
    private Date dataFim;
    @Column(nullable = false)
    private Enum status;

    @ManyToOne
    @JoinColumn(name = "id_vitima")
    private Vitima vitima;

    @ManyToOne
    @JoinColumn(name = "id_agressor")
    private Agressor agressor;
}

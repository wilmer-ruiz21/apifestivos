package org.ymcasas.springboot.api.festivos.modelos;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tipo")
@Data
public class Tipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tipo")
    private String tipo;

}

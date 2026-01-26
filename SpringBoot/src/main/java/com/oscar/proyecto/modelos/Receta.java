package com.oscar.proyecto.modelos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "receta")
public class Receta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(columnDefinition = "TEXT")
    private String tutorial;

    @Column(name = "tiempo_preparacion")
    @JsonProperty("tiempoPreparacion")
    private Integer tiempoPreparacion;

    @Column(name = "foto_url")
    @JsonProperty("fotoUrl")
    private String fotoUrl;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "receta_preferencia",
            joinColumns = @JoinColumn(name = "id_receta"),
            inverseJoinColumns = @JoinColumn(name = "id_preferencia")
    )
    @JsonManagedReference
    private Set<Preferencia> preferencias;

    @OneToMany(mappedBy = "receta")
    @JsonIgnore
    private Set<RecetaGuardada> guardados;

    @Transient
    private boolean guardada = false;
}

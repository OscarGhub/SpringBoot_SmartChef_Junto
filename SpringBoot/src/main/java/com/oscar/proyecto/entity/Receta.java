package com.oscar.proyecto.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @Column(columnDefinition = "LONGTEXT")
    private String tutorial;

    private Integer tiempo_preparacion;

    private String foto_url;

    @Column(name = "num_favoritos")
    private Long numFavoritos = 0L;

    @ManyToMany
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

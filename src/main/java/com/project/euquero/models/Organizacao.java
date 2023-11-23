package com.project.euquero.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "organizacao")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
        name = "tipo_organizacao",
        discriminatorType = DiscriminatorType.STRING)
public class Organizacao implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "url_site")
    private String urlSite;

    @Column(name = "url_imagem")
    private String urlImage;

    @OneToMany(mappedBy = "organizacao")
    private List<Endereco> enderecos;

    @Transient
    public String getDecriminatorValue(){
        return this.getClass().getAnnotation(DiscriminatorValue.class).value();
    }

}

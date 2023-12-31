package com.project.euquero.models;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("EMPRESA")
public class Empresa extends Organizacao implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Column(unique = true)
    private String cnpj;


}

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
@DiscriminatorValue("ONG")
public class Ongs extends Organizacao implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Column
    private String objetivo;

}

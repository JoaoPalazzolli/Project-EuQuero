package com.project.euquero.models;

import com.project.euquero.models.enums.TipoEmpresa;
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
@Table(name = "empresas")
public class Empresa implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descricao;

    @Column(unique = true)
    private String cnpj;

    @Column(nullable = false, name = "tipo_empresa")
    @Enumerated(EnumType.STRING)
    private TipoEmpresa tipoEmpresa;

    @Column(name = "url_empresa")
    private String urlEmpresa;

    @Column(name = "url_imagem")
    private String urlImage;

    @OneToMany(mappedBy = "empresa")
    private List<Endereco> enderecos;
}

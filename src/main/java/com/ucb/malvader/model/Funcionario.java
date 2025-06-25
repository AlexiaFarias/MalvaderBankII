package com.ucb.malvader.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Funcionario")
public class Funcionario extends Usuario {

    @Column(length = 20)
    private String codigoFuncionario;

    @Enumerated(EnumType.STRING)
    private Cargo cargo;

    @Column(length = 20)
    private String codigoEmpresa;

    public String getCodigoFuncionario() {
        return codigoFuncionario;
    }

    public void setCodigoFuncionario(String codigoFuncionario) {
        this.codigoFuncionario = codigoFuncionario;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public String getCodigoEmpresa() {
        return codigoEmpresa;
    }

    public void setCodigoEmpresa(String codigoEmpresa) {
        this.codigoEmpresa = codigoEmpresa;
    }
}

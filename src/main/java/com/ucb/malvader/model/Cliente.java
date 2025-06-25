package com.ucb.malvader.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Cliente")
@PrimaryKeyJoinColumn(name = "id_usuario")
public class Cliente extends Usuario {

    private Integer score_credito;

    public Integer getScore_credito() {
        return score_credito;
    }

    public void setScore_credito(Integer score_credito) {
        this.score_credito = score_credito;
    }
}



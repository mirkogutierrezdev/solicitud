package com.jpa.solicitud.solicitud.models.entities;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer rut;
    private String email;

    @ManyToOne
    @JoinColumn(name = "jefe_depto_id")
    private JefeDepto jefeDepto;

    public Funcionario() {
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Integer getRut() {
        return rut;
    }
    public void setRut(Integer rut) {
        this.rut = rut;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public JefeDepto getJefeDepto() {
        return jefeDepto;
    }

    public void setJefeDepto(JefeDepto jefeDepto) {
        this.jefeDepto = jefeDepto;
    }
}

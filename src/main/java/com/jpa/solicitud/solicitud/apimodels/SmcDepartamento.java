package com.jpa.solicitud.solicitud.apimodels;

public class SmcDepartamento {

    private String depto;
    private String nombreDepartamento;
    private String jefeDepartamento;
    private String cargoJefe;

    public String getDepto() {
        return depto;
    }

    public void setDepto(String depto) {
        this.depto = depto;
    }

    public String getNombreDepartamento() {
        return nombreDepartamento;
    }

    public void setNombreDepartamento(String nombreDepartamento) {
        this.nombreDepartamento = nombreDepartamento;
    }

    public String getJefeDepartamento() {
        return jefeDepartamento;
    }

    public void setJefeDepartamento(String jefeDepartamento) {
        this.jefeDepartamento = jefeDepartamento;
    }

    public String getCargoJefe() {
        return cargoJefe;
    }

    public void setCargoJefe(String cargoJefe) {
        this.cargoJefe = cargoJefe;
    }
}

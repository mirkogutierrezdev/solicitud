package com.jpa.solicitud.solicitud.models.entities;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "perfil_permiso")
public class PerfilPermiso implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Id
    @ManyToOne
    @JoinColumn(name = "perfil_id", nullable = false)
    private transient Perfil perfilUsuario;

    @Id
    @ManyToOne
    @JoinColumn(name = "permiso_id", nullable = false)
    private transient Permiso permisoUsuario;

    public Perfil getPerfilUsuario() {
        return perfilUsuario;
    }

    public void setPerfilUsuario(Perfil perfilUsuario) {
        this.perfilUsuario = perfilUsuario;
    }

    public Permiso getPermisoUsuario() {
        return permisoUsuario;
    }

    public void setPermisoUsuario(Permiso permisoUsuario) {
        this.permisoUsuario = permisoUsuario;
    }

    

    
}

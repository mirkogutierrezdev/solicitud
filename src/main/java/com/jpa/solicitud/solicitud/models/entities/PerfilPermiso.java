package com.jpa.solicitud.solicitud.models.entities;
import jakarta.persistence.*;

@Entity
@Table(name = "perfil_permiso")
public class PerfilPermiso {

    @EmbeddedId
    private PerfilPermisoId id;

    @ManyToOne
    @MapsId("perfilId") // Mapea el perfilId de la clave compuesta
    @JoinColumn(name = "perfil_id", nullable = false)
    private Perfil perfilUsuario;

    @ManyToOne
    @MapsId("permisoId") // Mapea el permisoId de la clave compuesta
    @JoinColumn(name = "permiso_id", nullable = false)
    private Permiso permisoUsuario;

    public PerfilPermiso() {}

    public PerfilPermiso(Perfil perfilUsuario, Permiso permisoUsuario) {
        this.perfilUsuario = perfilUsuario;
        this.permisoUsuario = permisoUsuario;
        this.id = new PerfilPermisoId(perfilUsuario.getId(), permisoUsuario.getId());
    }

    public PerfilPermisoId getId() {
        return id;
    }

    public void setId(PerfilPermisoId id) {
        this.id = id;
    }

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


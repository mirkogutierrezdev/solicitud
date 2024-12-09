package com.jpa.solicitud.solicitud.models.entities;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PerfilPermisoId implements Serializable {

    private Long perfilId;
    private Long permisoId;

    public PerfilPermisoId() {}

    public PerfilPermisoId(Long perfilId, Long permisoId) {
        this.perfilId = perfilId;
        this.permisoId = permisoId;
    }

    public Long getPerfilId() {
        return perfilId;
    }

    public void setPerfilId(Long perfilId) {
        this.perfilId = perfilId;
    }

    public Long getPermisoId() {
        return permisoId;
    }

    public void setPermisoId(Long permisoId) {
        this.permisoId = permisoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PerfilPermisoId that = (PerfilPermisoId) o;
        return Objects.equals(perfilId, that.perfilId) &&
               Objects.equals(permisoId, that.permisoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(perfilId, permisoId);
    }
}

package com.jpa.solicitud.solicitud.utils;

public class StringUtils {

    private StringUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static String buildName(String nombre, String apellidoPaterno, String apellidoMaterno) {

        StringBuilder sb = new StringBuilder();

        sb.append(nombre).append(" ").append(apellidoPaterno).append(" ").append(" ").append(apellidoMaterno);

        return sb.toString();
    }

}

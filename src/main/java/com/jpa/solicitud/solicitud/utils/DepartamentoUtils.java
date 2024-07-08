package com.jpa.solicitud.solicitud.utils;

public class DepartamentoUtils {

    public static String obtenerDireccion(String codigo) {
        return codigo.substring(0, 2) + "000000";
    }

    public static String obtenerDepartamento(String codigo) {
        return codigo.substring(0, 4) + "0000";
    }

    public static String obtenerSeccion(String codigo) {
        return codigo.substring(0, 6) + "00";
    }

    public static boolean esDireccion(String codigo) {
        return codigo.endsWith("000000");
    }

    public static boolean esDepartamento(String codigo) {
        return codigo.endsWith("0000") && !esDireccion(codigo);
    }

    public static boolean esSeccion(String codigo) {
        return codigo.endsWith("00") && !esDepartamento(codigo) && !esDireccion(codigo);
    }

    public static boolean esOficina(String codigo) {
        return !esSeccion(codigo) && !esDepartamento(codigo) && !esDireccion(codigo);
    }
}

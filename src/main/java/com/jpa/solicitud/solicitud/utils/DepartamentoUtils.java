package com.jpa.solicitud.solicitud.utils;

public class DepartamentoUtils {

    private DepartamentoUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static String obtenerDireccion(String codigo) {
        return codigo.substring(0, 2) + "000000";
    }

    public static String obtenerDepartamento(String codigo) {
        return codigo.substring(0, 4) + "0000";
    }

    public static String obtenerSeccion(String codigo) {
        return codigo.substring(0, 6) + "00";
    }

    public static String convertirEnDireccion(String codigo) {
        return codigo.substring(0, 2) + "00000000";
    }

    public static boolean esDireccion(String codigo) {
        return codigo.endsWith("00000000");
    }

    public static boolean esSubdir(String codigo) {

        return (codigo.endsWith("000000") && Integer.parseInt(codigo.substring(2, 4)) > 0);

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

    public static String determinaDerivacion(Long depto) {

        String codigo = depto.toString();

        if (DepartamentoUtils.esDireccion(codigo)) {
            return "1100000000";

        }

        if (DepartamentoUtils.esSubdir(codigo)) {
            return codigo.substring(0, 2).concat("00000000");
        }

        if (DepartamentoUtils.esDepartamento(codigo)) {
            return codigo.substring(0, 4).concat("000000");
        }

        if (DepartamentoUtils.esSeccion(codigo)) {
            return codigo.substring(0, 6).concat("0000");
        }

        if (DepartamentoUtils.esOficina(codigo)) {
            return codigo.substring(0, 8).concat("00");
        }

        return codigo;
    }

    public static boolean origenSubdir(String codigo) {

        return Integer.parseInt(codigo.substring(2, 4)) > 0;

    }

    public static boolean origenDir(String codigo) {

        return Integer.parseInt(codigo.substring(2, 4)) == 0;

    }
}

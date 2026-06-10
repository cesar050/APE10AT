package com.dialogforge.model;

/**
 * Representa la respuesta del motor DialogForge
 * tras analizar un script de diálogo RPG.
 * Indica si el script es válido sintácticamente
 * y retorna un mensaje descriptivo del resultado.
 *
 * @author Cesar Ramos
 * @version 1.0
 */
public class ParseResponse {

    /**
     * Indica si el script pasó el análisis léxico y sintáctico.
     */
    private boolean valido;

    /**
     * Mensaje descriptivo del resultado del análisis.
     * En caso de error indica la línea y columna del fallo.
     */
    private String mensaje;

    /**
     * Constructor completo.
     *
     * @param valido  true si el script es válido, false si tiene errores
     * @param mensaje descripción del resultado
     */
    public ParseResponse(boolean valido, String mensaje) {
        this.valido = valido;
        this.mensaje = mensaje;
    }

    /**
     * Indica si el script analizado es válido.
     *
     * @return true si es válido
     */
    public boolean isValido() {
        return valido;
    }

    /**
     * Obtiene el mensaje del resultado.
     *
     * @return mensaje descriptivo
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * Establece si el script es válido.
     *
     * @param valido resultado del análisis
     */
    public void setValido(boolean valido) {
        this.valido = valido;
    }

    /**
     * Establece el mensaje del resultado.
     *
     * @param mensaje descripción del resultado
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
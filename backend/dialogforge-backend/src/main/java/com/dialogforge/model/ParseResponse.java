package com.dialogforge.model;

import com.dialogforge.parser.NodoArbol;

/**
 * Representa la respuesta del motor DialogForge
 * tras analizar un script de diálogo RPG.
 * Incluye el resultado del análisis y el árbol de derivación.
 *
 * @author Cesar Ramos
 * @version 1.0
 */
public class ParseResponse {

    /** Indica si el script pasó el análisis léxico y sintáctico. */
    private boolean valido;

    /** Mensaje descriptivo del resultado del análisis. */
    private String mensaje;

    /** Árbol de derivación generado por el parser. */
    private NodoArbol arbol;

    /**
     * Constructor completo.
     *
     * @param valido  true si el script es válido
     * @param mensaje descripción del resultado
     * @param arbol   árbol de derivación
     */
    public ParseResponse(boolean valido, String mensaje, NodoArbol arbol) {
        this.valido = valido;
        this.mensaje = mensaje;
        this.arbol = arbol;
    }

    /**
     * Constructor sin árbol para respuestas de error.
     *
     * @param valido  false
     * @param mensaje descripción del error
     */
    public ParseResponse(boolean valido, String mensaje) {
        this.valido = valido;
        this.mensaje = mensaje;
        this.arbol = null;
    }

    public boolean isValido() { return valido; }
    public String getMensaje() { return mensaje; }
    public NodoArbol getArbol() { return arbol; }
    public void setValido(boolean valido) { this.valido = valido; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
    public void setArbol(NodoArbol arbol) { this.arbol = arbol; }
}
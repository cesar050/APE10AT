package com.dialogforge.parser;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa un nodo del árbol de derivación de la gramática DialogForge.
 * Cada nodo tiene un tipo, un valor opcional y una lista de hijos.
 *
 * @author Cesar Ramos
 * @version 1.0
 */
public class NodoArbol {

    /** Tipo del nodo, puede ser un terminal o no terminal. */
    private String tipo;

    /** Valor del nodo, solo aplica para terminales. */
    private String valor;

    /** Hijos del nodo en el árbol de derivación. */
    private List<NodoArbol> hijos;

    /**
     * Constructor para nodo terminal con valor.
     *
     * @param tipo  tipo del token
     * @param valor valor literal del token
     */
    public NodoArbol(String tipo, String valor) {
        this.tipo = tipo;
        this.valor = valor;
        this.hijos = new ArrayList<>();
    }

    /**
     * Constructor para nodo no terminal sin valor.
     *
     * @param tipo nombre del no terminal
     */
    public NodoArbol(String tipo) {
        this.tipo = tipo;
        this.valor = null;
        this.hijos = new ArrayList<>();
    }

    /**
     * Agrega un hijo al nodo.
     *
     * @param hijo nodo hijo a agregar
     */
    public void agregarHijo(NodoArbol hijo) {
        this.hijos.add(hijo);
    }

    public String getTipo() { return tipo; }
    public String getValor() { return valor; }
    public List<NodoArbol> getHijos() { return hijos; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public void setValor(String valor) { this.valor = valor; }
    public void setHijos(List<NodoArbol> hijos) { this.hijos = hijos; }
}
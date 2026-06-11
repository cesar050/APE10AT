package com.dialogforge.model;

/**
 * Representa la solicitud enviada al motor DialogForge.
 * Contiene el script de diálogo RPG que será analizado
 * léxica y sintácticamente.
 *
 * @authors Cesar Ramos, Cesar Lopez, Leonardo Espinoza
 * @version 1.0
 */
public class ParseRequest {

    /**
     * Script de diálogo RPG en texto plano.
     * Ejemplo: SAY "Bienvenido"; IF PLAYER_HAS "Espada" THEN SAY "Ve a luchar" ELSE GIVE "Espada";
     */
    private String script;

    /**
     * Obtiene el script de diálogo.
     *
     * @return script en texto plano
     */
    public String getScript() {
        return script;
    }

    /**
     * Establece el script de diálogo.
     *
     * @param script texto del guión RPG a analizar
     */
    public void setScript(String script) {
        this.script = script;
    }
}
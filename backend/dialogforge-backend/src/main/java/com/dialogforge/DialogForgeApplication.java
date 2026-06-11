package com.dialogforge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal del proyecto DialogForge.
 * Motor de análisis léxico y sintáctico para guiones
 * de diálogo de personajes no jugables (NPC) en videojuegos RPG.
 *
 * @authors Cesar Ramos, Cesar Lopez, Leonardo Espinoza
 * @version 1.0
 */
@SpringBootApplication
public class DialogForgeApplication {

    /**
     * Punto de entrada de la aplicación Spring Boot.
     *
     * @param args argumentos de línea de comandos
     */
    public static void main(String[] args) {
        SpringApplication.run(DialogForgeApplication.class, args);
    }
}
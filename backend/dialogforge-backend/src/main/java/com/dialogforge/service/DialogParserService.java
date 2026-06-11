package com.dialogforge.service;

import com.dialogforge.model.ParseRequest;
import com.dialogforge.model.ParseResponse;
import com.dialogforge.parser.DialogLexer;
import com.dialogforge.parser.DialogParser;
import org.springframework.stereotype.Service;

import java.io.StringReader;

/**
 * Servicio principal del motor DialogForge.
 * Coordina el análisis léxico y sintáctico de un script
 * de diálogo RPG utilizando DialogLexer (JFlex) y DialogParser (CUP).
 * Retorna el resultado del análisis junto con el árbol de derivación.
 *
 * @authors Cesar Ramos, Cesar Lopez, Leonardo Espinoza
 * @version 1.0
 */
@Service
public class DialogParserService {

    /**
     * Analiza un script de diálogo RPG.
     * Instancia el Lexer y el Parser, ejecuta el análisis
     * y retorna el resultado con el árbol de derivación generado.
     *
     * @param request objeto con el script a analizar
     * @return respuesta con el resultado y el árbol de derivación
     */
    public ParseResponse analizar(ParseRequest request) {
        try {
            StringReader reader = new StringReader(request.getScript());
            DialogLexer lexer = new DialogLexer(reader);
            DialogParser parser = new DialogParser(lexer);
            parser.parse();
            return new ParseResponse(true, "Script válido. El diálogo RPG es sintácticamente correcto.", parser.arbol);
        } catch (RuntimeException e) {
            String msg = e.getMessage();
            String script = request.getScript().trim();
            if (msg.contains("Syntax error")) {
                if (script.endsWith(";")) {
                    msg += ". El punto y coma (;) solo se usa como separador entre sentencias, no como terminador al final";
                }
            }
            return new ParseResponse(false, "Error sintáctico: " + msg);
        } catch (Exception e) {
            return new ParseResponse(false, "Error inesperado: " + e.getMessage());
        }
    }
}
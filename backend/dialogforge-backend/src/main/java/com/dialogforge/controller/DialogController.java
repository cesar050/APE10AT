package com.dialogforge.controller;

import com.dialogforge.model.ParseRequest;
import com.dialogforge.model.ParseResponse;
import com.dialogforge.service.DialogParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST del motor DialogForge.
 * Expone el endpoint para analizar scripts de diálogo RPG.
 * Permite peticiones desde cualquier origen (CORS habilitado)
 * para la integración con el frontend Vue.
 *
 * @authors Cesar Ramos, Cesar Lopez, Leonardo Espinoza
 * @version 1.0
 */
@RestController
@RequestMapping("/api/dialog")
@CrossOrigin(origins = "*")
public class DialogController {

    /**
     * Servicio que ejecuta el análisis léxico y sintáctico.
     */
    @Autowired
    private DialogParserService dialogParserService;

    /**
     * Endpoint POST para analizar un script de diálogo RPG.
     * Recibe el script en formato JSON y retorna si es válido
     * sintácticamente junto con un mensaje descriptivo.
     *
     * @param request objeto con el script a analizar
     * @return respuesta HTTP 200 con el resultado del análisis
     */
    @PostMapping("/analizar")
    public ResponseEntity<ParseResponse> analizar(@RequestBody ParseRequest request) {
        ParseResponse response = dialogParserService.analizar(request);
        return ResponseEntity.ok(response);
    }
}
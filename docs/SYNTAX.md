# Manual del Lenguaje DialogForge

Lenguaje de guiones para diálogos de PNJ (Personajes No Jugables) en videojuegos RPG.

---

## 1. Estructura general

Un script de diálogo es una **secuencia de una o más sentencias** separadas por `;`.

```
sentencia
sentencia ; sentencia
sentencia ; sentencia ; sentencia
```

**No existe `;` al final de la última sentencia.**

```
SAY "Hola"                  ← correcto
SAY "Hola";                 ← incorrecto (; al final sin otra sentencia)
SAY "Hola"; SAY "adiós"     ← correcto
```

---

## 2. `SAY "<texto>"`
**Qué hace:** El PNJ dice un mensaje al jugador.

```
SAY "Bienvenido a la aventura"
SAY "Cuidado con los goblins"
```

| Parte | Significado |
|---|---|
| `SAY` | Palabra clave: indica que el PNJ habla |
| `"<texto>"` | El mensaje entre comillas dobles |

**Reglas del texto:**
- Siempre entre comillas dobles: `"..."`
- Sin comillas dentro del texto: `SAY "di "hola""` es inválido
- Puede contener espacios, números, símbolos: `SAY "¡hola! 123"`

**Usos típicos:**
- Diálogo normal: `SAY "Ve al norte"`
- Tutorial: `SAY "Presiona E para interactuar"`
- Lore: `SAY "Este bosque fue un reino antiguo"`

---

## 3. `GIVE "<item>"`
**Qué hace:** El PNJ entrega un objeto al inventario del jugador.

```
GIVE "Espada de madera"
GIVE "Poción de vida"
```

| Parte | Significado |
|---|---|
| `GIVE` | Palabra clave: otorga un objeto |
| `"<item>"` | Nombre del objeto entre comillas |

**Usos típicos:**
- Recompensa: `GIVE "Llave del castillo"`
- Objeto inicial: `GIVE "Mapa del bosque"`
- Quest item: `GIVE "Carta del rey"`

---

## 4. Condicional: `IF PLAYER_HAS "<item>" THEN ... ELSE ...`
**Qué hace:** Pregunta si el jugador tiene un objeto en su inventario. Si lo tiene, ejecuta la consecuencia del THEN. Si no, ejecuta la del ELSE.

```
IF PLAYER_HAS "Espada" THEN SAY "Buena espada" ELSE GIVE "Espada"
```

| Parte | Significado |
|---|---|
| `IF` | Inicia la condición |
| `PLAYER_HAS "<item>"` | Verifica si el jugador posee el objeto |
| `THEN` | Separador: lo que sigue es la consecuencia si es verdadero |
| `<consecuencia>` | Acción(es) a ejecutar |
| `ELSE` | Separador: lo que sigue es la consecuencia si es falso |
| `<consecuencia>` | Acción(es) a ejecutar |

**El `IF` completo es UNA sola sentencia. No se puede partir con `;`:**

```
IF PLAYER_HAS "Llave" THEN SAY "abre" ELSE GIVE "Llave"
```

**Errores comunes:**

| Incorrecto | Por qué |
|---|---|
| `IF PLAYER_HAS "X" SAY "a" ELSE SAY "b"` | Falta `THEN` |
| `IF PLAYER_HAS "X" THEN SAY "a"` | Falta `ELSE` |
| `IF PLAYER_HAS "X" THEN ELSE SAY "b"` | THEN sin acción |
| `IF PLAYER_HAS "X"; THEN SAY "a" ELSE SAY "b"` | `;` rompe el IF |
| `IF PLAYER_HAS "X" THEN SAY "a" ELSE` | ELSE sin acción |

**Usos típicos:**
- Puerta cerrada: `IF PLAYER_HAS "Llave" THEN SAY "abriste" ELSE SAY "no tienes llave"`
- NPC quest: `IF PLAYER_HAS "Carta" THEN SAY "gracias" ELSE GIVE "Carta"`
- Tienda: `IF PLAYER_HAS "Oro" THEN GIVE "Poción" AND SAY "gracias" ELSE SAY "no tienes oro"`

---

## 5. `AND`: Múltiples acciones en THEN o ELSE

**Qué hace:** Permite ejecutar varias acciones seguidas dentro de un THEN o un ELSE.

```
THEN accion_simple
THEN accion_simple AND accion_simple AND accion_simple
ELSE accion_simple AND accion_simple
```

**Solo existe dentro de un THEN/ELSE.** No se puede usar fuera:

| Ejemplo | Válido |
|---|---|
| `THEN GIVE "Espada" AND SAY "toma"` | ✅ |
| `THEN SAY "ok" AND GIVE "Oro" AND SAY "listo"` | ✅ (3 acciones) |
| `SAY "hola" AND GIVE "x"` | ❌ (AND sin IF) |
| `GIVE "x" AND SAY "y"` | ❌ (AND sin IF) |

**Acciones válidas dentro de AND:**
- `SAY "<texto>"`
- `GIVE "<item>"`

**Ejemplos:**

```
IF PLAYER_HAS "Llave" THEN SAY "abres la puerta" AND GIVE "Experiencia"
                      ELSE GIVE "Llave" AND SAY "toma la llave" AND SAY "vuelve pronto"
```

---

## 6. `;` (punto y coma): Separador de sentencias

**Qué hace:** Separa una sentencia completa de la siguiente.

```
sentencia_1 ; sentencia_2 ; sentencia_3
```

| Uso | Válido |
|---|---|
| `SAY "a"; SAY "b"` | ✅ Separa dos sentencias |
| `SAY "a"; SAY "b"; SAY "c"` | ✅ Tres sentencias |
| `SAY "a";` | ❌ `;` al final sin otra sentencia |
| `SAY "a";; SAY "b"` | ❌ Doble `;` |
| `IF PLAYER_HAS "x"; THEN SAY "a"` | ❌ `;` dentro del IF |

**Regla de oro:** el `;` equivale a "y luego", no a "fin de línea".

```
SAY "Hola" ; GIVE "Mapa" ; IF PLAYER_HAS "Espada" THEN SAY "lucha" ELSE GIVE "Espada"
↑ sentencia 1   ↑ sentencia 2   ↑           sentencia 3 completa                  ↑
                                                                   sin ; al final  ok
```

---

## 7. Árbol de ejemplo visual

Para la sentencia:
```
IF PLAYER_HAS "Llave" THEN SAY "abre" ELSE GIVE "Llave" AND SAY "toma"
```

El árbol de derivación es:

```
                       Sentencia
                      /    |     \    |     \
                   IF  Condicion  THEN  Consec  ELSE  Consec
                        /     \         |               /    \
               PLAYER_HAS  STR_LIT   AccionSimple   Consec   AND   AccionSimple
                    "Llave"     SAY "abre"           |                    |
                                                  AccionSimple        SAY "toma"
                                                      |
                                                  GIVE "Llave"
```

---

## 8. Ejemplos completos

### Básico
```
SAY "Hola forastero"
GIVE "Pan"
```

### Tutorial
```
SAY "Bienvenido al juego"; GIVE "Espada de madera"; SAY "Ve a la cueva del norte"
```

### Quest simple
```
SAY "Necesito que recuperes mi anillo"
IF PLAYER_HAS "Anillo" THEN SAY "Gracias! Toma tu recompensa" AND GIVE "Oro"
                        ELSE SAY "Vuelve cuando tengas mi anillo"
```

### Mercader
```
IF PLAYER_HAS "Oro" THEN GIVE "Poción" AND SAY "Buena elección" ELSE SAY "No tienes suficiente oro"
```

### Puerta con llave
```
IF PLAYER_HAS "Llave de plata" THEN SAY "La puerta se abre" AND GIVE "Experiencia" AND SAY "Has ganado XP"
                               ELSE SAY "La puerta está cerrada con llave"
```

### Secuencia completa (varias sentencias)
```
SAY "Hola aventurero"; IF PLAYER_HAS "Mapa" THEN SAY "Ya tienes mapa" ELSE GIVE "Mapa" AND SAY "Toma un mapa";
SAY "Ten cuidado allá afuera"
```

---

## 9. Errores comunes y soluciones

| Error | Causa | Solución |
|---|---|---|
| `SAY "hola";` | `;` al final | Quitar el `;` final |
| `IF PLAYER_HAS "X" SAY "a"` | Falta `THEN` | Agregar `THEN` después de la condición |
| `IF PLAYER_HAS "X" THEN SAY "a"` | Falta `ELSE` | Agregar `ELSE` y una consecuencia |
| `IF PLAYER_HAS "X" THEN ELSE SAY "a"` | THEN vacío | Poner acción después de THEN |
| `SAY "hola" AND GIVE "x"` | AND fuera de IF | AND solo dentro de THEN/ELSE |
| `SAY "hola" "adiós"` | Dos textos seguidos | Una sentencia por vez, separar con `;` |
| `IF PLAYER_HAS X` | Falta `"..."` | Las comillas son obligatorias: `"X"` |

<template>
  <section class="panel panel-editor">
    <div class="panel-title">Script de Dialogo</div>
    <div class="test-cases">
      <button class="btn btn-test" @click="cargarCaso(0)" title="SAY &quot;Hola&quot;;">SAY simple</button>
      <button class="btn btn-test" @click="cargarCaso(1)">IF-THEN-ELSE</button>
      <button class="btn btn-test" @click="cargarCaso(2)">Acciones AND</button>
      <button class="btn btn-test" @click="cargarCaso(3)">Múltiple</button>
      <button class="btn btn-test" @click="cargarCaso(4)">Error sintáctico</button>
    </div>
    <button class="btn-toggle" @click="showTokens = !showTokens">
      {{ showTokens ? '▾' : '▸' }} Tokens permitidos
    </button>
    <div v-if="showTokens" class="token-ref">
      <table class="token-table">
        <tr><th>Token</th><th>Descripción</th><th>Ejemplo</th></tr>
        <tr><td class="tok">SAY</td><td>PNJ dice un mensaje</td><td><code>SAY "Hola";</code></td></tr>
        <tr><td class="tok">GIVE</td><td>Otorga un objeto al jugador</td><td><code>GIVE "Espada";</code></td></tr>
        <tr><td class="tok">IF</td><td>Inicia una condición</td><td><code>IF PLAYER_HAS ...</code></td></tr>
        <tr><td class="tok">THEN</td><td>Acción si la condición es verdadera</td><td><code>THEN SAY "ok";</code></td></tr>
        <tr><td class="tok">ELSE</td><td>Acción si la condición es falsa</td><td><code>ELSE GIVE "x";</code></td></tr>
        <tr><td class="tok">PLAYER_HAS</td><td>Verifica si el jugador posee un objeto</td><td><code>PLAYER_HAS "Llave"</code></td></tr>
        <tr><td class="tok">AND</td><td>Concatena acciones en THEN/ELSE</td><td><code>ELSE GIVE "x" AND SAY "y";</code></td></tr>
        <tr><td class="tok">;</td><td>Delimitador obligatorio de sentencia</td><td><code>SAY "a";</code></td></tr>
        <tr><td class="tok">STRING</td><td>Texto entre comillas dobles</td><td><code>"Hola mundo"</code></td></tr>
      </table>
    </div>
    <textarea
      :value="modelValue"
      @input="$emit('update:modelValue', $event.target.value)"
      class="editor"
      placeholder='SAY "Bienvenido"; IF PLAYER_HAS "Espada" THEN SAY "Ve a luchar" ELSE GIVE "Espada";'
      spellcheck="false"
    ></textarea>
    <div class="editor-actions">
      <button class="btn btn-secondary" @click="$emit('limpiar')">Limpiar</button>
      <button class="btn btn-primary" @click="$emit('analizar')" :disabled="cargando">
        {{ cargando ? 'Analizando...' : 'Analizar' }}
      </button>
    </div>
  </section>
</template>

<script>
export default {
  name: 'ScriptEditor',
  props: {
    modelValue: { type: String, default: '' },
    cargando: { type: Boolean, default: false }
  },
  emits: ['update:modelValue', 'analizar', 'limpiar'],
  data() {
    return {
      showTokens: false,
      casos: [
        'SAY "Hola";',
        'IF PLAYER_HAS "Espada" THEN SAY "ok" ELSE GIVE "Espada";',
        'IF PLAYER_HAS "Llave" THEN SAY "entra" ELSE GIVE "Llave" AND SAY "Toma";',
        'SAY "Bienvenido"; IF PLAYER_HAS "Espada" THEN SAY "Ve" ELSE GIVE "Espada" AND SAY "Toma esto";',
        'SAY "Hola" "Extra"'
      ]
    }
  },
  methods: {
    cargarCaso(idx) {
      this.$emit('update:modelValue', this.casos[idx])
    }
  }
}
</script>

<style scoped>
.panel-editor {
  background: #1e293b;
  border-radius: 10px;
  padding: 14px;
  display: flex;
  flex-direction: column;
  gap: 10px;
  border: 1px solid #2d3748;
}
.panel-title {
  font-size: 0.75rem;
  font-weight: 600;
  text-transform: uppercase;
  color: #3b82f6;
  letter-spacing: 0.08em;
}
.editor {
  flex: 1;
  background: #0f1117;
  border: 1px solid #2d3748;
  border-radius: 8px;
  color: #e2e8f0;
  font-family: 'Courier New', monospace;
  font-size: 0.85rem;
  padding: 12px;
  resize: none;
  outline: none;
  line-height: 1.6;
  min-height: 0;
}
.editor:focus { border-color: #3b82f6; }
.btn-toggle {
  background: none;
  border: none;
  color: #64748b;
  font-size: 0.75rem;
  cursor: pointer;
  text-align: left;
  padding: 2px 0;
  font-weight: 500;
}
.btn-toggle:hover { color: #3b82f6; }
.token-ref {
  background: #0f1117;
  border: 1px solid #2d3748;
  border-radius: 6px;
  padding: 8px;
  max-height: 220px;
  overflow-y: auto;
}
.token-table {
  width: 100%;
  border-collapse: collapse;
  font-size: 0.72rem;
}
.token-table th {
  text-align: left;
  color: #64748b;
  font-weight: 600;
  padding: 4px 6px;
  border-bottom: 1px solid #2d3748;
}
.token-table td {
  padding: 4px 6px;
  color: #cbd5e1;
  border-bottom: 1px solid #1e293b;
}
.token-table tr:last-child td { border-bottom: none; }
.tok {
  color: #3b82f6;
  font-family: 'Courier New', monospace;
  font-weight: 600;
  white-space: nowrap;
}
.token-table code {
  color: #94a3b8;
  font-family: 'Courier New', monospace;
  font-size: 0.7rem;
}
.test-cases {
  display: flex;
  flex-wrap: wrap;
  gap: 6px;
}
.btn-test {
  padding: 4px 10px;
  border-radius: 4px;
  border: 1px solid #334155;
  background: #0f1117;
  color: #94a3b8;
  cursor: pointer;
  font-size: 0.72rem;
  font-weight: 500;
  transition: all 0.2s;
}
.btn-test:hover {
  background: #1e293b;
  color: #3b82f6;
  border-color: #3b82f6;
}
.editor-actions {
  display: flex;
  gap: 8px;
  justify-content: flex-end;
}
.btn {
  padding: 8px 18px;
  border-radius: 6px;
  border: none;
  cursor: pointer;
  font-size: 0.85rem;
  font-weight: 600;
  transition: opacity 0.2s;
}
.btn:disabled { opacity: 0.5; cursor: not-allowed; }
.btn-primary { background: #3b82f6; color: #fff; }
.btn-primary:hover:not(:disabled) { background: #2563eb; }
.btn-secondary { background: #1e293b; color: #94a3b8; border: 1px solid #2d3748; }
.btn-secondary:hover { background: #2d3748; }
</style>
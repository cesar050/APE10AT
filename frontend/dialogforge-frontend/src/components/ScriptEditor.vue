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
      casos: [
        'SAY "Hola"',
        'IF PLAYER_HAS "Espada" THEN SAY "ok" ELSE GIVE "Espada"',
        'IF PLAYER_HAS "Llave" THEN SAY "entra" ELSE GIVE "Llave" AND SAY "Toma"',
        'SAY "Bienvenido"; IF PLAYER_HAS "Espada" THEN SAY "Ve" ELSE GIVE "Espada" AND SAY "Toma esto"',
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
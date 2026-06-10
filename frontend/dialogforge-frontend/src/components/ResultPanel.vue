<template>
  <div class="panel resultado" :class="resultadoClass">
    <div class="panel-title">Resultado</div>
    <div class="resultado-content">
      <div v-if="!analizado" class="resultado-empty">
        Escribe un script y presiona Analizar
      </div>
      <div v-else class="resultado-mensaje">
        <span class="resultado-icon">{{ valido ? '[OK]' : '[ERROR]' }}</span>
        <span>{{ mensaje }}</span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ResultPanel',
  props: {
    analizado: { type: Boolean, default: false },
    valido: { type: Boolean, default: false },
    mensaje: { type: String, default: '' }
  },
  computed: {
    resultadoClass() {
      if (!this.analizado) return ''
      return this.valido ? 'resultado-ok' : 'resultado-error'
    }
  }
}
</script>

<style scoped>
.panel {
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
.resultado-ok    { border-color: #4ade80; }
.resultado-error { border-color: #f87171; }
.resultado-content {
  min-height: 48px;
  display: flex;
  align-items: center;
}
.resultado-empty {
  color: #475569;
  font-size: 0.85rem;
}
.resultado-mensaje {
  display: flex;
  align-items: center;
  gap: 10px;
  font-size: 0.9rem;
}
.resultado-icon {
  font-weight: 700;
  color: #3b82f6;
  font-family: 'Courier New', monospace;
}
</style>
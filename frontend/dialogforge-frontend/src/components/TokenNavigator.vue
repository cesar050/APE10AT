<template>
  <div class="panel panel-tokens">
    <div class="panel-title">
      Tokens Lexicos
      <span class="token-counter" v-if="tokens.length > 0">
        {{ tokenIndex + 1 }} / {{ tokens.length }}
      </span>
    </div>

    <div class="token-display" v-if="tokens.length > 0">
      <div class="token-card">
        <div class="token-type">{{ tokens[tokenIndex].tipo }}</div>
        <div class="token-value">{{ tokens[tokenIndex].valor }}</div>
      </div>
    </div>

    <div class="token-empty" v-else>
      Los tokens aparecen tras analizar un script valido
    </div>

    <div class="token-nav" v-if="tokens.length > 0">
      <button
        class="btn btn-nav"
        @click="$emit('anterior')"
        :disabled="tokenIndex === 0"
      >
        Anterior
      </button>
      <button
        class="btn btn-nav"
        @click="$emit('siguiente')"
        :disabled="tokenIndex === tokens.length - 1"
      >
        Siguiente
      </button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'TokenNavigator',
  props: {
    tokens: { type: Array, default: () => [] },
    tokenIndex: { type: Number, default: 0 }
  },
  emits: ['anterior', 'siguiente']
}
</script>

<style scoped>
.panel-tokens {
  background: #1e293b;
  border-radius: 10px;
  padding: 14px;
  display: flex;
  flex-direction: column;
  gap: 10px;
  border: 1px solid #2d3748;
  flex: 1;
  min-height: 0;
}
.panel-title {
  font-size: 0.75rem;
  font-weight: 600;
  text-transform: uppercase;
  color: #3b82f6;
  letter-spacing: 0.08em;
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.token-counter {
  font-size: 0.7rem;
  color: #64748b;
  font-weight: 400;
  text-transform: none;
  letter-spacing: 0;
}
.token-display {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
}
.token-card {
  background: #0f1117;
  border: 1px solid #3b82f6;
  border-radius: 10px;
  padding: 20px 40px;
  text-align: center;
  width: 100%;
}
.token-type {
  font-size: 0.7rem;
  color: #3b82f6;
  text-transform: uppercase;
  letter-spacing: 0.1em;
  margin-bottom: 6px;
}
.token-value {
  font-size: 1.3rem;
  font-weight: 700;
  color: #e2e8f0;
  font-family: 'Courier New', monospace;
}
.token-empty {
  color: #475569;
  font-size: 0.85rem;
  text-align: center;
  padding: 20px 0;
}
.token-nav {
  display: flex;
  gap: 8px;
  justify-content: center;
}
.btn {
  padding: 6px 14px;
  border-radius: 6px;
  border: none;
  cursor: pointer;
  font-size: 0.85rem;
  font-weight: 600;
  transition: opacity 0.2s;
}
.btn-nav {
  background: #0f172a;
  color: #3b82f6;
  border: 1px solid #3b82f6;
}
.btn-nav:hover:not(:disabled) { background: #1e3a5f; }
.btn-nav:disabled { opacity: 0.3; cursor: not-allowed; }
</style>
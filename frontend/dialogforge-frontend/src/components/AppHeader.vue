<template>
  <header class="header">
    <div class="header-brand">
      <span class="brand-name">DialogForge</span>
      <span class="brand-sub">RPG Script Parser</span>
    </div>
    <div class="header-status" :class="statusClass">
      {{ statusText }}
    </div>
  </header>
</template>

<script>
export default {
  name: 'AppHeader',
  props: {
    analizado: { type: Boolean, default: false },
    valido: { type: Boolean, default: false }
  },
  computed: {
    statusClass() {
      if (!this.analizado) return 'status-idle'
      return this.valido ? 'status-ok' : 'status-error'
    },
    statusText() {
      if (!this.analizado) return 'En espera'
      return this.valido ? 'Valido' : 'Error sintactico'
    }
  }
}
</script>

<style scoped>
.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 8px;
}
.header-brand {
  display: flex;
  align-items: center;
  gap: 8px;
}
.brand-name { font-size: 1.2rem; font-weight: 700; color: #3b82f6; }
.brand-sub  { font-size: 0.75rem; color: #64748b; }
.header-status {
  font-size: 0.8rem;
  padding: 4px 12px;
  border-radius: 20px;
  font-weight: 600;
}
.status-idle  { background: #1e293b; color: #64748b; }
.status-ok    { background: #052e16; color: #4ade80; }
.status-error { background: #2d0a0a; color: #f87171; }
</style>
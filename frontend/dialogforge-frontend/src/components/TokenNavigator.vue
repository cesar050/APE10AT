<template>
  <div class="panel panel-tokens">
    <div class="panel-title">Tokens Lexicos</div>

    <div class="token-empty" v-if="tokens.length === 0">
      Los tokens aparecen tras analizar un script valido
    </div>

    <div class="token-table-wrapper" v-else>
      <table class="token-table">
        <thead>
          <tr>
            <th>#</th>
            <th>Tipo</th>
            <th>Valor</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(token, index) in tokens" :key="index">
            <td class="td-num">{{ index + 1 }}</td>
            <td>
              <span class="token-badge" :class="badgeClass(token.tipo)">
                {{ token.tipo }}
              </span>
            </td>
            <td class="token-value">{{ token.valor }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
export default {
  name: 'TokenNavigator',
  props: {
    tokens: { type: Array, default: () => [] }
  },
  methods: {
    badgeClass(tipo) {
      if (tipo === 'STRING_LITERAL') return 'badge-string'
      if (tipo === 'SEMICOLON') return 'badge-semicolon'
      return 'badge-keyword'
    }
  }
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
  overflow: hidden;
}
.panel-title {
  font-size: 0.75rem;
  font-weight: 600;
  text-transform: uppercase;
  color: #3b82f6;
  letter-spacing: 0.08em;
}
.token-empty {
  color: #475569;
  font-size: 0.85rem;
  text-align: center;
  padding: 20px 0;
}
.token-table-wrapper {
  flex: 1;
  overflow-y: auto;
  min-height: 0;
}
.token-table {
  width: 100%;
  border-collapse: collapse;
  font-size: 0.82rem;
}
.token-table thead tr {
  background: #0f1117;
  position: sticky;
  top: 0;
}
.token-table th {
  padding: 8px 12px;
  text-align: left;
  color: #3b82f6;
  font-weight: 600;
  font-size: 0.72rem;
  text-transform: uppercase;
  letter-spacing: 0.06em;
  border-bottom: 1px solid #2d3748;
}
.token-table td {
  padding: 7px 12px;
  border-bottom: 1px solid #1a2535;
  color: #e2e8f0;
}
.token-table tbody tr:hover {
  background: #243044;
}
.td-num {
  color: #475569;
  font-size: 0.75rem;
  width: 40px;
}
.token-badge {
  display: inline-block;
  padding: 2px 10px;
  border-radius: 4px;
  font-family: 'Courier New', monospace;
  font-size: 0.75rem;
  font-weight: 600;
}
.badge-keyword {
  background: #431407;
  color: #fb923c;
  border: 1px solid #9a3412;
}
.badge-string {
  background: #052e16;
  color: #4ade80;
  border: 1px solid #166534;
}
.badge-semicolon {
  background: #1e293b;
  color: #94a3b8;
  border: 1px solid #334155;
}
.token-value {
  font-family: 'Courier New', monospace;
  color: #94a3b8;
  font-size: 0.82rem;
}
</style>
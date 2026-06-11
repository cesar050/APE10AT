<template>
  <div class="app-container">
    <AppHeader
      :analizado="analizado"
      :valido="respuesta.valido"
    />

    <main class="main-grid">
      <!-- Columna izquierda -->
      <section class="col-left">
        <ScriptEditor
          v-model="script"
          :cargando="cargando"
          @analizar="analizar"
          @limpiar="limpiar"
        />
        <ResultPanel
          :analizado="analizado"
          :valido="respuesta.valido"
          :mensaje="respuesta.mensaje"
        />
      </section>

      <!-- Columna central -->
      <TokenNavigator :tokens="tokens" />

      <!-- Columna derecha -->
      <TreeView :arbol="respuesta.arbol" />
    </main>
  </div>
</template>

<script>
import axios from 'axios'
import AppHeader from './components/AppHeader.vue'
import ScriptEditor from './components/ScriptEditor.vue'
import ResultPanel from './components/ResultPanel.vue'
import TokenNavigator from './components/TokenNavigator.vue'
import TreeView from './components/TreeView.vue'

export default {
  name: 'App',
  components: { AppHeader, ScriptEditor, ResultPanel, TokenNavigator, TreeView },
  data() {
    return {
      script: '',
      cargando: false,
      analizado: false,
      respuesta: { valido: false, mensaje: '', arbol: null },
      tokens: []
    }
  },
  methods: {
    async analizar() {
      if (!this.script.trim()) return
      this.cargando = true
      this.analizado = false
      this.tokens = []
      this.respuesta = { valido: false, mensaje: '', arbol: null }
      try {
        const res = await axios.post('http://localhost:8080/api/dialog/analizar', {
          script: this.script
        })
        this.respuesta = res.data
        this.analizado = true
        if (res.data.valido) {
          this.extraerTokens()
        }
      } catch (e) {
        this.respuesta = { valido: false, mensaje: 'Error al conectar con el servidor', arbol: null }
        this.analizado = true
      } finally {
        this.cargando = false
      }
    },
    extraerTokens() {
      const keywords = ['SAY', 'IF', 'THEN', 'ELSE', 'GIVE', 'AND', 'PLAYER_HAS']
      const tokens = []
      const regex = /"[^"]*"|;|[A-Za-z_]+/g
      let match
      while ((match = regex.exec(this.script)) !== null) {
        const val = match[0]
        const valUpper = val.toUpperCase()
        if (val === ';') {
          tokens.push({ tipo: 'SEMICOLON', valor: ';' })
        } else if (val.startsWith('"')) {
          tokens.push({ tipo: 'STRING_LITERAL', valor: val })
        } else if (keywords.includes(valUpper)) {
          tokens.push({ tipo: valUpper, valor: val })
        }
      }
      this.tokens = tokens
    },
    limpiar() {
      this.script = ''
      this.analizado = false
      this.tokens = []
      this.respuesta = { valido: false, mensaje: '', arbol: null }
    }
  }
}
</script>

<style scoped>
.app-container {
  height: 100vh;
  display: flex;
  flex-direction: column;
  padding: 12px;
  gap: 12px;
}
.main-grid {
  flex: 1;
  display: grid;
  grid-template-columns: 1fr 1fr 1fr;
  gap: 12px;
  min-height: 0;
}
.col-left {
  display: flex;
  flex-direction: column;
  gap: 12px;
  min-height: 0;
}
</style>
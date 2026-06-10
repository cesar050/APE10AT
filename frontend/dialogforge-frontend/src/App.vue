<template>
  <div class="app-container">
    <AppHeader
      :analizado="analizado"
      :valido="respuesta.valido"
    />

    <main class="main-grid">
      <ScriptEditor
        v-model="script"
        :cargando="cargando"
        @analizar="analizar"
        @limpiar="limpiar"
      />

      <section class="panel-right">
        <ResultPanel
          :analizado="analizado"
          :valido="respuesta.valido"
          :mensaje="respuesta.mensaje"
        />
        <TokenNavigator
          :tokens="tokens"
          :tokenIndex="tokenIndex"
          @anterior="tokenAnterior"
          @siguiente="tokenSiguiente"
        />
      </section>
    </main>
  </div>
</template>

<script>
import axios from 'axios'
import AppHeader from './components/AppHeader.vue'
import ScriptEditor from './components/ScriptEditor.vue'
import ResultPanel from './components/ResultPanel.vue'
import TokenNavigator from './components/TokenNavigator.vue'

export default {
  name: 'App',
  components: { AppHeader, ScriptEditor, ResultPanel, TokenNavigator },
  data() {
    return {
      script: '',
      cargando: false,
      analizado: false,
      respuesta: { valido: false, mensaje: '' },
      tokens: [],
      tokenIndex: 0
    }
  },
  methods: {
    async analizar() {
      if (!this.script.trim()) return
      this.cargando = true
      this.analizado = false
      this.tokens = []
      this.tokenIndex = 0
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
        this.respuesta = { valido: false, mensaje: 'Error al conectar con el servidor' }
        this.analizado = true
      } finally {
        this.cargando = false
      }
    },
    extraerTokens() {
      const keywords = ['SAY', 'IF', 'THEN', 'ELSE', 'GIVE', 'AND', 'PLAYER_HAS']
      const tokens = []
      const regex = /"[^"]*"|;|[A-Z_]+/g
      let match
      while ((match = regex.exec(this.script)) !== null) {
        const val = match[0]
        if (val === ';') {
          tokens.push({ tipo: 'SEMICOLON', valor: ';' })
        } else if (val.startsWith('"')) {
          tokens.push({ tipo: 'STRING_LITERAL', valor: val })
        } else if (keywords.includes(val)) {
          tokens.push({ tipo: val, valor: val })
        }
      }
      this.tokens = tokens
    },
    tokenAnterior() {
      if (this.tokenIndex > 0) this.tokenIndex--
    },
    tokenSiguiente() {
      if (this.tokenIndex < this.tokens.length - 1) this.tokenIndex++
    },
    limpiar() {
      this.script = ''
      this.analizado = false
      this.tokens = []
      this.tokenIndex = 0
      this.respuesta = { valido: false, mensaje: '' }
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
  grid-template-columns: 1fr 1fr;
  gap: 12px;
  min-height: 0;
}
.panel-right {
  display: flex;
  flex-direction: column;
  gap: 12px;
  min-height: 0;
}
</style>
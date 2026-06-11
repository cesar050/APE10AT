<template>
  <div class="panel panel-tree">
    <div class="panel-title">
      Arbol de Derivacion
      <div class="zoom-controls" v-if="arbol">
        <button class="btn-zoom" @click="zoomIn">+</button>
        <button class="btn-zoom" @click="zoomOut">-</button>
        <button class="btn-zoom" @click="resetZoom">reset</button>
      </div>
    </div>
    <div class="tree-empty" v-if="!arbol">
      El arbol aparece tras analizar un script valido
    </div>
    <div class="tree-wrapper" ref="treeWrapper" v-else></div>
  </div>
</template>

<script>
import * as d3 from 'd3'

export default {
  name: 'TreeView',
  props: {
    arbol: { type: Object, default: null }
  },
  data() {
    return {
      svgElement: null,
      zoomBehavior: null
    }
  },
  watch: {
    arbol(val) {
      if (val) {
        this.$nextTick(() => this.dibujar())
      } else {
        if (this.$refs.treeWrapper) {
          this.$refs.treeWrapper.innerHTML = ''
        }
      }
    }
  },
  methods: {
    getColor(tipo) {
      const keywords = ['SAY', 'IF', 'THEN', 'ELSE', 'GIVE', 'AND', 'PLAYER_HAS', 'SEMICOLON']
      if (tipo === 'STRING_LITERAL') return { fill: '#052e16', stroke: '#166534', text: '#4ade80' }
      if (keywords.includes(tipo)) return { fill: '#431407', stroke: '#9a3412', text: '#fb923c' }
      return { fill: '#1e3a5f', stroke: '#2563eb', text: '#3b82f6' }
    },
    convertir(nodo, parent = null) {
      return {
        name: nodo.tipo,
        valor: nodo.valor || null,
        children: nodo.hijos && nodo.hijos.length > 0
          ? nodo.hijos.map(h => this.convertir(h))
          : undefined
      }
    },
    dibujar() {
      const wrapper = this.$refs.treeWrapper
      if (!wrapper) return
      wrapper.innerHTML = ''

      const width = wrapper.clientWidth || 400
      const height = wrapper.clientHeight || 400
      const nodeWidth = 110
      const nodeHeight = 50

      const root = d3.hierarchy(this.convertir(this.arbol))
      const treeLayout = d3.tree().nodeSize([nodeWidth + 20, nodeHeight + 40])
      treeLayout(root)

      const svg = d3.select(wrapper)
        .append('svg')
        .attr('width', '100%')
        .attr('height', '100%')

      const g = svg.append('g')

      this.zoomBehavior = d3.zoom()
        .scaleExtent([0.2, 3])
        .on('zoom', (event) => {
          g.attr('transform', event.transform)
        })

      svg.call(this.zoomBehavior)
      this.svgElement = svg

      // Centrar
      const nodes = root.descendants()
      const minX = d3.min(nodes, d => d.x)
      const maxX = d3.max(nodes, d => d.x)
      const centerX = width / 2 - (minX + maxX) / 2
      const centerY = 60

      g.attr('transform', `translate(${centerX}, ${centerY})`)
      svg.call(this.zoomBehavior.transform, d3.zoomIdentity.translate(centerX, centerY))

      // Links
      g.selectAll('.link')
        .data(root.links())
        .enter()
        .append('path')
        .attr('class', 'link')
        .attr('fill', 'none')
        .attr('stroke', '#3b82f6')
        .attr('stroke-width', 1.5)
        .attr('stroke-opacity', 0.5)
        .attr('d', d3.linkVertical()
          .x(d => d.x)
          .y(d => d.y)
        )

      // Nodos
      const node = g.selectAll('.node')
        .data(root.descendants())
        .enter()
        .append('g')
        .attr('class', 'node')
        .attr('transform', d => `translate(${d.x}, ${d.y})`)

      node.each(function(d) {
        const tipo = d.data.name
        const keywords = ['SAY', 'IF', 'THEN', 'ELSE', 'GIVE', 'AND', 'PLAYER_HAS', 'SEMICOLON']
        let fill, stroke, textColor

        if (tipo === 'STRING_LITERAL') {
          fill = '#052e16'; stroke = '#166534'; textColor = '#4ade80'
        } else if (keywords.includes(tipo)) {
          fill = '#431407'; stroke = '#9a3412'; textColor = '#fb923c'
        } else {
          fill = '#1e3a5f'; stroke = '#2563eb'; textColor = '#3b82f6'
        }

        const el = d3.select(this)

        el.append('rect')
          .attr('x', -50)
          .attr('y', -16)
          .attr('width', 100)
          .attr('height', 32)
          .attr('rx', 8)
          .attr('fill', fill)
          .attr('stroke', stroke)
          .attr('stroke-width', 1.5)

        el.append('text')
          .attr('text-anchor', 'middle')
          .attr('dy', d.data.valor ? '-2' : '5')
          .attr('fill', textColor)
          .attr('font-family', 'Courier New, monospace')
          .attr('font-size', '11px')
          .attr('font-weight', 'bold')
          .text(tipo)

        if (d.data.valor) {
          el.append('text')
            .attr('text-anchor', 'middle')
            .attr('dy', '12')
            .attr('fill', '#94a3b8')
            .attr('font-family', 'Courier New, monospace')
            .attr('font-size', '9px')
            .text(d.data.valor)
        }
      })
    },
    zoomIn() {
      if (this.svgElement && this.zoomBehavior) {
        this.svgElement.transition().call(this.zoomBehavior.scaleBy, 1.3)
      }
    },
    zoomOut() {
      if (this.svgElement && this.zoomBehavior) {
        this.svgElement.transition().call(this.zoomBehavior.scaleBy, 0.7)
      }
    },
    resetZoom() {
      const wrapper = this.$refs.treeWrapper
      if (!wrapper || !this.svgElement || !this.zoomBehavior) return
      const width = wrapper.clientWidth || 400
      this.svgElement.transition().call(
        this.zoomBehavior.transform,
        d3.zoomIdentity.translate(width / 2, 60)
      )
    }
  }
}
</script>

<style scoped>
.panel-tree {
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
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.zoom-controls {
  display: flex;
  gap: 4px;
}
.btn-zoom {
  background: #0f172a;
  color: #3b82f6;
  border: 1px solid #3b82f6;
  border-radius: 4px;
  padding: 2px 8px;
  cursor: pointer;
  font-size: 0.75rem;
  font-weight: 600;
}
.btn-zoom:hover { background: #1e3a5f; }
.tree-empty {
  color: #475569;
  font-size: 0.85rem;
  text-align: center;
  padding: 20px 0;
}
.tree-wrapper {
  flex: 1;
  min-height: 0;
  cursor: grab;
}
.tree-wrapper:active { cursor: grabbing; }
</style>
package com.search.practical.work;

import java.util.List;

public class ResultadoBusqueda {
    private final boolean encontrado;
    private final int nodosEvaluados;
    private final List<Coordenada> ruta;

    public ResultadoBusqueda(boolean encontrado, int nodosEvaluados, List<Coordenada> ruta) {
        this.encontrado = encontrado;
        this.nodosEvaluados = nodosEvaluados;
        this.ruta = ruta;
    }

    public boolean isEncontrado() { return encontrado; }
    public int getNodosEvaluados() { return nodosEvaluados; }
    public List<Coordenada> getRuta() { return ruta; }
}

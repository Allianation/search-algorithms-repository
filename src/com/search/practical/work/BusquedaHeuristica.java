package com.search.practical.work;

import java.util.*;

public class BusquedaHeuristica {

    private static class Nodo implements Comparable<Nodo> {
        Coordenada coord;
        double g; // Costo exacto desde el inicio hasta este nodo
        double h; // Costo heurístico estimado hasta la meta
        double f; // Costo total f = g + h
        Nodo padre; // Para reconstruir la trayectoria óptima

        Nodo(Coordenada coord) {
            this.coord = coord;
        }

        @Override
        public int compareTo(Nodo o) {
            int comparacionF = Double.compare(this.f, o.f);
            if (comparacionF == 0) {
                // Criterio de desempate: priorizar el nodo con menor distancia restante (h)
                return Double.compare(this.h, o.h);
            }
            return comparacionF;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Nodo)) return false;
            Nodo n = (Nodo) o;
            return coord.equals(n.coord);
        }

        @Override
        public int hashCode() {
            return coord.hashCode();
        }
    }

    public static ResultadoBusqueda buscar(int filas, int cols, Coordenada inicioCoord, Coordenada metaCoord) {
        PriorityQueue<Nodo> abierta = new PriorityQueue<>();
        Set<Nodo> cerrada = new HashSet<>();
        int[][] movimientos = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        Nodo inicio = new Nodo(inicioCoord);
        Nodo meta = new Nodo(metaCoord);

        inicio.g = 0;
        inicio.h = calcularHeuristica(inicio.coord, meta.coord);
        inicio.f = inicio.g + inicio.h;
        abierta.add(inicio);

        int nodosEvaluados = 0;
        boolean encontrado = false;
        Nodo nodoDestino = null;

        while (!abierta.isEmpty()) {
            Nodo actual = abierta.poll();
            cerrada.add(actual);
            nodosEvaluados++;

            if (actual.coord.equals(meta.coord)) {
                encontrado = true;
                nodoDestino = actual;
                break;
            }

            // Evaluar movimientos posibles a partir de la posición actual
            for (int[] mov : movimientos) {
                int nx = actual.coord.x + mov[0];
                int ny = actual.coord.y + mov[1];

                if (nx >= 0 && nx < filas && ny >= 0 && ny < cols) {
                    Nodo vecino = new Nodo(new Coordenada(nx, ny));
                    if (cerrada.contains(vecino)) continue;

                    double costoG = actual.g + 1;
                    boolean enAbierta = abierta.contains(vecino);

                    if (!enAbierta || costoG < vecino.g) {
                        vecino.padre = actual;
                        vecino.g = costoG;
                        vecino.h = calcularHeuristica(vecino.coord, meta.coord);
                        vecino.f = vecino.g + vecino.h;

                        if (!enAbierta) {
                            abierta.add(vecino);
                        } else {
                            abierta.remove(vecino);
                            abierta.add(vecino);
                        }
                    }
                }
            }
        }

        List<Coordenada> ruta = new ArrayList<>();
        if (encontrado && nodoDestino != null) {
            Nodo temp = nodoDestino;
            while (temp != null) {
                ruta.add(temp.coord);
                temp = temp.padre;
            }
            Collections.reverse(ruta);
        }

        return new ResultadoBusqueda(encontrado, nodosEvaluados, ruta);
    }

    private static double calcularHeuristica(Coordenada c1, Coordenada c2) {
        return Math.abs(c1.x - c2.x) + Math.abs(c1.y - c2.y);
    }
}
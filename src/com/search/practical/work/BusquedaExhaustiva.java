package com.search.practical.work;

import java.util.*;

public class BusquedaExhaustiva {

    public static ResultadoBusqueda buscar(int filas, int cols, Coordenada inicio, Coordenada meta) {
        Queue<Coordenada> frontera = new LinkedList<>();
        Set<Coordenada> explorados = new HashSet<>();
        Map<Coordenada, Coordenada> padres = new HashMap<>();
        
        // Movimientos permitidos del brazo robótico: Arriba, Abajo, Izquierda, Derecha
        int[][] movimientos = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        frontera.add(inicio);
        explorados.add(inicio);
        padres.put(inicio, null);

        int nodosEvaluados = 0;
        boolean encontrado = false;

        while (!frontera.isEmpty()) {
            Coordenada actual = frontera.poll();
            nodosEvaluados++;

            if (actual.equals(meta)) {
                encontrado = true;
                break;
            }

            // Expandir a los nodos vecinos (movimientos del brazo)
            for (int[] mov : movimientos) {
                int nx = actual.x + mov[0];
                int ny = actual.y + mov[1];
                Coordenada vecino = new Coordenada(nx, ny);

                // Verificar límites del área de trabajo y si ya fue explorado
                if (nx >= 0 && nx < filas && ny >= 0 && ny < cols && !explorados.contains(vecino)) {
                    frontera.add(vecino);
                    explorados.add(vecino);
                    padres.put(vecino, actual);
                }
            }
        }

        List<Coordenada> ruta = new ArrayList<>();
        if (encontrado) {
            Coordenada temp = meta;
            while (temp != null) {
                ruta.add(temp);
                temp = padres.get(temp);
            }
            Collections.reverse(ruta);
        }

        return new ResultadoBusqueda(encontrado, nodosEvaluados, ruta);
    }
}

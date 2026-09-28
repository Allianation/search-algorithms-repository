package com.search.practical.work;

import javax.swing.*;
import java.awt.*;
import java.util.stream.Collectors;

public class SimuladorBrazoRobotico extends JFrame {

    private static final long serialVersionUID = -3523206436831619459L;
    
	private JTextField txtFilas, txtCols, txtInicioX, txtInicioY, txtMetaX, txtMetaY;
    private JTextArea txtConsola;

    public SimuladorBrazoRobotico() {
        setTitle("Simulador de Búsqueda - Brazo Robótico (IA)");
        setSize(750, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // --- PANEL DE DATOS ---
        JPanel panelDatos = new JPanel(new GridLayout(3, 4, 5, 5));
        panelDatos.setBorder(BorderFactory.createTitledBorder("Configuración del Entorno (Motor)"));

        panelDatos.add(new JLabel("Filas (Tamaño):"));
        txtFilas = new JTextField("10");
        panelDatos.add(txtFilas);

        panelDatos.add(new JLabel("Columnas (Tamaño):"));
        txtCols = new JTextField("10");
        panelDatos.add(txtCols);

        panelDatos.add(new JLabel("Inicio X (Pos B):"));
        txtInicioX = new JTextField("0");
        panelDatos.add(txtInicioX);

        panelDatos.add(new JLabel("Inicio Y (Pos B):"));
        txtInicioY = new JTextField("0");
        panelDatos.add(txtInicioY);

        panelDatos.add(new JLabel("Meta X (Pos A):"));
        txtMetaX = new JTextField("8");
        panelDatos.add(txtMetaX);

        panelDatos.add(new JLabel("Meta Y (Pos A):"));
        txtMetaY = new JTextField("8");
        panelDatos.add(txtMetaY);

        add(panelDatos, BorderLayout.NORTH);

        // --- PANEL DE CONSOLA ---
        txtConsola = new JTextArea();
        txtConsola.setEditable(false);
        txtConsola.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(txtConsola);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Registro de Resultados"));
        add(scrollPane, BorderLayout.CENTER);

        // --- PANEL DE BOTONES ---
        JPanel panelBotones = new JPanel();
        JButton btnBFS = new JButton("Ejecutar Búsqueda Exhaustiva (BFS)");
        JButton btnAStar = new JButton("Ejecutar Búsqueda Heurística (A*)");
        JButton btnLimpiar = new JButton("Limpiar Consola");

        panelBotones.add(btnBFS);
        panelBotones.add(btnAStar);
        panelBotones.add(btnLimpiar);
        add(panelBotones, BorderLayout.SOUTH);

        // --- EVENTOS ---
        btnLimpiar.addActionListener(e -> txtConsola.setText(""));

        btnBFS.addActionListener(e -> {
            try {
                int filas = Integer.parseInt(txtFilas.getText());
                int cols = Integer.parseInt(txtCols.getText());
                Coordenada inicio = new Coordenada(Integer.parseInt(txtInicioX.getText()), Integer.parseInt(txtInicioY.getText()));
                Coordenada meta = new Coordenada(Integer.parseInt(txtMetaX.getText()), Integer.parseInt(txtMetaY.getText()));

                txtConsola.append("\n--- INICIANDO BÚSQUEDA EXHAUSTIVA A CIEGAS (BFS) ---\n");
                
                ResultadoBusqueda res = BusquedaExhaustiva.buscar(filas, cols, inicio, meta);

                if (res.isEncontrado()) {
                    txtConsola.append("✅ Meta encontrada en: " + meta + "\n");
                    txtConsola.append("⚠️ Total de puntos evaluados (Costo operativo): " + res.getNodosEvaluados() + "\n");
                } else {
                    txtConsola.append("❌ No se pudo alcanzar la meta.\n");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Asegúrese de ingresar coordenadas y dimensiones válidas.");
            }
        });

        btnAStar.addActionListener(e -> {
            try {
                int filas = Integer.parseInt(txtFilas.getText());
                int cols = Integer.parseInt(txtCols.getText());
                Coordenada inicio = new Coordenada(Integer.parseInt(txtInicioX.getText()), Integer.parseInt(txtInicioY.getText()));
                Coordenada meta = new Coordenada(Integer.parseInt(txtMetaX.getText()), Integer.parseInt(txtMetaY.getText()));

                txtConsola.append("\n--- INICIANDO BÚSQUEDA HEURÍSTICA INFORMADA (A*) ---\n");
                
                ResultadoBusqueda res = BusquedaHeuristica.buscar(filas, cols, inicio, meta);

                if (res.isEncontrado()) {
                    txtConsola.append("✅ Meta encontrada en: " + meta + "\n");
                    txtConsola.append("⚡ Total de puntos evaluados (Costo operativo): " + res.getNodosEvaluados() + "\n");
                    String rutaStr = res.getRuta().stream().map(Coordenada::toString).collect(Collectors.joining(" -> "));
                    txtConsola.append("Ruta óptima trazada: " + rutaStr + "\n");
                } else {
                    txtConsola.append("❌ No se pudo alcanzar la meta.\n");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Asegúrese de ingresar coordenadas y dimensiones válidas.");
            }
        });
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> new SimuladorBrazoRobotico().setVisible(true));
    }
}
package presentacion;

import javax.swing.*;
import java.awt.*;
import logicaNegocio.SistemaService;

public class MenuPrincipalFrame extends JFrame {

    private SistemaService service;
    private JPanel panelContenido;

    public MenuPrincipalFrame(SistemaService service) {
        this.service = service;
        setTitle("Sistema Pastoral Social");
        setSize(950, 620);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(panelSuperior(), BorderLayout.NORTH);
        add(panelLateral(), BorderLayout.WEST);

        panelContenido = new JPanel(new BorderLayout());
        panelContenido.setBackground(Estilos.FONDO);
        panelContenido.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel bienvenida = new JLabel("Bienvenido, " +
            service.getUsuarioActual().getNombre(), SwingConstants.CENTER);
        bienvenida.setFont(new Font("Segoe UI", Font.BOLD, 18));
        bienvenida.setForeground(Estilos.TEXTO_GRIS);
        panelContenido.add(bienvenida, BorderLayout.CENTER);

        add(panelContenido, BorderLayout.CENTER);
    }

    private JPanel panelSuperior() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Estilos.AZUL_OSCURO);
        p.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));

        JLabel titulo = new JLabel("Sistema Pastoral Social");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titulo.setForeground(Color.WHITE);

        String usuario = service.getUsuarioActual().getNombre() + " "
            + service.getUsuarioActual().getApellido()
            + "  |  " + service.getUsuarioActual().getRol();

        JLabel lblUsuario = new JLabel(usuario, SwingConstants.RIGHT);
        lblUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblUsuario.setForeground(new Color(180, 200, 230));

        p.add(titulo, BorderLayout.WEST);
        p.add(lblUsuario, BorderLayout.EAST);
        return p;
    }

    private JPanel panelLateral() {
        JPanel p = new JPanel();
        p.setPreferredSize(new Dimension(210, 0));
        p.setBackground(new Color(33, 47, 65));
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));

        // ── FORMULARIOS ──
        p.add(separador("FORMULARIOS"));
        p.add(botonMenu("Lista Formularios", () ->
            new ListaFormulariosFrame(service).setVisible(true)));
        p.add(botonMenu("Nuevo Formulario", () ->
            new RegistrarFormularioFrame(service).setVisible(true)));

        // ── ADENDUM ──
        p.add(separador("ADENDUM"));
        p.add(botonMenu("Nuevo Adendum", () ->
            new EntrevistaFrame(service).setVisible(true)));
        p.add(botonMenu("Lista Adendums", () -> 
            new ListarAdendumFrame(service).setVisible(true)));

        // Espacio flexible antes del botón de cerrar sesión
        p.add(Box.createVerticalGlue());

        // Botón de cierre de sesión (siempre al final)
        JButton btnSalir = new JButton("Cerrar Sesión");
        btnSalir.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSalir.setMaximumSize(new Dimension(200, 38));
        btnSalir.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnSalir.setBackground(Estilos.ROJO);
        btnSalir.setForeground(Color.WHITE);
        btnSalir.setFocusPainted(false);
        btnSalir.setBorderPainted(false);
        btnSalir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btnSalir.addActionListener(e -> {
            service.logout();
            dispose();
            new LoginFrame(service).setVisible(true);
        });

        p.add(btnSalir);
        return p;
    }

    private JLabel separador(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 10));
        lbl.setForeground(new Color(100, 130, 165));
        lbl.setBorder(BorderFactory.createEmptyBorder(14, 18, 4, 0));
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        return lbl;
    }

    private JComponent botonMenu(String texto, Runnable accion) {
        JButton b = new JButton(texto);
        b.setAlignmentX(Component.CENTER_ALIGNMENT);
        b.setMaximumSize(new Dimension(210, 44));
        b.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        b.setBackground(new Color(33, 47, 65));
        b.setForeground(new Color(200, 215, 235));
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setHorizontalAlignment(SwingConstants.LEFT);
        b.setBorder(BorderFactory.createEmptyBorder(0, 18, 0, 0));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        b.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                b.setBackground(Estilos.AZUL);
                b.setForeground(Color.WHITE);
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                b.setBackground(new Color(33, 47, 65));
                b.setForeground(new Color(200, 215, 235));
            }
        });

        b.addActionListener(e -> accion.run());
        return b;
    }
}
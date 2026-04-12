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

        // Panel de contenido SIMPLE 
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
        JPanel panelLateral = new JPanel(new BorderLayout());
        panelLateral.setPreferredSize(new Dimension(210, 0));
        panelLateral.setBackground(new Color(33, 47, 65));

        // Panel para los botones del menú (arriba)
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBackground(new Color(33, 47, 65));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));

        // FORMULARIOS
        menuPanel.add(separador("FORMULARIOS"));
        menuPanel.add(botonMenu("Lista Formularios", () -> new ListaFormulariosFrame(service).setVisible(true)));
        menuPanel.add(botonMenu("Nuevo Formulario", () -> new RegistrarFormularioFrame(service).setVisible(true)));

        // ADENDUM
        menuPanel.add(separador("ADENDUM"));
        menuPanel.add(botonMenu("Nuevo Adendum", () -> new EntrevistaFrame(service).setVisible(true)));
        menuPanel.add(botonMenu("Lista Adendums", () -> new ListarAdendumFrame(service).setVisible(true)));

        panelLateral.add(menuPanel, BorderLayout.CENTER);

        // Panel SUR para el botón de cierre de sesión (ocupa todo el ancho del sistema)
        JPanel surPanel = new JPanel(new BorderLayout());
        surPanel.setBackground(new Color(33, 47, 65));
        surPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 15, 0)); // solo padding vertical

        JButton btnSalir = crearBotonSalida();
        surPanel.add(btnSalir, BorderLayout.CENTER);

        panelLateral.add(surPanel, BorderLayout.SOUTH);

        return panelLateral;
    }

    private JButton crearBotonSalida() {
        JButton btn = new JButton("Cerrar Sesión");
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(180, 60, 50)); // Rojo oscuro sólido
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);
        
        // Para que ocupe todo el ancho disponible y tenga altura fija
        btn.setPreferredSize(new Dimension(0, 42));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        btn.setMinimumSize(new Dimension(0, 42));
        
        // Sin márgenes internos laterales, solo vertical para que el texto no quede pegado
        btn.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        
        // Efecto hover
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(new Color(200, 70, 60));
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(new Color(180, 60, 50));
            }
        });

        btn.addActionListener(e -> {
            service.logout();
            dispose();
            new LoginFrame(service).setVisible(true);
        });
        
        return btn;
    }

    private JLabel separador(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 10));
        lbl.setForeground(new Color(100, 130, 165));
        lbl.setBorder(BorderFactory.createEmptyBorder(14, 18, 4, 0));
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        return lbl;
    }

    private JButton botonMenu(String texto, Runnable accion) {
        JButton b = new JButton(texto);
        b.setAlignmentX(Component.LEFT_ALIGNMENT);
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

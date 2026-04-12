package presentacion;

import entidades.Formulario;
import logicaNegocio.SistemaService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class ListaFormulariosFrame extends JFrame {

    private final SistemaService service;
    private DefaultTableModel modeloTabla;
    private JTable tabla;

    public ListaFormulariosFrame(SistemaService service) {
        this.service = service;
        setTitle("Formularios de Asistencia Social");
        setSize(900, 560);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        add(construirEncabezado(), BorderLayout.NORTH);
        add(construirCuerpo(),     BorderLayout.CENTER);
        add(construirPie(),        BorderLayout.SOUTH);

        cargarDatos();
    }

    private JPanel construirEncabezado() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Estilos.AZUL_OSCURO);
        p.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));

        JLabel titulo = new JLabel("Formularios Registrados");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 15));
        titulo.setForeground(Color.WHITE);


        p.add(titulo, BorderLayout.WEST);
        return p;
    }

    private JPanel construirCuerpo() {
        String[] columnas = {
            "ID", "Ficha N°", "Fecha Inicio", "Parroquia",
            "Sector/Filial", "Dirección", "Teléfono", "Entrevistador"
        };

        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        tabla = new JTable(modeloTabla);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabla.setRowHeight(28);
        tabla.setGridColor(new Color(225, 232, 245));
        tabla.setSelectionBackground(new Color(210, 225, 250));
        tabla.setSelectionForeground(Estilos.TEXTO_GRIS);
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tabla.getTableHeader().setBackground(Estilos.AZUL_OSCURO);
        tabla.getTableHeader().setForeground(Color.WHITE);
        tabla.getTableHeader().setReorderingAllowed(false);

        DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
        centrado.setHorizontalAlignment(SwingConstants.CENTER);
        tabla.getColumnModel().getColumn(0).setCellRenderer(centrado);
        tabla.getColumnModel().getColumn(1).setCellRenderer(centrado);
        tabla.getColumnModel().getColumn(2).setCellRenderer(centrado);

        tabla.getColumnModel().getColumn(0).setPreferredWidth(40);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(70);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(100);
        tabla.getColumnModel().getColumn(3).setPreferredWidth(150);
        tabla.getColumnModel().getColumn(4).setPreferredWidth(120);
        tabla.getColumnModel().getColumn(5).setPreferredWidth(200);
        tabla.getColumnModel().getColumn(6).setPreferredWidth(100);
        tabla.getColumnModel().getColumn(7).setPreferredWidth(160);

        // ── Doble clic abre el detalle ──
        tabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) verDetalle();
            }
        });

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createEmptyBorder());

        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Estilos.FONDO);
        p.setBorder(BorderFactory.createEmptyBorder(14, 14, 0, 14));

        JLabel hint = new JLabel("  Seleccioná una fila y usá los botones del pie para actuar sobre ella");
        hint.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        hint.setForeground(new Color(150, 160, 175));
        hint.setBorder(BorderFactory.createEmptyBorder(0, 0, 6, 0));

        p.add(hint,   BorderLayout.NORTH);
        p.add(scroll, BorderLayout.CENTER);
        return p;
    }

    private JPanel construirPie() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT, 14, 10));
        p.setBackground(new Color(245, 247, 250));
        p.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(210, 220, 235)));

        JLabel lblTotal = new JLabel();
        lblTotal.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblTotal.setForeground(Estilos.TEXTO_GRIS);

        JButton btnDetalle = new JButton("Ver Detalle");
        btnDetalle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnDetalle.setBackground(new Color(230, 235, 245));
        btnDetalle.setForeground(Estilos.TEXTO_GRIS);
        btnDetalle.setFocusPainted(false);
        btnDetalle.setBorderPainted(false);
        btnDetalle.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnDetalle.setPreferredSize(new Dimension(120, 36));
        btnDetalle.addActionListener(e -> verDetalle()); // ── ahora llama verDetalle() ──

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnEliminar.setBackground(new Color(200, 60, 60));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setFocusPainted(false);
        btnEliminar.setBorderPainted(false);
        btnEliminar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnEliminar.setPreferredSize(new Dimension(110, 36));
        btnEliminar.addActionListener(e -> eliminarSeleccionado());

        JButton btnNuevo = new JButton("+ Nuevo Formulario");
        btnNuevo.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnNuevo.setBackground(Estilos.AZUL);
        btnNuevo.setForeground(Color.WHITE);
        btnNuevo.setFocusPainted(false);
        btnNuevo.setBorderPainted(false);
        btnNuevo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnNuevo.setPreferredSize(new Dimension(180, 36));
        btnNuevo.addActionListener(e -> new RegistrarFormularioFrame(service).setVisible(true));

        p.add(lblTotal);
        p.add(btnDetalle);
        p.add(btnEliminar);
        p.add(btnNuevo);

        this.lblTotalRef = lblTotal;
        return p;
    }

    private JLabel lblTotalRef;

    private void cargarDatos() {
        modeloTabla.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        List<Formulario> lista = service.listarFormularios();
        for (Formulario f : lista) {
            modeloTabla.addRow(new Object[]{
                f.getIdFormulario(),
                f.getFichaNumero() != null ? f.getFichaNumero() : "-",
                f.getFechaInicio() != null ? sdf.format(f.getFechaInicio()) : "-",
                nvl(f.getParroquia()),
                nvl(f.getSectorFilial()),
                nvl(f.getDireccion()),
                nvl(f.getTelefonoContacto()),
                nvl(f.getNombreEntrevistador())
            });
        }

        if (lblTotalRef != null)
            lblTotalRef.setText("Total: " + lista.size() + " formulario(s)   ");
    }

    // ── Ver detalle del formulario seleccionado ──
    private void verDetalle() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this,
                "Seleccioná un formulario de la tabla.",
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) modeloTabla.getValueAt(fila, 0);
        Formulario f = service.buscarFormularioPorId(id);

        if (f == null) {
            JOptionPane.showMessageDialog(this,
                "No se encontró el formulario.", "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        StringBuilder sb = new StringBuilder();
        sb.append("═══════════════════════════════════════\n");
        sb.append("  DETALLE DEL FORMULARIO\n");
        sb.append("═══════════════════════════════════════\n");
        sb.append("Ficha N°:        ").append(nvl2(f.getFichaNumero())).append("\n");
        sb.append("Fecha Inicio:    ").append(f.getFechaInicio() != null ? sdf.format(f.getFechaInicio()) : "-").append("\n");
        sb.append("Fecha Conclusión:").append(f.getFechaConclusion() != null ? sdf.format(f.getFechaConclusion()) : "-").append("\n");
        sb.append("───────────────────────────────────────\n");
        sb.append("Parroquia:       ").append(nvl(f.getParroquia())).append("\n");
        sb.append("Sector/Filial:   ").append(nvl(f.getSectorFilial())).append("\n");
        sb.append("Dirección:       ").append(nvl(f.getDireccion())).append("\n");
        sb.append("Teléfono:        ").append(nvl(f.getTelefonoContacto())).append("\n");
        sb.append("───────────────────────────────────────\n");
        sb.append("Entrevistador:   ").append(nvl(f.getNombreEntrevistador())).append("\n");
        sb.append("Observaciones:   ").append(nvl(f.getObservaciones())).append("\n");
        sb.append("═══════════════════════════════════════\n");

        JTextArea txtDetalle = new JTextArea(sb.toString());
        txtDetalle.setFont(new Font("Monospaced", Font.PLAIN, 12));
        txtDetalle.setEditable(false);
        txtDetalle.setBackground(new Color(245, 247, 250));

        JScrollPane scroll = new JScrollPane(txtDetalle);
        scroll.setPreferredSize(new Dimension(420, 320));

        JOptionPane.showMessageDialog(this, scroll,
            "Detalle — Ficha N° " + nvl2(f.getFichaNumero()),
            JOptionPane.PLAIN_MESSAGE);
    }

    private void eliminarSeleccionado() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this,
                "Seleccioná un formulario de la tabla.",
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) modeloTabla.getValueAt(fila, 0);
        String ficha = modeloTabla.getValueAt(fila, 1).toString();

        int confirm = JOptionPane.showConfirmDialog(this,
            "¿Seguro que querés eliminar el formulario Ficha N° " + ficha + "?",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);

        if (confirm != JOptionPane.YES_OPTION) return;

        Formulario f = service.buscarFormularioPorId(id);
        if (f == null) {
            JOptionPane.showMessageDialog(this,
                "No se encontró el formulario.", "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        String error = service.eliminarFormulario(f);
        if (error != null) {
            JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            modeloTabla.removeRow(fila);
            if (lblTotalRef != null)
                lblTotalRef.setText("Total: " + modeloTabla.getRowCount() + " formulario(s)   ");
            JOptionPane.showMessageDialog(this,
                "Formulario eliminado correctamente.", "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private String nvl(String s) {
        return (s == null || s.trim().isEmpty()) ? "-" : s;
    }

    private String nvl2(Object o) {
        return (o == null) ? "-" : o.toString();
    }
}
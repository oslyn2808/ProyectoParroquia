package presentacion;

import entidades.AdendumExpediente;
import entidades.Formulario;
import logicaNegocio.SistemaService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ListarAdendumFrame extends JFrame {

    private final SistemaService service;
    private JComboBox<Object> cmbFormularios;
    private JTextField txtIdFormulario;
    private JTable tabla;
    private DefaultTableModel modelo;
    private List<AdendumExpediente> listaActual;

    public ListarAdendumFrame(SistemaService service) {
        this.service = service;
        setTitle("Lista de Adéndums");
        setSize(950, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel norte: título + filtros juntos
        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.add(Estilos.panelTitulo("Adéndums",
                "Lista completa o filtrada por formulario"), BorderLayout.NORTH);
        panelNorte.add(panelFiltros(), BorderLayout.CENTER);

        add(panelNorte, BorderLayout.NORTH);
        add(panelTabla(), BorderLayout.CENTER);
        add(pie(), BorderLayout.SOUTH);

        cargarFormularios();
    }

    /////////////////////////////////
    /*
     * PANEL DE FILTROS:
     * Fila 1: ComboBox de formularios
     * Fila 2: Campo ID + Named Query
     */
    /////////////////////////////////
    private JPanel panelFiltros() {
        JPanel panelContenedor = new JPanel();
        panelContenedor.setLayout(new BoxLayout(panelContenedor, BoxLayout.Y_AXIS));
        panelContenedor.setBackground(Estilos.FONDO);
        panelContenedor.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));

        // --- Fila 1: Filtro por ComboBox ---
        JPanel filaCombo = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        filaCombo.setBackground(Estilos.FONDO);

        filaCombo.add(new JLabel("Filtrar por formulario:"));

        cmbFormularios = new JComboBox<>();
        cmbFormularios.setPreferredSize(new Dimension(430, 30));
        filaCombo.add(cmbFormularios);

        JButton btnFiltrarCombo = Estilos.botonPrimario("Filtrar");
        btnFiltrarCombo.addActionListener(e -> cargarAdendumsPorCombo());
        filaCombo.add(btnFiltrarCombo);

        // --- Separador visual ---
        JSeparator separador = new JSeparator(SwingConstants.HORIZONTAL);
        separador.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        separador.setForeground(Color.LIGHT_GRAY);

        // --- Fila 2: Filtro por ID (Named Query) ---
        JPanel filaId = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        filaId.setBackground(Estilos.FONDO);

        filaId.add(new JLabel("Buscar por ID de formulario:"));

        txtIdFormulario = new JTextField();
        txtIdFormulario.setPreferredSize(new Dimension(120, 30));
        txtIdFormulario.setToolTipText("Ingrese el ID numérico del formulario");
        filaId.add(txtIdFormulario);

        JButton btnBuscarId = Estilos.botonPrimario("Buscar por ID");
        btnBuscarId.addActionListener(e -> buscarPorIdFormulario());
        filaId.add(btnBuscarId);

        JButton btnMostrarTodos = Estilos.botonSecundario("Mostrar todos");
        btnMostrarTodos.addActionListener(e -> {
            txtIdFormulario.setText("");
            cmbFormularios.setSelectedIndex(0);
            cargarTodosAdendums();
        });
        filaId.add(btnMostrarTodos);

        panelContenedor.add(filaCombo);
        panelContenedor.add(separador);
        panelContenedor.add(filaId);

        return panelContenedor;
    }

    /////////////////////////////////
    /*
     * PANEL DE LA TABLA
     */
    /////////////////////////////////
    private JScrollPane panelTabla() {
        modelo = new DefaultTableModel(
            new String[]{"ID Adendum", "Fecha Adéndum", "Número de Ficha",
                         "Recomendación de ayuda (resumen)", "Estado"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabla = new JTable(modelo);
        tabla.setRowHeight(28);
        tabla.setFont(Estilos.NORMAL);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.getTableHeader().setReorderingAllowed(false);

        return new JScrollPane(tabla);
    }

    /////////////////////////////////
    /*
     * PIE DE PÁGINA
     */
    /////////////////////////////////
    private JPanel pie() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        p.setBackground(Estilos.FONDO);

        JButton btnEditar = Estilos.botonSecundario("Editar");
        btnEditar.addActionListener(e -> editar());

        JButton btnCambiarEstado = Estilos.botonSecundario("Activar/Inactivar");
        btnCambiarEstado.addActionListener(e -> cambiarEstado());

        JButton btnCerrar = Estilos.botonSecundario("Cerrar");
        btnCerrar.addActionListener(e -> dispose());

        p.add(btnEditar);
        p.add(btnCambiarEstado);
        p.add(btnCerrar);
        return p;
    }

    /////////////////////////////////
    /*
     * CARGAR FORMULARIOS EN EL COMBO
     */
    /////////////////////////////////
    private void cargarFormularios() {
        List<Formulario> formularios = service.listarFormularios();
        cmbFormularios.removeAllItems();
        cmbFormularios.addItem("=== TODOS LOS FORMULARIOS ===");
        for (Formulario f : formularios) {
            cmbFormularios.addItem(f);
        }
        cmbFormularios.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                    int index, boolean isSelected, boolean cellHasFocus) {
                if (value instanceof Formulario) {
                    Formulario f = (Formulario) value;
                    value = "ID: " + f.getIdFormulario()
                          + " - Ficha: " + f.getFichaNumero()
                          + " - Parroquia: " + f.getParroquia();
                }
                return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            }
        });
        cmbFormularios.setSelectedIndex(0);
        cargarTodosAdendums();
    }

    /////////////////////////////////
    /*
     * FILTRO 1: POR COMBOBOX
     */
    /////////////////////////////////
    private void cargarAdendumsPorCombo() {
        Object selected = cmbFormularios.getSelectedItem();
        if (selected instanceof Formulario) {
            listaActual = service.listarAdendumPorFormulario((Formulario) selected);
        } else {
            listaActual = service.listarTodosAdendums();
        }
        poblarTabla();
    }

    /////////////////////////////////
    /*
     * FILTRO 2: POR ID DE FORMULARIO (Named Query)
     * Usa: AdendumExpediente.findByFormulario
     */
    /////////////////////////////////
    private void buscarPorIdFormulario() {
        String textoId = txtIdFormulario.getText().trim();

        if (textoId.isEmpty()) {
            Estilos.mostrarError(this, "Ingrese un ID de formulario para buscar.");
            return;
        }

        int idFormulario;
        try {
            idFormulario = Integer.parseInt(textoId);
        } catch (NumberFormatException ex) {
            Estilos.mostrarError(this, "El ID debe ser un número entero válido.");
            txtIdFormulario.requestFocus();
            return;
        }

        // Llama al Named Query: AdendumExpediente.findByFormulario
        listaActual = service.listarAdendumPorFormularioId(idFormulario);

        if (listaActual.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "No se encontraron adéndums para el formulario con ID: " + idFormulario,
                "Sin resultados",
                JOptionPane.INFORMATION_MESSAGE);
        }

        poblarTabla();
    }

    /////////////////////////////////
    /*
     * CARGAR TODOS SIN FILTRO
     */
    /////////////////////////////////
    private void cargarTodosAdendums() {
        listaActual = service.listarTodosAdendums();
        poblarTabla();
    }

    /////////////////////////////////
    /*
     * POBLAR LA TABLA (método unificado - DRY)
     */
    /////////////////////////////////
    private void poblarTabla() {
        modelo.setRowCount(0);
        for (AdendumExpediente a : listaActual) {
            String recomCorta = a.getRecomendacionAyuda();
            if (recomCorta != null && recomCorta.length() > 50) {
                recomCorta = recomCorta.substring(0, 50) + "...";
            }
            String fichaInfo = a.getFormulario().getFichaNumero()
                             + " (ID " + a.getFormulario().getIdFormulario() + ")";
            modelo.addRow(new Object[]{
                a.getIdAdendum(),
                a.getFechaAdendum(),
                fichaInfo,
                recomCorta,
                a.getEstado()
            });
        }
    }

    /////////////////////////////////
    /*
     * EDITAR ADENDUM SELECCIONADO
     */
    /////////////////////////////////
    private void editar() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            Estilos.mostrarError(this, "Seleccione un adéndum para editar.");
            return;
        }
        AdendumExpediente seleccionado = listaActual.get(fila);
        new EditarAdendumFrame(service, seleccionado).setVisible(true);
        poblarTabla();
    }

    /////////////////////////////////
    /*
     * CAMBIAR ESTADO DEL ADENDUM
     */
    /////////////////////////////////
    private void cambiarEstado() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            Estilos.mostrarError(this, "Seleccione un adéndum para cambiar estado.");
            return;
        }
        AdendumExpediente a = listaActual.get(fila);
        String nuevoEstado = a.getEstado().equals("ACTIVO") ? "INACTIVO" : "ACTIVO";
        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Cambiar estado de '" + a.getEstado() + "' a '" + nuevoEstado + "'?",
                "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            a.setEstado(nuevoEstado);
            service.actualizarAdendum(a);
            poblarTabla();
        }
    }
}
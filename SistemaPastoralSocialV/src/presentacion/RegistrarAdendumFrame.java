package presentacion;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import logicaNegocio.SistemaService;
import entidades.AdendumExpediente;
import entidades.Formulario;
import entidades.PagoMensualDetalle;

public class RegistrarAdendumFrame extends JFrame {

    private final SistemaService service;
    private final Formulario formulario;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private JTextArea txtInfoAdicional;
    private JTextArea txtObservaciones;

    private JTextField[][] fechas = new JTextField[8][3];
    private final String[] servicios = {
        "Electricidad", "Agua", "Teléfono", "Internet", "Cable",
        "Alquiler/Préstamo", "Alimentación", "Medicamentos"
    };

    private JRadioButton[] rbSi = new JRadioButton[3];
    private JRadioButton[] rbNo = new JRadioButton[3];
    private JTextField[] txtFechaRecom = new JTextField[3];

    /////////////////////////////////
    /**
     * CONSTRUCTOR DEL FRAME
     */
    /////////////////////////////////
    ///
    public RegistrarAdendumFrame(SistemaService service, Formulario formulario) {
        this.service = service;
        this.formulario = formulario;

        setTitle("Adendum al Expediente - Formulario N° " + formulario.getFichaNumero());
        setMinimumSize(new Dimension(700, 500));
        setSize(900, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout());
        root.add(Estilos.panelTitulo("Adendum al Expediente",
                "Registro de fechas de pago y recomendación"), BorderLayout.NORTH);
        root.add(contenido(), BorderLayout.CENTER);
        root.add(pie(), BorderLayout.SOUTH);

        setContentPane(root);
    }

    /////////////////////////////////
    /**
     * CONTENIDO DEL FRAME
     */
    /////////////////////////////////
    ///
    private JScrollPane contenido() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(Estilos.FONDO);
        p.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));

        // Información adicional
        JPanel secInfo = seccion("Información adicional del expediente");
        txtInfoAdicional = Estilos.campoTextoArea();
        txtInfoAdicional.setRows(2);
        secInfo.add(new JScrollPane(txtInfoAdicional));
        p.add(secInfo);

        // Pagos mensuales
        JPanel secServicios = seccion("Pagos mensuales");

        JPanel subPublicos = subSeccion("Servicios públicos");
        agregarEncabezadoFechas(subPublicos);
        crearFilaServicio(subPublicos, servicios[0], 0);
        crearFilaServicio(subPublicos, servicios[1], 1);
        crearFilaServicio(subPublicos, servicios[2], 2);
        crearFilaServicio(subPublicos, servicios[3], 3);
        crearFilaServicio(subPublicos, servicios[4], 4);
        secServicios.add(subPublicos);

        JPanel subVivienda = subSeccion("Gastos de vivienda");
        agregarEncabezadoFechas(subVivienda);
        crearFilaServicio(subVivienda, servicios[5], 5);
        secServicios.add(subVivienda);

        JPanel subOtros = subSeccion("Otros gastos");
        agregarEncabezadoFechas(subOtros);
        crearFilaServicio(subOtros, servicios[6], 6);
        crearFilaServicio(subOtros, servicios[7], 7);
        secServicios.add(subOtros);

        p.add(secServicios);

        // Observaciones adicionales
        JPanel secObs = seccion("Observaciones adicionales del adendum");
        txtObservaciones = Estilos.campoTextoArea();
        txtObservaciones.setRows(2);
        secObs.add(new JScrollPane(txtObservaciones));
        p.add(secObs);

        // Recomendación de ayuda
        JPanel secRecom = seccion("Recomendación de ayuda");
        for (int i = 0; i < 3; i++) {
            secRecom.add(crearFilaRecomendacion(i));
        }
        p.add(secRecom);

        JScrollPane scroll = new JScrollPane(p);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        return scroll;
    }

    /////////////////////////////////
    /**
     * MÉTODO PARA EL ENCABEZADO DE FECHAS
     */
    /////////////////////////////////
    ///
    private void agregarEncabezadoFechas(JPanel parent) {
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 2));
        header.setOpaque(false);
        header.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel espacio = new JLabel(" ");
        espacio.setPreferredSize(new Dimension(130, 28));
        header.add(espacio);

        for (int j = 0; j < 3; j++) {
            JLabel lblFecha = new JLabel("Fecha " + (j + 1));
            lblFecha.setFont(new Font("Segoe UI", Font.BOLD, 12));
            lblFecha.setForeground(Estilos.TEXTO);
            lblFecha.setPreferredSize(new Dimension(100, 28));
            lblFecha.setHorizontalAlignment(SwingConstants.CENTER); 
            header.add(lblFecha);
        }
        parent.add(header);
    }

    /////////////////////////////////
    /**
     * FILA DE SERVICIOS (nombres y campos) 
     */
    /////////////////////////////////
    ///
    private void crearFilaServicio(JPanel parent, String nombre, int idxServicio) {
        JPanel fila = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 2));
        fila.setOpaque(false);
        fila.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblServicio = new JLabel(nombre);
        lblServicio.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblServicio.setPreferredSize(new Dimension(130, 28));
        fila.add(lblServicio);

        for (int j = 0; j < 3; j++) {
            fechas[idxServicio][j] = Estilos.campoTexto();
            fechas[idxServicio][j].setPreferredSize(new Dimension(100, 28));
            fila.add(fechas[idxServicio][j]);
        }
        parent.add(fila);
    }

    /////////////////////////////////
    /**
     * MÉTODO PARA CREAR LAS FILAS DE RECOMENDACIÓN
     */
    /////////////////////////////////
    ///
    private JPanel crearFilaRecomendacion(int idx) {
        JPanel fila = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 2));
        fila.setOpaque(false);

        rbSi[idx] = new JRadioButton("Sí");
        rbNo[idx] = new JRadioButton("No");
        ButtonGroup g = new ButtonGroup();
        g.add(rbSi[idx]);
        g.add(rbNo[idx]);

        txtFechaRecom[idx] = Estilos.campoTexto();
        txtFechaRecom[idx].setPreferredSize(new Dimension(100, 28));

        fila.add(new JLabel("Recomendación " + (idx + 1) + ":"));
        fila.add(rbSi[idx]);
        fila.add(rbNo[idx]);
        fila.add(new JLabel("Fecha prevista:"));
        fila.add(txtFechaRecom[idx]);

        return fila;
    }

    /////////////////////////////////
    /**
     * MÉTODO PARA EL PIE DEL FRAME
     */
    /////////////////////////////////
    ///
    private JPanel pie() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        p.setBackground(new Color(240, 240, 240));
        p.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(200, 200, 200)));
        p.setPreferredSize(new Dimension(0, 55));

        JButton btnGuardar = Estilos.botonPrimario("Guardar Adendum");
        btnGuardar.setPreferredSize(new Dimension(160, 35));
        btnGuardar.addActionListener(e -> guardarAdendum());
        p.add(btnGuardar);
        return p;
    }

    ////////////////////////////////////
    /// MÉTODO PARA GUARDAR ADENDUM  ///
    ////////////////////////////////////
    
    private void guardarAdendum() {
        try {
            if (service.existeAdendumActivo(formulario)) {
                JOptionPane.showMessageDialog(this,
                    "Ya existe un adéndum activo para este formulario.\n" +
                    "No se puede registrar otro hasta que el actual sea cerrado o finalizado.",
                    "Adéndum duplicado", JOptionPane.WARNING_MESSAGE);
                return;
            }

            boolean algunaRecomendacionSeleccionada = false;
            for (int i = 0; i < 3; i++) {
                if (rbSi[i].isSelected() || rbNo[i].isSelected()) {
                    algunaRecomendacionSeleccionada = true;
                    break;
                }
            }
            if (!algunaRecomendacionSeleccionada) {
                JOptionPane.showMessageDialog(this,
                    "Debe seleccionar al menos una recomendación de ayuda (Sí o No) en alguna de las tres opciones.",
                    "Recomendación obligatoria", JOptionPane.ERROR_MESSAGE);
                return;
            }

            for (int i = 0; i < 3; i++) {
                if (rbSi[i].isSelected()) {
                    String fecha = txtFechaRecom[i].getText().trim();
                    if (fecha.isEmpty()) {
                        JOptionPane.showMessageDialog(this,
                            "Recomendación " + (i + 1) + " marcada como 'Sí', pero no tiene fecha prevista.\n" +
                            "Ingrese una fecha o cambie a 'No'.",
                            "Datos incompletos", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
            }

            AdendumExpediente a = new AdendumExpediente();
            a.setFormulario(formulario);
            a.setFechaAdendum(LocalDate.now());
            a.setInformacionAdicional(txtInfoAdicional.getText().trim());
            a.setObservaciones_adicionales(txtObservaciones.getText().trim());
            a.setEstado("ACTIVO"); //ponemos por default el estado ACTIVO

            cargarFechas(a.getElectricidad(),       fechas[0]);
            cargarFechas(a.getAgua(),               fechas[1]);
            cargarFechas(a.getTelefono(),           fechas[2]);
            cargarFechas(a.getInternet(),           fechas[3]);
            cargarFechas(a.getCable(),              fechas[4]);
            cargarFechas(a.getAlquilerPrestamo(),   fechas[5]);
            cargarFechas(a.getAlimentacion(),       fechas[6]);
            cargarFechas(a.getMedicamentoMensual(), fechas[7]);

            StringBuilder rec = new StringBuilder();
            for (int i = 0; i < 3; i++) {
                if (rbSi[i].isSelected() || rbNo[i].isSelected()) {
                    rec.append("Recomendación ").append(i + 1).append(": ");
                    rec.append(rbSi[i].isSelected() ? "Sí" : "No");
                    String fecha = txtFechaRecom[i].getText().trim();
                    if (!fecha.isEmpty()) rec.append(" - Fecha: ").append(fecha);
                    rec.append("\n");
                }
            }
            a.setRecomendacionAyuda(rec.toString().isEmpty() ? "Sin recomendación" : rec.toString());

            service.guardarAdendum(a); //guardamos el adendum con el service

            JOptionPane.showMessageDialog(this,
                "Adendum guardado correctamente.\nID: " + a.getIdAdendum(),
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
            dispose();

        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this,
                "Formato de fecha inválido (use dd/MM/yyyy): " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Error al guardar: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /////////////////////////////////
    /**
     * MÉTODO PARA CARGAR CAMPOS DE FECHAS
     */
    /////////////////////////////////
    ///
    private void cargarFechas(PagoMensualDetalle pago, JTextField[] fechasArr) {
        if (pago == null) return;
        for (int i = 0; i < 3; i++) {
            String fechaStr = fechasArr[i].getText().trim();
            if (!fechaStr.isEmpty()) {
                LocalDate fecha = LocalDate.parse(fechaStr, dateFormatter);
                switch (i) {
                    case 0: pago.setFecha1(fecha); break;
                    case 1: pago.setFecha2(fecha); break;
                    case 2: pago.setFecha3(fecha); break;
                }
            }
        }
    }

    /////////////////////////////////
    /**
     * SECCION DEL FRAME
     */
    /////////////////////////////////
    ///
    private JPanel seccion(String titulo) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(Estilos.BLANCO);
        p.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(180, 200, 230), 1),
            titulo, TitledBorder.LEFT, TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 12), Estilos.AZUL
        ));
        p.setAlignmentX(Component.LEFT_ALIGNMENT);
        return p;
    }

    /////////////////////////////////
    /**
     * SUBSECCIONES DEL FRAME
     */
    /////////////////////////////////
    ///
    private JPanel subSeccion(String titulo) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setOpaque(false);
        p.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            titulo,
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 11)
        ));
        p.setAlignmentX(Component.LEFT_ALIGNMENT);
        return p;
    }
}
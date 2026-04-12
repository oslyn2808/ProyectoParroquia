package presentacion;

import entidades.AdendumExpediente;
import entidades.PagoMensualDetalle;
import logicaNegocio.SistemaService;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditarAdendumFrame extends JFrame {

    private final SistemaService service;
    private final AdendumExpediente adendum;
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
    private JTextField[] txtObservacionRecom = new JTextField[3];

    /////////////////////////////////
    /**
     * CONSTRUCTOR DEL FRAME
     */
    /////////////////////////////////
    public EditarAdendumFrame(SistemaService service, AdendumExpediente adendum) {
        this.service = service;
        this.adendum = adendum;

        setTitle("Editar Adéndum - ID " + adendum.getIdAdendum());
        setMinimumSize(new Dimension(700, 500));
        setSize(900, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout());
        root.add(Estilos.panelTitulo("Editar Adéndum",
                "Modifique los datos necesarios"), BorderLayout.NORTH);
        root.add(contenido(), BorderLayout.CENTER);
        root.add(pie(), BorderLayout.SOUTH);

        setContentPane(root);

        cargarDatosAdendum();
    }

    /////////////////////////////////
    /**
     * CONTENIDO DEL FRAME
     */
    /////////////////////////////////
    private JScrollPane contenido() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(Estilos.FONDO);
        p.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));

        JPanel secInfo = seccion("Información adicional del expediente");
        txtInfoAdicional = Estilos.campoTextoArea();
        txtInfoAdicional.setRows(2);
        secInfo.add(new JScrollPane(txtInfoAdicional));
        p.add(secInfo);

        JPanel secServicios = seccion("Pagos mensuales");

        JPanel subPublicos = subSeccion("Servicios públicos");
        crearFilaServicio(subPublicos, servicios[0], 0);
        crearFilaServicio(subPublicos, servicios[1], 1);
        crearFilaServicio(subPublicos, servicios[2], 2);
        crearFilaServicio(subPublicos, servicios[3], 3);
        crearFilaServicio(subPublicos, servicios[4], 4);
        secServicios.add(subPublicos);

        JPanel subVivienda = subSeccion("Gastos de vivienda");
        crearFilaServicio(subVivienda, servicios[5], 5);
        secServicios.add(subVivienda);

        JPanel subOtros = subSeccion("Otros gastos");
        crearFilaServicio(subOtros, servicios[6], 6);
        crearFilaServicio(subOtros, servicios[7], 7);
        secServicios.add(subOtros);

        p.add(secServicios);

        JPanel secObs = seccion("Observaciones adicionales del adendum");
        txtObservaciones = Estilos.campoTextoArea();
        txtObservaciones.setRows(2);
        secObs.add(new JScrollPane(txtObservaciones));
        p.add(secObs);

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
     * FILAS DE LOS SERVICIOS
     */
    /////////////////////////////////
    private void crearFilaServicio(JPanel parent, String nombre, int idxServicio) {
        JPanel fila = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 2));
        fila.setOpaque(false);
        fila.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblServicio = new JLabel(nombre);
        lblServicio.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblServicio.setPreferredSize(new Dimension(120, 28));
        fila.add(lblServicio);

        for (int j = 0; j < 3; j++) {
            JPanel sub = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 0));
            sub.setOpaque(false);
            sub.add(new JLabel("Fecha " + (j + 1) + ":"));
            fechas[idxServicio][j] = Estilos.campoTexto();
            fechas[idxServicio][j].setPreferredSize(new Dimension(90, 28));
            sub.add(fechas[idxServicio][j]);
            fila.add(sub);
        }
        parent.add(fila);
    }

    /////////////////////////////////
    /**
     * FILAS DE LAS RECOMENDACIONES
     */
    /////////////////////////////////
    private JPanel crearFilaRecomendacion(int idx) {
        JPanel fila = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 2));
        fila.setOpaque(false);

        rbSi[idx] = new JRadioButton("Sí");
        rbNo[idx] = new JRadioButton("No");
        ButtonGroup g = new ButtonGroup();
        g.add(rbSi[idx]);
        g.add(rbNo[idx]);

        txtFechaRecom[idx] = Estilos.campoTexto();
        txtFechaRecom[idx].setPreferredSize(new Dimension(90, 28));
        txtObservacionRecom[idx] = Estilos.campoTexto();
        txtObservacionRecom[idx].setPreferredSize(new Dimension(200, 28));

        fila.add(new JLabel("Recomendación " + (idx + 1) + ":"));
        fila.add(rbSi[idx]);
        fila.add(rbNo[idx]);
        fila.add(new JLabel("Fecha prevista:"));
        fila.add(txtFechaRecom[idx]);
        fila.add(new JLabel("Observación:"));
        fila.add(txtObservacionRecom[idx]);

        return fila;
    }

    /////////////////////////////////
    /**
     * PIE DEL FRAME (parte de abajo)
     */
    /////////////////////////////////
    private JPanel pie() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        p.setBackground(new Color(240, 240, 240));
        p.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(200, 200, 200)));
        p.setPreferredSize(new Dimension(0, 55));

        JButton btnGuardar = Estilos.botonPrimario("Actualizar Adéndum");
        btnGuardar.setPreferredSize(new Dimension(170, 35));
        btnGuardar.addActionListener(e -> guardarCambios());

        JButton btnCancelar = Estilos.botonSecundario("Cancelar");
        btnCancelar.setPreferredSize(new Dimension(100, 35));
        btnCancelar.addActionListener(e -> dispose());

        p.add(btnCancelar);
        p.add(btnGuardar);
        return p;
    }

    /////////////////////////////////
    /**
     * CARGA DE DATOS DESDE EL ADENDUM
     */
    /////////////////////////////////
    private void cargarDatosAdendum() {
        txtInfoAdicional.setText(adendum.getInformacionAdicional());
        txtObservaciones.setText(adendum.getObservaciones_adicionales());

        cargarFechasEnUI(adendum.getElectricidad(),       fechas[0]);
        cargarFechasEnUI(adendum.getAgua(),               fechas[1]);
        cargarFechasEnUI(adendum.getTelefono(),           fechas[2]);
        cargarFechasEnUI(adendum.getInternet(),           fechas[3]);
        cargarFechasEnUI(adendum.getCable(),              fechas[4]);
        cargarFechasEnUI(adendum.getAlquilerPrestamo(),   fechas[5]);
        cargarFechasEnUI(adendum.getAlimentacion(),       fechas[6]);
        cargarFechasEnUI(adendum.getMedicamentoMensual(), fechas[7]);

        cargarRecomendaciones(adendum.getRecomendacionAyuda());
    }

    /////////////////////////////////
    /**
     * METODO PARA CARGAR LAS FECHAS EN LA UI
     */
    /////////////////////////////////
    private void cargarFechasEnUI(PagoMensualDetalle pago, JTextField[] campos) {
        if (pago == null) return;
        if (pago.getFecha1() != null) campos[0].setText(pago.getFecha1().format(dateFormatter));
        if (pago.getFecha2() != null) campos[1].setText(pago.getFecha2().format(dateFormatter));
        if (pago.getFecha3() != null) campos[2].setText(pago.getFecha3().format(dateFormatter));
    }

    /////////////////////////////////
    /**
     * METODO PARA CARGAR LAS RECOMENDACIONES
     */
    /////////////////////////////////
    private void cargarRecomendaciones(String textoRecom) {
        if (textoRecom == null || textoRecom.trim().isEmpty()) return;

        Pattern pattern = Pattern.compile(
                "Recomendación (\\d+): (Sí|No)(?: - Fecha: (\\d{2}/\\d{2}/\\d{4}))?(?: - Obs: (.*?))?(?:\n|$)",
                Pattern.DOTALL
        );
        Matcher matcher = pattern.matcher(textoRecom);
        while (matcher.find()) {
            int idx = Integer.parseInt(matcher.group(1)) - 1;
            if (idx < 0 || idx >= 3) continue;

            String valor = matcher.group(2);
            String fecha = matcher.group(3);
            String obs   = matcher.group(4);

            if ("Sí".equals(valor)) {
                rbSi[idx].setSelected(true);
                if (fecha != null) txtFechaRecom[idx].setText(fecha);
            } else {
                rbNo[idx].setSelected(true);
            }
            if (obs != null) txtObservacionRecom[idx].setText(obs.trim());
        }
    }

    /////////////////////////////////
    /**
     * METODO PARA GUARDAR LOS CAMBIOS
     */
    /////////////////////////////////
    private void guardarCambios() {
        try {
            // Validar que al menos una recomendación esté seleccionada
            boolean algunaRecomendacion = false;
            for (int i = 0; i < 3; i++) {
                if (rbSi[i].isSelected() || rbNo[i].isSelected()) {
                    algunaRecomendacion = true;
                    break;
                }
            }
            if (!algunaRecomendacion) {
                JOptionPane.showMessageDialog(this,
                        "Debe seleccionar al menos una recomendación de ayuda (Sí o No).",
                        "Recomendación obligatoria", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validar que si es "Sí" tenga fecha
            for (int i = 0; i < 3; i++) {
                if (rbSi[i].isSelected()) {
                    String fecha = txtFechaRecom[i].getText().trim();
                    if (fecha.isEmpty()) {
                        JOptionPane.showMessageDialog(this,
                                "Recomendación " + (i + 1) + " marcada como 'Sí' pero no tiene fecha prevista.",
                                "Datos incompletos", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
            }

            // Actualizar campos simples
            adendum.setInformacionAdicional(txtInfoAdicional.getText().trim());
            adendum.setObservaciones_adicionales(txtObservaciones.getText().trim());

            // Actualizar fechas de servicios
            actualizarFechasDesdeUI(adendum.getElectricidad(),       fechas[0]);
            actualizarFechasDesdeUI(adendum.getAgua(),               fechas[1]);
            actualizarFechasDesdeUI(adendum.getTelefono(),           fechas[2]);
            actualizarFechasDesdeUI(adendum.getInternet(),           fechas[3]);
            actualizarFechasDesdeUI(adendum.getCable(),              fechas[4]);
            actualizarFechasDesdeUI(adendum.getAlquilerPrestamo(),   fechas[5]);
            actualizarFechasDesdeUI(adendum.getAlimentacion(),       fechas[6]);
            actualizarFechasDesdeUI(adendum.getMedicamentoMensual(), fechas[7]);

            // Construir el nuevo texto de recomendación
            StringBuilder rec = new StringBuilder();
            for (int i = 0; i < 3; i++) {
                if (rbSi[i].isSelected() || rbNo[i].isSelected()) {
                    rec.append("Recomendación ").append(i + 1).append(": ");
                    rec.append(rbSi[i].isSelected() ? "Sí" : "No");
                    String fecha = txtFechaRecom[i].getText().trim();
                    if (!fecha.isEmpty()) rec.append(" - Fecha: ").append(fecha);
                    String obs = txtObservacionRecom[i].getText().trim();
                    if (!obs.isEmpty()) rec.append(" - Obs: ").append(obs);
                    rec.append("\n");
                }
            }
            adendum.setRecomendacionAyuda(rec.toString().isEmpty() ? "Sin recomendación" : rec.toString());

            service.actualizarAdendum(adendum);

            JOptionPane.showMessageDialog(this, "Adéndum actualizado correctamente.");
            dispose();

        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this,
                    "Formato de fecha inválido (use dd/MM/yyyy): " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error al actualizar: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /////////////////////////////////
    /**
     * METODO PARA ACTUALIZAR FECHAS DESDE LA UI
     */
    /////////////////////////////////
    private void actualizarFechasDesdeUI(PagoMensualDetalle pago, JTextField[] campos) {
        if (pago == null) return;
        pago.setFecha1(parseFecha(campos[0].getText()));
        pago.setFecha2(parseFecha(campos[1].getText()));
        pago.setFecha3(parseFecha(campos[2].getText()));
    }

    /////////////////////////////////
    /**
     * METODO PARA PARSEAR FECHAS
     */
    /////////////////////////////////
    private LocalDate parseFecha(String texto) {
        if (texto == null || texto.trim().isEmpty()) return null;
        try {
            return LocalDate.parse(texto.trim(), dateFormatter);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /////////////////////////////////
    /*
     * SECCION CON BORDE AZUL
     */
    /////////////////////////////////
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
    /*
     * SUBSECCION CON BORDE GRIS
     */
    /////////////////////////////////
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
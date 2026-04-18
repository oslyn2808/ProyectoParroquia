package presentacion;

import entidades.*;
import logicaNegocio.SistemaService;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RegistrarFormularioFrame extends JFrame {

    private final SistemaService service;
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    private JTextField txtFechaInicio, txtFechaConclusion;
    private JTextField txtProlongacion1, txtProlongacion2, txtProlongacion3, txtProlongacion4;
    private JComboBox<Parroquia> comboParroquia;
    private JTextField txtSectorFilial, txtDireccion, txtTelefono;

    private JTextField[] txtNombre     = new JTextField[10];
    private JTextField[] txtDocumento  = new JTextField[10];
    private JComboBox[]  cmbSexo       = new JComboBox[10];
    private JTextField[] txtJefatura   = new JTextField[10];
    private JTextField[] txtRelacion   = new JTextField[10];
    private JTextField[] txtEdad       = new JTextField[10];
    private JTextField[] txtPais       = new JTextField[10];
    private JTextField[] txtMigracion  = new JTextField[10];
    private JTextField[] txtEducacion  = new JTextField[10];
    private JTextField[] txtSalud      = new JTextField[10];
    private JTextField[] txtSeguro     = new JTextField[10];
    private JTextField[] txtOcupacion  = new JTextField[10];
    private JCheckBox[]  chkTrabaja    = new JCheckBox[10];
    private JTextField[] txtIngreso    = new JTextField[10];

    private JComboBox<String> cmbTipoVivienda, cmbTenencia, cmbCondicion;
    private JCheckBox chkAlimentos, chkHigiene, chkIndumentaria, chkAlquiler,
                      chkServicios, chkMedicamentos, chkAparatos;
    private JTextField txtOtros1, txtOtros2;
    private JTextField txtModalidad, txtFrecuencia, txtDuracion, txtValor;
    private JTextField txtEntrevistador;
    private JTextArea  txtObservaciones;

    /////////////////////////////////
    /**
     * CONSTRUCTOR DEL FRAME
     */
    /////////////////////////////////
    ///
    public RegistrarFormularioFrame(SistemaService service) {
        this.service = service;
        setTitle("Registrar Formulario de Asistencia Social");
        setSize(900, 680);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        add(construirEncabezado(), BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane(JTabbedPane.TOP);
        tabs.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabs.addTab("Registro",            construirTabRegistro());
        tabs.addTab("Personas",            construirTabPersonas());
        tabs.addTab("Vivienda / Ingresos", construirTabViviendaIngresos());
        tabs.addTab("Asistencia",          construirTabAsistencia());
        tabs.addTab("Final",               construirTabFinal());

        add(tabs, BorderLayout.CENTER);
        add(construirPiePagina(), BorderLayout.SOUTH);

        // Cargar parroquias en el combo
        cargarParroquias();

        // Auto-completar campos al seleccionar una parroquia 
        comboParroquia.addActionListener(e -> {
            Object selected = comboParroquia.getSelectedItem();

            if (selected instanceof Parroquia) {
                Parroquia p = (Parroquia) selected;
                txtSectorFilial.setText(p.getSectorFilial());
            }
        });
    }

    /////////////////////////////////
    /**
     * ENCABEZADO DEL FRAME
     */
    /////////////////////////////////
    ///
    private JPanel construirEncabezado() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Estilos.AZUL_OSCURO);
        p.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        JLabel titulo = new JLabel("Formulario de Asistencia Social");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 15));
        titulo.setForeground(Color.WHITE);
        p.add(titulo, BorderLayout.WEST);
        return p;
    }

    /////////////////////////////////
    /**
     * PRIMERA SECCIÓN DEL REGISTRO
     */
    /////////////////////////////////
    ///
    private JScrollPane construirTabRegistro() {
        JPanel p = new JPanel();
        p.setBackground(Estilos.FONDO);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JPanel secRegistro = seccion("Datos del Registro");
        // Ficha generada automáticamente
        JPanel filaFicha = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 3));
        filaFicha.setBackground(secRegistro.getBackground());
        filaFicha.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel lblFicha = new JLabel("Ficha N:");
        lblFicha.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblFicha.setForeground(Estilos.TEXTO_GRIS);
        lblFicha.setPreferredSize(new Dimension(230, 24));
        JLabel lblAuto = new JLabel("CD-( numero de cedula del beneficiario ).");
        lblAuto.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblAuto.setForeground(Estilos.TEXTO_GRIS);
        filaFicha.add(lblFicha);
        filaFicha.add(lblAuto);
        secRegistro.add(filaFicha);

        txtFechaInicio     = campo(secRegistro, "Fecha Inicio (dd/MM/yyyy):");
        txtFechaInicio.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        txtFechaConclusion = campo(secRegistro, "Fecha Conclusion (dd/MM/yyyy):");
        txtProlongacion1   = campo(secRegistro, "Prolongacion 1 (dd/MM/yyyy):");
        txtProlongacion2   = campo(secRegistro, "Prolongacion 2 (dd/MM/yyyy):");
        txtProlongacion3   = campo(secRegistro, "Prolongacion 3 (dd/MM/yyyy):");
        txtProlongacion4   = campo(secRegistro, "Prolongacion 4 (dd/MM/yyyy):");
        p.add(secRegistro);
        p.add(Box.createVerticalStrut(16));

        JPanel secParroquiales = seccion("Datos Parroquiales");


        //combo box de parroquia
        comboParroquia = new JComboBox<>();
        estiloCombo(comboParroquia);
        addLabelCombo(secParroquiales, "Parroquia:", comboParroquia);

        txtSectorFilial = campo(secParroquiales, "Sector / Filial:");
        p.add(secParroquiales);
        p.add(Box.createVerticalStrut(16));

        JPanel secGenerales = seccion("Datos generales");
        txtDireccion    = campo(secGenerales, "Dirección:");
        txtTelefono     = campo(secGenerales, "Teléfono:");
        p.add(secGenerales);

        return wrapScroll(p);
    }

    /////////////////////////////////
    /**
     * SEGUNDA SECCIÓN DEL REGISTRO (tabla de las personas)
     */
    /////////////////////////////////
    ///
    private JPanel construirTabPersonas() {
        String[] cols = {"Nombre Completo", "Documento de Identidad", "Sexo", "Jefatura",
                         "Relacion", "Edad", "Pais", "Migracion", "Educacion", "Salud", "Seguro"};

        JPanel cabecera = new JPanel(new GridLayout(1, cols.length, 4, 0));
        cabecera.setBackground(Estilos.AZUL_OSCURO);
        for (String c : cols) {
            JLabel lbl = new JLabel(c, SwingConstants.CENTER);
            lbl.setForeground(Color.WHITE);
            lbl.setFont(new Font("Segoe UI", Font.BOLD, 11));
            lbl.setBorder(BorderFactory.createEmptyBorder(6, 4, 6, 4));
            cabecera.add(lbl);
        }

        JPanel filas = new JPanel(new GridLayout(10, cols.length, 4, 4));
        filas.setBackground(Estilos.FONDO);
        filas.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
        String[] sexoOpciones = {"", "M", "F", "Otro"};

        for (int i = 0; i < 10; i++) {
            txtNombre[i]    = new JTextField(); estiloCampo(txtNombre[i]);
            txtDocumento[i] = new JTextField(); estiloCampo(txtDocumento[i]);
            cmbSexo[i]      = new JComboBox<>(sexoOpciones); estiloCombo(cmbSexo[i]);
            txtJefatura[i]  = new JTextField(); estiloCampo(txtJefatura[i]);
            txtRelacion[i]  = new JTextField(); estiloCampo(txtRelacion[i]);
            txtEdad[i]      = new JTextField(); estiloCampo(txtEdad[i]);
            txtPais[i]      = new JTextField(); estiloCampo(txtPais[i]);
            txtMigracion[i] = new JTextField(); estiloCampo(txtMigracion[i]);
            txtEducacion[i] = new JTextField(); estiloCampo(txtEducacion[i]);
            txtSalud[i]     = new JTextField(); estiloCampo(txtSalud[i]);
            txtSeguro[i]    = new JTextField(); estiloCampo(txtSeguro[i]);

            filas.add(txtNombre[i]);    filas.add(txtDocumento[i]);
            filas.add(cmbSexo[i]);      filas.add(txtJefatura[i]);
            filas.add(txtRelacion[i]);  filas.add(txtEdad[i]);
            filas.add(txtPais[i]);      filas.add(txtMigracion[i]);
            filas.add(txtEducacion[i]); filas.add(txtSalud[i]);
            filas.add(txtSeguro[i]);
        }

        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setBackground(Estilos.FONDO);
        panelTabla.add(cabecera, BorderLayout.NORTH);
        panelTabla.add(filas,    BorderLayout.CENTER);

        JScrollPane scroll = new JScrollPane(panelTabla);
        scroll.setPreferredSize(new Dimension(860, 400));

        JPanel contenedor = new JPanel(new BorderLayout());
        contenedor.setBackground(Estilos.FONDO);
        JLabel nota = new JLabel("  Personas participantes (maximo 10)");
        nota.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        nota.setForeground(Estilos.TEXTO_GRIS);
        nota.setBorder(BorderFactory.createEmptyBorder(10, 10, 4, 0));
        contenedor.add(nota,   BorderLayout.NORTH);
        contenedor.add(scroll, BorderLayout.CENTER);

        JPanel wrap = new JPanel(new BorderLayout());
        wrap.setBackground(Estilos.FONDO);
        wrap.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        wrap.add(contenedor, BorderLayout.CENTER);
        return wrap;
    }

    /////////////////////////////////
    /**
     * TERCERA SECCIÓN DEL REGISTRO (tabla de ingresos y datos vivienda)
     */
    /////////////////////////////////
    ///
    private JScrollPane construirTabViviendaIngresos() {
        JPanel p = new JPanel();
        p.setBackground(Estilos.FONDO);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JPanel secViv = seccion("Datos de la Vivienda");
        cmbTipoVivienda = new JComboBox<>(new String[]{"", "Casa", "Apartamento", "Cuarto", "Tugurio", "Albergue", "Ninguna"});
        estiloCombo(cmbTipoVivienda);
        addLabelCombo(secViv, "Tipo de Vivienda:", cmbTipoVivienda);
        cmbTenencia = new JComboBox<>(new String[]{"", "Propia", "Paga a plazos", "Alquilada", "Prestada", "Precario"});
        estiloCombo(cmbTenencia);
        addLabelCombo(secViv, "Tenencia:", cmbTenencia);
        cmbCondicion = new JComboBox<>(new String[]{"", "Buena", "Regular", "Mala", "No aplica"});
        estiloCombo(cmbCondicion);
        addLabelCombo(secViv, "Condicion:", cmbCondicion);
        p.add(secViv);
        p.add(Box.createVerticalStrut(16));

        JPanel secIng = seccion("Ingreso Familiar (por persona)");
        String[] ingCols = {"Persona", "Ocupacion", "Trabaja", "Ingreso Mensual"};
        JPanel cabIng = new JPanel(new GridLayout(1, 4, 4, 0));
        cabIng.setBackground(Estilos.AZUL_OSCURO);
        for (String c : ingCols) {
            JLabel lbl = new JLabel(c, SwingConstants.CENTER);
            lbl.setForeground(Color.WHITE);
            lbl.setFont(new Font("Segoe UI", Font.BOLD, 11));
            lbl.setBorder(BorderFactory.createEmptyBorder(5, 4, 5, 4));
            cabIng.add(lbl);
        }
        cabIng.setAlignmentX(Component.LEFT_ALIGNMENT);
        secIng.add(cabIng);

        JPanel filasIng = new JPanel(new GridLayout(10, 4, 4, 4));
        filasIng.setBackground(Estilos.FONDO);
        filasIng.setBorder(BorderFactory.createEmptyBorder(4, 0, 4, 0));
        filasIng.setAlignmentX(Component.LEFT_ALIGNMENT);

        for (int i = 0; i < 10; i++) {
            JLabel lblNum = new JLabel("Persona " + (i + 1), SwingConstants.CENTER);
            lblNum.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            lblNum.setForeground(Estilos.TEXTO_GRIS);

            txtOcupacion[i] = new JTextField(); estiloCampo(txtOcupacion[i]);
            chkTrabaja[i]   = new JCheckBox();  chkTrabaja[i].setBackground(Estilos.FONDO);
            txtIngreso[i]   = new JTextField(); estiloCampo(txtIngreso[i]);

            JPanel chkPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 2));
            chkPanel.setBackground(Estilos.FONDO);
            chkPanel.add(chkTrabaja[i]);

            filasIng.add(lblNum);
            filasIng.add(txtOcupacion[i]);
            filasIng.add(chkPanel);
            filasIng.add(txtIngreso[i]);
        }
        secIng.add(filasIng);
        p.add(secIng);
        return wrapScroll(p);
    }

    /////////////////////////////////
    /**
     * CUARTA SECCIÓN DE LA TABLA DE ASISTENCIA 
     */
    /////////////////////////////////
    ///
    private JScrollPane construirTabAsistencia() {
        JPanel p = new JPanel();
        p.setBackground(Estilos.FONDO);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JPanel secTipos = seccion("Tipo de Asistencia Solicitada");
        chkAlimentos    = checkbox(secTipos, "Alimentos");
        chkHigiene      = checkbox(secTipos, "Higiene y Limpieza");
        chkIndumentaria = checkbox(secTipos, "Indumentaria");
        chkAlquiler     = checkbox(secTipos, "Alquiler");
        chkServicios    = checkbox(secTipos, "Servicios");
        chkMedicamentos = checkbox(secTipos, "Medicamentos");
        chkAparatos     = checkbox(secTipos, "Aparatos Ortopedicos");
        txtOtros1 = campo(secTipos, "Otros (1):");
        txtOtros2 = campo(secTipos, "Otros (2):");
        p.add(secTipos);
        p.add(Box.createVerticalStrut(16));

        JPanel secDetalle = seccion("Detalle de la Asistencia");
        txtModalidad  = campo(secDetalle, "Modalidad:");
        txtFrecuencia = campo(secDetalle, "Frecuencia:");
        txtDuracion   = campo(secDetalle, "Duracion:");
        txtValor      = campo(secDetalle, "Valor:");
        p.add(secDetalle);
        return wrapScroll(p);
    }

    /////////////////////////////////
    /**
     * QUINTA SECCIÓN DEL REGISTRO (nombre del entrevistador y observaciones)
     */
    /////////////////////////////////
    ///
    private JScrollPane construirTabFinal() {
        JPanel p = new JPanel();
        p.setBackground(Estilos.FONDO);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JPanel sec = seccion("Informacion Final");
        txtEntrevistador = campo(sec, "Nombre del Entrevistador:");

        JLabel lblObs = new JLabel("Observaciones:");
        lblObs.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblObs.setForeground(Estilos.TEXTO_GRIS);
        lblObs.setAlignmentX(Component.LEFT_ALIGNMENT);
        sec.add(Box.createVerticalStrut(6));
        sec.add(lblObs);

        txtObservaciones = new JTextArea(5, 30);
        txtObservaciones.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtObservaciones.setLineWrap(true);
        txtObservaciones.setWrapStyleWord(true);
        txtObservaciones.setBorder(BorderFactory.createLineBorder(new Color(200, 210, 225)));

        JScrollPane scrollObs = new JScrollPane(txtObservaciones);
        scrollObs.setAlignmentX(Component.LEFT_ALIGNMENT);
        scrollObs.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        sec.add(scrollObs);
        p.add(sec);
        return wrapScroll(p);
    }

    /////////////////////////////////
    /**
     * MÉTODO PARA PIE DEL FRAME
     */
    /////////////////////////////////
    ///
    private JPanel construirPiePagina() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT, 14, 10));
        p.setBackground(new Color(245, 247, 250));
        p.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(210, 220, 235)));

        JButton btnCerrar = Estilos.botonSecundario("Cerrar");
        btnCerrar.addActionListener(e -> dispose());

        JButton btnGuardar = new JButton("Guardar Formulario");
        estiloBtnPrimario(btnGuardar);
        btnGuardar.addActionListener(e -> guardarFormulario());

        p.add(btnGuardar);
        p.add(btnCerrar);
        return p;
    }


    /////////////////////////////////
    /**
     * MÉTODO PARA GUARDAR FORMULARIO
     */
    /////////////////////////////////
    ///
    private void guardarFormulario() {
        try {
            System.out.println("=== INICIANDO GUARDADO ===");
            Formulario f = new Formulario();

            // 1. FECHA INICIO
            
            System.out.println("Validando fecha inicio...");
            if (txtFechaInicio.getText().trim().isEmpty()) {
                mostrarError("La fecha de inicio es obligatoria.");
                return;
            }
            Date fechaInicio = parseDate(txtFechaInicio.getText());
            if (fechaInicio == null) {
                mostrarError("Formato de fecha de inicio inválido. Use dd/MM/yyyy.");
                return;
            }
            if (fechaInicio.after(new Date())) {
                mostrarError("La fecha de inicio no puede ser futura.");
                return;
            }
            f.setFechaInicio(fechaInicio);

            // 2. FECHA CONCLUSION 
            
            if (!txtFechaConclusion.getText().trim().isEmpty()) {
                Date fechaConclusion = parseDate(txtFechaConclusion.getText());
                if (fechaConclusion == null) {
                    mostrarError("Formato de fecha de conclusión inválido. Use dd/MM/yyyy.");
                    return;
                }
                if (fechaConclusion.before(fechaInicio)) {
                    mostrarError("La fecha de conclusión no puede ser anterior a la fecha de inicio.");
                    return;
                }
                f.setFechaConclusion(fechaConclusion);
            }

            // 3. PROLONGACIONES
            f.setProlongacion1(parseDate(txtProlongacion1.getText()));
            f.setProlongacion2(parseDate(txtProlongacion2.getText()));
            f.setProlongacion3(parseDate(txtProlongacion3.getText()));
            f.setProlongacion4(parseDate(txtProlongacion4.getText()));

            // 4. PARROQUIA (obligatoria)
            
            Parroquia seleccionada = (Parroquia) comboParroquia.getSelectedItem();
            System.out.println("Validando parroquia...");

            if (seleccionada == null) {
                mostrarError("Debe seleccionar una parroquia.");
                return;
            }

            f.setParroquia(seleccionada);

            // 5. DIRECCION Y TELEFONO
            
            f.setDireccion(txtDireccion.getText().trim());
            String telefono = txtTelefono.getText().trim();
            if (!telefono.isEmpty() && !telefono.matches("\\d{8,15}")) {
                mostrarError("El teléfono debe contener entre 8 y 15 dígitos.");
                return;
            }
            f.setTelefonoContacto(telefono);

            // 6. CONSTRUIR LISTA DE PERSONAS
            
            System.out.println("Construyendo lista de personas...");
            List<PersonaParticipante> personas = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                String nombre = txtNombre[i].getText().trim();
                if (nombre.isEmpty()) continue;

                PersonaParticipante p = new PersonaParticipante();
                p.setNumeroPersona(i + 1);
                p.setNombreCompleto(nombre);
                p.setDocumentoIdentidad(txtDocumento[i].getText().trim());
                p.setSexo((String) cmbSexo[i].getSelectedItem());
                p.setJefatura(txtJefatura[i].getText().trim());
                p.setRelacion(txtRelacion[i].getText().trim());

                String edadStr = txtEdad[i].getText().trim();
                if (!edadStr.isEmpty()) {
                    try {
                        p.setEdad(Integer.parseInt(edadStr));
                    } catch (NumberFormatException e) {
                        mostrarError("Edad inválida en la fila " + (i+1));
                        return;
                    }
                }

                p.setPais(txtPais[i].getText().trim());
                p.setMigracion(txtMigracion[i].getText().trim());
                p.setEducacion(txtEducacion[i].getText().trim());
                p.setSalud(txtSalud[i].getText().trim());
                p.setSeguro(txtSeguro[i].getText().trim());

                p.setOcupacion(txtOcupacion[i].getText().trim());
                p.setTrabaja(chkTrabaja[i].isSelected());

                String ingresoStr = txtIngreso[i].getText().trim();
                if (!ingresoStr.isEmpty()) {
                    try {
                        p.setIngresoMensual(Double.parseDouble(ingresoStr));
                    } catch (NumberFormatException e) {
                        mostrarError("Ingreso mensual inválido en la fila " + (i+1));
                        return;
                    }
                }
                
                // IMPORTANTE: establecer la relación inversa
                p.setFormulario(f);
                
                personas.add(p); //método para agregar las personas participantes
            }

            if (personas.isEmpty()) {
                mostrarError("Debe ingresar al menos una persona participante.");
                return;
            }
            
            // VALIDACION DE CEDULAS DUPLICADAS //
            
            // 1. Recolectamos todas las cedulas
            List<String> todasCedulas = new ArrayList<>();
            for (PersonaParticipante p : personas) {
            	String ced = p.getDocumentoIdentidad();
            	if (ced != null && !ced.trim().isEmpty()) {
            		todasCedulas.add(ced.trim());
            	}
            }
            
            // 2. Obtener las cedulas duplicadas (para nuevo formulario, idFormularioExcluir = null)
            if (!todasCedulas.isEmpty()) {
            	List<String> duplicadas = service.getCedulaDuplicadas(todasCedulas, null);
            	
            	//En caso de que hayan duplicadas, mostramos mensaje de error con la informacion detallada
            	if (!duplicadas.isEmpty()) {
            		StringBuilder mensaje = new StringBuilder("No se puede registrar el formulario porque las siguientes personas "
            				+ "ya tienen una ayuda activa en otro formulario:\n");
            		for (String ced : duplicadas) {
            			String info = service.obtenerInfoPersonaPorCedula(ced);
            			mensaje.append("• Cédula ").append(ced).append(": ").append(info).append("\n");
            		}
            		mensaje.append("\nPor favor, verifique los datos.");
            		mostrarError(mensaje.toString());
            		return; //Cancelamos el guardado
            	}
            }
            f.setPersonas(personas);

            // 7. GENERAR NUMERO DE FICHA
            System.out.println("Generando número de ficha...");
            String primerDocumento = personas.get(0).getDocumentoIdentidad();
            if (primerDocumento != null && !primerDocumento.trim().isEmpty()) {
                f.setFichaNumero("CD-" + primerDocumento.trim());
            } else {
                f.setFichaNumero("CD-SIN_DOC");
            }
            System.out.println("Ficha generada: " + f.getFichaNumero());

            // 8. VIVIENDA
            f.getVivienda().setTipoVivienda((String) cmbTipoVivienda.getSelectedItem());
            f.getVivienda().setTenencia((String) cmbTenencia.getSelectedItem());
            f.getVivienda().setCondicionVivienda((String) cmbCondicion.getSelectedItem());

            // 9. ASISTENCIA
            AsistenciaSolicitada asis = f.getAsistencia();
            asis.setAlimentos(chkAlimentos.isSelected());
            asis.setHigieneLimpieza(chkHigiene.isSelected());
            asis.setIndumentaria(chkIndumentaria.isSelected());
            asis.setAlquiler(chkAlquiler.isSelected());
            asis.setServicios(chkServicios.isSelected());
            asis.setMedicamentos(chkMedicamentos.isSelected());
            asis.setAparatosOrtopedicos(chkAparatos.isSelected());
            asis.setOtros1(txtOtros1.getText().trim());
            asis.setOtros2(txtOtros2.getText().trim());
            asis.setModalidad(txtModalidad.getText().trim());
            asis.setFrecuencia(txtFrecuencia.getText().trim());
            asis.setDuracion(txtDuracion.getText().trim());

            String valorStr = txtValor.getText().trim();
            if (!valorStr.isEmpty()) {
                try {
                    double valor = Double.parseDouble(valorStr);
                    if (valor < 0) throw new NumberFormatException();
                    asis.setValor(valor);
                } catch (NumberFormatException ex) {
                    mostrarError("El valor debe ser un número válido y no negativo.");
                    return;
                }
            }

            // 10. ENTREVISTADOR Y OBSERVACIONES
            System.out.println("Validando entrevistador...");
            if (txtEntrevistador.getText().trim().isEmpty()) {
                mostrarError("El nombre del entrevistador es obligatorio.");
                return;
            }
            f.setNombreEntrevistador(txtEntrevistador.getText().trim());
            f.setObservaciones(txtObservaciones.getText().trim());

            // 11. GUARDAR
            System.out.println("Llamando al servicio para guardar...");
            String error = service.registrarFormulario(f); //GUARDAMOS EL FORMULARIO 
            if (error != null) {
                mostrarError(error);
                System.out.println("Error devuelto por servicio: " + error);
            } else {
                JOptionPane.showMessageDialog(this,
                    "Formulario guardado correctamente.\nNúmero de ficha: " + f.getFichaNumero(),
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            mostrarError("Error inesperado: " + ex.getMessage());
        }
    }

    /////////////////////////////////
    /**
     * MÉTODOS DISEÑO DEL FRAME 
     */
    /////////////////////////////////
    ///
    private JPanel seccion(String titulo) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(Color.WHITE);
        p.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        TitledBorder border = BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(180, 200, 230), 1),
            titulo, TitledBorder.LEFT, TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 12), Estilos.AZUL
        );
        p.setBorder(BorderFactory.createCompoundBorder(
            border, BorderFactory.createEmptyBorder(10, 12, 10, 12)));
        return p;
    }

    private JTextField campo(JPanel parent, String etiqueta) {
        JPanel fila = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 3));
        fila.setBackground(parent.getBackground());
        fila.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel lbl = new JLabel(etiqueta);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lbl.setForeground(Estilos.TEXTO_GRIS);
        lbl.setPreferredSize(new Dimension(230, 24));
        JTextField txt = new JTextField(22);
        estiloCampo(txt);
        fila.add(lbl); fila.add(txt);
        parent.add(fila);
        return txt;
    }

    private void addLabelCombo(JPanel parent, String etiqueta, JComboBox combo) {
        JPanel fila = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 3));
        fila.setBackground(parent.getBackground());
        fila.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel lbl = new JLabel(etiqueta);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lbl.setForeground(Estilos.TEXTO_GRIS);
        lbl.setPreferredSize(new Dimension(230, 24));
        fila.add(lbl); fila.add(combo);
        parent.add(fila);
    }

    private JCheckBox checkbox(JPanel parent, String etiqueta) {
        JCheckBox chk = new JCheckBox(etiqueta);
        chk.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        chk.setForeground(Estilos.TEXTO_GRIS);
        chk.setBackground(Color.WHITE);
        chk.setAlignmentX(Component.LEFT_ALIGNMENT);
        parent.add(chk);
        return chk;
    }

    private void estiloCampo(JTextField t) {
        t.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        t.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 210, 225)),
            BorderFactory.createEmptyBorder(3, 6, 3, 6)));
    }

    private void estiloCombo(JComboBox combo) {
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        combo.setPreferredSize(new Dimension(180, 28));
    }

    private void estiloBtnPrimario(JButton b) {
        b.setBackground(Estilos.AZUL); b.setForeground(Color.WHITE);
        b.setFont(new Font("Segoe UI", Font.BOLD, 13));
        b.setFocusPainted(false); b.setBorderPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setPreferredSize(new Dimension(200, 38));
    }

    private JScrollPane wrapScroll(JPanel p) {
        JScrollPane scroll = new JScrollPane(p);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getVerticalScrollBar().setUnitIncrement(14);
        return scroll;
    }

    private Date parseDate(String texto) {
        if (texto == null || texto.trim().isEmpty()) return null;
        try {
            return sdf.parse(texto.trim());
        } catch (ParseException e) {
            return null;
        }
    }

    private void mostrarError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error de validación", JOptionPane.ERROR_MESSAGE);
    }

    private void cargarParroquias() {
        List<Parroquia> lista = service.listarParroquias();

        comboParroquia.removeAllItems();

        for (Parroquia p : lista) {
            comboParroquia.addItem(p);
        }
    }
}
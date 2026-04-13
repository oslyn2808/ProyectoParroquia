package presentacion;

import logicaNegocio.SistemaService;
import entidades.Formulario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class EntrevistaFrame extends JFrame {

    private SistemaService service;
    private JTable tabla;
    private DefaultTableModel modelo;
    private List<Formulario> lista;

    public EntrevistaFrame(SistemaService service) {
        this.service = service;

        setTitle("Seleccionar Formulario");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(Estilos.panelTitulo(
                "Seleccionar Formulario",
                "Seleccione un formulario para crear un adéndum"
        ), BorderLayout.NORTH);

        add(contenido(), BorderLayout.CENTER);
        add(pie(), BorderLayout.SOUTH);

        cargarDatos();
    }

    private JScrollPane contenido() {
        // Columnas en el orden solicitado
        modelo = new DefaultTableModel(
                new String[]{"Número de Ficha", "Fecha", "Observaciones", "Entrevistador", "Parroquia"}, 0
        );

        tabla = new JTable(modelo);
        tabla.setRowHeight(28);
        tabla.setFont(Estilos.NORMAL);

        return new JScrollPane(tabla);
    }

    private JPanel pie() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        p.setBackground(Estilos.FONDO);

        JButton btnSeleccionar = Estilos.botonPrimario("Seleccionar");
        btnSeleccionar.addActionListener(e -> seleccionar());

        p.add(btnSeleccionar);
        return p;
    }

    private void cargarDatos() {
        lista = service.listarFormularios();
        modelo.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        for (Formulario f : lista) {
            String numeroFicha = f.getFichaNumero() != null ? f.getFichaNumero() : "-";
            String fechaStr = f.getFechaInicio() != null ? sdf.format(f.getFechaInicio()) : "-";
            String observaciones = f.getObservaciones() != null ? f.getObservaciones() : "";
            String entrevistador = f.getNombreEntrevistador() != null ? f.getNombreEntrevistador() : "-";
            String parroquia = f.getParroquia() != null ? f.getParroquia().getNombre() : "-";

            modelo.addRow(new Object[]{
                    numeroFicha,
                    fechaStr,
                    observaciones,
                    entrevistador,
                    parroquia
            });
        }
    }

    private void seleccionar() {
        int fila = tabla.getSelectedRow();

        if (fila == -1) {
            Estilos.mostrarError(this, "Seleccione un formulario");
            return;
        }

        Formulario formularioSeleccionado = lista.get(fila);
        new RegistrarAdendumFrame(service, formularioSeleccionado).setVisible(true);
        dispose();
    }
}
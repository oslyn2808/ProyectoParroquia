package presentacion;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class Estilos {

    public static final Color AZUL_OSCURO   = new Color(26, 60, 100);
    public static final Color AZUL          = new Color(41, 98, 163);
    public static final Color AZUL_CLARO    = new Color(235, 243, 255);
    public static final Color ROJO          = new Color(192, 57, 43);
    public static final Color VERDE         = new Color(39, 174, 96);
    public static final Color FONDO         = new Color(245, 247, 250);
    public static final Color BLANCO        = Color.WHITE;
    public static final Color TEXTO         = new Color(33, 37, 41);
    public static final Color TEXTO_GRIS    = new Color(108, 117, 125);
    public static final Color BORDE         = new Color(206, 212, 218);

    public static final Font TITULO         = new Font("Segoe UI", Font.BOLD, 20);
    public static final Font SUBTITULO      = new Font("Segoe UI", Font.BOLD, 13);
    public static final Font NORMAL         = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font PEQUEÑO        = new Font("Segoe UI", Font.PLAIN, 11);
    public static final Font BOTON          = new Font("Segoe UI", Font.BOLD, 13);

    public static JTextField campoTexto() {
        JTextField c = new JTextField();
        c.setFont(NORMAL);
        c.setPreferredSize(new Dimension(220, 32));
        c.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDE),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));
        return c;
    }

    public static JPasswordField campoPassword() {
        JPasswordField c = new JPasswordField();
        c.setFont(NORMAL);
        c.setPreferredSize(new Dimension(220, 32));
        c.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDE),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));
        return c;
    }

    public static JTextArea campoTextoArea() {
        JTextArea c = new JTextArea(3, 20);
        c.setFont(NORMAL);
        c.setLineWrap(true);
        c.setWrapStyleWord(true);
        c.setBorder(BorderFactory.createEmptyBorder(6, 8, 6, 8));
        return c;
    }

    public static JComboBox<String> combo(String... opciones) {
        JComboBox<String> c = new JComboBox<>(opciones);
        c.setFont(NORMAL);
        c.setPreferredSize(new Dimension(220, 32));
        c.setBackground(BLANCO);
        return c;
    }

    public static JLabel label(String texto) {
        JLabel l = new JLabel(texto);
        l.setFont(SUBTITULO);
        l.setForeground(TEXTO);
        return l;
    }

    public static JLabel labelGris(String texto) {
        JLabel l = new JLabel(texto);
        l.setFont(PEQUEÑO);
        l.setForeground(TEXTO_GRIS);
        return l;
    }

    public static JButton botonPrimario(String texto) {
        JButton b = new JButton(texto);
        b.setFont(BOTON);
        b.setBackground(AZUL);
        b.setForeground(BLANCO);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setPreferredSize(new Dimension(140, 36));
        return b;
    }

    public static JButton botonSecundario(String texto) {
        JButton b = new JButton(texto);
        b.setFont(BOTON);
        b.setBackground(BORDE);
        b.setForeground(TEXTO);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setPreferredSize(new Dimension(140, 36));
        return b;
    }

    public static JButton botonPeligro(String texto) {
        JButton b = new JButton(texto);
        b.setFont(BOTON);
        b.setBackground(ROJO);
        b.setForeground(BLANCO);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setPreferredSize(new Dimension(140, 36));
        return b;
    }

    public static JPanel panelTitulo(String titulo, String subtitulo) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(AZUL_OSCURO);
        p.setBorder(BorderFactory.createEmptyBorder(18, 24, 18, 24));

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(TITULO);
        lblTitulo.setForeground(BLANCO);

        JLabel lblSub = new JLabel(subtitulo);
        lblSub.setFont(PEQUEÑO);
        lblSub.setForeground(new Color(180, 200, 230));

        JPanel textos = new JPanel(new GridLayout(2, 1));
        textos.setOpaque(false);
        textos.add(lblTitulo);
        textos.add(lblSub);
        p.add(textos, BorderLayout.CENTER);
        return p;
    }

    public static void mostrarError(Component parent, String msg) {
        JOptionPane.showMessageDialog(parent, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void mostrarExito(Component parent, String msg) {
        JOptionPane.showMessageDialog(parent, msg, "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }
}
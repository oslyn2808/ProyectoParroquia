package presentacion;

import javax.swing.*;
import java.awt.*;
import logicaNegocio.SistemaService;
import entidades.Usuario;

public class LoginFrame extends JFrame {

    private JTextField txtCorreo;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JLabel lblError;
    private SistemaService service;

    /////////////////////////////////
    /**
     * CONSTRUCTOR DEL FRAME
     */
    /////////////////////////////////
    ///
    public LoginFrame(SistemaService service) {
        this.service = service;
        setTitle("Sistema Pastoral Social");
        setSize(820, 520);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        add(panelIzquierdo(), BorderLayout.WEST);
        add(panelDerecho(), BorderLayout.CENTER);
    }

    /////////////////////////////////
    /**
     * PANEL IZQUIERDO DEL FRAME 
     */
    /////////////////////////////////
    ///
    private JPanel panelIzquierdo() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setPreferredSize(new Dimension(340, 520));
        panel.setBackground(Estilos.AZUL_OSCURO);

        GridBagConstraints g = new GridBagConstraints();
        g.gridx = 0;
        g.insets = new Insets(10, 30, 10, 30);
        g.anchor = GridBagConstraints.CENTER;

        JLabel icono = new JLabel("✝", SwingConstants.CENTER);
        icono.setFont(new Font("Segoe UI", Font.PLAIN, 60));
        icono.setForeground(new Color(255, 255, 255, 160));
        g.gridy = 0; panel.add(icono, g);

        JLabel titulo = new JLabel("Sistema Pastoral", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(Color.WHITE);
        g.gridy = 1; panel.add(titulo, g);

        JLabel titulo2 = new JLabel("Social", SwingConstants.CENTER);
        titulo2.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo2.setForeground(Color.WHITE);
        g.gridy = 2; panel.add(titulo2, g);

        JSeparator sep = new JSeparator();
        sep.setPreferredSize(new Dimension(180, 1));
        sep.setForeground(new Color(255, 255, 255, 60));
        g.gridy = 3; g.insets = new Insets(20, 30, 20, 30); panel.add(sep, g);

        JLabel desc = new JLabel(
            "<html><center>Gestión de beneficiarios<br>y apoyo comunitario</center></html>",
            SwingConstants.CENTER);
        desc.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        desc.setForeground(new Color(180, 200, 230));
        g.gridy = 4; g.insets = new Insets(0, 30, 0, 30); panel.add(desc, g);

        return panel;
    }

    /////////////////////////////////
    /**
     * PANEL DERECHO DEL FRAME
     */
    /////////////////////////////////
    ///
    private JPanel panelDerecho() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Estilos.FONDO);

        GridBagConstraints g = new GridBagConstraints();
        g.gridx = 0;
        g.fill = GridBagConstraints.HORIZONTAL;
        g.insets = new Insets(6, 50, 6, 50);

        JLabel lblBienvenido = new JLabel("Iniciar Sesión");
        lblBienvenido.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblBienvenido.setForeground(Estilos.TEXTO);
        g.gridy = 0; g.insets = new Insets(0, 50, 4, 50); panel.add(lblBienvenido, g);

        JLabel lblSub = Estilos.labelGris("Ingresá tus credenciales para continuar");
        g.gridy = 1; g.insets = new Insets(0, 50, 25, 50); panel.add(lblSub, g);

        g.insets = new Insets(5, 50, 2, 50);
        g.gridy = 2; panel.add(Estilos.label("Correo electrónico"), g);
        txtCorreo = Estilos.campoTexto();
        g.gridy = 3; panel.add(txtCorreo, g);

        g.gridy = 4; panel.add(Estilos.label("Contraseña"), g);
        txtPassword = Estilos.campoPassword();
        g.gridy = 5; panel.add(txtPassword, g);

        lblError = new JLabel(" ");
        lblError.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblError.setForeground(Estilos.ROJO);
        g.gridy = 6; g.insets = new Insets(2, 50, 2, 50); panel.add(lblError, g);

        btnLogin = Estilos.botonPrimario("Ingresar");
        btnLogin.setPreferredSize(new Dimension(340, 40));
        g.gridy = 7; g.insets = new Insets(10, 50, 5, 50); panel.add(btnLogin, g);

        JLabel hint = Estilos.labelGris("Demo: admin@parroquia.com / 1234");
        hint.setHorizontalAlignment(SwingConstants.CENTER);
        g.gridy = 8; g.insets = new Insets(8, 50, 0, 50); panel.add(hint, g);

        btnLogin.addActionListener(e -> login());
        txtPassword.addActionListener(e -> login());

        return panel;
    }

    /////////////////////////////////
    /**
     * MÉTODO PARA LOGIN
     */
    /////////////////////////////////
    ///
    private void login() { 

        String correo = txtCorreo.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (correo.isEmpty() || password.isEmpty()) {
            lblError.setText("Completá todos los campos.");
            return;
        }
        try {
            Usuario usuario = service.login(correo, password);
            if (usuario != null) {
                dispose();
                new MenuPrincipalFrame(service).setVisible(true);
            } else {
                lblError.setText("Correo o contraseña incorrectos.");
                txtPassword.setText("");
            }
        } catch (Exception e) {
            lblError.setText("Error al conectar con el sistema.");
            e.printStackTrace();
        }
    }
}

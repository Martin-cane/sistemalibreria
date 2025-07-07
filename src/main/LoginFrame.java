package main;

import javax.swing.*;
import java.awt.event.*;
import dao.UsuarioDAO;
import modelo.Usuario;

public class LoginFrame extends JFrame {

    //eclipse me lo sugirio
	private static final long serialVersionUID = 1L;
	
	
	private JTextField campoEmail;
    private JPasswordField campoPassword;
    private UsuarioDAO usuarioDAO;

    public LoginFrame() {
        usuarioDAO = new UsuarioDAO();

        setTitle("Login - Librería");
        setSize(350, 250); // un poco más alto para el botón extra
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        JLabel labelEmail = new JLabel("Email:");
        labelEmail.setBounds(20, 20, 80, 25);
        add(labelEmail);

        campoEmail = new JTextField();
        campoEmail.setBounds(100, 20, 200, 25);
        add(campoEmail);

        JLabel labelPassword = new JLabel("Contraseña:");
        labelPassword.setBounds(20, 60, 80, 25);
        add(labelPassword);

        campoPassword = new JPasswordField();
        campoPassword.setBounds(100, 60, 200, 25);
        add(campoPassword);

        JButton botonLogin = new JButton("Iniciar Sesión");
        botonLogin.setBounds(100, 100, 140, 30);
        add(botonLogin);

        JButton botonRegistrarse = new JButton("Registrarse");
        botonRegistrarse.setBounds(100, 140, 140, 30);
        add(botonRegistrarse);

        botonLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String email = campoEmail.getText();
                String pass = new String(campoPassword.getPassword());

                Usuario usuario = usuarioDAO.login(email, pass);
                if (usuario != null) {
                    JOptionPane.showMessageDialog(null, "Bienvenido " + usuario.getNombre());
                    dispose();
                    new MenuPrincipalFrame(usuario).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Email o contraseña incorrectos");
                }
            }
        });

        botonRegistrarse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Ocultar login mientras se registra
                setVisible(false);
                // Abrir ventana registro
                RegistroFrame registro = new RegistroFrame(LoginFrame.this);
                registro.setVisible(true);
            }
        });

        setVisible(true);
    }
}

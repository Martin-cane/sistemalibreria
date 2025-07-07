package main;

import javax.swing.*;

import dao.UsuarioDAO;
import modelo.Usuario;

public class RegistroFrame extends JFrame {

	//eclipse me lo sugirio
	private static final long serialVersionUID = 1L;
	
	
	
	private JTextField campoNombre, campoEmail;
    private JPasswordField campoPassword;
    private JComboBox<String> comboRol;
    private UsuarioDAO usuarioDAO;

    public RegistroFrame(LoginFrame loginFrame) {
        usuarioDAO = new UsuarioDAO();

        setTitle("Registro de Usuario");
        setSize(350, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);

        JLabel labelNombre = new JLabel("Nombre:");
        labelNombre.setBounds(20, 20, 80, 25);
        add(labelNombre);

        campoNombre = new JTextField();
        campoNombre.setBounds(100, 20, 200, 25);
        add(campoNombre);

        JLabel labelEmail = new JLabel("Email:");
        labelEmail.setBounds(20, 60, 80, 25);
        add(labelEmail);

        campoEmail = new JTextField();
        campoEmail.setBounds(100, 60, 200, 25);
        add(campoEmail);

        JLabel labelPassword = new JLabel("Contraseña:");
        labelPassword.setBounds(20, 100, 80, 25);
        add(labelPassword);

        campoPassword = new JPasswordField();
        campoPassword.setBounds(100, 100, 200, 25);
        add(campoPassword);

        JLabel labelRol = new JLabel("Rol:");
        labelRol.setBounds(20, 140, 80, 25);
        add(labelRol);

        comboRol = new JComboBox<>(new String[]{"CLIENTE", "ADMIN"});
        comboRol.setBounds(100, 140, 200, 25);
        add(comboRol);

        JButton botonRegistrar = new JButton("Registrarse");
        botonRegistrar.setBounds(100, 180, 140, 30);
        add(botonRegistrar);

        JButton botonCancelar = new JButton("Cancelar");
        botonCancelar.setBounds(100, 220, 140, 30);
        add(botonCancelar);

        botonRegistrar.addActionListener(e -> registrarUsuario());

        botonCancelar.addActionListener(e -> {
            dispose(); // Cierra registro
        });

        // Cuando se cierra la ventana (clic en X), vuelve a mostrar login
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                if (loginFrame != null) {
                    loginFrame.setVisible(true);
                }
            }
        });

        setVisible(true);
    }

    public RegistroFrame() {
		// TODO Auto-generated constructor stub
	}

	private void registrarUsuario() {
        String nombre = campoNombre.getText().trim();
        String email = campoEmail.getText().trim();
        String pass = new String(campoPassword.getPassword()).trim();
        String rol = comboRol.getSelectedItem().toString();

        if (nombre.isEmpty() || email.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            JOptionPane.showMessageDialog(this, "Email inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (usuarioDAO.existeEmail(email)) {
            JOptionPane.showMessageDialog(this, "Ese email ya está registrado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Usuario nuevoUsuario = new Usuario(0, nombre, email, pass, rol);
        boolean exito = usuarioDAO.registrarUsuario(nuevoUsuario);

        if (exito) {
            JOptionPane.showMessageDialog(this, "Usuario registrado con éxito.");
            dispose();  // Cierra registro y vuelve a mostrar login por listener
        } else {
            JOptionPane.showMessageDialog(this, "Error al registrar usuario.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}


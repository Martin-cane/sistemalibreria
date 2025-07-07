package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class InicioFrame extends JFrame {

    //eclipse me lo sugirio 
	private static final long serialVersionUID = 1L;

	public InicioFrame() {
        setTitle("Bienvenido a la Librería");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // centra la ventana

        // Panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1, 10, 10));

        JLabel label = new JLabel("¿Qué desea hacer?");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label);

        JButton btnLogin = new JButton("Iniciar sesión");
        JButton btnRegistro = new JButton("Registrarse");

        panel.add(btnLogin);
        panel.add(btnRegistro);

        // Acción del botón de login
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // cierra esta ventana
                new LoginFrame(); // abre login
            }
        });

        // Acción del botón de registro
        btnRegistro.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // cierra esta ventana
                new RegistroFrame(); // abre registro
            }
        });

        add(panel);
        setVisible(true);
    }

    // Para iniciar la app desde acá
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InicioFrame());
    }
}

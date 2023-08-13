package functions;
import javax.swing.*;

import java.awt.Color;
import java.awt.event.*;

/**
 * Clase que define la interfaz de errores del programa
 * 
 * @author Manuel Rodriguez Rodriguez
 * @version 02/05/2023
 */
public class UIError extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 800;
	private static final int HEIGHT = 400;
	

	
	private JButton botonSalir;
	private JLabel bien;
	private JButton botonAceptar;
	
	/**
	 * Contructor de la clase. Se encarga de generar la pantalla principal del programa.
	 * @param mensaje de error
	 */
	public UIError(String mensaje) {
		
		setLayout(null);
		
		
		
		botonSalir = new JButton("Salir");
		botonSalir.setBounds(650,20,100,30);
		add(botonSalir);
		
		botonSalir.addActionListener(this);
		if (mensaje.isEmpty()) {
			bien = new JLabel("Ha ocurrido un error.");
		} else {
			bien = new JLabel(mensaje);
		}

		bien.setBounds(230,330, 350, 30);
		bien.setHorizontalAlignment(JLabel.CENTER);
		bien.setForeground(Color.RED);
		add(bien);
		
		ImageIcon imagen = new ImageIcon("resources/error.png");
		JLabel etiquetaImagen = new JLabel(imagen);
		etiquetaImagen.setBounds(200,20, 400, 300);
		etiquetaImagen.setHorizontalAlignment(JLabel.CENTER);
		etiquetaImagen.setVerticalAlignment(JLabel.CENTER);
		add(etiquetaImagen);
		
		
		botonAceptar = new JButton("Aceptar");
		botonAceptar.setBounds(20,20,100,30);
		add(botonAceptar);
		
		botonAceptar.addActionListener(this);
	
		setBounds(0, 0, WIDTH, HEIGHT);
		setVisible(true);
		setLocationRelativeTo(null);
		setResizable(false);
		 
		
	}

	/**
	 * Método encargado de recepcionar un evento y realizar una accion según dicho evento
	 * @param e El evento a recepcionar
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == botonSalir) {
			System.exit(0);
		}
		if(e.getSource() == botonAceptar) {
			UI firma = new UI();
			this.setVisible(false);
		}	
	}
}

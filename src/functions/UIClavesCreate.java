package functions;
import javax.swing.*;

import java.awt.event.*;
import java.io.File;
import java.util.Scanner;

/**
 * Clase que define la interfaz de datos de las claves del programa
 * 
 * @author Manuel Rodriguez Rodriguez
 * @version 02/05/2023
 */
public class UIClavesCreate extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 800;
	private static final int HEIGHT = 400;
	

	Clave clave;
	Cifrado CifradoClaves;
    Firma firma;
    Scanner teclado;
    File fKey;
    File fPubKey;
    File fPrivKey;
    Main p4;
    
    String name;
	    
	
	private JButton botonSalir;
	private JLabel label, label2, label3;
	private JButton botonAceptar,botonVolver;
	private JComboBox<String> type,tamano;
	private JPasswordField passField;
	
	/**
	 * Contructor de la clase. Se encarga de generar la pantalla principal del programa.
	 * @param nombre El nombre del fichero de claves
	 */
	public UIClavesCreate(String nombre) {
		
		name = nombre;
		setLayout(null);
		

		label = new JLabel("El nombre del fichero elegido es: "+name);
		label.setBounds(20, 15, 400, 30);
		add(label);
		
		botonSalir = new JButton("Salir");
		botonSalir.setBounds(650,20,100,30);
		add(botonSalir);
		
		botonSalir.addActionListener(this);
		

		botonAceptar = new JButton("Aceptar");
		botonAceptar.setBounds(20,200,100,30);
		add(botonAceptar);
		
		botonAceptar.addActionListener(this);
		

		label = new JLabel("Elige el tipo de claves");
		label.setBounds(20, 40, 250, 30);
		add(label);
		type = new JComboBox<String>();
		type.setBounds(20, 70, 100, 30);
		add(type);
		type.addItem("RSA");
		type.addItem("DSA");
		
		label2 = new JLabel("Elige el tamaño de claves");
		label2.setBounds(300, 40, 250, 30);
		add(label2);
		tamano = new JComboBox<String>();
		tamano.setBounds(300, 70, 100, 30);
		add(tamano);
		tamano.addItem("512");
		tamano.addItem("768");
		tamano.addItem("1024");
		
		label3 = new JLabel("Introduce la contraseña de cifrado de claves");
		label3.setBounds(20, 120, 300, 30);
		add(label3);
		
		passField = new JPasswordField();
		passField.setBounds(20, 150, 300, 30);
		add(passField);
		

		botonVolver = new JButton("Volver");
		botonVolver.setBounds(20,300,100,30);
		add(botonVolver);
		
		botonVolver.addActionListener(this);

		 
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
			
				String tipo = (String) type.getSelectedItem().toString();
				String tam = (String) tamano.getSelectedItem().toString();
				char[] pass = passField.getPassword();
				System.out.println(pass);
				clave = new Clave(name);
				clave.crearClaves(tipo,tam, pass);
				
				UI ui = new UI();
				this.setVisible(false);
			
		}
		if(e.getSource() == botonVolver) {
			UIClavesDatos ui = new UIClavesDatos();
			this.setVisible(false);
		}
	}
}

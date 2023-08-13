package functions;
import javax.swing.*;

import java.awt.Color;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Scanner;

/**
 * Clase que define la interfaz de verificación de firma del programa
 * 
 * @author Manuel Rodriguez Rodriguez
 * @version 02/05/2023
 */
public class UIVerifyFirma extends JFrame implements ActionListener{

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
	private JPasswordField passField;
	private JTextField field, field2;
	
	/**
	 * Contructor de la clase. Se encarga de generar la pantalla principal del programa.
	 */
	public UIVerifyFirma() {
		
//		name = nombre;
		setLayout(null);
		
		label = new JLabel("Inserta el nombre del archivo de claves: ");
		label.setBounds(20, 20, 250, 30);
		add(label);
		
		field = new JTextField("Sin extensión");
		field.setBounds(250, 20, 300, 30);
		add(field);
		


		label2 = new JLabel("Inserta el nombre con extensión del fichero a verificar: ");
		label2.setBounds(20, 60, 350, 30);
		add(label2);
		
		field2 = new JTextField();
		field2.setBounds(340, 60, 300, 30);
		add(field2);
		
		botonSalir = new JButton("Salir");
		botonSalir.setBounds(650,20,100,30);
		add(botonSalir);
		
		botonSalir.addActionListener(this);
		

		botonAceptar = new JButton("Aceptar");
		botonAceptar.setBounds(20,259,100,30);
		add(botonAceptar);
		
		botonAceptar.addActionListener(this);
		

		
		label3 = new JLabel("Introduce la contraseña de cifrado de claves");
		label3.setBounds(20, 160, 300, 30);
		add(label3);
		
		passField = new JPasswordField();
		passField.setBounds(20, 190, 300, 30);
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
			try {
				char[] pass = passField.getPassword();
				String ficheroCif = field2.getText();
				name = field.getText();

				Clave clave = new Clave(name);
				File file = new File(ficheroCif);

				InputStream in = new FileInputStream(file);
				fPubKey = new File(clave.getfName() + ".pubkey");
				fPrivKey = new File(clave.getfName() + ".privkey");
				if(!clave.cargarClaves(pass)) {
					throw new RuntimeException("No se han podido cargar las claves. Revisa la contraseña");
				}
				

				Firma firma = new Firma(in, clave);
				if (firma.verificarFirma(ficheroCif)) {
					JLabel bien = new JLabel("El fichero se ha verificado y su firma es correcta");
					bien.setBounds(400, 200, 400, 30);
					bien.setForeground(Color.BLUE);
					add(bien);

					setBounds(0, 0, WIDTH, HEIGHT);
					setVisible(true);
					setLocationRelativeTo(null);
					setResizable(false);
				} else {
					UIError error = new UIError("No se ha podido verificar la firma");
					this.setVisible(false);
				}

			} catch (Exception e1) {
				UIError error = new UIError(e1.getMessage());
				this.setVisible(false);

			}
			
			
			
		}	
		if(e.getSource() == botonVolver) {
			
			UI ui = new UI();
			this.setVisible(false);
		}	
	}
}

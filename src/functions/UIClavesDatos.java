package functions;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.util.Scanner;

/**
 * Clase que define la interfaz de claves, donde se especifica el fichero de claves
 * 
 * @author Manuel Rodriguez Rodriguez
 * @version 02/05/2023
 */
public class UIClavesDatos extends JFrame implements ActionListener{

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
	    
	
	private JButton botonSalir;
	private JTextField field;
	private JLabel label;
	private JButton botonAceptar;
	
	/**
	 * Contructor de la clase. Se encarga de generar la pantalla principal del programa.
	 */
	public UIClavesDatos() {
		
		p4 = new Main();
		setLayout(null);
		
		
		
		botonSalir = new JButton("Salir");
		botonSalir.setBounds(650,20,100,30);
		add(botonSalir);
		
		botonSalir.addActionListener(this);
		
		label = new JLabel("Inserta el nombre del fichero de claves: ");
		label.setBounds(20, 20, 250, 30);
		add(label);
		
		field = new JTextField();
		field.setBounds(250, 20, 300, 30);
		add(field);
		
		botonAceptar = new JButton("Aceptar");
		botonAceptar.setBounds(20,70,100,30);
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
			if(field.getText() != null) {
				UIClavesCreate uicc = new UIClavesCreate(field.getText());
				this.setVisible(false);
			}
		
		}	
	}
}

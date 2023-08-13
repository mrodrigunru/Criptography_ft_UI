package functions;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.util.Scanner;

/**
 * Clase que define la interfaz principal de claves del programa
 * 
 * @author Manuel Rodriguez Rodriguez
 * @version 02/05/2023
 */
public class UIClaves extends JFrame implements ActionListener{

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
	    
	
	JButton botonSalir;
	JButton botonClaves;
	
	/**
	 * Contructor de la clase. Se encarga de generar la pantalla principal del programa.
	 */
	public UIClaves() {
		
		p4 = new Main();
		setLayout(null);
		
		
		
		botonSalir = new JButton("Salir");
		botonSalir.setBounds(650,20,100,30);
		add(botonSalir);
		
		botonSalir.addActionListener(this);
	

		
		botonClaves = new JButton("Generar un par de claves");
		botonClaves.setBounds(20,20,200,30);
		add(botonClaves);
		
		botonClaves.addActionListener(this);
		 
		 
		 
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
		if(e.getSource() == botonClaves) {
			UIClavesDatos uicd = new UIClavesDatos();
			this.setVisible(false);
		}	
	}
}

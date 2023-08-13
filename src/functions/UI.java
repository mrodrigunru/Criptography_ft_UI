package functions;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

/**
 * Clase que define la interfaz principal del programa
 * 
 * @author Manuel Rodriguez Rodriguez
 * @version 02/05/2023
 */
public class UI extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 800;
	private static final int HEIGHT = 400;
	

    Main p4;
	JButton botonSalir;
	JMenuItem item1,item2,item3,item4,item5;
	
	/**
	 * Contructor de la clase. Se encarga de generar la pantalla principal del programa.
	 */
	public UI() {
		
		p4 = new Main();
		setLayout(null);
		JLabel label = new JLabel ("Herramienta de criptografía");
		label.setBounds(10, 10, 800, 40);
		label.setFont(new Font("Arial", Font.BOLD, 16));
		label.setForeground(Color.BLUE);
		add(label);
		
		ImageIcon imagen = new ImageIcon("resources/firstImage.png");
		JLabel etiquetaImagen = new JLabel(imagen);
		etiquetaImagen.setBounds(190, 40, 400, 300);
		etiquetaImagen.setHorizontalAlignment(JLabel.CENTER);
		etiquetaImagen.setVerticalAlignment(JLabel.CENTER);
		add(etiquetaImagen);


		
		botonSalir = new JButton("Salir");
		botonSalir.setBounds(650,20,100,30);
		add(botonSalir);
		
		botonSalir.addActionListener(this);
		
		 //barra de menu
		 JMenuBar menuBar = new JMenuBar();
		
		 
		 //pestañas desplegables
		 JMenu menu1 = new JMenu("Claves");
		 menuBar.add(menu1);
		 
		 //opciones del menu
		 item1 = new JMenuItem ("Generar un par de claves");
		 item1.addActionListener(this);
		 menu1.add(item1);
		 
//		 setJMenuBar(menuBar);
		 
		 //pestañas desplegables
		 JMenu menu2 = new JMenu("Cifrado/descifrado");
		 menuBar.add(menu2);
		 
		 //opciones del menu
		 item2 = new JMenuItem ("Cifrar un archivo");
		 item2.addActionListener(this);
		 menu2.add(item2);
		 
		 item3 = new JMenuItem ("Descifrar un archivo");
		 item3.addActionListener(this);
		 menu2.add(item3);
		 
//		 setJMenuBar(menuBar);
		 
		 
		 //pestañas desplegables
		 JMenu menu3 = new JMenu("Firma digital");
		 menuBar.add(menu3);
		 
		 //opciones del menu
		 item4 = new JMenuItem ("Firmar un archivo");
		 item4.addActionListener(this);
		 menu3.add(item4);
		 
		 item5 = new JMenuItem ("Verificar firma");
		 item5.addActionListener(this);
		 menu3.add(item5);
		 
		 setJMenuBar(menuBar);
		 
		 
		 
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
		if(e.getSource() == item1) {
			UIClaves uic = new UIClaves();
			this.setVisible(false);
		}	
		if(e.getSource() == item2) {
			UICifrado uicif = new UICifrado();
			this.setVisible(false);
		}	
		if(e.getSource() == item3) {
			UIDescifrado uidescif = new UIDescifrado();
			this.setVisible(false);
		}	
		if(e.getSource() == item4) {
			UIFirma uidescif = new UIFirma();
			this.setVisible(false);
		}	
		if(e.getSource() == item5) {
			UIVerifyFirma uidescif = new UIVerifyFirma();
			this.setVisible(false);
		}	
	}
}

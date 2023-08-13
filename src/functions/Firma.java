package functions;

import java.io.*;
import java.security.*;

import utility.Header;
import utility.Options;

/**
 * Clase que define las operaciones de firma y verificación
 * 
 * @author Manuel Rodriguez Rodriguez
 * @version 02/05/2023
 */
public class Firma {

	Clave clave;
	int blockSize;
	Signature dsa;
	InputStream file;

	/**
	 * Constructor parametrizado de la clase
	 * 
	 * @param file  Fichero (como flujo de entrada) sobre el que se realizarán las
	 *              operaciones
	 * @param clave
	 */
	public Firma(InputStream file, Clave clave) {

		this.file = file;
		this.clave = clave;
	}

	/**
	 * Funcion dedicada a la firma de un fichero
	 * 
	 * @param algoritmo Algoritmo elegido para realizar la firma
	 * @param nameFile  Nombre del fichero a firmar
	 */
	public boolean firmar(String algoritmo, String nameFile) {
		
		try {

			dsa = Signature.getInstance(algoritmo);
			dsa.initSign((PrivateKey) clave.getPriv());
			OutputStream out = new FileOutputStream(nameFile.substring(0, nameFile.lastIndexOf(".")) + ".fir");

			byte[] b = new byte[64];

			int i;
			while ((i = file.read(b)) != -1) {
				dsa.update(b);
			}

			Header h = new Header(Options.OP_SIGNED, Options.OP_NONE_ALGORITHM, algoritmo, dsa.sign());
			h.save(out);
			file.close();
			InputStream file2 = new FileInputStream(nameFile);

			while ((i = file2.read()) != -1) {
				out.write((byte) i);
			}
			file2.close();
			// out.flush();
			out.close();
			return true;

		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Función dedicada a verificar la firma de un fichero de texto previamente
	 * firmado
	 * 
	 * @param nameFile El nombre del fichero a firmar
	 * @return True si la firma ha sido verificada correctamente, false en caso
	 *         contrario
	 */
	public boolean verificarFirma(String nameFile) {
		boolean valido = false;
		try {

			Header h = new Header();
			/*
			 * Se ha modificado el fichero Options.java para añadir el algoritmo SHA1withDSA
			 * a los algoritmos de autenticación
			 */
			h.load(file);
			String algoritmo = h.getAlgorithm2();

			dsa = Signature.getInstance(algoritmo);
			dsa.initVerify((PublicKey) clave.getPub());

			OutputStream out = new FileOutputStream(nameFile.substring(0, nameFile.lastIndexOf(".")) + ".cla");

			byte[] b = new byte[64];
			int i;
			while ((i = file.read(b)) != -1) {
				out.write(b, 0, i);
				dsa.update(b);
			}

			if (dsa.verify(h.getData())) {
				valido = true;
			}

			// out.flush();
			out.close();

		} catch (Exception e) {
			
			return false;
		}
		return valido;
	}
}

package functions;

import java.io.FileOutputStream;

/**
 * Clase que define las funciones de cifrado y descifrado de un fichero de texto
 * @author Manuel Rodriguez Rodriguez
 * @version 02/05/2023
 */
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.interfaces.RSAKey;

import javax.crypto.Cipher;

import utility.Header;
import utility.Options;

/**
 * Clase que define las operaciones de cifrado y descifrado
 * 
 * @author Manuel Rodriguez Rodriguez
 * @version 02/05/2023
 */
public class Cifrado {
	Clave clave;
	int blockSize;
	String algoritmo = "RSA/ECB/PKCS1Padding";
	InputStream file;

	/**
	 * Constructor parametrizado de la clase
	 * 
	 * @param file   fichero (como flujo de entrada) sobre el que se van a realizar
	 *               las operaciones de cifrado y descifrado
	 * @param clave de la clase Clave a utilizar en las operaciones de cifrado y
	 *               descifrado
	 */
	public Cifrado(InputStream file, Clave clave) {
		this.file = file;
		this.clave = clave;
	}

	/**
	 * Método que cifra un fichero de texto de nombre definido
	 * 
	 * @param nameFile Nombre del fichero de texto a cifrar. EL fichero deberá tener
	 *                 extensión .txt y generará un .cif
	 */
	public void cifrar(String nameFile) {
		if (!clave.getPub().getAlgorithm().equals("DSA")) {
			try (OutputStream out = new FileOutputStream(nameFile.substring(0, nameFile.lastIndexOf(".")) + ".cif")) {
				Cipher cipher = Cipher.getInstance(algoritmo);
				cipher.init(Cipher.ENCRYPT_MODE, (Key) clave.getPub());
				byte[] salt = new byte[] {};
				Header h = new Header(Options.OP_PUBLIC_CIPHER, algoritmo, Options.OP_NONE_ALGORITHM, salt);
				h.save(out);

				/*
				 * The interface to a public or private key in PKCS#1 v2.2 standard,such as
				 * those for RSA, or RSASSA-PSS algorithms
				 */
				RSAKey rsaKey = (RSAKey) clave.getPub();
				int tamClave = rsaKey.getModulus().bitLength();

				/*
				 * 512 -> (en octetos) 64 -11 = 53 768 -> 512 + 256 -> (en octetos) 64 + 32 -11
				 * = 85 1024 -> 512 +512 -> (en octetos) 64 + 64 -11 = 117
				 */

				switch (tamClave) {
				case 512:
					blockSize = 53;
					break;
				case 768:
					blockSize = 85;
					break;
				case 1024:
					blockSize = 117;
					break;
				}
				final byte[] b = new byte[blockSize];
				int i;
				System.out.print("\n");
				while ((i = file.read(b)) != -1) {
					System.out.print(blockSize + " ");
					out.write(cipher.doFinal(b, 0, i));
				}
				file.close();
				out.flush();
				out.close();
				System.out.println("\nFichero cifrado correctamente");
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Se ha producido un error al intentar cifrar el archivo");
			}
		} else
			System.out.println("No se puede realizar esta operación con una clave DSA");
	}

	/**
	 * Funcion dedicada a descifrar un fichero ya cifrado con anterioridad
	 * 
	 * @param nameFile Nomnre del fichero a descifrar. El fichero deberá tener
	 *                 extensión .cif y generará un .cla
	 */
	public void descifrar(String nameFile) {

		if (!clave.getPriv().getAlgorithm().equals("DSA")) {
			try (OutputStream out = new FileOutputStream(nameFile.substring(0, nameFile.lastIndexOf(".")) + ".cla")) {

				Header h = new Header();
				h.load(file);
				algoritmo = h.getAlgorithm1();
				Cipher cipher = Cipher.getInstance(algoritmo);
				cipher.init(Cipher.DECRYPT_MODE, clave.getPriv());

				/*
				 * The interface to a public or private key in PKCS#1 v2.2 standard,such as
				 * those for RSA, or RSASSA-PSS algorithms
				 */
				RSAKey rsaKey = (RSAKey) clave.getPriv();
				int tamClave = rsaKey.getModulus().bitLength();

				/*
				 * 512 -> (en octetos) 64 768 -> 512 + 256 -> (en octetos) 64 + 32 = 96 1024 ->
				 * 512 +512 -> (en octetos) 64 + 64 = 128
				 */

				switch (tamClave) {
				case 512:
					blockSize = 64;
					break;
				case 768:
					blockSize = 96;
					break;
				case 1024:
					blockSize = 128;
					break;
				}

				final byte[] b = new byte[blockSize];
				int i;
				System.out.print("\n");
				while ((i = file.read(b)) != -1) {
					System.out.print(blockSize + " ");
					// Si se hace directamente con doFinal(b) no recorre bien el fichero
					out.write(cipher.doFinal(b, 0, i));
				}

				file.close();
				out.flush();
				out.close();
				System.out.println("\nFichero descifrado correctamente");
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Se ha producido un error al intentar descifrar el archivo");
			}
		} else
			System.out.println("No se puede realizar esta operación con una clave DSA");
	}

}

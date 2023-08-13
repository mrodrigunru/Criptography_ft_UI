package functions;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.*;

/**
 * Clase que define las operaciones que se ralizarán sobre las claves. Estas
 * operaciones son generar un par de claves, así como cargar las claves de
 * fichero y cifrar/descifrar las mismas
 *
 * @author Manuel Rodriguez Rodriguez
 * @version 02/05/2023
 */
public class Clave {

    File fPubKey;
    File fPrivKey;
    KeyPairGenerator kpg;
    KeyPair keyP;
    int tam = 0;
    Cipher cipher;
    String keyAlg;
    Scanner teclado;
    String nombre;
    Key publica;
    Key privada;
    byte[] PubWrappedKey;
    byte[] PrivWrappedKey;
    Key clave;
    String tipo;

    byte[] salt = "salt1234".getBytes();
    int iterationCount = 1024;

    /**
     * Constructor parametrizado de la clase. Se encarga de generar los ficheros
     * donde se almacenarán las claves
     *
     * @param nombre Nombre del fichero de claves (sin extensión)
     */
    public Clave(String nombre) {
        try {
            Security.addProvider(new BouncyCastleProvider());

            File archivo = new File(nombre);
            if (!archivo.exists()) {

                fPubKey = new File(nombre + ".pubkey");
                fPrivKey = new File(nombre + ".privkey");
            }
        } catch (Exception e) {
//            System.out.println("Ya existe un fichero de claves");
        }
    }

    /**
     * Funcion dedicada a obtener la clave privada
     *
     * @return La clave privada
     */

    public Key getPriv() {
        return privada;
    }

    /**
     * Funcion dedicada a obtener la clave publica
     *
     * @return La clave publica
     */
    public Key getPub() {
        return publica;
    }

    /**
     * Función dedicada a devolver el nombre de los dicheros de clave
     *
     * @return El nombre (sin extensión) de los ficheros de clave
     */
    public String getfName() {
        return nombre;
    }

    /**
     * Función dedicada a generar un nuevo par de claves
     *
     * @param tipo El algoritmo que define a las claves. Podrá ser RSA o DSA
     * @param t    El tamaño de las claves.
     * @param pass Contraseña de cifrado de las claves
     * @return True si las claves se han generado con éxito, false en caso contrario
     */
    public boolean crearClaves(String tipo, String t, char[] pass) {

        try {
            System.out.println(tipo);
            kpg = KeyPairGenerator.getInstance(tipo);
        } catch (NoSuchAlgorithmException e1) {

            e1.printStackTrace();
        }

        int tamano = Integer.parseInt(t);
        System.out.println(tamano);

        kpg.initialize(tamano);
        keyP = kpg.generateKeyPair();

        publica = keyP.getPublic();
        privada = keyP.getPrivate();

        try {
            FileOutputStream fOut = new FileOutputStream(fPrivKey);
            ObjectOutputStream objetoSalida = new ObjectOutputStream(fOut);

            /*
             * Privada
             */
            PrivWrappedKey = cifrarClave(privada, pass);

            objetoSalida.writeObject(PrivWrappedKey);
            fOut.close();
            objetoSalida.close();

            /*
             * Publica
             */
            FileOutputStream fOut2 = new FileOutputStream(fPubKey);
            ObjectOutputStream objetoSalida2 = new ObjectOutputStream(fOut2);

            objetoSalida2.writeObject(publica);
            fOut2.close();
            objetoSalida2.close();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Función dedicada a cargar las claves desde un fichero. La carga se realizará
     * en los objetos Key correspondientes
     *
     * @param pass Contraseña de cifrado de claves
     * @return True si las claves se han cargado con éxito, false en caso contrario
     */
    public boolean cargarClaves(char[] pass) {

        try {
            /*
             * Clave publica
             */
            FileInputStream fis = new FileInputStream(fPubKey);

            ObjectInputStream in = new ObjectInputStream(fis);

            publica = (PublicKey) in.readObject();

            in.close();
            fis.close();

            tipo = publica.getAlgorithm();

            /*
             * Clave privada
             */
            FileInputStream fis2 = new FileInputStream(fPrivKey);
            ObjectInputStream in2 = new ObjectInputStream(fis2);
            PrivWrappedKey = (byte[]) in2.readObject();
            privada = (PrivateKey) descifrarClave(PrivWrappedKey, pass);

            in2.close();
            fis2.close();

            return true;
        } catch (Exception e) {

            return false;
        }
    }

    /**
     * Función dedicada a cifrar una clave mediante cifrado PBE
     *
     * @param key      Clave a cifrar
     * @param password Contraseña de cifrado de claves
     * @return La clave cifrada
     */
    public byte[] cifrarClave(Key key, char[] password)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidKeyException,
            InvalidAlgorithmParameterException, IllegalBlockSizeException {

        tipo = key.getAlgorithm();
        PBEParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);
        PBEKeySpec keySpec = new PBEKeySpec(password);
        SecretKeyFactory kf = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
        SecretKey passwordKey = kf.generateSecret(keySpec);
        Cipher c = Cipher.getInstance("PBEWithMD5AndDES");
        c.init(Cipher.WRAP_MODE, passwordKey, paramSpec);

        return c.wrap(key);
    }

    /**
     * Función dedicada a descifrar una clave previamente cifrada con cifrado PBE
     *
     * @param data     El array de bytes perteneciente a la clave cifrada
     * @param password Contraseña de cifrado de la clave
     * @return La clave descifrada
     */
    public Key descifrarClave(byte[] data, char[] password) throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeySpecException, InvalidKeyException, InvalidAlgorithmParameterException {

        Cipher c = Cipher.getInstance("PBEWithMD5AndDES");
        PBEParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);
        SecretKeyFactory kf = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
        PBEKeySpec keySpec = new PBEKeySpec(password);
        SecretKey passwordKey = kf.generateSecret(keySpec);
        c.init(Cipher.UNWRAP_MODE, passwordKey, paramSpec);

        return c.unwrap(data, tipo, Cipher.PRIVATE_KEY);
    }

}

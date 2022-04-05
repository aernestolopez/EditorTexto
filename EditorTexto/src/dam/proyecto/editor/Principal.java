package dam.proyecto.editor;

import javax.swing.JFrame;

/**
 * Clase principal del proyecto
 * @author Antonio Lopez
 *
 */
public class Principal {

	public static void main(String[] args) {
		//objeto creado para ejecutar una ventana
		Marco marco =new Marco();
		marco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//metodo para que el marco sea visible
		marco.setVisible(true);
	}

}

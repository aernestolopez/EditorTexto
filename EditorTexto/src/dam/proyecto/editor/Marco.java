package dam.proyecto.editor;

import javax.swing.JFrame;
/**
 * Clase que se encarga de crear el marco del editor
 * @author Antonio Lopez
 *
 */
public class Marco extends JFrame{
	public Marco() {
		//metodo que recibe 4 parametros los dos primeros son coordenadas
		//de donde queremos que aparezca la ventana.
		//Los ultimos dos ser�n el tama�o de la ventana.
		setBounds(300,300,300,300);
		setTitle("Editor de Texto");
	}

}

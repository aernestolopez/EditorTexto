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
		//Los ultimos dos serán el tamaño de la ventana.
		setBounds(300,300,300,300);
		//Metodo para añadir un titulo a la ventana
		setTitle("Editor de Texto");
		//Creacion de un objeto de tipo Panel
		Panel panel= new Panel();
		add(panel);
	}

}

package dam.proyecto.editor;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
/**
 * Clase utilizada para crear paneles al marco
 * @author Antonio Lopez
 *
 */
public class Panel extends JPanel{
	public Panel() {
		//---------------Area de Texto-------------------
		tPane =new JTabbedPane();
	
		//-----------------------------------------------
		CreaPanel();
		
		
		add(tPane);
	}
	//Metodo usado para crear una pestaña con un area de texto 
	public void CreaPanel() {
		//instanciamos el panel
		ventana=new JPanel();
		//instanciamos el area de texto
		areaTexto=new JTextPane();
		//Agregamos el area de texto en el contenedor
		ventana.add(areaTexto);
		//Agregamos la ventana que tiene en su interior el area de texto en la pestaña
		tPane.addTab("title", ventana);
	}
	
	
	//Clase usada para poder crear pestañas en nuestro editor
	private JTabbedPane tPane;
	//Clases utilizadas para poder crear areas de texto
	private JPanel ventana;
	private JTextPane areaTexto;
}

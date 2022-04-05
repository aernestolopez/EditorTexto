package dam.proyecto.editor;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
/**
 * Clase utilizada para crear paneles al marco
 * @author Antonio Lopez
 *
 */
public class Panel extends JPanel{
	public Panel() {

		tPane =new JTabbedPane();
	
		
		add(tPane);
	}
	//Clase usada para poder crear pestañas en nuestro editor
	private JTabbedPane tPane;
}

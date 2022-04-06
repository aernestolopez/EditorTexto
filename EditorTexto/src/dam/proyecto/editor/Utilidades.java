package dam.proyecto.editor;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
/**
 * Clase utilizada para poder añadir lineas de texto debajo de otras anteriormente leidas
 * @author Antonio Lopez
 *
 */

public class Utilidades {
	public static void append(String linea, JTextPane areaTexto) {
		try {
			Document doc= areaTexto.getDocument();
			doc.insertString(doc.getLength(), linea, null);
		}catch(BadLocationException exc) {
			exc.printStackTrace();
		}
	}

}

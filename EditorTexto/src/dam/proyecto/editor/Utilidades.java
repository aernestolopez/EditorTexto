package dam.proyecto.editor;

import java.awt.Container;
import java.awt.Image;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
/**
 * Clase utilizada para poder añadir lineas de texto debajo de otras anteriormente leidas
 * y demas utiliadades
 * @author Antonio Lopez
 *
 */

public class Utilidades {
	//------------------Agrega Texto-------------------------------
	public static void append(String linea, JTextPane areaTexto) {
		try {
			Document doc= areaTexto.getDocument();
			doc.insertString(doc.getLength(), linea, null);
		}catch(BadLocationException exc) {
			exc.printStackTrace();
		}
	}
	//------------------Mostrar Numeracion--------------------------
	
	public static void viewNumeracionInicio(boolean numeracion, JTextPane textArea, JScrollPane scroll) {
		if(numeracion) {
			scroll.setRowHeaderView(new TextLineNumber(textArea));
		}else {
			scroll.setRowHeaderView(null);
		}
	}
	public static void viewNumeracion(int contador, boolean numeracion, ArrayList<JTextPane> textArea, ArrayList<JScrollPane> scroll) {
		if(numeracion) {
			for(int i=0; i<contador; i++) {
				scroll.get(i).setRowHeaderView(new TextLineNumber(textArea.get(i)));
			}
		}else {
			for(int i=0; i<contador; i++) {
				scroll.get(i).setRowHeaderView(null);
			}
		}
	}
	
	//-----------------------------------------------------------------
	//-------------------Button----------------------------------------
	public static JButton addButton(URL url, Object objContenedor, String rotulo) {
		JButton button=new JButton(new ImageIcon(new ImageIcon(url).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
		button.setToolTipText(rotulo);
		((Container) objContenedor).add(button);
		return button;
	}
	//-----------------------------------------------------------------
}

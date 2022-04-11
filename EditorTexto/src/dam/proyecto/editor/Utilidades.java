package dam.proyecto.editor;

import java.awt.Container;
import java.awt.Image;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

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
	
	
	//--------------Tamaño Texto---------------------------------------
	public static void tamTexto(int tamanio, int contador, ArrayList<JTextPane> list) {
		for(int i=0; i<contador; i++) {
			//Selecciona todo el texto del area de texto
			list.get(i).selectAll();
			
			StyleContext sc= StyleContext.getDefaultStyleContext();
			//Para cambiar el tamaño del texto
			AttributeSet aset=sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.FontSize, tamanio);
			//aplica el tamaño del texto en el area de texto
			list.get(i).setCharacterAttributes(aset, false);
		}
	}
	
	
	//-----------------------------------------------------------------
}

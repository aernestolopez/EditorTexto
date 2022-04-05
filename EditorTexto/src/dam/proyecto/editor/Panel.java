package dam.proyecto.editor;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
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
		//---------------Menu----------------------------
		//Instanciamos los objetos del menu
		JPanel panelMenu= new JPanel();
		menu=new JMenuBar();
		//Instanciamos los componentes del menu
		archivo=new JMenu("Archivo");
		editar=new JMenu("Editar");
		seleccion=new JMenu("Selección");
		ver=new JMenu("Ver");
		apariencia=new JMenu("Apariencia");
		//Agregamos los componentes al menu
		menu.add(archivo);
		menu.add(editar);
		menu.add(seleccion);
		menu.add(ver);
		//eliminamos el menu.add(apariencia ya que pertenece al menu ver)
		
		//------------Elementos De Archivo----------------
		creaItem("Nuevo Archivo", "archivo", "nuevo");
		creaItem("Abrir Archivo", "archivo", "abrir");
		archivo.addSeparator();
		creaItem("Guardar", "archivo", "guardar");
		creaItem("Guardar Como", "archivo", "guardarcomo");
		//-----------------------------------------------
		
		//---------------Elementos de editar-------------
		creaItem("Deshacer", "editar", "");
		creaItem("Rehacer", "editar", "");
		editar.addSeparator();
		creaItem("Cortar", "editar", "");
		creaItem("Copiar", "editar", "");
		creaItem("Pegar", "editar", "");
		
		//-----------------------------------------------
		
		//----------------Elementos de seleccion---------
		creaItem("Seleccionar Todo", "seleccion", "");
		
		//-----------------------------------------------
		
		//----------------Elementos de ver---------------
		creaItem("Numeración", "ver", "");
		ver.add(apariencia);
		creaItem("Normal", "apariencia", "");
		creaItem("Dark", "apariencia", "");
		
		//-----------------------------------------------
		panelMenu.add(menu);
		
		
		//---------------Area de Texto-------------------
		
		tPane =new JTabbedPane();
	
		//-----------------------------------------------
		creaPanel();
		add(panelMenu);
		
		add(tPane);
	}
	//Metodo usado para crear acciones en el menu
	public void creaItem(String rotulo, String menu, String accion) {
		elementoItem=new JMenuItem(rotulo);
		
		if(menu.equals("archivo")) {
			archivo.add(elementoItem);
		}
		else if(menu.equals("editar")) {
			editar.add(elementoItem);
		}else if(menu.equals("seleccion")) {
			seleccion.add(elementoItem);
		}else if(menu.equals("ver")) {
			ver.add(elementoItem);
		}else if(menu.equals("apariencia")) {
			apariencia.add(elementoItem);
		}
	}
	
	//Metodo usado para crear una pestaña con un area de texto 
	public void creaPanel() {
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
	//Objetos utilizados para crear el menu
	private JMenuBar menu;
	private JMenu archivo, editar, seleccion, ver, apariencia;
	private JMenuItem elementoItem;
}

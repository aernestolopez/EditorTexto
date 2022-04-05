package dam.proyecto.editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
/**
 * Clase utilizada para crear paneles al marco
 * @author Antonio Lopez
 *
 */
public class Panel extends JPanel {
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
		creaItem("Abrir Reciente", "archivo", "abrirReciente");
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

		listFile = new ArrayList<File>();
		listAreaTexto=new ArrayList<JTextPane>();
		listScroll=new ArrayList<JScrollPane>();

		//-----------------------------------------------

		add(panelMenu);

		add(tPane);
	}
	//Metodo usado para crear acciones en el menu
	public void creaItem(String rotulo, String menu, String accion) {
		elementoItem=new JMenuItem(rotulo);

		if(menu.equals("archivo")) {
			archivo.add(elementoItem);
			if(accion.equals("nuevo")) {
				elementoItem.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e) {
						creaPanel();
					}
				});
			}
			else if(accion.equals("abrir")) {
				elementoItem.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						creaPanel();
						//Instanciamos el objeto para poder usar la opcion de abrir archivo
						JFileChooser selectorArchivos = new JFileChooser();
						//Nos permitirá seleccionar archivos y directorios
						selectorArchivos.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
						int resultado=selectorArchivos.showOpenDialog(listAreaTexto.get(tPane.getSelectedIndex()));

						if(resultado==JFileChooser.APPROVE_OPTION) {
							try {
								boolean existePath=false;
								for(int i=0; i<tPane.getTabCount(); i++) {
									File f=selectorArchivos.getSelectedFile();
									if(listFile.get(i).getPath().equals(f.getPath())) existePath=true;

								}
								if(!existePath) {
									File archivo=selectorArchivos.getSelectedFile();
									listFile.set(tPane.getSelectedIndex(), archivo);

									FileReader entrada=new FileReader(
											listFile.get(tPane.getSelectedIndex()).getPath());

									BufferedReader miBuffer=new BufferedReader(entrada);
									String linea= "";
									
									String titulo= listFile.get(tPane.getSelectedIndex()).getName();
									//El titulo se le agrega a la pestaña del panel que se crea
									//nuestra area de texto, lugar donde ira el texto del archivo que el usuario ha seleccionado
									tPane.setTitleAt(tPane.getSelectedIndex(), titulo);
								
								}
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}



					}

				});
			}
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
		listFile.add(new File(""));
		listAreaTexto.add(new JTextPane());
		listScroll.add(new JScrollPane(listAreaTexto.get(contadorPanel)));


		//Agregamos el area de texto en el contenedor
		ventana.add(listScroll.get(contadorPanel));
		//Agregamos la ventana que tiene en su interior el area de texto en la pestaña
		tPane.addTab("title", ventana);

		tPane.setSelectedIndex(contadorPanel);
		contadorPanel++;
		existePanel=true;
	}
	//Contara cuantos paneles se han creado
	private int contadorPanel=0;
	//Dira si ya se ha creado un panel
	private boolean existePanel=false;

	//Clase usada para poder crear pestañas en nuestro editor
	private JTabbedPane tPane;
	//Clases utilizadas para poder crear areas de texto
	private JPanel ventana;
	//private JTextPane areaTexto;
	private ArrayList<JTextPane> listAreaTexto;
	private ArrayList<JScrollPane> listScroll;
	private ArrayList<File> listFile;
	//Objetos utilizados para crear el menu
	private JMenuBar menu;
	private JMenu archivo, editar, seleccion, ver, apariencia;
	private JMenuItem elementoItem;
}

package dam.proyecto.editor;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultEditorKit;
import javax.swing.undo.UndoManager;
/**
 * Clase utilizada para crear paneles al marco
 * @author Antonio Lopez
 *
 */
public class Panel extends JPanel {
	public Panel() {	
		setLayout(new BorderLayout());

		//---------------Menu----------------------------
		//Instanciamos los objetos del menu
		JPanel panelMenu= new JPanel();
		items = new JMenuItem[8];
		
		panelMenu.setLayout(new BorderLayout());
		menu=new JMenuBar();
		//Instanciamos los componentes del menu
		archivo=new JMenu("Archivo");
		editar=new JMenu("Editar");
		seleccion=new JMenu("Selecci?n");
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
		creaItem("Guardar Como", "archivo", "guardarComo");
		//-----------------------------------------------

		//---------------Elementos de editar-------------
		creaItem("Deshacer", "editar", "deshacer");
		creaItem("Rehacer", "editar", "rehacer");
		editar.addSeparator();
		creaItem("Cortar", "editar", "cortar");
		creaItem("Copiar", "editar", "copiar");
		creaItem("Pegar", "editar", "pegar");

		//-----------------------------------------------

		//----------------Elementos de seleccion---------
		creaItem("Seleccionar Todo", "seleccion", "seleccion");

		//-----------------------------------------------

		//----------------Elementos de ver---------------
		creaItem("Numeraci?n", "ver", "numeracion");
		creaItem("Contar Palabras", "ver", "contarpalabras");
		//-----------------------------------------------


		panelMenu.add(menu, BorderLayout.NORTH);


		//---------------Area de Texto-------------------

		tPane =new JTabbedPane();

		listFile = new ArrayList<File>();
		listAreaTexto=new ArrayList<JTextPane>();
		listScroll=new ArrayList<JScrollPane>();
		listManager=new ArrayList<UndoManager>();
		
		Utilidades.desactivaItem(items);
		//-----------------------------------------------

		//-----------------------Barra Herramientas------
		herramientas=new JToolBar(JToolBar.VERTICAL);
		url=Principal.class.getResource("/dam/proyecto/img/x.png");
		Utilidades.addButton(url, herramientas, "Cerrar Pesta?a Actual").addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int seleccion=tPane.getSelectedIndex();
				if(seleccion!=-1) {
					//Si existen pesta?as abiertas eliminamos la que tengamos seleccionada
					listScroll.get(tPane.getSelectedIndex()).setRowHeader(null);
					tPane.remove(seleccion);
					listScroll.remove(seleccion);
					listManager.remove(seleccion);
					listFile.remove(seleccion);

					contadorPanel--;
					//si devuelve -1 quiere decir que no existen paneles creados
					if(tPane.getSelectedIndex()==-1) {
						existePanel=false;
						Utilidades.desactivaItem(items);
					}
				}
			}

		});

		url=Principal.class.getResource("/dam/proyecto/img/mas.png");
		Utilidades.addButton(url, herramientas,"Nuevo Archivo").addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				creaPanel();
				if(existePanel) Utilidades.activaItems(items);
			}

		});



		//-----------------------------------------------

		//-----------------Panel Extra-------------------
		panelExtra=new JPanel();
		panelExtra.setLayout(new BorderLayout());

		JPanel panelIzquierdo = new JPanel();



		panelExtra.add(panelIzquierdo);


		JPanel panelCentro =new JPanel();
		slider=new JSlider(8,38,14);
		//La separaci?n entre las barritas sera de 6 en 6
		slider.setMajorTickSpacing(6);
		//La separacion entre las barras peque?as es de 2 en 2
		slider.setMinorTickSpacing(2);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				Utilidades.tamTexto(slider.getValue(), contadorPanel, listAreaTexto);
				
			}
			
		});
		
		
		
		
		panelCentro.add(slider);

		panelExtra.add(panelCentro);

		//-----------------------------------------------
		
		//-----------------Menu Emergente----------------
		menuEmergente=new JPopupMenu();
		JMenuItem cortar=new JMenuItem("Cortar");
		JMenuItem copiar=new JMenuItem("Copiar");
		JMenuItem pegar=new JMenuItem("Pegar");
		
		cortar.addActionListener(new DefaultEditorKit.CutAction());
		copiar.addActionListener(new DefaultEditorKit.CopyAction());
		pegar.addActionListener(new DefaultEditorKit.PasteAction());
		
		menuEmergente.add(cortar);
		menuEmergente.add(copiar);
		menuEmergente.add(pegar);
		
		//-----------------------------------------------
		
		
		add(panelMenu,BorderLayout.NORTH);
		add(tPane, BorderLayout.CENTER);
		add(herramientas, BorderLayout.WEST);
		add(panelExtra, BorderLayout.SOUTH);
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
						if(existePanel) Utilidades.activaItems(items);
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
						//Nos permitir? seleccionar archivos y directorios
						selectorArchivos.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
						int resultado=selectorArchivos.showOpenDialog(listAreaTexto.get(tPane.getSelectedIndex()));

						if(resultado==JFileChooser.APPROVE_OPTION) {
							if(existePanel) Utilidades.activaItems(items);
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
									//El titulo se le agrega a la pesta?a del panel que se crea
									//nuestra area de texto, lugar donde ira el texto del archivo que el usuario ha seleccionado
									tPane.setTitleAt(tPane.getSelectedIndex(), titulo);

									while(linea !=null) {
										//Lee linea a linea cada linea del archivo y es almacenado en el String
										linea=miBuffer.readLine();
										if(linea!=null) Utilidades.append(linea+"\n", listAreaTexto.get(tPane.getSelectedIndex()));
									}

								}else {
									//si la ruta del archivo ya existe y esta abierto vamos a recorrer todos los paneles
									//para saber cual tiene el path del fichero y seleccionar este
									
									for(int i=0; i<tPane.getTabCount();i++){
										File f= selectorArchivos.getSelectedFile();
										if(listFile.get(i).getPath().equals(f.getPath())) {
											//seleccionamos el panel que ya tiene abierto el archivo
											tPane.setSelectedIndex(i);//pasamos por parametro la posicion del panel que tiene el path



											listAreaTexto.remove(tPane.getTabCount()-1);
											listScroll.remove(tPane.getTabCount()-1);
											listFile.remove(tPane.getTabCount()-1);
											tPane.remove(tPane.getTabCount()-1);
											contadorPanel--;
											break;
										}
									}

								}
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}else {
							//Si se oprime el boton cancelar en la ventana de abrir archivo eliminamos el panel del area de texto que se crea por defecto

							int seleccion=tPane.getSelectedIndex();
							if(seleccion !=-1) {
								existePanel=false;
								Utilidades.desactivaItem(items);
								listAreaTexto.remove(tPane.getTabCount()-1);
								listScroll.remove(tPane.getTabCount()-1);
								listFile.remove(tPane.getTabCount()-1);
								tPane.remove(tPane.getTabCount()-1);
								contadorPanel--;
							}

						}



					}

				});
			}
			else if(accion.equals("guardar")) {
				items[0]=elementoItem;
				elementoItem.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						//Guardar Como si el archivo no existe
						if(listFile.get(tPane.getSelectedIndex()).getPath().equals("")) {
							JFileChooser guardarArchivos=new JFileChooser();
							int opc=guardarArchivos.showSaveDialog(null);

							if(opc==JFileChooser.APPROVE_OPTION) {
								File archivo= guardarArchivos.getSelectedFile();
								listFile.set(tPane.getSelectedIndex(), archivo);
								tPane.setTitleAt(tPane.getSelectedIndex(), archivo.getName());

								try {
									FileWriter fw=new FileWriter(listFile.get(tPane.getSelectedIndex()).getPath());
									String texto=listAreaTexto.get(tPane.getSelectedIndex()).getText();

									for(int i=0; i<texto.length();i++) {
										fw.write(texto.charAt(i));
									}
									fw.close();

								} catch (IOException e1) {
									e1.printStackTrace();
								}
							}
						}else {
							try {
								FileWriter fw=new FileWriter(listFile.get(tPane.getSelectedIndex()).getPath());
								String texto=listAreaTexto.get(tPane.getSelectedIndex()).getText();
								//recorre todos los caracteres del archivp
								for(int i=0; i<texto.length();i++) {
									fw.write(texto.charAt(i));
								}
								fw.close();

							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}

					}

				});
			}
			else if(accion.equals("guardarComo")) {
				items[1]=elementoItem;
				elementoItem.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						JFileChooser guardarArchivos=new JFileChooser();
						int opc=guardarArchivos.showSaveDialog(null);

						if(opc==JFileChooser.APPROVE_OPTION) {
							File archivo= guardarArchivos.getSelectedFile();
							listFile.set(tPane.getSelectedIndex(), archivo);
							tPane.setTitleAt(tPane.getSelectedIndex(), archivo.getName());

							try {
								FileWriter fw=new FileWriter(listFile.get(tPane.getSelectedIndex()).getPath());
								String texto=listAreaTexto.get(tPane.getSelectedIndex()).getText();

								for(int i=0; i<texto.length();i++) {
									fw.write(texto.charAt(i));
								}
								fw.close();

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
			if(accion.equals("deshacer")) {
				items[2]=elementoItem;
				elementoItem.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						if(listManager.get(tPane.getSelectedIndex()).canUndo()) listManager.get(tPane.getSelectedIndex()).undo();
					}

				});
			}else if(accion.equals("rehacer")) {
				items[3]=elementoItem;
				elementoItem.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						if(listManager.get(tPane.getSelectedIndex()).canRedo()) listManager.get(tPane.getSelectedIndex()).redo();

					}

				});

			}else if(accion.equals("cortar")) {
				items[4]=elementoItem;
				elementoItem.addActionListener(new DefaultEditorKit.CutAction());

			}else if(accion.equals("copiar")) {
				items[5]=elementoItem;
				elementoItem.addActionListener(new DefaultEditorKit.CopyAction());
			}else if(accion.equals("pegar")) {
				items[6]=elementoItem;
				elementoItem.addActionListener(new DefaultEditorKit.PasteAction());
			}
		}else if(menu.equals("seleccion")) {
			seleccion.add(elementoItem);
			if(accion.equals("seleccion")) {
				items[7]=elementoItem;
				elementoItem.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						listAreaTexto.get(tPane.getSelectedIndex()).selectAll();

					}

				});
			}

		}else if(menu.equals("ver")) {
			ver.add(elementoItem);
			if(accion.equals("numeracion")) {
				elementoItem.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						numeracion=!numeracion;

						Utilidades.viewNumeracion(contadorPanel, numeracion, listAreaTexto, listScroll);

					}

				});
			}
		}
	}

	//Metodo usado para crear una pesta?a con un area de texto 
	public void creaPanel() {
		//instanciamos el panel
		ventana=new JPanel();
		ventana.setLayout(new BorderLayout());
		listFile.add(new File(""));
		listAreaTexto.add(new JTextPane());
		listScroll.add(new JScrollPane(listAreaTexto.get(contadorPanel)));
		//Sirve para rastrear los cambios del area de texto
		listManager.add(new UndoManager());
		listAreaTexto.get(contadorPanel).getDocument().addUndoableEditListener(listManager.get(contadorPanel));
		
		listAreaTexto.get(contadorPanel).setComponentPopupMenu(menuEmergente);

		//Agregamos el area de texto en el contenedor
		ventana.add(listScroll.get(contadorPanel), BorderLayout.CENTER );
		//Agregamos la ventana que tiene en su interior el area de texto en la pesta?a
		tPane.addTab("Nuevo Archivo", ventana);

		Utilidades.viewNumeracionInicio(numeracion, listAreaTexto.get(contadorPanel),listScroll.get(contadorPanel));
		tPane.setSelectedIndex(contadorPanel);
		contadorPanel++;
		existePanel=true;
	}
	private boolean numeracion=false;
	//Contara cuantos paneles se han creado
	private int contadorPanel=0;
	//Dira si ya se ha creado un panel
	private boolean existePanel=false;

	//Clase usada para poder crear pesta?as en nuestro editor
	private JTabbedPane tPane;
	//Clases utilizadas para poder crear areas de texto
	private JPanel ventana;
	private JPanel panelExtra;
	private ArrayList<JTextPane> listAreaTexto;
	private ArrayList<JScrollPane> listScroll;
	private ArrayList<File> listFile;
	//Clase para hacer la funcion de deshacer y rehacer
	private ArrayList<UndoManager> listManager;
	//Objetos utilizados para crear el menu
	private JMenuBar menu;
	private JMenu archivo, editar, seleccion, ver, apariencia;
	private JMenuItem elementoItem;
	private JToolBar herramientas;
	private URL url;
	private JSlider slider;
	private JPopupMenu menuEmergente;
	private JMenuItem items[];
}

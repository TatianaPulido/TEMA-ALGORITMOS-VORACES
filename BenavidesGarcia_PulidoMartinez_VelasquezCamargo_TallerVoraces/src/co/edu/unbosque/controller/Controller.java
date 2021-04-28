package co.edu.unbosque.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import co.edu.unbosque.model.AgenteViajero;
import co.edu.unbosque.model.AlgoritmoPrim;
import co.edu.unbosque.model.Arista;
import co.edu.unbosque.model.AsignacionTareas;
import co.edu.unbosque.model.Nodo;
import co.edu.unbosque.model.Vertice;
import co.edu.unbosque.view.Vista;

/**
 * Esta clase permite la interacción del usuario con las demas funcionalidades
 * de la aplicación, atravez de la vista. Incluidos los métodos de esta clase
 * asi como los métodos de la clase modelo.
 * 
 * @author Daniela Benavides
 * @author Tatiana Pulido
 * @author Juan Pablo Velasquez
 */
public class Controller {
	Vista vista;
	AgenteViajero agenteViajero;
	AsignacionTareas asignacionTareas;
	private int[][] G;
	private int[][] t;
	private boolean[][] in;
	private boolean[][] temp;
	private int n;
	private int mincost = 0;
	private int k, l, num_ed = 0;

	/**
	 * Este es el método constructor el cual se le asigna la inicialización de los
	 * atributos y objectos. De esta manera el objecto es creado con un valor
	 * inicial. Este método se llama automaticamente cuando se crea el objeto.
	 */
	public Controller() {
		agenteViajero = new AgenteViajero();
		asignacionTareas = new AsignacionTareas();
		vista = new Vista();
		menu();

	}

	/**
	 * Este método permite a usuario interactuar entre las diferentes opciones del
	 * menu o dar por teminada la aplicación.<br>
	 * <b>pre</b> Se debe generar la vista con la que va a interactuar el
	 * usuario.<br>
	 * <b>post</b> El usuario puede elegir una opción del menu.<br>
	 */
	public void menu() {
		int opcion = -1;
		while (opcion != 0) {
			try {
				opcion = vista.mostrarMenu();
				if (0 <= opcion && opcion <= 4) {
					if (opcion == 1) {
						algoritmoPrim();
					} else if (opcion == 2) {
						vista.mostrarMensaje("--Algoritmo de Kruskal--");
						boolean vKruskal = false;
						while (!vKruskal) {
							vista.mostrarMensaje("\n\nDe el número de vértices: ");
							n = vista.leerNumero("");
							if ((n > 0)) {
								vKruskal = true;
							} else {
								vista.mostrarMensaje("Número de vértices incorrecto, ingreselo nuevamente.");
							}
						}
						mKruskal(n);
						vista.mostrarMensaje("\n\nSolucion : \n");
						Kruskals();
						vista.mostrarMensaje("\n\n\nEl peso del árbol mínimo es de: " + mincost + "\n");
					} else if (opcion == 3) {
						conexionesCiudades(ciudades());
						mostrarResultadoViajero();
					} else if (opcion == 4) {
						conexionesTareas(trabajadores());
						mostrarResultadoAsignacionTareas();
					} else {
						vista.mostrarMensaje("Usted decidió salir.");
					}
				} else {
					vista.mostrarMensaje("Opción inválida.");
					opcion = -1;
				}
			} catch (NumberFormatException e) {
				vista.mostrarMensaje("Opción inválida.");
				opcion = -1;
			}

		}
	}

	/**
	 * Este método le permite al usuario ingresar el numero de ciudades que desea
	 * visitar asi como el nombre de cada ciudad. <br>
	 * <b>pre</b> El número de ciudades debe ser mayor a 1. El nombre de la ciudad
	 * no se debe repetir.<br>
	 * <b>post</b> Se obtiene la lista de las ciudades que el viajero desea
	 * visitar.<br>
	 * 
	 * @return Lista de ciudades que el viajero va a visitar.
	 */
	public ArrayList<Nodo> ciudades() {
		int nCiudades = 0;
		boolean vCiudades = false;
		ArrayList<Nodo> listaCiudades = new ArrayList<>();
		while (!vCiudades) {
			try {
				nCiudades = vista.leerNumero("Ingrese el número de ciudades que va a visitar.");
				if (nCiudades > 1) {
					vCiudades = true;
					int contadorC = 0;
					Pattern rango = Pattern.compile("[a-zA-Z1-9]+");

					while (contadorC != nCiudades) {
						String lugar = vista.pedirCiudad(contadorC + 1);
						Matcher cadenaValida = rango.matcher(lugar);

						if (cadenaValida.matches()) {
							boolean esta = false;
							for (int i = 0; i < listaCiudades.size(); i++) {
								if (listaCiudades.get(i).getNombre().equalsIgnoreCase(lugar)) {
									esta = true;
								}
							}
							if (esta) {
								vista.mostrarMensaje("La ciudad ya se encuentra ingrese una diferente.");
							} else {
								contadorC++;
								agenteViajero.agregarCiudad(lugar);
								listaCiudades = agenteViajero.getListaCiudades();

							}
						} else {
							vista.mostrarMensaje(
									"Ingrese por lo menos una letra o número, no ingrese caracteres especiales.");
						}
					}
				} else {
					vista.mostrarMensaje("Cantidad de ciudades debe ser mayor a 1.");
				}
			} catch (NumberFormatException e) {
				vista.mostrarMensaje("Cantidad de ciudades invalido");
			}

		}

		return listaCiudades;

	}

	/**
	 * Este método le permite al usuario ingresar el costo del viaje entre dos
	 * ciudades. Se tiene en cuenta que el costo de viaje entre una ciudad A y una
	 * ciudad B es el mismo que si se viaja de la ciudad B a la ciudad A. <br>
	 * <b>pre</b> El costo del viaje entre las dos ciudades debe ser mayor a 0.<br>
	 * <b>post</b> Todas las ciudades deben estar conectadas entre si.<br>
	 * 
	 * @param listaCiudades Este parametro corresponde a una lista de nodos, cada
	 *                      nodo corresponde a una ciudad. listaCiudades != null,
	 *                      listaCiudades != "".
	 */
	public void conexionesCiudades(ArrayList<Nodo> listaCiudades) {
		String ciudadOrigen = "";
		String ciudadDestino = "";

		for (int i = 0; i < listaCiudades.size(); i++) {
			ciudadOrigen = listaCiudades.get(i).getNombre();
			for (int j = 0; j < listaCiudades.size(); j++) {
				ciudadDestino = listaCiudades.get(j).getNombre();
				if (!ciudadOrigen.equalsIgnoreCase(ciudadDestino)) {
					boolean vCosto = false;
					while (!vCosto) {
						try {
							if (agenteViajero.rutaNoExiste(i, ciudadDestino)) {
								double costo = vista.leerPeso(
										"Ingrese el costo de la ruta " + ciudadOrigen + " - " + ciudadDestino);
								if (costo > 0) {
									vCosto = true;
									agenteViajero.agregarRuta(i, ciudadOrigen, ciudadDestino, costo);
									agenteViajero.agregarRuta(j, ciudadDestino, ciudadOrigen, costo);
								} else {
									vista.mostrarMensaje("Costo de la ruta invalido, ingreselo nuevamente.");
								}
							} else {
								vCosto = true;
							}
						} catch (NumberFormatException e) {
							vista.mostrarMensaje("Costo de la ruta invalido, ingreselo nuevamente.");
						}

					}

				}
			}
		}
	}

	/**
	 * Este método le permite al usuario definir por que ciudad desea comenzar el
	 * viaje. <br>
	 * <b>pre</b> El número de ciudades debe ser mayor a 1.<br>
	 * <b>post</b> El número elegido pos el usuario correspondera a la posición en
	 * la que se encuentra la ciudad dentro de las lista.<br>
	 * 
	 * @return Posición en la lista de ciudades desde donde se va a comenzar el
	 *         viaje.
	 */
	public int nodoInicio() {
		ArrayList<Nodo> listaCiudades = agenteViajero.getListaCiudades();
		int cantidadCiudades = listaCiudades.size();
		int valorInicio = -1;
		boolean valInicio = false;
		while (!valInicio) {
			try {
				String mensaje = "Ingrese el número de ciudad por la que desea comenzar el recorrido: \n";
				for (int j = 0; j < cantidadCiudades; j++) {
					mensaje += j + ". " + listaCiudades.get(j).getNombre();
					mensaje += (j != (cantidadCiudades - 1)) ? "\n" : "";
				}
				valorInicio = vista.leerNumero(mensaje);
				if (valorInicio >= 0 && valorInicio < cantidadCiudades) {
					valInicio = true;
				} else {
					vista.mostrarMensaje("Número de ciudad invalido, ingreselo nuevamente.");
				}
			} catch (NumberFormatException e) {
				vista.mostrarMensaje("Número de ciudad invalido, ingreselo nuevamente.");
			}

		}
		return valorInicio;
	}

	/**
	 * Este método calcula y muestra la ruta de menor peso desde la ciudad de origen
	 * hasta volver a ella pasando por todad las ciudades una sola vez ademas,
	 * muestra cada una de las rutas con sus respectivos pesos.<br>
	 * <b>pre</b> El número de ciudades debe ser mayor a 1 y todas las ciudades
	 * deben estar conectadas entre si. El número elegido por el usuario
	 * correspondera a la posición en la que se encuentra la ciudad dentro de las
	 * lista<br>
	 * <b>post</b> Calcula la ruta de menor peso ademas y su costo.<br>
	 * 
	 */
	public void mostrarResultadoViajero() {
		ArrayList<String> mejorRuta = agenteViajero.algoritmoVoraz(nodoInicio());
		ArrayList<Nodo> listaCiudades = agenteViajero.getListaCiudades();
		vista.mostrarTabla("ORIGEN", "DESTINO", "PESO");
		for (int i = 0; i < listaCiudades.size(); i++) {
			ArrayList<Arista> listaConexiones = listaCiudades.get(i).getLista_rutas();
			for (int j = 0; j < listaConexiones.size(); j++) {
				vista.mostrarTabla(listaConexiones.get(j).getNodo_origen(), listaConexiones.get(j).getNodo_destino(),
						String.valueOf(listaConexiones.get(j).getPeso()));
			}
		}
		String ruta = "La mejor ruta es --->  ";
		for (int i = 0; i < mejorRuta.size() - 1; i++) {
			ruta += mejorRuta.get(i);
			ruta += (i != (mejorRuta.size() - 2)) ? " - " : "";

		}
		vista.mostrarMensaje(ruta);
		vista.mostrarMensaje("El costo de esta ruta es: " + mejorRuta.get(mejorRuta.size() - 1) + "\n");
	}

	/**
	 * Este método le permite al usuario ingresar el numero de trabajadors que desea
	 * asi como el nombre de cada uno. <br>
	 * <b>pre</b> La cantidad de trabajadores debe ser mayor a 0. El nombre del
	 * trabajador no se debe repetir.<br>
	 * <b>post</b> Se obtiene la lista de los trabajadores<br>
	 * 
	 * @return Lista de trabajadores.
	 */
	public ArrayList<Nodo> trabajadores() {
		int nTrabajadores = 0;
		boolean vTrabajadores = false;
		ArrayList<Nodo> listaTrabajadores = new ArrayList<>();
		while (!vTrabajadores) {
			try {
				nTrabajadores = vista.leerNumero("Ingrese el número de trabajadores");
				if (nTrabajadores > 0) {
					vTrabajadores = true;
					int contadorC = 0;
					Pattern rango = Pattern.compile("[a-zA-Z]+");

					while (contadorC != nTrabajadores) {
						String nombre = vista.pedirTrabajador(contadorC + 1);
						Matcher cadenaValida = rango.matcher(nombre);

						if (cadenaValida.matches()) {
							boolean esta = false;
							for (int i = 0; i < listaTrabajadores.size(); i++) {
								if (listaTrabajadores.get(i).getNombre().equalsIgnoreCase(nombre)) {
									esta = true;
								}
							}
							if (esta) {
								vista.mostrarMensaje("El trabajador ya se encuentra, ingrese un nombre diferente..");
							} else {
								contadorC++;
								asignacionTareas.agregarTrabajador(nombre);
								listaTrabajadores = asignacionTareas.getListaTrabajadores();

							}
						} else {
							vista.mostrarMensaje(
									"Ingrese por lo menos una letra, no ingrese caracteres especiales ni numeros.");
						}
					}
				} else {
					vista.mostrarMensaje("Cantidad de trabajadores debe ser mayor a 0.");
				}
			} catch (NumberFormatException e) {
				vista.mostrarMensaje("Cantidad de trabajadores invalido");
			}

		}

		return listaTrabajadores;

	}

	/**
	 * Este método le permite al usuario ingresar el costo que siginfica para un
	 * trabajador realizar una tarea. <br>
	 * <b>pre</b> El costo de la tarea debe ser mayor a 0.<br>
	 * <b>post</b> Todos los trabadores se relacionan con todas las tareas.<br>
	 * 
	 * @param listaTrabajadores Este parametro corresponde a una lista de nodos,
	 *                          cada nodo corresponde a un trabajador.
	 *                          listaTrabajadores != null, listaTrabajadores != "".
	 */
	public void conexionesTareas(ArrayList<Nodo> listaTrabajadores) {
		Pattern rango = Pattern.compile("[a-zA-Z]+");
		int numeroTareas = listaTrabajadores.size();
		ArrayList<String> listaTareas = new ArrayList<>();
		for (int i = 0; i < listaTrabajadores.size(); i++) {
			boolean vTarea = false;
			while (!vTarea) {
				String tareas = vista.pedirTarea(i + 1);
				Matcher cadenaValida = rango.matcher(tareas);

				if (cadenaValida.matches()) {
					listaTareas.add(tareas);
					vTarea = true;
				} else {
					vista.mostrarMensaje(
							"Ingrese por lo menos una letra, no ingrese caracteres especiales ni numeros.");
				}
			}
		}

		String nombreTrabajador = "";
		String nombreTarea = "";

		for (int i = 0; i < listaTrabajadores.size(); i++) {
			nombreTrabajador = listaTrabajadores.get(i).getNombre();
			for (int j = 0; j < listaTareas.size(); j++) {
				nombreTarea = listaTareas.get(j);
				boolean vCosto = false;
				while (!vCosto) {
					try {
						double costo = vista.leerPeso("Ingrese el costo de la tarea " + nombreTarea
								+ " para el trabajador " + nombreTrabajador);
						if (costo > 0) {
							vCosto = true;
							asignacionTareas.agregarTareas(i, nombreTrabajador, nombreTarea, costo);
						} else {
							vista.mostrarMensaje("Costo de la ruta invalido, ingreselo nuevamente.");
						}
					} catch (NumberFormatException e) {
						vista.mostrarMensaje("Costo de la ruta invalido, ingreselo nuevamente.");
					}

				}

			}
		}
	}

	/**
	 * Este método calcula y muestra cual es la menor tarea que le corresponde a
	 * cada trabajador, se tiene en cuenta que los trabajadores no pueden repetir
	 * una tarea.<br>
	 * <b>pre</b> El número de tareas debe ser igual al número de trabajadores.<br>
	 * <b>post</b> Se muestran los costos de cada trabajador respecto a cada tarea y
	 * que tarea le corresponde a cada trabajador.<br>
	 */
	public void mostrarResultadoAsignacionTareas() {
		HashMap<String, String> mejorRuta = asignacionTareas.algoritmoVoraz();
		ArrayList<Nodo> listaCiudades = asignacionTareas.getListaTrabajadores();
		vista.mostrarTabla("TRABAJADOR", "TAREA", "PESO");
		for (int i = 0; i < listaCiudades.size(); i++) {
			ArrayList<Arista> listaConexiones = listaCiudades.get(i).getLista_rutas();
			for (int j = 0; j < listaConexiones.size(); j++) {
				vista.mostrarTabla(listaConexiones.get(j).getNodo_origen(), listaConexiones.get(j).getNodo_destino(),
						String.valueOf(listaConexiones.get(j).getPeso()));
			}
		}

		vista.mostrarMensaje("La mejor tarea para cada trabajador es\n");
		vista.mostrarTrabajadores("TRABAJADOR", "TAREA");
		for (String key : mejorRuta.keySet()) {
			vista.mostrarTrabajadores(key, mejorRuta.get(key));
		}
	}

	// Implementacion del algoritmo de kruskal

	/**
	 * Este metodo obtiene como parametro el numero de vetices y sucesivamente va
	 * pidiendo el valor de cada arista
	 * 
	 * @param n Numero de vertices que va a tener el arbol
	 */
	public void mKruskal(int n) {
		try {
			this.G = new int[n + 1][n + 1];
			this.in = new boolean[n + 1][n + 1];
			this.t = new int[n + 1][3];

			vista.mostrarMensaje("\nCual es el valor de la arista:\n");
			for (int i = 1; i <= n; i++)
				for (int j = 1; j <= n; j++) {
					in[i][j] = in[j][i] = false;
					if ((i != j) && (i < j)) {
						vista.mostrarMensaje("Del " + i + " al " + j + ": ");

						G[j][i] = G[i][j] = vista.leerNumero("");

						if (G[i][j] == 0)
							G[j][i] = G[i][j] = 7001;
					}
					if (i == j)
						G[i][j] = 7001;
				}
		} catch (NumberFormatException e) {
			vista.mostrarMensaje(
					"\n¡Solo se aceptan numeros enteros verifique bien lo ingresado, y vuelva a ejecutar el programa!\n");
		}
	}

	/**
	 * Implementa el algoritmo de Kruskals
	 */
	public void Kruskals() {
		for (int i = 1; i <= n; i++) {
			getMinKL();
			if (k == l) {
				break;
			}
			vista.mostrarMensaje(l + "-" + k);
			if (formscycle(i - 1)) {
				vista.mostrarMensaje(" --> Ya estan en el mismo conjunto\n");
				i--;
				continue;
			} else {
				vista.mostrarMensaje("");
			}
			mincost = mincost + G[k][l];
			num_ed = (isPresent(i, k)) ? num_ed : num_ed + 1;
			num_ed = (isPresent(i, l)) ? num_ed : num_ed + 1;

			t[i][1] = l;
			t[i][2] = k;
			if (num_ed >= n) {
				if (allconnect(i)) {
					return;
				}
			}

		}
		vista.mostrarMensaje("\n¡No hay solucion!\n"); // (1)
	}

	/**
	 * metodo que sirve para determinar el peso minimo del arbol
	 */
	public void getMinKL() {
		int k1 = 1, l1 = 1;
		for (int i = 1; i <= n; i++)
			for (int j = 1; j <= n; j++) {
				if ((i != j) && (i < j)) {
					if ((G[i][j] < G[k1][l1]) && G[i][j] != 0 && in[j][i] == false) {
						k1 = i;
						l1 = j;
					}
				}
			}
		if (G[k1][l1] != 0) {
			k = k1;
			l = l1;
			in[k][l] = in[l][k] = true;
		}
	}

	/**
	 * Mira que las aristas no formen un ciclo
	 * 
	 * @param i donde se encuentra actualmente en el vertice
	 * @return true or false
	 */
	public boolean formscycle(int i) {
		if (isPresent(i, k) && isPresent(i, l)) {
			temp = new boolean[n + 1][n + 1];
			for (int a = 1; a <= n; a++)
				for (int b = 1; b <= n; b++)
					temp[a][b] = temp[b][a] = false;

			if (can_reach(k, l, i))
				return true;
		}
		return false;
	}

	/**
	 * Determina actualmente donde se encuentra, en el ventice
	 * 
	 * @param i   Donde se encuentra actualmente en el vertice
	 * @param val Valor de la arista
	 * @return true o false
	 */
	public boolean isPresent(int i, int val) {
		for (int o = 1; o <= i; o++) {
			if ((val == t[o][1]) || (val == t[o][2])) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @param i Valor en donde se encuenta en el vertice
	 * @return True o False dependiendo si se encuentra
	 */
	public boolean allconnect(int i) {
		for (int c = 2; c <= n; c++) {
			temp = new boolean[n + 1][n + 1];
			for (int a = 1; a <= n; a++) {
				for (int b = 1; b <= n; b++) {
					temp[a][b] = temp[b][a] = false;
				}
			}
			if (can_reach(1, c, i) == false) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 
	 * Determina si puede alcanzar otro valor entre vertices
	 * 
	 * @param k valor vetice
	 * @param l valor vertice
	 * @param i valor vetice
	 * @return true o false
	 */
	public boolean can_reach(int k, int l, int i) {
		temp[k][l] = temp[l][k] = true; // 4
		for (int o = 1; o <= i; o++) { // (2n + 2)
			if (((k == t[o][1]) && (l == t[o][2])) || ((l == t[o][1]) && (k == t[o][2]))) {// (n + 8)
				return true; // (n + 1)
			}
			if ((k == t[o][1]) && !(temp[t[o][2]][l])) { // (n + 4)
				if (can_reach(t[o][2], l, i) == true) {
					return true; //
				}
			} else if ((k == t[o][2]) && !(temp[t[o][1]][l])) { // (n + 4)
				if (can_reach(t[o][1], l, i) == true) {
					return true;
				}
			}
		}
		return false; // 1
	}

	// Fin de la implementacion del algoritmo de Krustal

	// Algoritmo de Prim

	/**
	 * Este metodo se encarga de implementar el algoritmo de prim. Primero se pide
	 * el numero de vertices al usuario. Luego se imprime el arbol original y
	 * finalmente se imprime el arbol de recubrimiento minimo.
	 */
	public void algoritmoPrim() {

		boolean verificar = false;
		while (!verificar) {

			try {

				int n = vista.leerNumero("\nDe el número de vértices: ");

				if (n > 0) {

					verificar = true;

					AlgoritmoPrim prim = new AlgoritmoPrim(createGraph(n));
					vista.mostrarMensaje("\nÁrbol Orginial");
					vista.mostrarMensaje(prim.originalGraphToString());
					prim.run();
					prim.resetPrintHistory();
					vista.mostrarMensaje("Árbol de Recubrimiento Mínimo");
					vista.mostrarMensaje(prim.minimumSpanningTreeToString());

				} else {
					vista.mostrarMensaje("Solamente se aceptan números enteros positivos. ");
				}

			} catch (NumberFormatException e) {
				vista.mostrarMensaje("Valor erroneo.");
			}

		}

	}

	/**
	 * Este metodo se encarga de crear los vertices y conexiones del arbol de
	 * recubrimiento minimo. Ademas, se encarga de crear las aristas con los pesos
	 * ingresados por el usuario.
	 * 
	 * @param tam Este parametro resperesnta el numero de vertices que desea crear
	 *            el usuario
	 * 
	 * @return Se retorna una lista de los vertices creados.
	 */
	public List<Vertice> createGraph(int tam) {

		ArrayList<Vertice> lista_vertices = new ArrayList<Vertice>();

		ArrayList<Arista> lista_aristas = new ArrayList<Arista>();

		for (int i = 0; i < tam; i++) {

			String letra = String.valueOf((char) ('A' + (i)));
			lista_vertices.add(new Vertice(letra.toUpperCase()));

		}

		HashMap<String, Integer> dic = new HashMap<String, Integer>();

		for (int j2 = 0; j2 < tam; j2++) {
			dic.put(String.valueOf((char) ('A' + (j2))), j2);

		}

		int[][] matriz = new int[tam][tam];

		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz[0].length; j++) {
				matriz[i][j] = -2;
			}
		}

		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz[0].length; j++) {

				if (i != j) {

					if (matriz[i][j] != -1 && matriz[i][j] == -2) {

						boolean verificar = false;
						while (!verificar) {

							try {

								int n = vista
										.leerNumero("\nIngrese peso entre " + getKey(dic, i) + " y " + getKey(dic, j));

								if (n > 0) {

									if (n != 0) {
										matriz[i][j] = n;
										matriz[j][i] = n;

										lista_aristas.add(new Arista(n));
										lista_vertices.get(dic.get(getKey(dic, i))).addEdge(lista_vertices.get(j),
												lista_aristas.get(lista_aristas.size() - 1));
										lista_vertices.get(dic.get(getKey(dic, j))).addEdge(lista_vertices.get(i),
												lista_aristas.get(lista_aristas.size() - 1));

									} else {
										matriz[i][j] = n;
										matriz[j][i] = n;
									}

									verificar = true;

								} else {
									vista.mostrarMensaje("No se aceptan valores númericos negativos.");
								}

							} catch (NumberFormatException e) {
								vista.mostrarMensaje("Solamente se aceptan valores númericos. ");
							}

						}

					}

				} else {
					matriz[i][j] = -1;
				}
			}
		}

		return lista_vertices;
	}

	/**
	 * Este metodo se encarga de regresar el valor de la llave a partir del valor en
	 * uno de los items del hashmap o diccionario.
	 * 
	 * @param <K>   Este parametro corresponde a la llave del HashMap.
	 * @param <V>   Este parametro corresponde al valor del HashMap.
	 * @param map   Este parametro corresponde a un dicionario realizado con
	 *              HashMap. map != null, map != "".
	 * @param value Este parametro corresponde al valor al que se desea conseguir su
	 *              correspondiente llave. value != null, value != "".
	 * @return Retorna la llave correspondiente.
	 */
	public static <K, V> K getKey(Map<K, V> map, V value) {

		for (Map.Entry<K, V> entry : map.entrySet()) {

			if (value.equals(entry.getValue())) {
				return entry.getKey();
			}
		}
		return null;
	}

}

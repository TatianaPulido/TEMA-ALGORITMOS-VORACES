/**
 * @author Daniela Benavides
 * @author Tatiana Pulido
 * @author Juan Pablo Velasquez 
 */
package co.edu.unbosque.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import co.edu.unbosque.model.AgenteViajero;
import co.edu.unbosque.model.Arista;
import co.edu.unbosque.model.AsignacionTareas;
import co.edu.unbosque.model.Nodo;
import co.edu.unbosque.view.Vista;

/**
 * Esta clase permite la interacción del usuario con las demas funcionalidades
 * de la aplicación, atravez de la vista. Incluidos los métodos de esta clase
 * asi como los métodos de la clase modelo.
 */
public class Controller {
	Vista vista;
	AgenteViajero agenteViajero;
	AsignacionTareas asignacionTareas;

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
						vista.mostrarMensaje("1");
					} else if (opcion == 2) {
						vista.mostrarMensaje("2");
					} else if (opcion == 3) {
						conexionesCiudades(ciudades());
						mostrarResultadoViajero();
					} else if (opcion == 4) {
						conexionesTareas(trabajadores());
						mostrarResultadoAsignacionTareas();
					} else {
						vista.mostrarMensaje("Usted decidio salir.");
					}
				} else {
					vista.mostrarMensaje("Opcion invalida.");
					opcion = -1;
				}
			} catch (NumberFormatException e) {
				vista.mostrarMensaje("Opcion invalida.");
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
								if (listaCiudades.get(i).getNombre().equals(lugar)) {
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
				if (!ciudadOrigen.equals(ciudadDestino)) {
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
					mensaje += j + ". " + listaCiudades.get(j).getNombre() + "\n";
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
								if (listaTrabajadores.get(i).getNombre().equals(nombre)) {
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

}

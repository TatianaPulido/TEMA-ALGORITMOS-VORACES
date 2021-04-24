package co.edu.unbosque.model;

/**
 * @author Daniela Benavides
 * @author Tatiana Pulido
 * @author Juan Pablo Velasquez 
 */
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Esta clase contiene todos los métodos y atributos necesarios para dar
 * solución al problema de la asignacion de tareas a travez de un algoritmo
 * voraz.
 */
public class AsignacionTareas {

	private ArrayList<Nodo> listaTrabajadores;

	/**
	 * Este es el método constructor el cual se le asigna la inicialización de los
	 * atributos y objectos. De esta manera el objecto es creado con un valor
	 * inicial. Este método se llama automaticamente cuando se crea el objeto.
	 */
	public AsignacionTareas() {
		listaTrabajadores = new ArrayList<>();
	}

	/**
	 * Este método permite agregar un trabajador de tipo nodo a una lista de
	 * trabajadores, cada nodo o trabajador contiene un nombre y una lista de
	 * conexiones. <b>pre</b> Se debe inicializar la lista de trabajadores.<br>
	 * <b>post</b> Se genera un nuevo trabajador con una lista de conexiones vacia y
	 * se agrega a la lista de trabajadores.<br>
	 * 
	 * @param nombre Este parametro corresponde al nombre del trabajador. nombre !=
	 *               null, nombre != "".
	 */
	public void agregarTrabajador(String nombre) {
		ArrayList<Arista> listaTareas = new ArrayList<>();
		Nodo trabajador = new Nodo();
		trabajador.setNombre(nombre);
		trabajador.setLista_rutas(listaTareas);
		this.listaTrabajadores.add(trabajador);
	}

	/**
	 * Este método permite agregar una ruta de tipo arista a un trabajador o nodo,
	 * cada arista el nombre del trabajador, la tareasy el costo. <b>pre</b> Se debe
	 * inicializar la lista de trabajadores y la lista de conexiones de cada
	 * trabajador.<br>
	 * <b>post</b>Se genera una nueva arista y se agrega al trabajador.<br>
	 * 
	 * @param posicion   Este parametro corresponde a la posicion de la lista donde
	 *                   se encuentra el trabajador al que se le va a agregar la
	 *                   arista. posicion != null.
	 * @param trabajador Este parametro corresponde al nombre del trabajador.
	 *                   trabajador != null, trabajador != "".
	 * @param tarea      Este parametro corresponde al nombre de la tarea. tarea !=
	 *                   null, tarea!= "".
	 * @param costo      Este parametro corresponde al costo que le genera al
	 *                   trabajador realizar la tarea. costo != null, costo != 0.
	 */
	public void agregarTareas(int posicion, String trabajador, String tarea, double costo) {
		ArrayList<Arista> listaConexiones = listaTrabajadores.get(posicion).getLista_rutas();
		Arista ruta = new Arista(trabajador, tarea, costo);
		listaConexiones.add(ruta);
		listaTrabajadores.get(posicion).setLista_rutas(listaConexiones);
	}

	/**
	 * Este método contiene una lista de tareas, cada tarea contiene un boolean que
	 * define si ya fue o no asignada a un trabajador.<br>
	 * <b>pre</b> Se debe inicializar la lista de trabajadores.<br>
	 * <b>post</b>Se genera un diccionario.<br>
	 * 
	 * @return Diccionario con una lista de los nombres de las tareas inicialmente
	 *         todas marcadas como no asignadas.
	 */
	public HashMap<String, Boolean> tareasAsignadas() {
		HashMap<String, Boolean> tareasAsignadas = new HashMap<>();
		for (int i = 0; i < listaTrabajadores.get(0).getLista_rutas().size(); i++) {
			tareasAsignadas.put(listaTrabajadores.get(0).getLista_rutas().get(i).getNodo_destino(), false);
		}
		return tareasAsignadas;
	}

	/**
	 * Este método permite asignar la tarea que menor costo le signifique a cada
	 * trabajador. Esta busqueda se realiza a partir de un algoritmo voraz.<br>
	 * <b>pre </b>El número de tareas debe ser igual al numero de trabajadores.<br>
	 * <b>post </b>A cada trabajador se le asigna una tarea.<br>
	 * 
	 * @return Menor tarea para cada trabajador.
	 */
	public HashMap<String, String> algoritmoVoraz() {
		HashMap<String, Boolean> tareasAsignadas = tareasAsignadas();
		HashMap<String, String> mejorTarea = new HashMap<>();

		double menorPeso = Double.MAX_VALUE;
		String menorTarea = "";
		String nombreTrabajador = "";
		int posTrabajador = 0;
		int posTarea = 0;
		for (int i = 0; i < listaTrabajadores.size(); i++) {
			nombreTrabajador = listaTrabajadores.get(i).getNombre();
			posTrabajador = i;
			for (int j = 0; j < listaTrabajadores.get(i).getLista_rutas().size(); j++) {
				if (menorPeso > listaTrabajadores.get(i).getLista_rutas().get(j).getPeso()
						&& !tareasAsignadas.get(listaTrabajadores.get(i).getLista_rutas().get(j).getNodo_destino())) {
					menorPeso = listaTrabajadores.get(i).getLista_rutas().get(j).getPeso();
					menorTarea = listaTrabajadores.get(i).getLista_rutas().get(j).getNodo_destino();

					posTarea = j;
				}
			}
			tareasAsignadas.put(listaTrabajadores.get(posTrabajador).getLista_rutas().get(posTarea).getNodo_destino(),
					true);
			mejorTarea.put(nombreTrabajador, menorTarea);
			menorPeso = Double.MAX_VALUE;
			menorTarea = "";
		}

		return mejorTarea;
	}

	/**
	 * @return the listaTrabajadores
	 */
	public ArrayList<Nodo> getListaTrabajadores() {
		return listaTrabajadores;
	}

	/**
	 * @param listaTrabajadores the listaTrabajadores to set
	 */
	public void setListaTrabajadores(ArrayList<Nodo> listaTrabajadores) {
		this.listaTrabajadores = listaTrabajadores;
	}

}

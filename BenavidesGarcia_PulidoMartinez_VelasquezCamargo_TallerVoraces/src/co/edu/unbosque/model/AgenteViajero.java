package co.edu.unbosque.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Esta clase contiene todos los métodos y atributos necesarios para dar
 * solución al problema del agente viajero a travez de un algoritmo voraz.
 * 
 * @author Daniela Benavides
 * @author Tatiana Pulido
 * @author Juan Pablo Velasquez
 */
public class AgenteViajero {

	private ArrayList<Nodo> listaCiudades;

	/**
	 * Este es el método constructor el cual se le asigna la inicialización de los
	 * atributos y objectos. De esta manera el objecto es creado con un valor
	 * inicial. Este método se llama automaticamente cuando se crea el objeto.
	 */
	public AgenteViajero() {
		listaCiudades = new ArrayList<>();
	}

	/**
	 * Este método permite agregar una ciudad de tipo nodo a una lista de ciudades,
	 * cada nodo o ciudad contiene un nombre y una lista de conexiones. <b>pre</b>
	 * Se debe inicializar la lista de ciudades.<br>
	 * <b>post</b> Se genera una nueva ciudad con una lista de conexiones vacia y se
	 * agrega a la lista de ciudades.<br>
	 * 
	 * @param lugar Este parametro corresponde al nombre de la ciudad. lugar !=
	 *              null, lugar != "".
	 */
	public void agregarCiudad(String lugar) {
		ArrayList<Arista> listaConexiones = new ArrayList<>();
		Nodo ciudad = new Nodo();
		ciudad.setNombre(lugar);
		ciudad.setLista_rutas(listaConexiones);
		this.listaCiudades.add(ciudad);
	}

	/**
	 * Este método permite agregar una ruta de tipo arista a una ciudad o nodo, cada
	 * ruta contiene una ciudad de origen una ciudad de destino y un costo..
	 * <b>pre</b> Se debe inicializar la lista de ciudades y la lista de conexiones
	 * de cada ciudad.<br>
	 * <b>post</b>Se genera una nueva ruta y se agrega a la ciudad..<br>
	 * 
	 * @param posicion      Este parametro corresponde a la posicion de la lista
	 *                      donde se encuentra la ciudad a la que se le va a agregar
	 *                      la ruta. posicion != null.
	 * @param ciudadOrigen  Este parametro corresponde al nombre de la ciudad de
	 *                      origen. ciudadOrigen != null, ciudadOrigen != "".
	 * @param ciudadDestino Este parametro corresponde al nombre de la ciudad de
	 *                      destino. ciudadDestin != null, ciudadDestino != "".
	 * @param costo         Este parametro corresponde al costo de viaje de la
	 *                      ciudad de origen a la ciudad de destino. costo != null,
	 *                      costo != 0.
	 */
	public void agregarRuta(int posicion, String ciudadOrigen, String ciudadDestino, double costo) {
		ArrayList<Arista> listaConexiones = listaCiudades.get(posicion).getLista_rutas();
		Arista ruta = new Arista(ciudadOrigen, ciudadDestino, costo);
		listaConexiones.add(ruta);
		listaCiudades.get(posicion).setLista_rutas(listaConexiones);
	}

	/**
	 * Este método contiene una lista de ciudades, cada ciudad contiene un boolean
	 * que define si ya fue o no visitada.<br>
	 * <b>pre</b> Se debe inicializar la lista de ciudades.<br>
	 * <b>post</b>Se genera un diccionario.<br>
	 * 
	 * @return Diccionario con una lista de los nombres de las ciudades inicialmente
	 *         todas marcadas como no visitadas.
	 */
	public HashMap<String, Boolean> visitasCiudades() {
		HashMap<String, Boolean> ciudadesVisitadas = new HashMap<>();
		for (int i = 0; i < listaCiudades.size(); i++) {
			ciudadesVisitadas.put(listaCiudades.get(i).getNombre(), false);
		}
		return ciudadesVisitadas;
	}

	/**
	 * Este método permite calcular la ruta de menor peso a partir de una ciudad de
	 * inicio. El objetivo es recorrer todas las ciudades buscando la ruta de menor
	 * costo, cada ciudad solo puede ser visitada una vez. Esta busqueda se realiza
	 * a partir de un algoritmo voraz.<br>
	 * <b>pre </b>Todas las ciudades deben estar interconectadas entre si, esto para
	 * asegurar que se pueda volver al punto de partida.<br>
	 * <b>post </b>Se genera una ruta que puede ser la de menor costo, no se asegura
	 * que esto siempre se cumpla.<br>
	 * 
	 * @param inicio Este parametro corresponde al punto desde donde se va a iniciar
	 *               el recorrido, este punto tambien corresponde al punto de
	 *               llegada. inicio != null.
	 * @return Ruta de menor peso y peso total.
	 */
	public ArrayList<String> algoritmoVoraz(int inicio) {
		HashMap<String, Boolean> ciudadesVisitadas = visitasCiudades();
		ArrayList<String> mejorRuta = new ArrayList<>();
		ArrayList<Nodo> ciudades_por_visitar = new ArrayList<>(this.listaCiudades);
		mejorRuta.add(ciudades_por_visitar.get(inicio).getNombre());
		Nodo anterior = ciudades_por_visitar.get(inicio);
		String nombreCiudadInicio = anterior.getNombre();
		ciudades_por_visitar.remove(inicio);
		ciudadesVisitadas.put(nombreCiudadInicio, true);
		double pesoRuta = 0;
		while (!ciudades_por_visitar.isEmpty()) {
			double menorPeso = Double.MAX_VALUE;
			String ciudadMenor = "";
			for (int i = 0; i < anterior.getLista_rutas().size(); i++) {
				if (menorPeso > anterior.getLista_rutas().get(i).getPeso()
						&& !ciudadesVisitadas.get(anterior.getLista_rutas().get(i).getNodo_destino())) {
					menorPeso = anterior.getLista_rutas().get(i).getPeso();
					ciudadMenor = anterior.getLista_rutas().get(i).getNodo_destino();
				}
			}
			pesoRuta += menorPeso;
			mejorRuta.add(ciudadMenor);
			for (int i = 0; i < ciudades_por_visitar.size(); i++) {
				if (ciudadMenor.equals(ciudades_por_visitar.get(i).getNombre())) {
					anterior = ciudades_por_visitar.get(i);
					ciudadesVisitadas.put(ciudades_por_visitar.get(i).getNombre(), true);
					ciudades_por_visitar.remove(i);

				}
			}

		}
		for (int i = 0; i < anterior.getLista_rutas().size(); i++) {
			if (anterior.getLista_rutas().get(i).getNodo_destino().equals(nombreCiudadInicio)) {
				pesoRuta += anterior.getLista_rutas().get(i).getPeso();
				mejorRuta.add(nombreCiudadInicio);
			}
		}
		// El ultimo elemento corresponde al peso de la ruta
		mejorRuta.add(String.valueOf(pesoRuta));
		return mejorRuta;
	}

	/**
	 * @return the listaCiudades
	 */
	public ArrayList<Nodo> getListaCiudades() {
		return listaCiudades;
	}

	/**
	 * @param listaCiudades the listaCiudades to set
	 */
	public void setListaCiudades(ArrayList<Nodo> listaCiudades) {
		this.listaCiudades = listaCiudades;
	}

	/**
	 * Este método permite validar si ya existe un peso para la ruta que se va a
	 * ingresar.
	 * 
	 * @param posicion      Este parametro corresponde a la posicion de la ciudad a
	 *                      la que se le va agregar la ruta. posicion != null.
	 * @param ciudadDestino Este parametro corresponde al nombre de la ciudad de
	 *                      destino. ciudadDestino != null.
	 * @return Verdadero si la ruta no existe, falso si ya se agrego la ruta.
	 */
	public boolean rutaNoExiste(int posicion, String ciudadDestino) {

		ArrayList<Arista> listaConexiones = listaCiudades.get(posicion).getLista_rutas();
		for (int i = 0; i < listaConexiones.size(); i++) {
			if (listaConexiones.get(i).getNodo_destino().equals(ciudadDestino)) {
				return (listaConexiones.get(i).getPeso() == 0.0) ? true : false;
			}
		}
		return true;
	}

}

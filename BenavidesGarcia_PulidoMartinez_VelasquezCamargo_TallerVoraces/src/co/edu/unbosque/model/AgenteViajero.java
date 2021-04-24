package co.edu.unbosque.model;

import java.util.ArrayList;

public class AgenteViajero {

	private ArrayList<Nodo> listaCiudades;

	public AgenteViajero() {
		listaCiudades = new ArrayList<>();
	}

	public void agregarCiudad(String lugar) {
		ArrayList<Arista> listaConexiones = new ArrayList<>();
		Nodo ciudad = new Nodo();
		ciudad.setNombre(lugar);
		ciudad.setLista_rutas(listaConexiones);
		this.listaCiudades.add(ciudad);
	}

	public void agregarRuta(int posicion, String ciudadOrigen, String ciudadDestino, double costo) {
		ArrayList<Arista> listaConexiones = listaCiudades.get(posicion).getLista_rutas();
		Arista ruta = new Arista(ciudadOrigen, ciudadDestino, costo);
		listaConexiones.add(ruta);
		listaCiudades.get(posicion).setLista_rutas(listaConexiones);
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

}

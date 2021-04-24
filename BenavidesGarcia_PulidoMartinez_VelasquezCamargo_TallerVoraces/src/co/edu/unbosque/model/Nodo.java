package co.edu.unbosque.model;

import java.util.ArrayList;

public class Nodo {

	private String nombre;
	private ArrayList<Arista> lista_rutas;

	/**
	 * @param nombre
	 * @param lista_rutas
	 */
	public Nodo(String nombre, ArrayList<Arista> lista_rutas) {
		this.nombre = nombre;
		this.lista_rutas = lista_rutas;
	}

	/**
	 * 
	 */
	public Nodo() {
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Nodo [nombre=" + nombre + ", lista_rutas=" + lista_rutas + "]";
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the lista_rutas
	 */
	public ArrayList<Arista> getLista_rutas() {
		return lista_rutas;
	}

	/**
	 * @param lista_rutas the lista_rutas to set
	 */
	public void setLista_rutas(ArrayList<Arista> lista_rutas) {
		this.lista_rutas = lista_rutas;
	}

}

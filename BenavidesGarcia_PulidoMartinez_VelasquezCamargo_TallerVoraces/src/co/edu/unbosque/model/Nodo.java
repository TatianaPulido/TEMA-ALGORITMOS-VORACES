package co.edu.unbosque.model;

import java.util.ArrayList;

/**
 * @author Daniela Benavides
 * @author Tatiana Pulido
 * @author Juan Pablo Velasquez 
 */

/**
 * Esta clase corresponde a un nodo. Cada nodo tiene un nombre y una lista de
 * rutas, cada ruta es una arista.
 */
public class Nodo {

	private String nombre;
	private ArrayList<Arista> lista_rutas;

	/**
	 * Este es el m�todo constructor el cual se le asigna la inicializaci�n de los
	 * atributos. De esta manera el objecto es creado con un valor inicial. Este
	 * m�todo se llama automaticamente cuando se crea el objeto.
	 * 
	 * @param nombre      Este parametro corresponde al nombre del nodo.
	 * @param lista_rutas Este parametro corresponde a la lista de rutas de un nodo.
	 */
	public Nodo(String nombre, ArrayList<Arista> lista_rutas) {
		this.nombre = nombre;
		this.lista_rutas = lista_rutas;
	}

	/**
	 * Este es el m�todo constructor. Este m�todo se llama automaticamente cuando se
	 * crea el objeto.
	 */
	public Nodo() {
	}

	/*
	 * (non-Javadoc)
	 * 
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

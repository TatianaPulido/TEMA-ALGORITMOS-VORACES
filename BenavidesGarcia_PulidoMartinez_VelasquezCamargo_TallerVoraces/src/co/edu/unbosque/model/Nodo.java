package co.edu.unbosque.model;

import java.util.ArrayList;

public class Nodo {

	private String nombre;
	private ArrayList<Arista> lista_aristas;

	public Nodo(String nombre, ArrayList<Arista> lista_aristas) {
		this.nombre = nombre;
		this.lista_aristas = lista_aristas;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public ArrayList<Arista> getLista_aristas() {
		return lista_aristas;
	}

	public void addArista(Arista arista) {
		if (lista_aristas == null) {
			lista_aristas = new ArrayList<Arista>();
		}

		lista_aristas.add(arista);
	}

}

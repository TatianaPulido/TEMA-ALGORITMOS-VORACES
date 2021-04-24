package co.edu.unbosque.model;

import java.util.ArrayList;

public class Grafo {

	private ArrayList<Nodo> lista_nodos;

	public Grafo(ArrayList<Nodo> lista_nodos) {
		this.lista_nodos = lista_nodos;
	}

	public ArrayList<Nodo> getLista_nodos() {
		return lista_nodos;
	}

	public void addNodo(Nodo nodo) {
		if (lista_nodos == null) {
			lista_nodos = new ArrayList<Nodo>();
		}

		lista_nodos.add(nodo);
	}

}

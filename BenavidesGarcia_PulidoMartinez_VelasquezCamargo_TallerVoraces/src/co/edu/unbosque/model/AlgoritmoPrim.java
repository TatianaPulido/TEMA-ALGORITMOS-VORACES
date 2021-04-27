package co.edu.unbosque.model;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.util.Pair;

/**
 * Esta clase contiene todos los métodos y atributos necesarios para dar
 * solución al algoritmo de prim a travez de un algoritmo voraz.
 * 
 * @author Daniela Benavides
 * @author Tatiana Pulido
 * @author Juan Pablo Velasquez
 */
public class AlgoritmoPrim {

	private List<Vertice> graph;

	/**
	 * Este es el método constructor el cual se le asigna la inicialización de los
	 * atributos. De esta manera el objecto es creado con un valor inicial. Este
	 * método se llama automaticamente cuando se crea el objeto AlgoritmoPrim para
	 * la implementacion de dicho algoritmo
	 * 
	 * @param graph Este atributo es utilizado para guardar todo el grafico, ya que
	 *              dentro del Vertice se encuentra una estructura de datos que
	 *              ayuda a identificar las conexiones. graph != null, graph != "".
	 */
	public AlgoritmoPrim(List<Vertice> graph) {
		this.graph = graph;
	}

	/**
	 * Este metodo representa la accion del subproceso del algoritmo prim. Se
	 * comienza colocando el primer elemento de la lista de vertices en visitado.
	 * Despues, isDisconnected devuelve verdadero si todavia hay vertices dentro del
	 * arbol que no se hayan visitado hasta el momento.
	 */
	public void run() {
		if (graph.size() > 0) {
			graph.get(0).setVisited(true);
		}
		while (isDisconnected()) {
			Arista nextMinimum = new Arista(Integer.MAX_VALUE);
			Vertice nextVertex = graph.get(0);
			for (Vertice vertex : graph) {
				if (vertex.isVisited()) {
					Pair<Vertice, Arista> candidate = vertex.nextMinimum();
					if (candidate.getValue().getPeso() < nextMinimum.getPeso()) {
						nextMinimum = candidate.getValue();
						nextVertex = candidate.getKey();
					}
				}
			}
			nextMinimum.setIncluido(true);
			nextVertex.setVisited(true);
		}
	}

	/**
	 * Este metodo se encarga de devolver verdadero si todavia hay vertices dentro
	 * del arbol que no se hayan visitado hasta el momento. En caso contrario
	 * devuelve falso.
	 * 
	 * @return Devuelve verdadero o falso si encuentra o no otros vertices no
	 *         visitados.
	 */
	private boolean isDisconnected() {
		for (Vertice vertex : graph) {
			if (!vertex.isVisited()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Este metodo se encarga de retornas el arbol original.
	 * 
	 * @return Una cadena de caracteres con el arbol.
	 */
	public String originalGraphToString() {

		StringBuilder sb = new StringBuilder();

		for (Vertice vertex : graph) {
			sb.append(vertex.originalToString());
		}

		return sb.toString();
	}

	/**
	 * Este metodo se encarga de reinicar la historia de impresion de los pares. Es
	 * decir, que el atributo de la clase Arista print se coloca falso.
	 */
	public void resetPrintHistory() {

		for (Vertice vertex : graph) {
			Iterator<Map.Entry<Vertice, Arista>> it = vertex.getEdges().entrySet().iterator();

			while (it.hasNext()) {
				Map.Entry<Vertice, Arista> pair = it.next();
				pair.getValue().setPrint(false);
			}
		}
	}

	/**
	 * Este metodo se encarga de retornar el arbol de recubrimiento minimo generado
	 * a partir del algoritmo de prim.
	 * 
	 * @return Una cadena de caracteres con el arbol final.
	 */
	public String minimumSpanningTreeToString() {

		StringBuilder sb = new StringBuilder();

		for (Vertice vertex : graph) {
			sb.append(vertex.includedToString());
		}

		return sb.toString();
	}

}

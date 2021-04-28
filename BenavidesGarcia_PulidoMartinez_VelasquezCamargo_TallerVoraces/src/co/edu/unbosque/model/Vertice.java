package co.edu.unbosque.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.math3.util.Pair;

/**
 * Esta clase corresponde a un vertice o nodo. Cada vertice tiene un nombre, un
 * estado de visitado y un tipo de diccionario que almacena el vertice y la
 * arista.
 * 
 * @author Daniela Benavides
 * @author Tatiana Pulido
 * @author Juan Pablo Velasquez
 *
 */
public class Vertice {

	private String label = null;
	private Map<Vertice, Arista> edges = new HashMap<>();
	private boolean visitado = false;

	/**
	 * Este es el método constructor el cual se le asigna la inicialización de los
	 * atributos. De esta manera el objecto es creado con un valor inicial. Este
	 * método se llama automaticamente cuando se crea el objeto Vertice para la
	 * implementacion del algoritmo Prim.
	 * 
	 * @param label Este parametro corresponde al nombre del vertice. label != null,
	 *              label != "".
	 */
	public Vertice(String label) {
		this.label = label;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * 
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @return the edges
	 */
	public Map<Vertice, Arista> getEdges() {
		return edges;
	}

	public void addEdge(Vertice vertice, Arista arista) {
		if (this.edges.containsKey(vertice)) {
			if (arista.getPeso() < this.edges.get(vertice).getPeso()) {
				this.edges.replace(vertice, arista);
			}
		} else {
			this.edges.put(vertice, arista);
		}
	}

	/**
	 * @return the visited
	 */
	public boolean isVisited() {
		return visitado;
	}

	/**
	 * @param visitado the visitado to set
	 */
	public void setVisited(boolean visitado) {
		this.visitado = visitado;
	}

	/**
	 * Este metodo se encarga de crear el arbol de recubrimiento minimo al iterar
	 * por todos los vertices ya visitados para encontrar la arista de menor peso.
	 * <b>post</b> Todos las aristas al final deben tener el atributo incluido en
	 * verdadero. <br>
	 * 
	 * @return El valor de retorno es un Pair que contiene una simple asociacion
	 *         llave-valor. Asi, se retorna un pair del siguiente vertice que
	 *         contiene la arista de menor peso.
	 */
	public Pair<Vertice, Arista> nextMinimum() {

		Arista nextMinimum = new Arista(Integer.MAX_VALUE);
		Vertice nextVertex = this; 
		Iterator<Map.Entry<Vertice, Arista>> it = edges.entrySet().iterator();

		while (it.hasNext()) {

			Map.Entry<Vertice, Arista> pair = it.next(); 

			if (!pair.getKey().isVisited()) { 
				if (!pair.getValue().isIncluido()) { 
					if (pair.getValue().getPeso() < nextMinimum.getPeso()) {

						nextMinimum = pair.getValue();
						nextVertex = pair.getKey(); 
					}
				}
			}
		}

		return new Pair<>(nextVertex, nextMinimum);
	}

	/**
	 * Este metodo se encarga de retornar el árbol creado originalmente sin ninguna
	 * implementacion de algun algoritmo. Se tienen dos vertices y el peso de la
	 * arista que los une.
	 * 
	 * @return Se retorna una cadena de caracteres con todos los vertices, aristas y
	 *         pesos.
	 */
	public String originalToString() {

		StringBuilder sb = new StringBuilder();
		Iterator<Map.Entry<Vertice, Arista>> it = edges.entrySet().iterator();

		while (it.hasNext()) {

			Map.Entry<Vertice, Arista> pair = it.next();
			if (!pair.getValue().isPrint()) {

				sb.append(getLabel());
				sb.append(" --- ");
				sb.append(pair.getValue().getPeso());
				sb.append(" --- ");
				sb.append(pair.getKey().getLabel());
				sb.append("\n");
				pair.getValue().setPrint(true);
			}
		}

		return sb.toString();
	}

	/**
	 * Este metodo se encarga de retorna un vertice la arista con el peso y el otro
	 * vertice conectado, que fueron visitados por el algoritmo prim.
	 * 
	 * @return Se retorna una cadena de caracteres con los dos vertices, su arista y
	 *         peso.
	 */
	public String includedToString() {

		StringBuilder sb = new StringBuilder();

		if (isVisited()) {
			Iterator<Map.Entry<Vertice, Arista>> it = edges.entrySet().iterator();

			while (it.hasNext()) {
				Map.Entry<Vertice, Arista> pair = it.next();

				if (pair.getValue().isIncluido()) {
					if (!pair.getValue().isPrint()) {

						sb.append(getLabel());
						sb.append(" --- ");
						sb.append(pair.getValue().getPeso());
						sb.append(" --- ");
						sb.append(pair.getKey().getLabel());
						sb.append("\n");
						pair.getValue().setPrint(true);
					}
				}
			}
		}

		return sb.toString();
	}
}
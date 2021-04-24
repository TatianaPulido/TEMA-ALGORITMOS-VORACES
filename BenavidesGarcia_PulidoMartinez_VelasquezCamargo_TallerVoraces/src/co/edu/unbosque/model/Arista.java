package co.edu.unbosque.model;
/**
 * @author Daniela Benavides
 * @author Tatiana Pulido
 * @author Juan Pablo Velasquez 
 */

/**
 * Esta clase corresponde a conexión entre dos nodos. Esta conexión tiene un
 * peso estrictamente mayor a 0.
 */
public class Arista {

	private String nodo_origen;
	private String nodo_destino;
	private double peso;

	/**
	 * 
	 * Este es el método constructor el cual se le asigna la inicialización de los
	 * atributos. De esta manera el objecto es creado con un valor inicial. Este
	 * método se llama automaticamente cuando se crea el objeto.
	 *
	 * @param nodo_origen  Este parametro corresponde al nombre del nodo de origen.
	 * @param nodo_destino Este parametro corresponde al nombre del nodo de destino.
	 * @param peso         Este parametro corresponde al peso.
	 */
	public Arista(String nodo_origen, String nodo_destino, double peso) {
		this.nodo_origen = nodo_origen;
		this.nodo_destino = nodo_destino;
		this.peso = peso;
	}

	/**
	 * @return the nodo_origen
	 */
	public String getNodo_origen() {
		return nodo_origen;
	}

	/**
	 * @param nodo_origen the nodo_origen to set
	 */
	public void setNodo_origen(String nodo_origen) {
		this.nodo_origen = nodo_origen;
	}

	/**
	 * @return the nodo_destino
	 */
	public String getNodo_destino() {
		return nodo_destino;
	}

	/**
	 * @param nodo_destino the nodo_destino to set
	 */
	public void setNodo_destino(String nodo_destino) {
		this.nodo_destino = nodo_destino;
	}

	/**
	 * @return the peso
	 */
	public double getPeso() {
		return peso;
	}

	/**
	 * @param peso the peso to set
	 */
	public void setPeso(double peso) {
		this.peso = peso;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Arista [nodo_origen=" + nodo_origen + ", nodo_destino=" + nodo_destino + ", peso=" + peso + "]";
	}
}

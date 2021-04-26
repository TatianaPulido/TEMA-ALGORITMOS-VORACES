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
	private boolean incluido = false;
	private boolean print = false;

	/**
	 * 
	 * Este es el método constructor el cual se le asigna la inicialización de los
	 * atributos. De esta manera el objecto es creado con un valor inicial. Este
	 * método se llama automaticamente cuando se crea el objeto.
	 *
	 * @param nodo_origen  Este parametro corresponde al nombre del nodo de origen.
	 *                     nodo_origen != null, nodo_origen != "".
	 * @param nodo_destino Este parametro corresponde al nombre del nodo de destino.
	 *                     nodo_destino != null, nodo_destino != "".
	 * @param peso         Este parametro corresponde al peso. peso != null, peso !=
	 *                     "".
	 */
	public Arista(String nodo_origen, String nodo_destino, double peso) {
		this.nodo_origen = nodo_origen;
		this.nodo_destino = nodo_destino;
		this.peso = peso;
	}

	/**
	 * Este es el constructor de la clase Arista el cual solamente se le asigna la
	 * inicializacion de un atributo. De esta manera este método cumple con la
	 * función de hacer una sobrecarga de constructores. Ademas, este constructur se
	 * utiliza para la creacion del algoritmo de prim.
	 * 
	 * @param peso Este parametro corresponde al peso de la arista. peso != null,
	 *             peso != "".
	 */
	public Arista(int peso) {
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

	/**
	 * @return the incluido
	 */
	public boolean isIncluido() {
		return incluido;
	}

	/**
	 * @param incluido the incluido to set
	 */
	public void setIncluido(boolean incluido) {
		this.incluido = incluido;
	}

	/**
	 * @return the print
	 */
	public boolean isPrint() {
		return print;
	}

	/**
	 * @param print the print to set
	 */
	public void setPrint(boolean print) {
		this.print = print;
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

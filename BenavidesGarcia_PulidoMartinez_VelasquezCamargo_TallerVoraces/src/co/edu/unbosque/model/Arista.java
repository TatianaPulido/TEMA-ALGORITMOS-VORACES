package co.edu.unbosque.model;

public class Arista {

	private Nodo nodo_origen;
	private Nodo nodo_destino;
	private double peso;

	public Arista(Nodo nodo_origen, Nodo nodo_destino, double peso) {

		this.peso = peso;
		this.nodo_origen = nodo_origen;
		this.nodo_destino = nodo_destino;

	}

	public Nodo getNodo_origen() {
		return nodo_origen;
	}

	public void setNodo_origen(Nodo nodo_origen) {
		this.nodo_origen = nodo_origen;
	}

	public Nodo getNodo_destino() {
		return nodo_destino;
	}

	public void setNodo_destino(Nodo nodo_destino) {
		this.nodo_destino = nodo_destino;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

}

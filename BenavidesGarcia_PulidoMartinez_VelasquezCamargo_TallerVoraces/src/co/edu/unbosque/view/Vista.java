package co.edu.unbosque.view;

import java.util.Scanner;

public class Vista {
	Scanner leer;

	public Vista() {
		leer = new Scanner(System.in);
	}

	public int leerNumero(String mensaje) {
		int numero = 0;
		System.out.println(mensaje);
		String aux = leer.next();
		numero = Integer.parseInt(aux);
		return numero;
	}

	public double leerPeso(String mensaje) {
		double numero = 0.0;
		System.out.println(mensaje);
		String aux = leer.next();
		numero = Double.parseDouble(aux);
		return numero;
	}

	public String pedirCiudad(int i) {
		System.out.println("Ingrese el nombre de la ciudad " + i);
		return leer.next();
	}

	public void mostrarMensaje(String mensaje) {
		System.out.println(mensaje);
	}

	public int mostrarMenu() {
		int numero = 0;
		System.out.println(
				"ALGORITMOS VORACES\n   1. Algoritmo Prim\n   2. Algoritmo de Kruskal\n   3. Agente viajero\n   4. Asignacion de tareas\n   0. Salir\n");
		String aux = leer.next();
		numero = Integer.parseInt(aux);
		return numero;
	}

	public void mostrarTabla(String origen,String destino, String peso) {
		String fmt = "%1$-15s %2$-15s %3$-10s%n";
		System.out.printf(fmt, origen,destino,peso);
	}

}


package co.edu.unbosque.view;

import java.util.Scanner;

/**
 * Esta clase permite la interacci�n del usuario con la aplicaci�n.
 * 
 * @author Daniela Benavides
 * @author Tatiana Pulido
 * @author Juan Pablo Velasquez
 */
public class Vista {
	Scanner leer;

	/**
	 * Este es el m�todo constructor el cual se le asigna la inicializaci�n de los
	 * atributos y objectos. De esta manera el objecto es creado con un valor
	 * inicial. Este m�todo se llama automaticamente cuando se crea el objeto.
	 */
	public Vista() {
		leer = new Scanner(System.in);
	}

	/**
	 * Este m�todo permite al usuario ingresar un n�mero entero.
	 * 
	 * @param mensaje Este parametro corresponde al mensaje que vera el usuario.
	 * @return n�mero entero.
	 */
	public int leerNumero(String mensaje) {
		int numero = 0;
		System.out.println(mensaje);
		String aux = leer.next();
		numero = Integer.parseInt(aux);
		return numero;
	}

	/**
	 * Este m�todo permite al usuario ingresar un n�mero decimal.
	 * 
	 * @param mensaje Este parametro corresponde al mensaje que vera el usuario.
	 * @return n�mero decimal.
	 */
	public double leerPeso(String mensaje) {
		double numero = 0.0;
		System.out.println(mensaje);
		String aux = leer.next();
		numero = Double.parseDouble(aux);
		return numero;
	}

	/**
	 * Este m�todo solicita al usuario el nombre de una ciudad.
	 * 
	 * @param i Este parametro a un numero entero.
	 * @return Nombre de ciudad.
	 */
	public String pedirCiudad(int i) {
		System.out.println("Ingrese el nombre de la ciudad " + i);
		return leer.next();
	}

	/**
	 * Este m�todo solicita al usuario el nombre de una tarea.
	 * 
	 * @param i Este parametro a un numero entero.
	 * @return Nombre de tarea.
	 */
	public String pedirTarea(int i) {
		System.out.println("Ingrese el nombre de la tareas " + i);
		return leer.next();
	}

	/**
	 * Este m�todo solicita al usuario el nombre de un trabajador.
	 * 
	 * @param i Este parametro a un numero entero.
	 * @return Nombre de trabajador.
	 */
	public String pedirTrabajador(int i) {
		System.out.println("Ingrese el nombre del trabajador " + i);
		return leer.next();
	}

	/**
	 * Este m�todo muestra el mensaje a un usuario.
	 * 
	 * @param mensaje Este parametro corresponde al mensaje a mostrar.
	 */
	public void mostrarMensaje(String mensaje) {
		System.out.println(mensaje);
	}

	/**
	 * Este m�todo muestra el menu de opciones al usuario y toma la opci�n elegida.
	 * 
	 * @return Opci�n elegida.
	 */
	public int mostrarMenu() {
		int numero = 0;
		System.out.println(
				"ALGORITMOS VORACES\n   1. Algoritmo Prim\n   2. Algoritmo de Kruskal\n   3. Agente viajero\n   4. Asignacion de tareas\n   0. Salir\n");
		String aux = leer.next();
		numero = Integer.parseInt(aux);
		return numero;
	}

	/**
	 * Este m�todo muestra una tabla de resultados.
	 * 
	 * @param origen  Origen.
	 * @param destino Destino.
	 * @param peso    Peso entre el origen y el destino.
	 */
	public void mostrarTabla(String origen, String destino, String peso) {
		String fmt = "%1$-15s %2$-15s %3$-10s%n";
		System.out.printf(fmt, origen, destino, peso);
	}

	/**
	 * Este m�todo muestra una tabla de trabajadores.
	 * 
	 * @param trabajador Nombre de trabajador.
	 * @param tarea      Nombre de tareas.
	 */
	public void mostrarTrabajadores(String trabajador, String tarea) {
		String fmt = "%1$-15s %2$-15s%n";
		System.out.printf(fmt, trabajador, tarea);
	}

}

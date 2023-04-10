package org.iesalandalus.programacion.alquilervehiculos.vista.texto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Autobus;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Cliente;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Furgoneta;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Turismo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.vista.Opcion;
import org.iesalandalus.programacion.utilidades.Entrada;

public class Consola {

	private static final String PATRON_FECHA = "dd/MM/yyyy";
	private static final String PATRON_MES = "MM/yyyy";
	private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern(PATRON_FECHA);

	private Consola() {
	}

	public static void mostrarCabecera(String mensaje) {
		StringBuilder lineas = new StringBuilder();
		for (int i = 0; i < mensaje.length(); i++) {
			lineas.append("-");
		}
		System.out.printf("%n%s%n%s%n", mensaje, lineas);
	}

	private static String leerCadena(String mensaje) {
		System.out.print(mensaje);
		return Entrada.cadena();
	}

	private static Integer leerEntero(String mensaje) {
		System.out.print(mensaje);
		return Entrada.entero();
	}

	private static LocalDate leerFecha(String mensaje, String patronMes) {
		System.out.print(mensaje);
		LocalDate fecha = null;
		try {
			fecha = LocalDate.parse(Entrada.cadena(), FORMATO_FECHA);
		} catch (DateTimeParseException e) {
			System.out.printf("%s", e.getMessage());
		}
		return fecha;
	}

	public static Accion elegirAcccion() {
		Accion opcionElegida = null;
		do {
			opcionElegida = Accion.get(leerEntero("Has elegido: "));
		} while (opcionElegida == null);
		return opcionElegida;
	}

	public static Cliente leerCliente() {
		return new Cliente(leerNombre(), leerCadena("Dimeme un DNI: "), leerTelefono());
	}

	public static Cliente leerClienteDni() {
		return Cliente.getClienteConDni(leerCadena("Dimeme un DNI: "));
	}

	public static String leerNombre() {
		return leerCadena("Dime el nombre del cliente: ");
	}

	public static String leerTelefono() {
		return leerCadena("Dime un teléfono del cliente: ");
	}

	public static Vehiculo leerVehiculo() {
		mostrarMenuTiposVehiculos();
		return leerVehiculo(elegirTipoVehiculo());

	}

	private static void mostrarMenuTiposVehiculos() {
		Consola.mostrarCabecera("Tipos:");
		for (TipoVehiculo tipo : TipoVehiculo.values())
			System.out.printf("%s%n", tipo);
	}

	private static TipoVehiculo elegirTipoVehiculo() {
		return TipoVehiculo.get(leerEntero("Tipo:") - 1);

	}

	public static Vehiculo leerVehiculo(TipoVehiculo tipoVehiculo) {
		Vehiculo vehiculoSalida = null;
		String marca = leerCadena("De la marca:");
		String modelo = leerCadena("Del modelo:");
		String matricula = leerCadena("Y con matrícula:");

		if (tipoVehiculo == TipoVehiculo.TURISMO) {
			vehiculoSalida = new Turismo(marca, modelo, leerEntero("Turismo con cilindrada:"), matricula);
		}
		if (tipoVehiculo == TipoVehiculo.FURGONETA) {
			vehiculoSalida = new Furgoneta(marca, modelo, matricula, leerEntero("Con número de plazas:"),
					leerEntero("Con Peso Máximo Autorizado:"));
		}

		if (tipoVehiculo == TipoVehiculo.AUTOBUS) {
			vehiculoSalida = new Autobus(marca, modelo, leerEntero("Con número de plazas:"), matricula);
		}

		return vehiculoSalida;
	}

	public static Alquiler leerAlquiler() {
		return new Alquiler(leerClienteDni(), leerVehiculo(), leerFecha("Dime una fecha de alquiler: ", PATRON_FECHA));
	}

	public static LocalDate leerFechaDevolucion() {
		return leerFecha("Dime una fecha de devolucion: ", PATRON_FECHA);
	}

	public static LocalDate leerMes() {
		return leerFecha("Fecha mes:", PATRON_MES);

	}

}

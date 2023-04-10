package org.iesalandalus.programacion.alquilervehiculos.modelo.dominio;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import javax.naming.OperationNotSupportedException;

public class Alquiler {
	protected static DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private static int PRECIO_DIA = 20;
	private LocalDate fechaAlquiler;
	private LocalDate fechaDevolucion;
	private Cliente cliente;
	private Vehiculo vehiculo;

	public Alquiler(Cliente cliente, Vehiculo vehiculo, LocalDate fechaAlquiler) {
		setCliente(cliente);
		setVehiculo(vehiculo);
		setFechaAlquiler(fechaAlquiler);
	}

	public Alquiler(Alquiler alquiler) { // revisar
		if (alquiler == null) {
			throw new NullPointerException("ERROR: No es posible copiar un alquiler nulo.");
		}
		cliente = new Cliente(alquiler.getCliente());
		vehiculo = Vehiculo.copiar(alquiler.getVehiculo());
		setFechaAlquiler(alquiler.getFechaAlquiler());
		setFechaDevolucion(alquiler.getFechaDevolucion());
	}

	public LocalDate getFechaAlquiler() {
		return fechaAlquiler;
	}

	private void setFechaAlquiler(LocalDate fechaAlquiler) {
		LocalDate hoy = LocalDate.now();
		if (fechaAlquiler == null) {
			throw new NullPointerException("ERROR: La fecha de alquiler no puede ser nula.");
		}
		if (fechaAlquiler.isAfter(hoy)) {
			throw new IllegalArgumentException("ERROR: La fecha de alquiler no puede ser futura.");
		}

		this.fechaAlquiler = fechaAlquiler;
	}

	public LocalDate getFechaDevolucion() {
		return fechaDevolucion;
	}

	private void setFechaDevolucion(LocalDate fechaDevolucion) {
		if (fechaDevolucion == null) {
			throw new NullPointerException("ERROR: La fecha de devolución no puede ser nula.");
		}
		if (fechaDevolucion.isAfter(LocalDate.now())) {
			throw new IllegalArgumentException("ERROR: La fecha de devolución no puede ser futura.");
		}
		if (fechaDevolucion.isBefore(fechaAlquiler) || fechaDevolucion.isEqual(getFechaAlquiler())) {
			throw new IllegalArgumentException("ERROR: La fecha de devolución debe ser posterior a la fecha de alquiler.");
		}
		this.fechaDevolucion = fechaDevolucion;
	}

	public Cliente getCliente() {
		return cliente;
	}

	private void setCliente(Cliente cliente) {
		if (cliente == null) {
			throw new NullPointerException("ERROR: El cliente no puede ser nulo.");
		}
		this.cliente = cliente;
	}

	public Vehiculo getVehiculo() {
		return vehiculo;
	}

	private void setVehiculo(Vehiculo vehiculo) {
		if (vehiculo == null)
			throw new NullPointerException("ERROR: El vehículo no puede ser nulo.");
		this.vehiculo = vehiculo;
	}

	public void devolver(LocalDate fechaDevolucion) throws OperationNotSupportedException { // revisar
		if (this.fechaDevolucion != null) {
			throw new OperationNotSupportedException("ERROR: La devolución ya estaba registrada.");
		}
		setFechaDevolucion(fechaDevolucion);
	}

	public int getPrecio() {
		if (getFechaDevolucion() == null)
			return 0;
		return (int) ((PRECIO_DIA + (vehiculo.getFactorPrecio()))
				* (ChronoUnit.DAYS.between(fechaAlquiler, fechaDevolucion)));
	}

	@Override
	public int hashCode() {
		return Objects.hash(PRECIO_DIA, cliente, fechaAlquiler, fechaDevolucion, vehiculo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Alquiler other = (Alquiler) obj;
		return  Objects.equals(cliente, other.cliente)
				&& Objects.equals(fechaAlquiler, other.fechaAlquiler)
				&& Objects.equals(fechaDevolucion, other.fechaDevolucion) && Objects.equals(vehiculo, other.vehiculo);
	}

	@Override
	public String toString() {
		if (getFechaDevolucion() == null)
			return String.format("%s <---> %s, %s - %s (%d€)", cliente, vehiculo,
					getFechaAlquiler().format(Alquiler.FORMATO_FECHA), "Aún no devuelto", 0);
		return String.format("%s <---> %s, %s - %s (%d€)", cliente, vehiculo,
				getFechaAlquiler().format(Alquiler.FORMATO_FECHA),
				getFechaDevolucion().format(Alquiler.FORMATO_FECHA), getPrecio());
	}

}

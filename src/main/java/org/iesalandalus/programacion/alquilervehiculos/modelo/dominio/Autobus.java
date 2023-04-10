package org.iesalandalus.programacion.alquilervehiculos.modelo.dominio;

public class Autobus extends Vehiculo {
	private static int FACTOR_PLAZAS = 2;
	private int plazas;

	public Autobus(String marca, String modelo, int plazas, String matricula) {
		super(marca, modelo, matricula);
		if (plazas < 7 || plazas > 100)
			throw new IllegalArgumentException("ERROR: Las plazas no son correctas.");
		setPlazas(plazas);
	}

	public Autobus(Autobus autobus) {
		super(autobus);
		if (autobus == null) {
			throw new NullPointerException("ERROR: No es posible copiar un autobus nulo.");
		}
		setPlazas(autobus.plazas);
	}

	private int getPlazas() {
		return plazas;
	}

	public void setPlazas(int plazas) {
		this.plazas = plazas;
	}

	@Override
	public int getFactorPrecio() {
		return FACTOR_PLAZAS * plazas;
	}

	@Override
	public String toString() {
		return String.format("%s %s (%d plazas) - %s", this.getMarca(), this.getModelo(), this.getPlazas(),
				this.getMatricula());
	}

}

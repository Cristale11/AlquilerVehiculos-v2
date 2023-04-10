package org.iesalandalus.programacion.alquilervehiculos.vista;

import org.iesalandalus.programacion.alquilervehiculos.vista.texto.VistaTexto;

public enum FactorioVista {
	TEXTO {

		@Override
		public Vista crear() {
			return new VistaTexto();
		}
	};
	public abstract Vista crear();
}

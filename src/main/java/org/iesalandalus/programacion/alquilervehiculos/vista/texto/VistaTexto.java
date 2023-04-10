package org.iesalandalus.programacion.alquilervehiculos.vista.texto;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.Map;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Alquiler;
import org.iesalandalus.programacion.alquilervehiculos.vista.Vista;

public class VistaTexto extends Vista {

	public VistaTexto() {
   Accion.setVista(this);
 }
	
	@Override
	public void comenzar() {
		Accion opcionElegida;
		do {
			Consola.mostrarCabecera("Elige una opción del menú");
			Consola.mostrarMenu();
			opcionElegida = Consola.elegirAcccion();
			opcionElegida.ejecutar();	
		}while (opcionElegida!=Accion.SALIR);

	}

	@Override
	public void terminar() {
		String salida ="Hasta la próxima, usuario";
		System.out.printf("%n%s%n",salida);
	}
	
	public void insertarCliente() {
		Consola.mostrarCabecera("Dame un cliente, por favor");
		try {
			getControlador().insertar(Consola.leerCliente());
		} catch (OperationNotSupportedException e) {
			e.printStackTrace();
		}
 }

	public void insertarVehiculo() {
		Consola.mostrarCabecera("Dame un vehículo, por favor");
		try {
			getControlador().insertar(Consola.leerVehiculo());
		} catch (OperationNotSupportedException e) {
			e.printStackTrace();
		}
		
	}

	public void insertarAlquiler() {
		Consola.mostrarCabecera("Dame un alquiler, por favor");
		try {
			getControlador().insertar(Consola.leerAlquiler());
		} catch (OperationNotSupportedException e) {
			e.printStackTrace();
		}
		
	}

	public void buscarCliente() {
		Consola.mostrarCabecera("Dame un cliente, por favor");
		try {
			getControlador().buscar(Consola.leerCliente());
		} catch (OperationNotSupportedException e) {
			e.printStackTrace();
		}
		
	}

	public void buscarVehiculo() {
		Consola.mostrarCabecera("Dame un vehículo, por favor");
		try {
			getControlador().buscar(Consola.leerVehiculo());
		} catch (OperationNotSupportedException e) {
			e.printStackTrace();
		}
		
	}

	public void buscarAlquiler() {
		Consola.mostrarCabecera("Dame un alquiler, por favor");
		try {
			getControlador().buscar(Consola.leerAlquiler());
		} catch (OperationNotSupportedException e) {
			e.printStackTrace();
		}
		
	}

	public void modificarCliente() {
		Consola.mostrarCabecera("Dame un cliente y el nuevo nombre y teléfono, por favor");
		try {
			getControlador().modificar(Consola.leerCliente(),Consola.leerNombre(),Consola.leerTelefono());
		} catch (OperationNotSupportedException e) {
			e.printStackTrace();
		}
		
	}

	public void devolverAlquilerCliente() {
		Consola.mostrarCabecera("Dame un cliente y la fecha de devolución, por favor");
		try {
			getControlador().devolver(Consola.leerCliente(), Consola.leerFechaDevolucion());
		} catch (OperationNotSupportedException e) {
			e.printStackTrace();
		}
		
	}

	public void devolverAlquilerVehiculo() {
		Consola.mostrarCabecera("Dame un vehiculo y su fecha de devolución, por favor");
		try {
			getControlador().devolver(Consola.leerVehiculo(), Consola.leerFechaDevolucion());
		} catch (OperationNotSupportedException e) {
			e.printStackTrace();
		}
		
	}

	public void borrarCliente() {
		Consola.mostrarCabecera("Dame un cliente, por favor");
		try {
			getControlador().borrar(Consola.leerCliente());
		} catch (OperationNotSupportedException e) {
			e.printStackTrace();
		}
		
	}

	public void borrarVehiculo() {
		Consola.mostrarCabecera("Dame un vehículo, por favor");
		try {
			getControlador().borrar(Consola.leerVehiculo());
		} catch (OperationNotSupportedException e) {
			e.printStackTrace();
		}
		
	}

	public void borrarAlquiler() {
		Consola.mostrarCabecera("Dame un alquiler, por favor");
		try {
			getControlador().borrar(Consola.leerAlquiler());
		} catch (OperationNotSupportedException e) {
			e.printStackTrace();
		}
		
	}

	public void listarClientes() {
		getControlador().getClientes();
		
	}

	public void listarVehiculos() {
		getControlador().getVehiculos();
		
	}

	public void listarAlquileres() {
		getControlador().getAlquileres();
		
	}

	public void listarAlquileresCliente() {
		Consola.mostrarCabecera("Dame un cliente, por favor");
		getControlador().getAlquileres(Consola.leerCliente());
		
	}

	public void listarAlquileresVehiculo() {
		Consola.mostrarCabecera("Dame un vehículo, por favor");
		getControlador().getAlquileres(Consola.leerVehiculo());
		
	}

	public void mostrarEstadisticasMensualesTipoVehiculo() {
		 Map<TipoVehiculo, Integer> estadisticas = inicializarEstadisticas();
		  LocalDate mes = Consola.leerMes();
		  for(Alquiler alquiler:getControlador().getAlquileres()) {
			  if(alquiler.getFechaAlquiler().getMonth().equals(mes.getMonth())&&alquiler.getFechaAlquiler().getYear()==(mes.getYear()))
			  {
				  estadisticas.put(TipoVehiculo.get(alquiler.getVehiculo()),estadisticas.get(TipoVehiculo.get(alquiler.getVehiculo()))+1 );
			  }
		  }
		  if (estadisticas == null) 
				System.out.println("ERROR: No es posible mostrar las estadisticas, la fecha no es válida.");
		  else
			  for(Map.Entry<TipoVehiculo, Integer> elemento:estadisticas.entrySet()) {
				  TipoVehiculo tipo = elemento.getKey();
				  Integer cantidad = elemento.getValue();
				  System.out.printf("Del tipo %s hay %d%n",tipo,cantidad);
			  }
	}
	private Map <TipoVehiculo,Integer> inicializarEstadisticas(){
		Map <TipoVehiculo,Integer> mapa = new EnumMap<>(TipoVehiculo.class);
		for(int i=0;i<TipoVehiculo.values().length;i++) {
			mapa.put(TipoVehiculo.get(i), 0);
		}
		
		return mapa;
	}

}

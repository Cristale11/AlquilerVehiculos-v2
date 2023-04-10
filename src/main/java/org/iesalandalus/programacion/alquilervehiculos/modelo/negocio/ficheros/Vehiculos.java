package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.ficheros;

import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Autobus;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Furgoneta;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Turismo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.IVehiculos;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.lang.model.element.Element;
import javax.naming.OperationNotSupportedException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class Vehiculos implements IVehiculos {
	private static final File FICHERO_VEHICULOS = new File(
			"C:\\Users\\Crist\\\\git\\AlquilerVehiculos-v2\\datos\\vehiculos.xml");
	private static final String RAIZ = "vehiculos";
	private static final String VEHICULO = "";
	private static final String MARCA = "";
	private static final String MODELO = "";
	private static final String MATRICULA = "";
	private static final String CILINDRADA = "";
	private static final String PLAZAS = "";
	private static final String PMA = "";
	private static final String TIPO = "";
	private static final String TURISMO = "turismo";
	private static final String AUTOBUS = "autobus";
	private static final String FURGONETA = "furgoneta";
	private static Vehiculos instancia;

	private List<Vehiculo> coleccionVehiculos;

	public Vehiculos() {
		coleccionVehiculos = new ArrayList<>();
	}

	static Vehiculos getInstancia() {
		if (instancia == null)
			instancia = new Vehiculos();
		return instancia;
	}

	public void comenzar() {

		leerDom(UtilidadesXml.leerXmlDeFichero(FICHERO_VEHICULOS));

	}

	private void leerDom(Document documentoXml) {

		NodeList nodosClientes = documentoXml.getElementsByTagName(VEHICULO);
		for (int i = 0; i < nodosClientes.getLength(); i++) {
			coleccionVehiculos.add(getVehiculo((Element) nodosClientes.item(i)));
		}
	}

	private Vehiculo getVehiculo(Element element) {
		Node nodoCliente = (Node) element;

		Vehiculo vehiculoNuevo = null;

		if (Objects.equals(nodoCliente.getAttributes().getNamedItem(TIPO).getTextContent(), AUTOBUS)) {

			String marca = nodoCliente.getAttributes().getNamedItem(MARCA).getTextContent();
			String matricula = nodoCliente.getAttributes().getNamedItem(MATRICULA).getTextContent();
			String modelo = nodoCliente.getAttributes().getNamedItem(MODELO).getTextContent();
			String plazas = nodoCliente.getAttributes().getNamedItem(PLAZAS).getTextContent();

			Autobus nuevoBus = new Autobus(marca, modelo, Integer.parseInt(plazas), matricula);

			vehiculoNuevo = nuevoBus;
		}

		else if (Objects.equals(nodoCliente.getAttributes().getNamedItem(TIPO).getTextContent(), TURISMO)) {

			String cilindrada = nodoCliente.getAttributes().getNamedItem(CILINDRADA).getTextContent();
			String marca = nodoCliente.getAttributes().getNamedItem(MARCA).getTextContent();
			String matricula = nodoCliente.getAttributes().getNamedItem(MATRICULA).getTextContent();
			String modelo = nodoCliente.getAttributes().getNamedItem(MODELO).getTextContent();

			Turismo turismoNuevo = new Turismo(marca, modelo, Integer.parseInt(cilindrada), matricula);

			vehiculoNuevo = turismoNuevo;
		}

		else if (Objects.equals(nodoCliente.getAttributes().getNamedItem(TIPO).getTextContent(), FURGONETA)) {

			String marca = nodoCliente.getAttributes().getNamedItem(MARCA).getTextContent();
			String matricula = nodoCliente.getAttributes().getNamedItem(MATRICULA).getTextContent();
			String modelo = nodoCliente.getAttributes().getNamedItem(MODELO).getTextContent();
			String plazas = nodoCliente.getAttributes().getNamedItem(PLAZAS).getTextContent();
			String pma = nodoCliente.getAttributes().getNamedItem(PMA).getTextContent();

			Furgoneta nuevaFurgo = new Furgoneta(marca, modelo, Integer.parseInt(pma), Integer.parseInt(plazas),
					matricula);

			vehiculoNuevo = nuevaFurgo;
		}

		return vehiculoNuevo;

	}
	
	public void terminar() {

		UtilidadesXml.escribirXmlAFichero(crearDom(), FICHERO_VEHICULOS);

	}
	private Document crearDom() {
		try {

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document documento = dBuilder.newDocument();
			Element raiz = (Element) documento.createElement(RAIZ);
			documento.appendChild((Node) raiz);
			for (Vehiculo vehiculo : coleccionVehiculos) {
				Element elementoVehiculo = getElemento(documento, vehiculo);
				((Node) raiz).appendChild((Node) elementoVehiculo);
			}

			return documento;

		} catch (ParserConfigurationException e) {
			System.out.println("Error al crear el DOM");
			return null;
		}
	}
	private Element getElemento(Document documentoXml, Vehiculo vehiculo) {
		
		Element elementoVehiculo = (Element) documentoXml.createElement("vehiculo");
		
		if (vehiculo instanceof Turismo) {
			((DocumentBuilderFactory) elementoVehiculo).setAttribute("cilindrada",((Turismo) vehiculo).getCilindrada());
			((DocumentBuilderFactory) elementoVehiculo).setAttribute("marca",((Turismo) vehiculo).getMarca());
			((DocumentBuilderFactory) elementoVehiculo).setAttribute("matricula",((Turismo) vehiculo).getMatricula());
			((DocumentBuilderFactory) elementoVehiculo).setAttribute("modelo",((Turismo) vehiculo).getModelo());
			((DocumentBuilderFactory) elementoVehiculo).setAttribute("tipo", "turismo" );
		}
			
		else if (vehiculo instanceof Autobus) {
			((DocumentBuilderFactory) elementoVehiculo).setAttribute("marca",((Autobus) vehiculo).getMarca());
			((DocumentBuilderFactory) elementoVehiculo).setAttribute("matricula",((Autobus) vehiculo).getMatricula());
			((DocumentBuilderFactory) elementoVehiculo).setAttribute("modelo",((Autobus) vehiculo).getModelo());
			((DocumentBuilderFactory) elementoVehiculo).setAttribute("plazas",((Autobus) vehiculo).getPlazas());
			((DocumentBuilderFactory) elementoVehiculo).setAttribute("tipo", "autobus" );
		}
		else if (vehiculo instanceof Furgoneta) {
			
			((DocumentBuilderFactory) elementoVehiculo).setAttribute("marca",((Furgoneta) vehiculo).getMarca());
			((DocumentBuilderFactory) elementoVehiculo).setAttribute("matricula",((Furgoneta) vehiculo).getMatricula());
			((DocumentBuilderFactory) elementoVehiculo).setAttribute("modelo",((Furgoneta) vehiculo).getModelo());
			((DocumentBuilderFactory) elementoVehiculo).setAttribute("plazas",((Furgoneta) vehiculo).getPlazas());
			((DocumentBuilderFactory) elementoVehiculo).setAttribute("pma",((Furgoneta) vehiculo).getPma());
			((DocumentBuilderFactory) elementoVehiculo).setAttribute("tipo", "turismo" );
		
		}
		
		
		return elementoVehiculo;
	}
	
	
	@Override
	public List<Vehiculo> get() {
		return new ArrayList<>(coleccionVehiculos);
	}

	@Override
	public void insertar(Vehiculo vehiculo) throws OperationNotSupportedException {
		if (vehiculo == null) {
			throw new NullPointerException("ERROR: No se puede insertar un vehículo nulo.");
		}
		if (coleccionVehiculos.contains(vehiculo)) {
			throw new OperationNotSupportedException("ERROR: Ya existe un vehículo con esa matrícula.");
		} else {
			coleccionVehiculos.add(vehiculo);
		}

	}

	@Override
	public Vehiculo buscar(Vehiculo vehiculo) {
		if (vehiculo == null)
			throw new NullPointerException("ERROR: No se puede buscar un vehículo nulo.");
		for (Vehiculo vehiculoTemp : coleccionVehiculos) {
			if (vehiculoTemp.equals(vehiculo)) {
				return vehiculoTemp;
			}
		}
		return null;
	}

	@Override
	public void borrar(Vehiculo vehiculo) throws OperationNotSupportedException {
		if (vehiculo == null) {
			throw new NullPointerException("ERROR: No se puede borrar un vehículo nulo.");
		}
		if (!coleccionVehiculos.contains(vehiculo)) {
			throw new OperationNotSupportedException("ERROR: No existe ningún vehículo con esa matrícula.");
		}
		coleccionVehiculos.remove(vehiculo);
	}

}
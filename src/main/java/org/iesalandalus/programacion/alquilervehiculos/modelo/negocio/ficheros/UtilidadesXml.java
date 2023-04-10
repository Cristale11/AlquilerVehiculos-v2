package org.iesalandalus.programacion.alquilervehiculos.modelo.negocio.ficheros;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class UtilidadesXml {
	private UtilidadesXml() {

	}
	public static DocumentBuilder crearConstructorDocumentoXml(){
		DocumentBuilder constructorDocumentos = null;
		try {
			DocumentBuilderFactory fabricaConstructor = DocumentBuilderFactory.newInstance();
			fabricaConstructor.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
			constructorDocumentos = fabricaConstructor.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		
		return constructorDocumentos;
	}
	public static void escribirXmlAFichero(Document documento, File salida){
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = null;
		try {
			transformer = transformerFactory.newTransformer();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		}
		DOMSource documentoRaiz = new DOMSource(documento);
		StreamResult archivoSalida = new StreamResult(salida);
		try {
			transformer.transform(documentoRaiz, archivoSalida);
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}
	public static Document leerXmlDeFichero(File ficheroXml){
		
		Document documento = null;
		try {
			DocumentBuilder constructorDocumento = crearConstructorDocumentoXml();
			if(constructorDocumento!=null) {
			documento = constructorDocumento.parse(ficheroXml);
			documento.getDocumentElement().normalize();
			}
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return documento;
	}
	
}

package br.com.calendario.beans;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement( name = "ListaEvento" )   
@XmlAccessorType( XmlAccessType.FIELD ) 
public class ListaEvento {

	private List<Evento> listaEventos = new ArrayList<Evento>();

	public void setListaEventos(List<Evento> listaEventos) {
		this.listaEventos = listaEventos;
	}

	public List<Evento> getListaEventos() {
		return listaEventos;
	}
}

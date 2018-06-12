package br.com.calendario.beans;


import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


@XmlRegistry
public class ObjectFactory {

    private final static QName _NFe_QNAME = new QName("calendario", "cal");

    public ObjectFactory() {
    	
    }
    
    public ListaEvento createListaEvento(){
    	return new ListaEvento();
    }
    
    public Evento createEvento(){
    	return new Evento();
    }
    

    @XmlElementDecl(name = "ListaEvento")
    public JAXBElement<ListaEvento> createNFe(ListaEvento value) {
        return new JAXBElement<ListaEvento>(_NFe_QNAME, ListaEvento.class, null, value);
    }

}

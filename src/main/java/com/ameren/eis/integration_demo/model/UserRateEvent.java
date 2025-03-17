package com.ameren.eis.integration_demo.model;

import java.io.Serializable;
import java.io.StringWriter;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.annotation.XmlAccessOrder;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorOrder;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement(name="UserRateEvent")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlAccessorOrder(XmlAccessOrder.ALPHABETICAL)
@XmlType(propOrder={"email", "rating" })
public class UserRateEvent implements Serializable  {
    
    @XmlElement(name="email" )    
    private String email;
    @XmlElement(name="rating")    
    private double rating;

    public UserRateEvent() {}

    public UserRateEvent(String email, double rating) {        
        this.email = email;
        this.rating = rating;
    }

    public String toXml() throws JAXBException  {             
        JAXBContext jaxbContext = JAXBContext.newInstance(UserRateEvent.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);  
        StringWriter sw = new StringWriter();           
        jaxbMarshaller.marshal(this, sw);
        return sw.toString();            
    }

}

//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.01.19 at 10:37:01 AM CET 
//


package bank.wsdl;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for history complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="history"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://bsr.poznan.put.cs/model}transfer"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="balanceAfter" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="balanceBefore" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/&gt;
 *         &lt;element name="nrb" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="timestamp" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "history", namespace = "http://bsr.poznan.put.cs/model", propOrder = {
    "balanceAfter",
    "balanceBefore",
    "nrb",
    "timestamp"
})
public class History
    extends Transfer
{

    @XmlElement(namespace = "")
    protected BigDecimal balanceAfter;
    @XmlElement(namespace = "")
    protected BigDecimal balanceBefore;
    @XmlElement(namespace = "")
    protected String nrb;
    @XmlElement(namespace = "")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar timestamp;

    /**
     * Gets the value of the balanceAfter property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getBalanceAfter() {
        return balanceAfter;
    }

    /**
     * Sets the value of the balanceAfter property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setBalanceAfter(BigDecimal value) {
        this.balanceAfter = value;
    }

    /**
     * Gets the value of the balanceBefore property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getBalanceBefore() {
        return balanceBefore;
    }

    /**
     * Sets the value of the balanceBefore property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setBalanceBefore(BigDecimal value) {
        this.balanceBefore = value;
    }

    /**
     * Gets the value of the nrb property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNrb() {
        return nrb;
    }

    /**
     * Sets the value of the nrb property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNrb(String value) {
        this.nrb = value;
    }

    /**
     * Gets the value of the timestamp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the value of the timestamp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTimestamp(XMLGregorianCalendar value) {
        this.timestamp = value;
    }

}

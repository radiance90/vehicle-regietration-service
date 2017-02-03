
package au.edu.unsw.soacourse.pinkslipdefinitions;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the au.edu.unsw.soacourse.pinkslipdefinitions package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _AgeMessage_QNAME = new QName("http://soacourse.unsw.edu.au/pinkslipdefinitions", "ageMessage");
    private final static QName _ApprovalMessage_QNAME = new QName("http://soacourse.unsw.edu.au/pinkslipdefinitions", "approvalMessage");
    private final static QName _RenewalInformationMessage_QNAME = new QName("http://soacourse.unsw.edu.au/pinkslipdefinitions", "renewalInformationMessage");
    private final static QName _VehicleAgeMessage_QNAME = new QName("http://soacourse.unsw.edu.au/pinkslipdefinitions", "vehicleAgeMessage");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: au.edu.unsw.soacourse.pinkslipdefinitions
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AgeType }
     * 
     */
    public AgeType createAgeType() {
        return new AgeType();
    }

    /**
     * Create an instance of {@link ApprovalType }
     * 
     */
    public ApprovalType createApprovalType() {
        return new ApprovalType();
    }

    /**
     * Create an instance of {@link RenewalInputType }
     * 
     */
    public RenewalInputType createRenewalInputType() {
        return new RenewalInputType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AgeType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soacourse.unsw.edu.au/pinkslipdefinitions", name = "ageMessage")
    public JAXBElement<AgeType> createAgeMessage(AgeType value) {
        return new JAXBElement<AgeType>(_AgeMessage_QNAME, AgeType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ApprovalType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soacourse.unsw.edu.au/pinkslipdefinitions", name = "approvalMessage")
    public JAXBElement<ApprovalType> createApprovalMessage(ApprovalType value) {
        return new JAXBElement<ApprovalType>(_ApprovalMessage_QNAME, ApprovalType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RenewalInputType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soacourse.unsw.edu.au/pinkslipdefinitions", name = "renewalInformationMessage")
    public JAXBElement<RenewalInputType> createRenewalInformationMessage(RenewalInputType value) {
        return new JAXBElement<RenewalInputType>(_RenewalInformationMessage_QNAME, RenewalInputType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RenewalInputType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soacourse.unsw.edu.au/pinkslipdefinitions", name = "vehicleAgeMessage")
    public JAXBElement<RenewalInputType> createVehicleAgeMessage(RenewalInputType value) {
        return new JAXBElement<RenewalInputType>(_VehicleAgeMessage_QNAME, RenewalInputType.class, null, value);
    }

}

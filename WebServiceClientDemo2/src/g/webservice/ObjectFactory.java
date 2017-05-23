
package g.webservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the g.webservice package. 
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

    private final static QName _GetWeatherByCityNameResponse_QNAME = new QName("http://webservice.g/", "getWeatherByCityNameResponse");
    private final static QName _GetWeatherByCityName_QNAME = new QName("http://webservice.g/", "getWeatherByCityName");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: g.webservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetWeatherByCityNameResponse }
     * 
     */
    public GetWeatherByCityNameResponse createGetWeatherByCityNameResponse() {
        return new GetWeatherByCityNameResponse();
    }

    /**
     * Create an instance of {@link GetWeatherByCityName }
     * 
     */
    public GetWeatherByCityName createGetWeatherByCityName() {
        return new GetWeatherByCityName();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetWeatherByCityNameResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.g/", name = "getWeatherByCityNameResponse")
    public JAXBElement<GetWeatherByCityNameResponse> createGetWeatherByCityNameResponse(GetWeatherByCityNameResponse value) {
        return new JAXBElement<GetWeatherByCityNameResponse>(_GetWeatherByCityNameResponse_QNAME, GetWeatherByCityNameResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetWeatherByCityName }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.g/", name = "getWeatherByCityName")
    public JAXBElement<GetWeatherByCityName> createGetWeatherByCityName(GetWeatherByCityName value) {
        return new JAXBElement<GetWeatherByCityName>(_GetWeatherByCityName_QNAME, GetWeatherByCityName.class, null, value);
    }

}


package com.oyun.media.port;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.port package.
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

    private final static QName _FunctionResponse_QNAME = new QName("http://port.com/", "functionResponse");
    private final static QName _Function_QNAME = new QName("http://port.com/", "function");
    private final static QName _Exception_QNAME = new QName("http://port.com/", "Exception");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.port
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link FunctionResponse }
     * 
     */
    public FunctionResponse createFunctionResponse() {
        return new FunctionResponse();
    }

    /**
     * Create an instance of {@link Function }
     * 
     */
    public Function createFunction() {
        return new Function();
    }

    /**
     * Create an instance of {@link java.lang.Exception }
     * 
     */
    public java.lang.Exception createException() {
        return new java.lang.Exception();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FunctionResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://port.com/", name = "functionResponse")
    public JAXBElement<FunctionResponse> createFunctionResponse(FunctionResponse value) {
        return new JAXBElement<FunctionResponse>(_FunctionResponse_QNAME, FunctionResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Function }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://port.com/", name = "function")
    public JAXBElement<Function> createFunction(Function value) {
        return new JAXBElement<Function>(_Function_QNAME, Function.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link java.lang.Exception }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://port.com/", name = "Exception")
    public JAXBElement<java.lang.Exception> createException(java.lang.Exception value) {
        return new JAXBElement<java.lang.Exception>(_Exception_QNAME, java.lang.Exception.class, null, value);
    }

}


package com.oyun.media.transService;

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

    private final static QName _FileITMDTrans_QNAME = new QName("http://port.com/", "fileITMDTrans");
    private final static QName _FileITMDTransWithSpace_QNAME = new QName("http://port.com/", "fileITMDTransWithSpace");
    private final static QName _Exception_QNAME = new QName("http://port.com/", "Exception");
    private final static QName _FileITMDTransWithSpaceResponse_QNAME = new QName("http://port.com/", "fileITMDTransWithSpaceResponse");
    private final static QName _FileITMDTransResponse_QNAME = new QName("http://port.com/", "fileITMDTransResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.port
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link FileITMDTrans }
     * 
     */
    public FileITMDTrans createFileITMDTrans() {
        return new FileITMDTrans();
    }

    /**
     * Create an instance of {@link FileITMDTransWithSpace }
     * 
     */
    public FileITMDTransWithSpace createFileITMDTransWithSpace() {
        return new FileITMDTransWithSpace();
    }

    /**
     * Create an instance of {@link FileITMDTransResponse }
     * 
     */
    public FileITMDTransResponse createFileITMDTransResponse() {
        return new FileITMDTransResponse();
    }

    /**
     * Create an instance of {@link java.lang.Exception }
     * 
     */
    public java.lang.Exception createException() {
        return new java.lang.Exception();
    }

    /**
     * Create an instance of {@link FileITMDTransWithSpaceResponse }
     * 
     */
    public FileITMDTransWithSpaceResponse createFileITMDTransWithSpaceResponse() {
        return new FileITMDTransWithSpaceResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FileITMDTrans }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://port.com/", name = "fileITMDTrans")
    public JAXBElement<FileITMDTrans> createFileITMDTrans(FileITMDTrans value) {
        return new JAXBElement<FileITMDTrans>(_FileITMDTrans_QNAME, FileITMDTrans.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FileITMDTransWithSpace }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://port.com/", name = "fileITMDTransWithSpace")
    public JAXBElement<FileITMDTransWithSpace> createFileITMDTransWithSpace(FileITMDTransWithSpace value) {
        return new JAXBElement<FileITMDTransWithSpace>(_FileITMDTransWithSpace_QNAME, FileITMDTransWithSpace.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link java.lang.Exception }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://port.com/", name = "Exception")
    public JAXBElement<java.lang.Exception> createException(java.lang.Exception value) {
        return new JAXBElement<java.lang.Exception>(_Exception_QNAME, java.lang.Exception.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FileITMDTransWithSpaceResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://port.com/", name = "fileITMDTransWithSpaceResponse")
    public JAXBElement<FileITMDTransWithSpaceResponse> createFileITMDTransWithSpaceResponse(FileITMDTransWithSpaceResponse value) {
        return new JAXBElement<FileITMDTransWithSpaceResponse>(_FileITMDTransWithSpaceResponse_QNAME, FileITMDTransWithSpaceResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FileITMDTransResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://port.com/", name = "fileITMDTransResponse")
    public JAXBElement<FileITMDTransResponse> createFileITMDTransResponse(FileITMDTransResponse value) {
        return new JAXBElement<FileITMDTransResponse>(_FileITMDTransResponse_QNAME, FileITMDTransResponse.class, null, value);
    }

}

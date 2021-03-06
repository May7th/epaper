
package com.oyun.media.transService;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebFault(name = "Exception", targetNamespace = "http://port.com/")
public class Exception_Exception
    extends java.lang.Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private com.oyun.media.port.Exception faultInfo;

    /**
     *
     * @param faultInfo
     * @param message
     */
    public Exception_Exception(String message, com.oyun.media.port.Exception faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     *
     * @param faultInfo
     * @param cause
     * @param message
     */
    public Exception_Exception(String message, com.oyun.media.port.Exception faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     *
     * @return
     *     returns fault bean: com.port.Exception
     */
    public com.oyun.media.port.Exception getFaultInfo() {
        return faultInfo;
    }

}

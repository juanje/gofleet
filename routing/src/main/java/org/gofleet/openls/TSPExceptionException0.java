
/**
 * TSPExceptionException0.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.4.1  Built on : Aug 13, 2008 (05:03:35 LKT)
 */

package org.gofleet.openls;

public class TSPExceptionException0 extends java.lang.Exception{
    
    private org.gofleet.openls.RoutingServiceStub.TSPExceptionE faultMessage;
    
    public TSPExceptionException0() {
        super("TSPExceptionException0");
    }
           
    public TSPExceptionException0(java.lang.String s) {
       super(s);
    }
    
    public TSPExceptionException0(java.lang.String s, java.lang.Throwable ex) {
      super(s, ex);
    }
    
    public void setFaultMessage(org.gofleet.openls.RoutingServiceStub.TSPExceptionE msg){
       faultMessage = msg;
    }
    
    public org.gofleet.openls.RoutingServiceStub.TSPExceptionE getFaultMessage(){
       return faultMessage;
    }
}
    
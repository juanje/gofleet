/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.1.2</a>, using an XML
 * Schema.
 * $Id: PtType.java 358 2008-11-24 19:06:17Z ringler $
 */

package net.sourceforge.gpstools.gpx;

/**
 * A geographic point with optional elevation and time. Available
 * for use by other schemas.
 *  
 * 
 * @version $Revision: 358 $ $Date: 2007-07-16 16:55:47 +0200 (Mo, 16 Jul 2007) $
 */
@SuppressWarnings("serial")
public abstract class PtType implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The latitude of the point. Decimal degrees, WGS84 datum.
     *  
     */
    private java.math.BigDecimal _lat;

    /**
     * The latitude of the point. Decimal degrees, WGS84 datum.
     *  
     */
    private java.math.BigDecimal _lon;

    /**
     * The elevation (in meters) of the point.
     *  
     */
    private java.math.BigDecimal _ele;

    /**
     * The time that the point was recorded.
     *  
     */
    private java.util.Date _time;


      //----------------/
     //- Constructors -/
    //----------------/

    public PtType() {
        super();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'ele'. The field 'ele' has the
     * following description: The elevation (in meters) of the
     * point.
     *  
     * 
     * @return the value of field 'Ele'.
     */
    public java.math.BigDecimal getEle(
    ) {
        return this._ele;
    }

    /**
     * Returns the value of field 'lat'. The field 'lat' has the
     * following description: The latitude of the point. Decimal
     * degrees, WGS84 datum.
     *  
     * 
     * @return the value of field 'Lat'.
     */
    public java.math.BigDecimal getLat(
    ) {
        return this._lat;
    }

    /**
     * Returns the value of field 'lon'. The field 'lon' has the
     * following description: The latitude of the point. Decimal
     * degrees, WGS84 datum.
     *  
     * 
     * @return the value of field 'Lon'.
     */
    public java.math.BigDecimal getLon(
    ) {
        return this._lon;
    }

    /**
     * Returns the value of field 'time'. The field 'time' has the
     * following description: The time that the point was recorded.
     *  
     * 
     * @return the value of field 'Time'.
     */
    public java.util.Date getTime(
    ) {
        return this._time;
    }

    /**
     * Method isValid.
     * 
     * @return true if this object is valid according to the schema
     */
    public boolean isValid(
    ) {
        try {
            validate();
        } catch (org.exolab.castor.xml.ValidationException vex) {
            return false;
        }
        return true;
    }

    /**
     * Sets the value of field 'ele'. The field 'ele' has the
     * following description: The elevation (in meters) of the
     * point.
     *  
     * 
     * @param ele the value of field 'ele'.
     */
    public void setEle(
            final java.math.BigDecimal ele) {
        this._ele = ele;
    }

    /**
     * Sets the value of field 'lat'. The field 'lat' has the
     * following description: The latitude of the point. Decimal
     * degrees, WGS84 datum.
     *  
     * 
     * @param lat the value of field 'lat'.
     */
    public void setLat(
            final java.math.BigDecimal lat) {
        this._lat = lat;
    }

    /**
     * Sets the value of field 'lon'. The field 'lon' has the
     * following description: The latitude of the point. Decimal
     * degrees, WGS84 datum.
     *  
     * 
     * @param lon the value of field 'lon'.
     */
    public void setLon(
            final java.math.BigDecimal lon) {
        this._lon = lon;
    }

    /**
     * Sets the value of field 'time'. The field 'time' has the
     * following description: The time that the point was recorded.
     *  
     * 
     * @param time the value of field 'time'.
     */
    public void setTime(
            final java.util.Date time) {
        this._time = time;
    }

    /**
     * 
     * 
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     */
    public void validate(
    )
    throws org.exolab.castor.xml.ValidationException {
        org.exolab.castor.xml.Validator validator = new org.exolab.castor.xml.Validator();
        validator.validate(this);
    }

}

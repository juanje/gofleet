/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.1.2</a>, using an XML
 * Schema.
 * $Id: BoundsType.java 358 2008-11-24 19:06:17Z ringler $
 */

package net.sourceforge.gpstools.gpx;

/**
 * Two lat/lon pairs defining the extent of an element.
 *  
 * 
 * @version $Revision: 358 $ $Date: 2007-07-16 16:55:47 +0200 (Mo, 16 Jul 2007) $
 */
@SuppressWarnings("serial")
public abstract class BoundsType implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The minimum latitude.
     *  
     */
    private java.math.BigDecimal _minlat;

    /**
     * The minimum longitude.
     *  
     */
    private java.math.BigDecimal _minlon;

    /**
     * The maximum latitude.
     *  
     */
    private java.math.BigDecimal _maxlat;

    /**
     * The maximum longitude.
     *  
     */
    private java.math.BigDecimal _maxlon;


      //----------------/
     //- Constructors -/
    //----------------/

    public BoundsType() {
        super();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'maxlat'. The field 'maxlat' has
     * the following description: The maximum latitude.
     *  
     * 
     * @return the value of field 'Maxlat'.
     */
    public java.math.BigDecimal getMaxlat(
    ) {
        return this._maxlat;
    }

    /**
     * Returns the value of field 'maxlon'. The field 'maxlon' has
     * the following description: The maximum longitude.
     *  
     * 
     * @return the value of field 'Maxlon'.
     */
    public java.math.BigDecimal getMaxlon(
    ) {
        return this._maxlon;
    }

    /**
     * Returns the value of field 'minlat'. The field 'minlat' has
     * the following description: The minimum latitude.
     *  
     * 
     * @return the value of field 'Minlat'.
     */
    public java.math.BigDecimal getMinlat(
    ) {
        return this._minlat;
    }

    /**
     * Returns the value of field 'minlon'. The field 'minlon' has
     * the following description: The minimum longitude.
     *  
     * 
     * @return the value of field 'Minlon'.
     */
    public java.math.BigDecimal getMinlon(
    ) {
        return this._minlon;
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
     * Sets the value of field 'maxlat'. The field 'maxlat' has the
     * following description: The maximum latitude.
     *  
     * 
     * @param maxlat the value of field 'maxlat'.
     */
    public void setMaxlat(
            final java.math.BigDecimal maxlat) {
        this._maxlat = maxlat;
    }

    /**
     * Sets the value of field 'maxlon'. The field 'maxlon' has the
     * following description: The maximum longitude.
     *  
     * 
     * @param maxlon the value of field 'maxlon'.
     */
    public void setMaxlon(
            final java.math.BigDecimal maxlon) {
        this._maxlon = maxlon;
    }

    /**
     * Sets the value of field 'minlat'. The field 'minlat' has the
     * following description: The minimum latitude.
     *  
     * 
     * @param minlat the value of field 'minlat'.
     */
    public void setMinlat(
            final java.math.BigDecimal minlat) {
        this._minlat = minlat;
    }

    /**
     * Sets the value of field 'minlon'. The field 'minlon' has the
     * following description: The minimum longitude.
     *  
     * 
     * @param minlon the value of field 'minlon'.
     */
    public void setMinlon(
            final java.math.BigDecimal minlon) {
        this._minlon = minlon;
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

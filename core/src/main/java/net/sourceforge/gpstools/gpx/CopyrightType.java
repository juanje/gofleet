/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.1.2</a>, using an XML
 * Schema.
 * $Id: CopyrightType.java 358 2008-11-24 19:06:17Z ringler $
 */

package net.sourceforge.gpstools.gpx;

/**
 * Information about the copyright holder and any license governing
 * use of this file. By linking to an appropriate license,
 *  you may place your data into the public domain or grant
 * additional usage rights.
 *  
 * 
 * @version $Revision: 358 $ $Date: 2007-07-16 16:55:47 +0200 (Mo, 16 Jul 2007) $
 */
@SuppressWarnings("serial")
public abstract class CopyrightType implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Copyright holder (TopoSoft, Inc.)
     *  
     */
    private java.lang.String _author;

    /**
     * Year of copyright.
     *  
     */
    private org.exolab.castor.types.GYear _year;

    /**
     * Link to external file containing license text.
     *  
     */
    private java.lang.String _license;


      //----------------/
     //- Constructors -/
    //----------------/

    public CopyrightType() {
        super();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'author'. The field 'author' has
     * the following description: Copyright holder (TopoSoft, Inc.)
     *  
     * 
     * @return the value of field 'Author'.
     */
    public java.lang.String getAuthor(
    ) {
        return this._author;
    }

    /**
     * Returns the value of field 'license'. The field 'license'
     * has the following description: Link to external file
     * containing license text.
     *  
     * 
     * @return the value of field 'License'.
     */
    public java.lang.String getLicense(
    ) {
        return this._license;
    }

    /**
     * Returns the value of field 'year'. The field 'year' has the
     * following description: Year of copyright.
     *  
     * 
     * @return the value of field 'Year'.
     */
    public org.exolab.castor.types.GYear getYear(
    ) {
        return this._year;
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
     * Sets the value of field 'author'. The field 'author' has the
     * following description: Copyright holder (TopoSoft, Inc.)
     *  
     * 
     * @param author the value of field 'author'.
     */
    public void setAuthor(
            final java.lang.String author) {
        this._author = author;
    }

    /**
     * Sets the value of field 'license'. The field 'license' has
     * the following description: Link to external file containing
     * license text.
     *  
     * 
     * @param license the value of field 'license'.
     */
    public void setLicense(
            final java.lang.String license) {
        this._license = license;
    }

    /**
     * Sets the value of field 'year'. The field 'year' has the
     * following description: Year of copyright.
     *  
     * 
     * @param year the value of field 'year'.
     */
    public void setYear(
            final org.exolab.castor.types.GYear year) {
        this._year = year;
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

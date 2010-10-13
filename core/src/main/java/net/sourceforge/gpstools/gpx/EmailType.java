/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.1.2</a>, using an XML
 * Schema.
 * $Id: EmailType.java 358 2008-11-24 19:06:17Z ringler $
 */

package net.sourceforge.gpstools.gpx;

/**
 * An email address. Broken into two parts (id and domain) to help
 * prevent email harvesting.
 *  
 * 
 * @version $Revision: 358 $ $Date: 2007-07-16 16:55:47 +0200 (Mo, 16 Jul 2007) $
 */
@SuppressWarnings("serial")
public abstract class EmailType implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * id half of email address (billgates2004)
     *  
     */
    private java.lang.String _id;

    /**
     * domain half of email address (hotmail.com)
     *  
     */
    private java.lang.String _domain;


      //----------------/
     //- Constructors -/
    //----------------/

    public EmailType() {
        super();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'domain'. The field 'domain' has
     * the following description: domain half of email address
     * (hotmail.com)
     *  
     * 
     * @return the value of field 'Domain'.
     */
    public java.lang.String getDomain(
    ) {
        return this._domain;
    }

    /**
     * Returns the value of field 'id'. The field 'id' has the
     * following description: id half of email address
     * (billgates2004)
     *  
     * 
     * @return the value of field 'Id'.
     */
    public java.lang.String getId(
    ) {
        return this._id;
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
     * Sets the value of field 'domain'. The field 'domain' has the
     * following description: domain half of email address
     * (hotmail.com)
     *  
     * 
     * @param domain the value of field 'domain'.
     */
    public void setDomain(
            final java.lang.String domain) {
        this._domain = domain;
    }

    /**
     * Sets the value of field 'id'. The field 'id' has the
     * following description: id half of email address
     * (billgates2004)
     *  
     * 
     * @param id the value of field 'id'.
     */
    public void setId(
            final java.lang.String id) {
        this._id = id;
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

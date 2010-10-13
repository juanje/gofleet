/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.1.2</a>, using an XML
 * Schema.
 * $Id: PersonType.java 358 2008-11-24 19:06:17Z ringler $
 */

package net.sourceforge.gpstools.gpx;

/**
 * A person or organization.
 *  
 * 
 * @version $Revision: 358 $ $Date: 2007-07-16 16:55:47 +0200 (Mo, 16 Jul 2007) $
 */
@SuppressWarnings("serial")
public abstract class PersonType implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Name of person or organization.
     *  
     */
    private java.lang.String _name;

    /**
     * Email address.
     *  
     */
    private net.sourceforge.gpstools.gpx.Email _email;

    /**
     * Link to Web site or other external information about person.
     *  
     */
    private net.sourceforge.gpstools.gpx.PersonLink _personLink;


      //----------------/
     //- Constructors -/
    //----------------/

    public PersonType() {
        super();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'email'. The field 'email' has
     * the following description: Email address.
     *  
     * 
     * @return the value of field 'Email'.
     */
    public net.sourceforge.gpstools.gpx.Email getEmail(
    ) {
        return this._email;
    }

    /**
     * Returns the value of field 'name'. The field 'name' has the
     * following description: Name of person or organization.
     *  
     * 
     * @return the value of field 'Name'.
     */
    public java.lang.String getName(
    ) {
        return this._name;
    }

    /**
     * Returns the value of field 'personLink'. The field
     * 'personLink' has the following description: Link to Web site
     * or other external information about person.
     *  
     * 
     * @return the value of field 'PersonLink'.
     */
    public net.sourceforge.gpstools.gpx.PersonLink getPersonLink(
    ) {
        return this._personLink;
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
     * Sets the value of field 'email'. The field 'email' has the
     * following description: Email address.
     *  
     * 
     * @param email the value of field 'email'.
     */
    public void setEmail(
            final net.sourceforge.gpstools.gpx.Email email) {
        this._email = email;
    }

    /**
     * Sets the value of field 'name'. The field 'name' has the
     * following description: Name of person or organization.
     *  
     * 
     * @param name the value of field 'name'.
     */
    public void setName(
            final java.lang.String name) {
        this._name = name;
    }

    /**
     * Sets the value of field 'personLink'. The field 'personLink'
     * has the following description: Link to Web site or other
     * external information about person.
     *  
     * 
     * @param personLink the value of field 'personLink'.
     */
    public void setPersonLink(
            final net.sourceforge.gpstools.gpx.PersonLink personLink) {
        this._personLink = personLink;
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

/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.1.2</a>, using an XML
 * Schema.
 * $Id: LinkType.java 358 2008-11-24 19:06:17Z ringler $
 */

package net.sourceforge.gpstools.gpx;

/**
 * A link to an external resource (Web page, digital photo, video
 * clip, etc) with additional information.
 *  
 * 
 * @version $Revision: 358 $ $Date: 2007-07-16 16:55:47 +0200 (Mo, 16 Jul 2007) $
 */
@SuppressWarnings("serial")
public abstract class LinkType implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * URL of hyperlink.
     *  
     */
    private java.lang.String _href;

    /**
     * Text of hyperlink.
     *  
     */
    private java.lang.String _text;

    /**
     * Mime type of content (image/jpeg)
     *  
     */
    private java.lang.String _type;


      //----------------/
     //- Constructors -/
    //----------------/

    public LinkType() {
        super();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Returns the value of field 'href'. The field 'href' has the
     * following description: URL of hyperlink.
     *  
     * 
     * @return the value of field 'Href'.
     */
    public java.lang.String getHref(
    ) {
        return this._href;
    }

    /**
     * Returns the value of field 'text'. The field 'text' has the
     * following description: Text of hyperlink.
     *  
     * 
     * @return the value of field 'Text'.
     */
    public java.lang.String getText(
    ) {
        return this._text;
    }

    /**
     * Returns the value of field 'type'. The field 'type' has the
     * following description: Mime type of content (image/jpeg)
     *  
     * 
     * @return the value of field 'Type'.
     */
    public java.lang.String getType(
    ) {
        return this._type;
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
     * Sets the value of field 'href'. The field 'href' has the
     * following description: URL of hyperlink.
     *  
     * 
     * @param href the value of field 'href'.
     */
    public void setHref(
            final java.lang.String href) {
        this._href = href;
    }

    /**
     * Sets the value of field 'text'. The field 'text' has the
     * following description: Text of hyperlink.
     *  
     * 
     * @param text the value of field 'text'.
     */
    public void setText(
            final java.lang.String text) {
        this._text = text;
    }

    /**
     * Sets the value of field 'type'. The field 'type' has the
     * following description: Mime type of content (image/jpeg)
     *  
     * 
     * @param type the value of field 'type'.
     */
    public void setType(
            final java.lang.String type) {
        this._type = type;
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

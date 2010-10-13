/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.1.2</a>, using an XML
 * Schema.
 * $Id: MetadataType.java 358 2008-11-24 19:06:17Z ringler $
 */

package net.sourceforge.gpstools.gpx;

/**
 * Information about the GPX file, author, and copyright
 * restrictions goes in the metadata section. Providing rich,
 *  meaningful information about your GPX files allows others to
 * search for and use your GPS data.
 *  
 * 
 * @version $Revision: 358 $ $Date: 2007-07-16 16:55:47 +0200 (Mo, 16 Jul 2007) $
 */
@SuppressWarnings("serial")
public abstract class MetadataType implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The name of the GPX file.
     *  
     */
    private java.lang.String _name;

    /**
     * A description of the contents of the GPX file.
     *  
     */
    private java.lang.String _desc;

    /**
     * The person or organization who created the GPX file.
     *  
     */
    private net.sourceforge.gpstools.gpx.Author _author;

    /**
     * Copyright and license information governing use of the file.
     *  
     */
    private net.sourceforge.gpstools.gpx.Copyright _copyright;

    /**
     * URLs associated with the location described in the file.
     *  
     */
    private java.util.List<net.sourceforge.gpstools.gpx.MetadataLink> _metadataLinkList;

    /**
     * The creation date of the file.
     *  
     */
    private java.util.Date _time;

    /**
     * Keywords associated with the file. Search engines or
     * databases can use this information to classify the data.
     *  
     */
    private java.lang.String _keywords;

    /**
     * Minimum and maximum coordinates which describe the extent of
     * the coordinates in the file.
     *  
     */
    private net.sourceforge.gpstools.gpx.Bounds _bounds;

    /**
     * You can add extend GPX by adding your own elements from
     * another schema here.
     *  
     */
    private net.sourceforge.gpstools.gpx.MetadataExtensions _metadataExtensions;


      //----------------/
     //- Constructors -/
    //----------------/

    public MetadataType() {
        super();
        this._metadataLinkList = new java.util.ArrayList<net.sourceforge.gpstools.gpx.MetadataLink>();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * 
     * 
     * @param vMetadataLink
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addMetadataLink(
            final net.sourceforge.gpstools.gpx.MetadataLink vMetadataLink)
    throws java.lang.IndexOutOfBoundsException {
        this._metadataLinkList.add(vMetadataLink);
    }

    /**
     * 
     * 
     * @param index
     * @param vMetadataLink
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addMetadataLink(
            final int index,
            final net.sourceforge.gpstools.gpx.MetadataLink vMetadataLink)
    throws java.lang.IndexOutOfBoundsException {
        this._metadataLinkList.add(index, vMetadataLink);
    }

    /**
     * Method enumerateMetadataLink.
     * 
     * @return an Enumeration over all possible elements of this
     * collection
     */
    public java.util.Enumeration<net.sourceforge.gpstools.gpx.MetadataLink> enumerateMetadataLink(
    ) {
        return java.util.Collections.enumeration(this._metadataLinkList);
    }

    /**
     * Returns the value of field 'author'. The field 'author' has
     * the following description: The person or organization who
     * created the GPX file.
     *  
     * 
     * @return the value of field 'Author'.
     */
    public net.sourceforge.gpstools.gpx.Author getAuthor(
    ) {
        return this._author;
    }

    /**
     * Returns the value of field 'bounds'. The field 'bounds' has
     * the following description: Minimum and maximum coordinates
     * which describe the extent of the coordinates in the file.
     *  
     * 
     * @return the value of field 'Bounds'.
     */
    public net.sourceforge.gpstools.gpx.Bounds getBounds(
    ) {
        return this._bounds;
    }

    /**
     * Returns the value of field 'copyright'. The field
     * 'copyright' has the following description: Copyright and
     * license information governing use of the file.
     *  
     * 
     * @return the value of field 'Copyright'.
     */
    public net.sourceforge.gpstools.gpx.Copyright getCopyright(
    ) {
        return this._copyright;
    }

    /**
     * Returns the value of field 'desc'. The field 'desc' has the
     * following description: A description of the contents of the
     * GPX file.
     *  
     * 
     * @return the value of field 'Desc'.
     */
    public java.lang.String getDesc(
    ) {
        return this._desc;
    }

    /**
     * Returns the value of field 'keywords'. The field 'keywords'
     * has the following description: Keywords associated with the
     * file. Search engines or databases can use this information
     * to classify the data.
     *  
     * 
     * @return the value of field 'Keywords'.
     */
    public java.lang.String getKeywords(
    ) {
        return this._keywords;
    }

    /**
     * Returns the value of field 'metadataExtensions'. The field
     * 'metadataExtensions' has the following description: You can
     * add extend GPX by adding your own elements from another
     * schema here.
     *  
     * 
     * @return the value of field 'MetadataExtensions'.
     */
    public net.sourceforge.gpstools.gpx.MetadataExtensions getMetadataExtensions(
    ) {
        return this._metadataExtensions;
    }

    /**
     * Method getMetadataLink.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the
     * net.sourceforge.gpstools.gpx.MetadataLink at the given index
     */
    public net.sourceforge.gpstools.gpx.MetadataLink getMetadataLink(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._metadataLinkList.size()) {
            throw new IndexOutOfBoundsException("getMetadataLink: Index value '" + index + "' not in range [0.." + (this._metadataLinkList.size() - 1) + "]");
        }
        
        return (net.sourceforge.gpstools.gpx.MetadataLink) _metadataLinkList.get(index);
    }

    /**
     * Method getMetadataLink.Returns the contents of the
     * collection in an Array.  <p>Note:  Just in case the
     * collection contents are changing in another thread, we pass
     * a 0-length Array of the correct type into the API call. 
     * This way we <i>know</i> that the Array returned is of
     * exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public net.sourceforge.gpstools.gpx.MetadataLink[] getMetadataLink(
    ) {
        net.sourceforge.gpstools.gpx.MetadataLink[] array = new net.sourceforge.gpstools.gpx.MetadataLink[0];
        return (net.sourceforge.gpstools.gpx.MetadataLink[]) this._metadataLinkList.toArray(array);
    }

    /**
     * Method getMetadataLinkCount.
     * 
     * @return the size of this collection
     */
    public int getMetadataLinkCount(
    ) {
        return this._metadataLinkList.size();
    }

    /**
     * Returns the value of field 'name'. The field 'name' has the
     * following description: The name of the GPX file.
     *  
     * 
     * @return the value of field 'Name'.
     */
    public java.lang.String getName(
    ) {
        return this._name;
    }

    /**
     * Returns the value of field 'time'. The field 'time' has the
     * following description: The creation date of the file.
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
     * Method iterateMetadataLink.
     * 
     * @return an Iterator over all possible elements in this
     * collection
     */
    public java.util.Iterator<net.sourceforge.gpstools.gpx.MetadataLink> iterateMetadataLink(
    ) {
        return this._metadataLinkList.iterator();
    }

    /**
     */
    public void removeAllMetadataLink(
    ) {
        this._metadataLinkList.clear();
    }

    /**
     * Method removeMetadataLink.
     * 
     * @param vMetadataLink
     * @return true if the object was removed from the collection.
     */
    public boolean removeMetadataLink(
            final net.sourceforge.gpstools.gpx.MetadataLink vMetadataLink) {
        boolean removed = _metadataLinkList.remove(vMetadataLink);
        return removed;
    }

    /**
     * Method removeMetadataLinkAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public net.sourceforge.gpstools.gpx.MetadataLink removeMetadataLinkAt(
            final int index) {
        java.lang.Object obj = this._metadataLinkList.remove(index);
        return (net.sourceforge.gpstools.gpx.MetadataLink) obj;
    }

    /**
     * Sets the value of field 'author'. The field 'author' has the
     * following description: The person or organization who
     * created the GPX file.
     *  
     * 
     * @param author the value of field 'author'.
     */
    public void setAuthor(
            final net.sourceforge.gpstools.gpx.Author author) {
        this._author = author;
    }

    /**
     * Sets the value of field 'bounds'. The field 'bounds' has the
     * following description: Minimum and maximum coordinates which
     * describe the extent of the coordinates in the file.
     *  
     * 
     * @param bounds the value of field 'bounds'.
     */
    public void setBounds(
            final net.sourceforge.gpstools.gpx.Bounds bounds) {
        this._bounds = bounds;
    }

    /**
     * Sets the value of field 'copyright'. The field 'copyright'
     * has the following description: Copyright and license
     * information governing use of the file.
     *  
     * 
     * @param copyright the value of field 'copyright'.
     */
    public void setCopyright(
            final net.sourceforge.gpstools.gpx.Copyright copyright) {
        this._copyright = copyright;
    }

    /**
     * Sets the value of field 'desc'. The field 'desc' has the
     * following description: A description of the contents of the
     * GPX file.
     *  
     * 
     * @param desc the value of field 'desc'.
     */
    public void setDesc(
            final java.lang.String desc) {
        this._desc = desc;
    }

    /**
     * Sets the value of field 'keywords'. The field 'keywords' has
     * the following description: Keywords associated with the
     * file. Search engines or databases can use this information
     * to classify the data.
     *  
     * 
     * @param keywords the value of field 'keywords'.
     */
    public void setKeywords(
            final java.lang.String keywords) {
        this._keywords = keywords;
    }

    /**
     * Sets the value of field 'metadataExtensions'. The field
     * 'metadataExtensions' has the following description: You can
     * add extend GPX by adding your own elements from another
     * schema here.
     *  
     * 
     * @param metadataExtensions the value of field
     * 'metadataExtensions'.
     */
    public void setMetadataExtensions(
            final net.sourceforge.gpstools.gpx.MetadataExtensions metadataExtensions) {
        this._metadataExtensions = metadataExtensions;
    }

    /**
     * 
     * 
     * @param index
     * @param vMetadataLink
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setMetadataLink(
            final int index,
            final net.sourceforge.gpstools.gpx.MetadataLink vMetadataLink)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._metadataLinkList.size()) {
            throw new IndexOutOfBoundsException("setMetadataLink: Index value '" + index + "' not in range [0.." + (this._metadataLinkList.size() - 1) + "]");
        }
        
        this._metadataLinkList.set(index, vMetadataLink);
    }

    /**
     * 
     * 
     * @param vMetadataLinkArray
     */
    public void setMetadataLink(
            final net.sourceforge.gpstools.gpx.MetadataLink[] vMetadataLinkArray) {
        //-- copy array
        _metadataLinkList.clear();
        
        for (int i = 0; i < vMetadataLinkArray.length; i++) {
                this._metadataLinkList.add(vMetadataLinkArray[i]);
        }
    }

    /**
     * Sets the value of field 'name'. The field 'name' has the
     * following description: The name of the GPX file.
     *  
     * 
     * @param name the value of field 'name'.
     */
    public void setName(
            final java.lang.String name) {
        this._name = name;
    }

    /**
     * Sets the value of field 'time'. The field 'time' has the
     * following description: The creation date of the file.
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

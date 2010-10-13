/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.1.2</a>, using an XML
 * Schema.
 * $Id: RteType.java 358 2008-11-24 19:06:17Z ringler $
 */

package net.sourceforge.gpstools.gpx;

/**
 * rte represents route - an ordered list of waypoints representing
 * a series of turn points leading to a destination.
 *  
 * 
 * @version $Revision: 358 $ $Date: 2007-07-16 16:55:47 +0200 (Mo, 16 Jul 2007) $
 */
@SuppressWarnings("serial")
public abstract class RteType implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * GPS name of route.
     *  
     */
    private java.lang.String _name;

    /**
     * GPS comment for route.
     *  
     */
    private java.lang.String _cmt;

    /**
     * Text description of route for user. Not sent to GPS.
     *  
     */
    private java.lang.String _desc;

    /**
     * Source of data. Included to give user some idea of
     * reliability and accuracy of data.
     *  
     */
    private java.lang.String _src;

    /**
     * Links to external information about the route.
     *  
     */
    private java.util.List<net.sourceforge.gpstools.gpx.RteLink> _rteLinkList;

    /**
     * GPS route number.
     *  
     */
    private long _number;

    /**
     * keeps track of state for field: _number
     */
    private boolean _has_number;

    /**
     * Type (classification) of route.
     *  
     */
    private java.lang.String _type;

    /**
     * You can add extend GPX by adding your own elements from
     * another schema here.
     *  
     */
    private net.sourceforge.gpstools.gpx.RteExtensions _rteExtensions;

    /**
     * A list of route points.
     *  
     */
    private java.util.List<net.sourceforge.gpstools.gpx.Rtept> _rteptList;


      //----------------/
     //- Constructors -/
    //----------------/

    public RteType() {
        super();
        this._rteLinkList = new java.util.ArrayList<net.sourceforge.gpstools.gpx.RteLink>();
        this._rteptList = new java.util.ArrayList<net.sourceforge.gpstools.gpx.Rtept>();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * 
     * 
     * @param vRteLink
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addRteLink(
            final net.sourceforge.gpstools.gpx.RteLink vRteLink)
    throws java.lang.IndexOutOfBoundsException {
        this._rteLinkList.add(vRteLink);
    }

    /**
     * 
     * 
     * @param index
     * @param vRteLink
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addRteLink(
            final int index,
            final net.sourceforge.gpstools.gpx.RteLink vRteLink)
    throws java.lang.IndexOutOfBoundsException {
        this._rteLinkList.add(index, vRteLink);
    }

    /**
     * 
     * 
     * @param vRtept
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addRtept(
            final net.sourceforge.gpstools.gpx.Rtept vRtept)
    throws java.lang.IndexOutOfBoundsException {
        this._rteptList.add(vRtept);
    }

    /**
     * 
     * 
     * @param index
     * @param vRtept
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addRtept(
            final int index,
            final net.sourceforge.gpstools.gpx.Rtept vRtept)
    throws java.lang.IndexOutOfBoundsException {
        this._rteptList.add(index, vRtept);
    }

    /**
     */
    public void deleteNumber(
    ) {
        this._has_number= false;
    }

    /**
     * Method enumerateRteLink.
     * 
     * @return an Enumeration over all possible elements of this
     * collection
     */
    public java.util.Enumeration<net.sourceforge.gpstools.gpx.RteLink> enumerateRteLink(
    ) {
        return java.util.Collections.enumeration(this._rteLinkList);
    }

    /**
     * Method enumerateRtept.
     * 
     * @return an Enumeration over all possible elements of this
     * collection
     */
    public java.util.Enumeration<net.sourceforge.gpstools.gpx.Rtept> enumerateRtept(
    ) {
        return java.util.Collections.enumeration(this._rteptList);
    }

    /**
     * Returns the value of field 'cmt'. The field 'cmt' has the
     * following description: GPS comment for route.
     *  
     * 
     * @return the value of field 'Cmt'.
     */
    public java.lang.String getCmt(
    ) {
        return this._cmt;
    }

    /**
     * Returns the value of field 'desc'. The field 'desc' has the
     * following description: Text description of route for user.
     * Not sent to GPS.
     *  
     * 
     * @return the value of field 'Desc'.
     */
    public java.lang.String getDesc(
    ) {
        return this._desc;
    }

    /**
     * Returns the value of field 'name'. The field 'name' has the
     * following description: GPS name of route.
     *  
     * 
     * @return the value of field 'Name'.
     */
    public java.lang.String getName(
    ) {
        return this._name;
    }

    /**
     * Returns the value of field 'number'. The field 'number' has
     * the following description: GPS route number.
     *  
     * 
     * @return the value of field 'Number'.
     */
    public long getNumber(
    ) {
        return this._number;
    }

    /**
     * Returns the value of field 'rteExtensions'. The field
     * 'rteExtensions' has the following description: You can add
     * extend GPX by adding your own elements from another schema
     * here.
     *  
     * 
     * @return the value of field 'RteExtensions'.
     */
    public net.sourceforge.gpstools.gpx.RteExtensions getRteExtensions(
    ) {
        return this._rteExtensions;
    }

    /**
     * Method getRteLink.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the
     * net.sourceforge.gpstools.gpx.RteLink at the given index
     */
    public net.sourceforge.gpstools.gpx.RteLink getRteLink(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._rteLinkList.size()) {
            throw new IndexOutOfBoundsException("getRteLink: Index value '" + index + "' not in range [0.." + (this._rteLinkList.size() - 1) + "]");
        }
        
        return (net.sourceforge.gpstools.gpx.RteLink) _rteLinkList.get(index);
    }

    /**
     * Method getRteLink.Returns the contents of the collection in
     * an Array.  <p>Note:  Just in case the collection contents
     * are changing in another thread, we pass a 0-length Array of
     * the correct type into the API call.  This way we <i>know</i>
     * that the Array returned is of exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public net.sourceforge.gpstools.gpx.RteLink[] getRteLink(
    ) {
        net.sourceforge.gpstools.gpx.RteLink[] array = new net.sourceforge.gpstools.gpx.RteLink[0];
        return (net.sourceforge.gpstools.gpx.RteLink[]) this._rteLinkList.toArray(array);
    }

    /**
     * Method getRteLinkCount.
     * 
     * @return the size of this collection
     */
    public int getRteLinkCount(
    ) {
        return this._rteLinkList.size();
    }

    /**
     * Method getRtept.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the net.sourceforge.gpstools.gpx.Rtept
     * at the given index
     */
    public net.sourceforge.gpstools.gpx.Rtept getRtept(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._rteptList.size()) {
            throw new IndexOutOfBoundsException("getRtept: Index value '" + index + "' not in range [0.." + (this._rteptList.size() - 1) + "]");
        }
        
        return (net.sourceforge.gpstools.gpx.Rtept) _rteptList.get(index);
    }

    /**
     * Method getRtept.Returns the contents of the collection in an
     * Array.  <p>Note:  Just in case the collection contents are
     * changing in another thread, we pass a 0-length Array of the
     * correct type into the API call.  This way we <i>know</i>
     * that the Array returned is of exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public net.sourceforge.gpstools.gpx.Rtept[] getRtept(
    ) {
        net.sourceforge.gpstools.gpx.Rtept[] array = new net.sourceforge.gpstools.gpx.Rtept[0];
        return (net.sourceforge.gpstools.gpx.Rtept[]) this._rteptList.toArray(array);
    }

    /**
     * Method getRteptCount.
     * 
     * @return the size of this collection
     */
    public int getRteptCount(
    ) {
        return this._rteptList.size();
    }

    /**
     * Returns the value of field 'src'. The field 'src' has the
     * following description: Source of data. Included to give user
     * some idea of reliability and accuracy of data.
     *  
     * 
     * @return the value of field 'Src'.
     */
    public java.lang.String getSrc(
    ) {
        return this._src;
    }

    /**
     * Returns the value of field 'type'. The field 'type' has the
     * following description: Type (classification) of route.
     *  
     * 
     * @return the value of field 'Type'.
     */
    public java.lang.String getType(
    ) {
        return this._type;
    }

    /**
     * Method hasNumber.
     * 
     * @return true if at least one Number has been added
     */
    public boolean hasNumber(
    ) {
        return this._has_number;
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
     * Method iterateRteLink.
     * 
     * @return an Iterator over all possible elements in this
     * collection
     */
    public java.util.Iterator<net.sourceforge.gpstools.gpx.RteLink> iterateRteLink(
    ) {
        return this._rteLinkList.iterator();
    }

    /**
     * Method iterateRtept.
     * 
     * @return an Iterator over all possible elements in this
     * collection
     */
    public java.util.Iterator<net.sourceforge.gpstools.gpx.Rtept> iterateRtept(
    ) {
        return this._rteptList.iterator();
    }

    /**
     */
    public void removeAllRteLink(
    ) {
        this._rteLinkList.clear();
    }

    /**
     */
    public void removeAllRtept(
    ) {
        this._rteptList.clear();
    }

    /**
     * Method removeRteLink.
     * 
     * @param vRteLink
     * @return true if the object was removed from the collection.
     */
    public boolean removeRteLink(
            final net.sourceforge.gpstools.gpx.RteLink vRteLink) {
        boolean removed = _rteLinkList.remove(vRteLink);
        return removed;
    }

    /**
     * Method removeRteLinkAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public net.sourceforge.gpstools.gpx.RteLink removeRteLinkAt(
            final int index) {
        java.lang.Object obj = this._rteLinkList.remove(index);
        return (net.sourceforge.gpstools.gpx.RteLink) obj;
    }

    /**
     * Method removeRtept.
     * 
     * @param vRtept
     * @return true if the object was removed from the collection.
     */
    public boolean removeRtept(
            final net.sourceforge.gpstools.gpx.Rtept vRtept) {
        boolean removed = _rteptList.remove(vRtept);
        return removed;
    }

    /**
     * Method removeRteptAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public net.sourceforge.gpstools.gpx.Rtept removeRteptAt(
            final int index) {
        java.lang.Object obj = this._rteptList.remove(index);
        return (net.sourceforge.gpstools.gpx.Rtept) obj;
    }

    /**
     * Sets the value of field 'cmt'. The field 'cmt' has the
     * following description: GPS comment for route.
     *  
     * 
     * @param cmt the value of field 'cmt'.
     */
    public void setCmt(
            final java.lang.String cmt) {
        this._cmt = cmt;
    }

    /**
     * Sets the value of field 'desc'. The field 'desc' has the
     * following description: Text description of route for user.
     * Not sent to GPS.
     *  
     * 
     * @param desc the value of field 'desc'.
     */
    public void setDesc(
            final java.lang.String desc) {
        this._desc = desc;
    }

    /**
     * Sets the value of field 'name'. The field 'name' has the
     * following description: GPS name of route.
     *  
     * 
     * @param name the value of field 'name'.
     */
    public void setName(
            final java.lang.String name) {
        this._name = name;
    }

    /**
     * Sets the value of field 'number'. The field 'number' has the
     * following description: GPS route number.
     *  
     * 
     * @param number the value of field 'number'.
     */
    public void setNumber(
            final long number) {
        this._number = number;
        this._has_number = true;
    }

    /**
     * Sets the value of field 'rteExtensions'. The field
     * 'rteExtensions' has the following description: You can add
     * extend GPX by adding your own elements from another schema
     * here.
     *  
     * 
     * @param rteExtensions the value of field 'rteExtensions'.
     */
    public void setRteExtensions(
            final net.sourceforge.gpstools.gpx.RteExtensions rteExtensions) {
        this._rteExtensions = rteExtensions;
    }

    /**
     * 
     * 
     * @param index
     * @param vRteLink
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setRteLink(
            final int index,
            final net.sourceforge.gpstools.gpx.RteLink vRteLink)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._rteLinkList.size()) {
            throw new IndexOutOfBoundsException("setRteLink: Index value '" + index + "' not in range [0.." + (this._rteLinkList.size() - 1) + "]");
        }
        
        this._rteLinkList.set(index, vRteLink);
    }

    /**
     * 
     * 
     * @param vRteLinkArray
     */
    public void setRteLink(
            final net.sourceforge.gpstools.gpx.RteLink[] vRteLinkArray) {
        //-- copy array
        _rteLinkList.clear();
        
        for (int i = 0; i < vRteLinkArray.length; i++) {
                this._rteLinkList.add(vRteLinkArray[i]);
        }
    }

    /**
     * 
     * 
     * @param index
     * @param vRtept
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setRtept(
            final int index,
            final net.sourceforge.gpstools.gpx.Rtept vRtept)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._rteptList.size()) {
            throw new IndexOutOfBoundsException("setRtept: Index value '" + index + "' not in range [0.." + (this._rteptList.size() - 1) + "]");
        }
        
        this._rteptList.set(index, vRtept);
    }

    /**
     * 
     * 
     * @param vRteptArray
     */
    public void setRtept(
            final net.sourceforge.gpstools.gpx.Rtept[] vRteptArray) {
        //-- copy array
        _rteptList.clear();
        
        for (int i = 0; i < vRteptArray.length; i++) {
                this._rteptList.add(vRteptArray[i]);
        }
    }

    /**
     * Sets the value of field 'src'. The field 'src' has the
     * following description: Source of data. Included to give user
     * some idea of reliability and accuracy of data.
     *  
     * 
     * @param src the value of field 'src'.
     */
    public void setSrc(
            final java.lang.String src) {
        this._src = src;
    }

    /**
     * Sets the value of field 'type'. The field 'type' has the
     * following description: Type (classification) of route.
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

/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.1.2</a>, using an XML
 * Schema.
 * $Id: TrkType.java 358 2008-11-24 19:06:17Z ringler $
 */

package net.sourceforge.gpstools.gpx;

/**
 * trk represents a track - an ordered list of points describing a
 * path.
 *  
 * 
 * @version $Revision: 358 $ $Date: 2007-07-16 16:55:47 +0200 (Mo, 16 Jul 2007) $
 */
@SuppressWarnings("serial")
public abstract class TrkType implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * GPS name of track.
     *  
     */
    private java.lang.String _name;

    /**
     * GPS comment for track.
     *  
     */
    private java.lang.String _cmt;

    /**
     * User description of track.
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
     * Links to external information about track.
     *  
     */
    private java.util.List<net.sourceforge.gpstools.gpx.TrkLink> _trkLinkList;

    /**
     * GPS track number.
     *  
     */
    private long _number;

    /**
     * keeps track of state for field: _number
     */
    private boolean _has_number;

    /**
     * Type (classification) of track.
     *  
     */
    private java.lang.String _type;

    /**
     * You can add extend GPX by adding your own elements from
     * another schema here.
     *  
     */
    private net.sourceforge.gpstools.gpx.TrkExtensions _trkExtensions;

    /**
     * A Track Segment holds a list of Track Points which are
     * logically connected in order. To represent a single GPS
     * track where GPS reception was lost, or the GPS receiver was
     * turned off, start a new Track Segment for each continuous
     * span of track data.
     *  
     */
    private java.util.List<net.sourceforge.gpstools.gpx.Trkseg> _trksegList;


      //----------------/
     //- Constructors -/
    //----------------/

    public TrkType() {
        super();
        this._trkLinkList = new java.util.ArrayList<net.sourceforge.gpstools.gpx.TrkLink>();
        this._trksegList = new java.util.ArrayList<net.sourceforge.gpstools.gpx.Trkseg>();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * 
     * 
     * @param vTrkLink
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addTrkLink(
            final net.sourceforge.gpstools.gpx.TrkLink vTrkLink)
    throws java.lang.IndexOutOfBoundsException {
        this._trkLinkList.add(vTrkLink);
    }

    /**
     * 
     * 
     * @param index
     * @param vTrkLink
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addTrkLink(
            final int index,
            final net.sourceforge.gpstools.gpx.TrkLink vTrkLink)
    throws java.lang.IndexOutOfBoundsException {
        this._trkLinkList.add(index, vTrkLink);
    }

    /**
     * 
     * 
     * @param vTrkseg
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addTrkseg(
            final net.sourceforge.gpstools.gpx.Trkseg vTrkseg)
    throws java.lang.IndexOutOfBoundsException {
        this._trksegList.add(vTrkseg);
    }

    /**
     * 
     * 
     * @param index
     * @param vTrkseg
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addTrkseg(
            final int index,
            final net.sourceforge.gpstools.gpx.Trkseg vTrkseg)
    throws java.lang.IndexOutOfBoundsException {
        this._trksegList.add(index, vTrkseg);
    }

    /**
     */
    public void deleteNumber(
    ) {
        this._has_number= false;
    }

    /**
     * Method enumerateTrkLink.
     * 
     * @return an Enumeration over all possible elements of this
     * collection
     */
    public java.util.Enumeration<net.sourceforge.gpstools.gpx.TrkLink> enumerateTrkLink(
    ) {
        return java.util.Collections.enumeration(this._trkLinkList);
    }

    /**
     * Method enumerateTrkseg.
     * 
     * @return an Enumeration over all possible elements of this
     * collection
     */
    public java.util.Enumeration<net.sourceforge.gpstools.gpx.Trkseg> enumerateTrkseg(
    ) {
        return java.util.Collections.enumeration(this._trksegList);
    }

    /**
     * Returns the value of field 'cmt'. The field 'cmt' has the
     * following description: GPS comment for track.
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
     * following description: User description of track.
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
     * following description: GPS name of track.
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
     * the following description: GPS track number.
     *  
     * 
     * @return the value of field 'Number'.
     */
    public long getNumber(
    ) {
        return this._number;
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
     * Returns the value of field 'trkExtensions'. The field
     * 'trkExtensions' has the following description: You can add
     * extend GPX by adding your own elements from another schema
     * here.
     *  
     * 
     * @return the value of field 'TrkExtensions'.
     */
    public net.sourceforge.gpstools.gpx.TrkExtensions getTrkExtensions(
    ) {
        return this._trkExtensions;
    }

    /**
     * Method getTrkLink.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the
     * net.sourceforge.gpstools.gpx.TrkLink at the given index
     */
    public net.sourceforge.gpstools.gpx.TrkLink getTrkLink(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._trkLinkList.size()) {
            throw new IndexOutOfBoundsException("getTrkLink: Index value '" + index + "' not in range [0.." + (this._trkLinkList.size() - 1) + "]");
        }
        
        return (net.sourceforge.gpstools.gpx.TrkLink) _trkLinkList.get(index);
    }

    /**
     * Method getTrkLink.Returns the contents of the collection in
     * an Array.  <p>Note:  Just in case the collection contents
     * are changing in another thread, we pass a 0-length Array of
     * the correct type into the API call.  This way we <i>know</i>
     * that the Array returned is of exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public net.sourceforge.gpstools.gpx.TrkLink[] getTrkLink(
    ) {
        net.sourceforge.gpstools.gpx.TrkLink[] array = new net.sourceforge.gpstools.gpx.TrkLink[0];
        return (net.sourceforge.gpstools.gpx.TrkLink[]) this._trkLinkList.toArray(array);
    }

    /**
     * Method getTrkLinkCount.
     * 
     * @return the size of this collection
     */
    public int getTrkLinkCount(
    ) {
        return this._trkLinkList.size();
    }

    /**
     * Method getTrkseg.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the net.sourceforge.gpstools.gpx.Trkseg
     * at the given index
     */
    public net.sourceforge.gpstools.gpx.Trkseg getTrkseg(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._trksegList.size()) {
            throw new IndexOutOfBoundsException("getTrkseg: Index value '" + index + "' not in range [0.." + (this._trksegList.size() - 1) + "]");
        }
        
        return (net.sourceforge.gpstools.gpx.Trkseg) _trksegList.get(index);
    }

    /**
     * Method getTrkseg.Returns the contents of the collection in
     * an Array.  <p>Note:  Just in case the collection contents
     * are changing in another thread, we pass a 0-length Array of
     * the correct type into the API call.  This way we <i>know</i>
     * that the Array returned is of exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public net.sourceforge.gpstools.gpx.Trkseg[] getTrkseg(
    ) {
        net.sourceforge.gpstools.gpx.Trkseg[] array = new net.sourceforge.gpstools.gpx.Trkseg[0];
        return (net.sourceforge.gpstools.gpx.Trkseg[]) this._trksegList.toArray(array);
    }

    /**
     * Method getTrksegCount.
     * 
     * @return the size of this collection
     */
    public int getTrksegCount(
    ) {
        return this._trksegList.size();
    }

    /**
     * Returns the value of field 'type'. The field 'type' has the
     * following description: Type (classification) of track.
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
     * Method iterateTrkLink.
     * 
     * @return an Iterator over all possible elements in this
     * collection
     */
    public java.util.Iterator<net.sourceforge.gpstools.gpx.TrkLink> iterateTrkLink(
    ) {
        return this._trkLinkList.iterator();
    }

    /**
     * Method iterateTrkseg.
     * 
     * @return an Iterator over all possible elements in this
     * collection
     */
    public java.util.Iterator<net.sourceforge.gpstools.gpx.Trkseg> iterateTrkseg(
    ) {
        return this._trksegList.iterator();
    }

    /**
     */
    public void removeAllTrkLink(
    ) {
        this._trkLinkList.clear();
    }

    /**
     */
    public void removeAllTrkseg(
    ) {
        this._trksegList.clear();
    }

    /**
     * Method removeTrkLink.
     * 
     * @param vTrkLink
     * @return true if the object was removed from the collection.
     */
    public boolean removeTrkLink(
            final net.sourceforge.gpstools.gpx.TrkLink vTrkLink) {
        boolean removed = _trkLinkList.remove(vTrkLink);
        return removed;
    }

    /**
     * Method removeTrkLinkAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public net.sourceforge.gpstools.gpx.TrkLink removeTrkLinkAt(
            final int index) {
        java.lang.Object obj = this._trkLinkList.remove(index);
        return (net.sourceforge.gpstools.gpx.TrkLink) obj;
    }

    /**
     * Method removeTrkseg.
     * 
     * @param vTrkseg
     * @return true if the object was removed from the collection.
     */
    public boolean removeTrkseg(
            final net.sourceforge.gpstools.gpx.Trkseg vTrkseg) {
        boolean removed = _trksegList.remove(vTrkseg);
        return removed;
    }

    /**
     * Method removeTrksegAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public net.sourceforge.gpstools.gpx.Trkseg removeTrksegAt(
            final int index) {
        java.lang.Object obj = this._trksegList.remove(index);
        return (net.sourceforge.gpstools.gpx.Trkseg) obj;
    }

    /**
     * Sets the value of field 'cmt'. The field 'cmt' has the
     * following description: GPS comment for track.
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
     * following description: User description of track.
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
     * following description: GPS name of track.
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
     * following description: GPS track number.
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
     * Sets the value of field 'trkExtensions'. The field
     * 'trkExtensions' has the following description: You can add
     * extend GPX by adding your own elements from another schema
     * here.
     *  
     * 
     * @param trkExtensions the value of field 'trkExtensions'.
     */
    public void setTrkExtensions(
            final net.sourceforge.gpstools.gpx.TrkExtensions trkExtensions) {
        this._trkExtensions = trkExtensions;
    }

    /**
     * 
     * 
     * @param index
     * @param vTrkLink
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setTrkLink(
            final int index,
            final net.sourceforge.gpstools.gpx.TrkLink vTrkLink)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._trkLinkList.size()) {
            throw new IndexOutOfBoundsException("setTrkLink: Index value '" + index + "' not in range [0.." + (this._trkLinkList.size() - 1) + "]");
        }
        
        this._trkLinkList.set(index, vTrkLink);
    }

    /**
     * 
     * 
     * @param vTrkLinkArray
     */
    public void setTrkLink(
            final net.sourceforge.gpstools.gpx.TrkLink[] vTrkLinkArray) {
        //-- copy array
        _trkLinkList.clear();
        
        for (int i = 0; i < vTrkLinkArray.length; i++) {
                this._trkLinkList.add(vTrkLinkArray[i]);
        }
    }

    /**
     * 
     * 
     * @param index
     * @param vTrkseg
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setTrkseg(
            final int index,
            final net.sourceforge.gpstools.gpx.Trkseg vTrkseg)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._trksegList.size()) {
            throw new IndexOutOfBoundsException("setTrkseg: Index value '" + index + "' not in range [0.." + (this._trksegList.size() - 1) + "]");
        }
        
        this._trksegList.set(index, vTrkseg);
    }

    /**
     * 
     * 
     * @param vTrksegArray
     */
    public void setTrkseg(
            final net.sourceforge.gpstools.gpx.Trkseg[] vTrksegArray) {
        //-- copy array
        _trksegList.clear();
        
        for (int i = 0; i < vTrksegArray.length; i++) {
                this._trksegList.add(vTrksegArray[i]);
        }
    }

    /**
     * Sets the value of field 'type'. The field 'type' has the
     * following description: Type (classification) of track.
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

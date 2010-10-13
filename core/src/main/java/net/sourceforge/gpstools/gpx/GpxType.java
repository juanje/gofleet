/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.1.2</a>, using an XML
 * Schema.
 * $Id: GpxType.java 358 2008-11-24 19:06:17Z ringler $
 */

package net.sourceforge.gpstools.gpx;

/**
 * GPX documents contain a metadata header, followed by waypoints,
 * routes, and tracks. You can add your own elements
 *  to the extensions section of the GPX document.
 *  
 * 
 * @version $Revision: 358 $ $Date: 2007-07-16 16:55:47 +0200 (Mo, 16 Jul 2007) $
 */
@SuppressWarnings("serial")
public abstract class GpxType implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * You must include the version number in your GPX document.
     *  
     */
    private java.lang.String _version = "1.1";

    /**
     * You must include the name or URL of the software that
     * created your GPX document. This allows others to
     *  inform the creator of a GPX instance document that fails to
     * validate.
     *  
     */
    private java.lang.String _creator;

    /**
     * Metadata about the file.
     *  
     */
    private net.sourceforge.gpstools.gpx.Metadata _metadata;

    /**
     * A list of waypoints.
     *  
     */
    private java.util.List<net.sourceforge.gpstools.gpx.Wpt> _wptList;

    /**
     * A list of routes.
     *  
     */
    private java.util.List<net.sourceforge.gpstools.gpx.Rte> _rteList;

    /**
     * A list of tracks.
     *  
     */
    private java.util.List<net.sourceforge.gpstools.gpx.Trk> _trkList;

    /**
     * You can add extend GPX by adding your own elements from
     * another schema here.
     *  
     */
    private net.sourceforge.gpstools.gpx.GpxExtensions _gpxExtensions;


      //----------------/
     //- Constructors -/
    //----------------/

    public GpxType() {
        super();
        setVersion("1.1");
        this._wptList = new java.util.ArrayList<net.sourceforge.gpstools.gpx.Wpt>();
        this._rteList = new java.util.ArrayList<net.sourceforge.gpstools.gpx.Rte>();
        this._trkList = new java.util.ArrayList<net.sourceforge.gpstools.gpx.Trk>();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * 
     * 
     * @param vRte
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addRte(
            final net.sourceforge.gpstools.gpx.Rte vRte)
    throws java.lang.IndexOutOfBoundsException {
        this._rteList.add(vRte);
    }

    /**
     * 
     * 
     * @param index
     * @param vRte
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addRte(
            final int index,
            final net.sourceforge.gpstools.gpx.Rte vRte)
    throws java.lang.IndexOutOfBoundsException {
        this._rteList.add(index, vRte);
    }

    /**
     * 
     * 
     * @param vTrk
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addTrk(
            final net.sourceforge.gpstools.gpx.Trk vTrk)
    throws java.lang.IndexOutOfBoundsException {
        this._trkList.add(vTrk);
    }

    /**
     * 
     * 
     * @param index
     * @param vTrk
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addTrk(
            final int index,
            final net.sourceforge.gpstools.gpx.Trk vTrk)
    throws java.lang.IndexOutOfBoundsException {
        this._trkList.add(index, vTrk);
    }

    /**
     * 
     * 
     * @param vWpt
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addWpt(
            final net.sourceforge.gpstools.gpx.Wpt vWpt)
    throws java.lang.IndexOutOfBoundsException {
        this._wptList.add(vWpt);
    }

    /**
     * 
     * 
     * @param index
     * @param vWpt
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addWpt(
            final int index,
            final net.sourceforge.gpstools.gpx.Wpt vWpt)
    throws java.lang.IndexOutOfBoundsException {
        this._wptList.add(index, vWpt);
    }

    /**
     * Method enumerateRte.
     * 
     * @return an Enumeration over all possible elements of this
     * collection
     */
    public java.util.Enumeration<net.sourceforge.gpstools.gpx.Rte> enumerateRte(
    ) {
        return java.util.Collections.enumeration(this._rteList);
    }

    /**
     * Method enumerateTrk.
     * 
     * @return an Enumeration over all possible elements of this
     * collection
     */
    public java.util.Enumeration<net.sourceforge.gpstools.gpx.Trk> enumerateTrk(
    ) {
        return java.util.Collections.enumeration(this._trkList);
    }

    /**
     * Method enumerateWpt.
     * 
     * @return an Enumeration over all possible elements of this
     * collection
     */
    public java.util.Enumeration<net.sourceforge.gpstools.gpx.Wpt> enumerateWpt(
    ) {
        return java.util.Collections.enumeration(this._wptList);
    }

    /**
     * Returns the value of field 'creator'. The field 'creator'
     * has the following description: You must include the name or
     * URL of the software that created your GPX document. This
     * allows others to
     *  inform the creator of a GPX instance document that fails to
     * validate.
     *  
     * 
     * @return the value of field 'Creator'.
     */
    public java.lang.String getCreator(
    ) {
        return this._creator;
    }

    /**
     * Returns the value of field 'gpxExtensions'. The field
     * 'gpxExtensions' has the following description: You can add
     * extend GPX by adding your own elements from another schema
     * here.
     *  
     * 
     * @return the value of field 'GpxExtensions'.
     */
    public net.sourceforge.gpstools.gpx.GpxExtensions getGpxExtensions(
    ) {
        return this._gpxExtensions;
    }

    /**
     * Returns the value of field 'metadata'. The field 'metadata'
     * has the following description: Metadata about the file.
     *  
     * 
     * @return the value of field 'Metadata'.
     */
    public net.sourceforge.gpstools.gpx.Metadata getMetadata(
    ) {
        return this._metadata;
    }

    /**
     * Method getRte.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the net.sourceforge.gpstools.gpx.Rte at
     * the given index
     */
    public net.sourceforge.gpstools.gpx.Rte getRte(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._rteList.size()) {
            throw new IndexOutOfBoundsException("getRte: Index value '" + index + "' not in range [0.." + (this._rteList.size() - 1) + "]");
        }
        
        return (net.sourceforge.gpstools.gpx.Rte) _rteList.get(index);
    }

    /**
     * Method getRte.Returns the contents of the collection in an
     * Array.  <p>Note:  Just in case the collection contents are
     * changing in another thread, we pass a 0-length Array of the
     * correct type into the API call.  This way we <i>know</i>
     * that the Array returned is of exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public net.sourceforge.gpstools.gpx.Rte[] getRte(
    ) {
        net.sourceforge.gpstools.gpx.Rte[] array = new net.sourceforge.gpstools.gpx.Rte[0];
        return (net.sourceforge.gpstools.gpx.Rte[]) this._rteList.toArray(array);
    }

    /**
     * Method getRteCount.
     * 
     * @return the size of this collection
     */
    public int getRteCount(
    ) {
        return this._rteList.size();
    }

    /**
     * Method getTrk.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the net.sourceforge.gpstools.gpx.Trk at
     * the given index
     */
    public net.sourceforge.gpstools.gpx.Trk getTrk(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._trkList.size()) {
            throw new IndexOutOfBoundsException("getTrk: Index value '" + index + "' not in range [0.." + (this._trkList.size() - 1) + "]");
        }
        
        return (net.sourceforge.gpstools.gpx.Trk) _trkList.get(index);
    }

    /**
     * Method getTrk.Returns the contents of the collection in an
     * Array.  <p>Note:  Just in case the collection contents are
     * changing in another thread, we pass a 0-length Array of the
     * correct type into the API call.  This way we <i>know</i>
     * that the Array returned is of exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public net.sourceforge.gpstools.gpx.Trk[] getTrk(
    ) {
        net.sourceforge.gpstools.gpx.Trk[] array = new net.sourceforge.gpstools.gpx.Trk[0];
        return (net.sourceforge.gpstools.gpx.Trk[]) this._trkList.toArray(array);
    }

    /**
     * Method getTrkCount.
     * 
     * @return the size of this collection
     */
    public int getTrkCount(
    ) {
        return this._trkList.size();
    }

    /**
     * Returns the value of field 'version'. The field 'version'
     * has the following description: You must include the version
     * number in your GPX document.
     *  
     * 
     * @return the value of field 'Version'.
     */
    public java.lang.String getVersion(
    ) {
        return this._version;
    }

    /**
     * Method getWpt.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the net.sourceforge.gpstools.gpx.Wpt at
     * the given index
     */
    public net.sourceforge.gpstools.gpx.Wpt getWpt(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._wptList.size()) {
            throw new IndexOutOfBoundsException("getWpt: Index value '" + index + "' not in range [0.." + (this._wptList.size() - 1) + "]");
        }
        
        return (net.sourceforge.gpstools.gpx.Wpt) _wptList.get(index);
    }

    /**
     * Method getWpt.Returns the contents of the collection in an
     * Array.  <p>Note:  Just in case the collection contents are
     * changing in another thread, we pass a 0-length Array of the
     * correct type into the API call.  This way we <i>know</i>
     * that the Array returned is of exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public net.sourceforge.gpstools.gpx.Wpt[] getWpt(
    ) {
        net.sourceforge.gpstools.gpx.Wpt[] array = new net.sourceforge.gpstools.gpx.Wpt[0];
        return (net.sourceforge.gpstools.gpx.Wpt[]) this._wptList.toArray(array);
    }

    /**
     * Method getWptCount.
     * 
     * @return the size of this collection
     */
    public int getWptCount(
    ) {
        return this._wptList.size();
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
     * Method iterateRte.
     * 
     * @return an Iterator over all possible elements in this
     * collection
     */
    public java.util.Iterator<net.sourceforge.gpstools.gpx.Rte> iterateRte(
    ) {
        return this._rteList.iterator();
    }

    /**
     * Method iterateTrk.
     * 
     * @return an Iterator over all possible elements in this
     * collection
     */
    public java.util.Iterator<net.sourceforge.gpstools.gpx.Trk> iterateTrk(
    ) {
        return this._trkList.iterator();
    }

    /**
     * Method iterateWpt.
     * 
     * @return an Iterator over all possible elements in this
     * collection
     */
    public java.util.Iterator<net.sourceforge.gpstools.gpx.Wpt> iterateWpt(
    ) {
        return this._wptList.iterator();
    }

    /**
     */
    public void removeAllRte(
    ) {
        this._rteList.clear();
    }

    /**
     */
    public void removeAllTrk(
    ) {
        this._trkList.clear();
    }

    /**
     */
    public void removeAllWpt(
    ) {
        this._wptList.clear();
    }

    /**
     * Method removeRte.
     * 
     * @param vRte
     * @return true if the object was removed from the collection.
     */
    public boolean removeRte(
            final net.sourceforge.gpstools.gpx.Rte vRte) {
        boolean removed = _rteList.remove(vRte);
        return removed;
    }

    /**
     * Method removeRteAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public net.sourceforge.gpstools.gpx.Rte removeRteAt(
            final int index) {
        java.lang.Object obj = this._rteList.remove(index);
        return (net.sourceforge.gpstools.gpx.Rte) obj;
    }

    /**
     * Method removeTrk.
     * 
     * @param vTrk
     * @return true if the object was removed from the collection.
     */
    public boolean removeTrk(
            final net.sourceforge.gpstools.gpx.Trk vTrk) {
        boolean removed = _trkList.remove(vTrk);
        return removed;
    }

    /**
     * Method removeTrkAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public net.sourceforge.gpstools.gpx.Trk removeTrkAt(
            final int index) {
        java.lang.Object obj = this._trkList.remove(index);
        return (net.sourceforge.gpstools.gpx.Trk) obj;
    }

    /**
     * Method removeWpt.
     * 
     * @param vWpt
     * @return true if the object was removed from the collection.
     */
    public boolean removeWpt(
            final net.sourceforge.gpstools.gpx.Wpt vWpt) {
        boolean removed = _wptList.remove(vWpt);
        return removed;
    }

    /**
     * Method removeWptAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public net.sourceforge.gpstools.gpx.Wpt removeWptAt(
            final int index) {
        java.lang.Object obj = this._wptList.remove(index);
        return (net.sourceforge.gpstools.gpx.Wpt) obj;
    }

    /**
     * Sets the value of field 'creator'. The field 'creator' has
     * the following description: You must include the name or URL
     * of the software that created your GPX document. This allows
     * others to
     *  inform the creator of a GPX instance document that fails to
     * validate.
     *  
     * 
     * @param creator the value of field 'creator'.
     */
    public void setCreator(
            final java.lang.String creator) {
        this._creator = creator;
    }

    /**
     * Sets the value of field 'gpxExtensions'. The field
     * 'gpxExtensions' has the following description: You can add
     * extend GPX by adding your own elements from another schema
     * here.
     *  
     * 
     * @param gpxExtensions the value of field 'gpxExtensions'.
     */
    public void setGpxExtensions(
            final net.sourceforge.gpstools.gpx.GpxExtensions gpxExtensions) {
        this._gpxExtensions = gpxExtensions;
    }

    /**
     * Sets the value of field 'metadata'. The field 'metadata' has
     * the following description: Metadata about the file.
     *  
     * 
     * @param metadata the value of field 'metadata'.
     */
    public void setMetadata(
            final net.sourceforge.gpstools.gpx.Metadata metadata) {
        this._metadata = metadata;
    }

    /**
     * 
     * 
     * @param index
     * @param vRte
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setRte(
            final int index,
            final net.sourceforge.gpstools.gpx.Rte vRte)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._rteList.size()) {
            throw new IndexOutOfBoundsException("setRte: Index value '" + index + "' not in range [0.." + (this._rteList.size() - 1) + "]");
        }
        
        this._rteList.set(index, vRte);
    }

    /**
     * 
     * 
     * @param vRteArray
     */
    public void setRte(
            final net.sourceforge.gpstools.gpx.Rte[] vRteArray) {
        //-- copy array
        _rteList.clear();
        
        for (int i = 0; i < vRteArray.length; i++) {
                this._rteList.add(vRteArray[i]);
        }
    }

    /**
     * 
     * 
     * @param index
     * @param vTrk
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setTrk(
            final int index,
            final net.sourceforge.gpstools.gpx.Trk vTrk)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._trkList.size()) {
            throw new IndexOutOfBoundsException("setTrk: Index value '" + index + "' not in range [0.." + (this._trkList.size() - 1) + "]");
        }
        
        this._trkList.set(index, vTrk);
    }

    /**
     * 
     * 
     * @param vTrkArray
     */
    public void setTrk(
            final net.sourceforge.gpstools.gpx.Trk[] vTrkArray) {
        //-- copy array
        _trkList.clear();
        
        for (int i = 0; i < vTrkArray.length; i++) {
                this._trkList.add(vTrkArray[i]);
        }
    }

    /**
     * Sets the value of field 'version'. The field 'version' has
     * the following description: You must include the version
     * number in your GPX document.
     *  
     * 
     * @param version the value of field 'version'.
     */
    public void setVersion(
            final java.lang.String version) {
        this._version = version;
    }

    /**
     * 
     * 
     * @param index
     * @param vWpt
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setWpt(
            final int index,
            final net.sourceforge.gpstools.gpx.Wpt vWpt)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._wptList.size()) {
            throw new IndexOutOfBoundsException("setWpt: Index value '" + index + "' not in range [0.." + (this._wptList.size() - 1) + "]");
        }
        
        this._wptList.set(index, vWpt);
    }

    /**
     * 
     * 
     * @param vWptArray
     */
    public void setWpt(
            final net.sourceforge.gpstools.gpx.Wpt[] vWptArray) {
        //-- copy array
        _wptList.clear();
        
        for (int i = 0; i < vWptArray.length; i++) {
                this._wptList.add(vWptArray[i]);
        }
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

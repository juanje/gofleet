/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.1.2</a>, using an XML
 * Schema.
 * $Id: TrksegType.java 358 2008-11-24 19:06:17Z ringler $
 */

package net.sourceforge.gpstools.gpx;

/**
 * A Track Segment holds a list of Track Points which are logically
 * connected in order. To represent a single GPS track where GPS
 * reception was lost, or the GPS receiver was turned off, start a
 * new Track Segment for each continuous span of track data.
 *  
 * 
 * @version $Revision: 358 $ $Date: 2007-07-16 16:55:47 +0200 (Mo, 16 Jul 2007) $
 */
@SuppressWarnings("serial")
public abstract class TrksegType implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * A Track Point holds the coordinates, elevation, timestamp,
     * and metadata for a single point in a track.
     *  
     */
    private java.util.List<net.sourceforge.gpstools.gpx.Trkpt> _trkptList;

    /**
     * You can add extend GPX by adding your own elements from
     * another schema here.
     *  
     */
    private net.sourceforge.gpstools.gpx.TrksegExtensions _trksegExtensions;


      //----------------/
     //- Constructors -/
    //----------------/

    public TrksegType() {
        super();
        this._trkptList = new java.util.ArrayList<net.sourceforge.gpstools.gpx.Trkpt>();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * 
     * 
     * @param vTrkpt
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addTrkpt(
            final net.sourceforge.gpstools.gpx.Trkpt vTrkpt)
    throws java.lang.IndexOutOfBoundsException {
        this._trkptList.add(vTrkpt);
    }

    /**
     * 
     * 
     * @param index
     * @param vTrkpt
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addTrkpt(
            final int index,
            final net.sourceforge.gpstools.gpx.Trkpt vTrkpt)
    throws java.lang.IndexOutOfBoundsException {
        this._trkptList.add(index, vTrkpt);
    }

    /**
     * Method enumerateTrkpt.
     * 
     * @return an Enumeration over all possible elements of this
     * collection
     */
    public java.util.Enumeration<net.sourceforge.gpstools.gpx.Trkpt> enumerateTrkpt(
    ) {
        return java.util.Collections.enumeration(this._trkptList);
    }

    /**
     * Method getTrkpt.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the net.sourceforge.gpstools.gpx.Trkpt
     * at the given index
     */
    public net.sourceforge.gpstools.gpx.Trkpt getTrkpt(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._trkptList.size()) {
            throw new IndexOutOfBoundsException("getTrkpt: Index value '" + index + "' not in range [0.." + (this._trkptList.size() - 1) + "]");
        }
        
        return (net.sourceforge.gpstools.gpx.Trkpt) _trkptList.get(index);
    }

    /**
     * Method getTrkpt.Returns the contents of the collection in an
     * Array.  <p>Note:  Just in case the collection contents are
     * changing in another thread, we pass a 0-length Array of the
     * correct type into the API call.  This way we <i>know</i>
     * that the Array returned is of exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public net.sourceforge.gpstools.gpx.Trkpt[] getTrkpt(
    ) {
        net.sourceforge.gpstools.gpx.Trkpt[] array = new net.sourceforge.gpstools.gpx.Trkpt[0];
        return (net.sourceforge.gpstools.gpx.Trkpt[]) this._trkptList.toArray(array);
    }

    /**
     * Method getTrkptCount.
     * 
     * @return the size of this collection
     */
    public int getTrkptCount(
    ) {
        return this._trkptList.size();
    }

    /**
     * Returns the value of field 'trksegExtensions'. The field
     * 'trksegExtensions' has the following description: You can
     * add extend GPX by adding your own elements from another
     * schema here.
     *  
     * 
     * @return the value of field 'TrksegExtensions'.
     */
    public net.sourceforge.gpstools.gpx.TrksegExtensions getTrksegExtensions(
    ) {
        return this._trksegExtensions;
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
     * Method iterateTrkpt.
     * 
     * @return an Iterator over all possible elements in this
     * collection
     */
    public java.util.Iterator<net.sourceforge.gpstools.gpx.Trkpt> iterateTrkpt(
    ) {
        return this._trkptList.iterator();
    }

    /**
     */
    public void removeAllTrkpt(
    ) {
        this._trkptList.clear();
    }

    /**
     * Method removeTrkpt.
     * 
     * @param vTrkpt
     * @return true if the object was removed from the collection.
     */
    public boolean removeTrkpt(
            final net.sourceforge.gpstools.gpx.Trkpt vTrkpt) {
        boolean removed = _trkptList.remove(vTrkpt);
        return removed;
    }

    /**
     * Method removeTrkptAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public net.sourceforge.gpstools.gpx.Trkpt removeTrkptAt(
            final int index) {
        java.lang.Object obj = this._trkptList.remove(index);
        return (net.sourceforge.gpstools.gpx.Trkpt) obj;
    }

    /**
     * 
     * 
     * @param index
     * @param vTrkpt
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setTrkpt(
            final int index,
            final net.sourceforge.gpstools.gpx.Trkpt vTrkpt)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._trkptList.size()) {
            throw new IndexOutOfBoundsException("setTrkpt: Index value '" + index + "' not in range [0.." + (this._trkptList.size() - 1) + "]");
        }
        
        this._trkptList.set(index, vTrkpt);
    }

    /**
     * 
     * 
     * @param vTrkptArray
     */
    public void setTrkpt(
            final net.sourceforge.gpstools.gpx.Trkpt[] vTrkptArray) {
        //-- copy array
        _trkptList.clear();
        
        for (int i = 0; i < vTrkptArray.length; i++) {
                this._trkptList.add(vTrkptArray[i]);
        }
    }

    /**
     * Sets the value of field 'trksegExtensions'. The field
     * 'trksegExtensions' has the following description: You can
     * add extend GPX by adding your own elements from another
     * schema here.
     *  
     * 
     * @param trksegExtensions the value of field 'trksegExtensions'
     */
    public void setTrksegExtensions(
            final net.sourceforge.gpstools.gpx.TrksegExtensions trksegExtensions) {
        this._trksegExtensions = trksegExtensions;
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

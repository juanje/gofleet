/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.1.2</a>, using an XML
 * Schema.
 * $Id: PtsegType.java 358 2008-11-24 19:06:17Z ringler $
 */

package net.sourceforge.gpstools.gpx;

/**
 * An ordered sequence of points. (for polygons or polylines, e.g.)
 *  
 * 
 * @version $Revision: 358 $ $Date: 2007-07-16 16:55:47 +0200 (Mo, 16 Jul 2007) $
 */
@SuppressWarnings("serial")
public abstract class PtsegType implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Ordered list of geographic points.
     *  
     */
    private java.util.List<net.sourceforge.gpstools.gpx.Pt> _ptList;


      //----------------/
     //- Constructors -/
    //----------------/

    public PtsegType() {
        super();
        this._ptList = new java.util.ArrayList<net.sourceforge.gpstools.gpx.Pt>();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * 
     * 
     * @param vPt
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addPt(
            final net.sourceforge.gpstools.gpx.Pt vPt)
    throws java.lang.IndexOutOfBoundsException {
        this._ptList.add(vPt);
    }

    /**
     * 
     * 
     * @param index
     * @param vPt
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addPt(
            final int index,
            final net.sourceforge.gpstools.gpx.Pt vPt)
    throws java.lang.IndexOutOfBoundsException {
        this._ptList.add(index, vPt);
    }

    /**
     * Method enumeratePt.
     * 
     * @return an Enumeration over all possible elements of this
     * collection
     */
    public java.util.Enumeration<net.sourceforge.gpstools.gpx.Pt> enumeratePt(
    ) {
        return java.util.Collections.enumeration(this._ptList);
    }

    /**
     * Method getPt.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the net.sourceforge.gpstools.gpx.Pt at
     * the given index
     */
    public net.sourceforge.gpstools.gpx.Pt getPt(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._ptList.size()) {
            throw new IndexOutOfBoundsException("getPt: Index value '" + index + "' not in range [0.." + (this._ptList.size() - 1) + "]");
        }
        
        return (net.sourceforge.gpstools.gpx.Pt) _ptList.get(index);
    }

    /**
     * Method getPt.Returns the contents of the collection in an
     * Array.  <p>Note:  Just in case the collection contents are
     * changing in another thread, we pass a 0-length Array of the
     * correct type into the API call.  This way we <i>know</i>
     * that the Array returned is of exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public net.sourceforge.gpstools.gpx.Pt[] getPt(
    ) {
        net.sourceforge.gpstools.gpx.Pt[] array = new net.sourceforge.gpstools.gpx.Pt[0];
        return (net.sourceforge.gpstools.gpx.Pt[]) this._ptList.toArray(array);
    }

    /**
     * Method getPtCount.
     * 
     * @return the size of this collection
     */
    public int getPtCount(
    ) {
        return this._ptList.size();
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
     * Method iteratePt.
     * 
     * @return an Iterator over all possible elements in this
     * collection
     */
    public java.util.Iterator<net.sourceforge.gpstools.gpx.Pt> iteratePt(
    ) {
        return this._ptList.iterator();
    }

    /**
     */
    public void removeAllPt(
    ) {
        this._ptList.clear();
    }

    /**
     * Method removePt.
     * 
     * @param vPt
     * @return true if the object was removed from the collection.
     */
    public boolean removePt(
            final net.sourceforge.gpstools.gpx.Pt vPt) {
        boolean removed = _ptList.remove(vPt);
        return removed;
    }

    /**
     * Method removePtAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public net.sourceforge.gpstools.gpx.Pt removePtAt(
            final int index) {
        java.lang.Object obj = this._ptList.remove(index);
        return (net.sourceforge.gpstools.gpx.Pt) obj;
    }

    /**
     * 
     * 
     * @param index
     * @param vPt
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setPt(
            final int index,
            final net.sourceforge.gpstools.gpx.Pt vPt)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._ptList.size()) {
            throw new IndexOutOfBoundsException("setPt: Index value '" + index + "' not in range [0.." + (this._ptList.size() - 1) + "]");
        }
        
        this._ptList.set(index, vPt);
    }

    /**
     * 
     * 
     * @param vPtArray
     */
    public void setPt(
            final net.sourceforge.gpstools.gpx.Pt[] vPtArray) {
        //-- copy array
        _ptList.clear();
        
        for (int i = 0; i < vPtArray.length; i++) {
                this._ptList.add(vPtArray[i]);
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

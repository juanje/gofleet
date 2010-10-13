/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.1.2</a>, using an XML
 * Schema.
 * $Id: WptType.java 358 2008-11-24 19:06:17Z ringler $
 */

package net.sourceforge.gpstools.gpx;

/**
 * wpt represents a waypoint, point of interest, or named feature
 * on a map.
 *  
 * 
 * @version $Revision: 358 $ $Date: 2007-07-16 16:55:47 +0200 (Mo, 16 Jul 2007) $
 */
@SuppressWarnings("serial")
public abstract class WptType implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The latitude of the point. Decimal degrees, WGS84 datum.
     *  
     */
    private java.math.BigDecimal _lat;

    /**
     * The latitude of the point. Decimal degrees, WGS84 datum.
     *  
     */
    private java.math.BigDecimal _lon;

    /**
     * Elevation (in meters) of the point.
     *  
     */
    private java.math.BigDecimal _ele;

    /**
     * Creation/modification timestamp for element. Date and time
     * in are in Univeral Coordinated Time (UTC), not local time!
     * Conforms to ISO 8601 specification for date/time
     * representation. Fractional seconds are allowed for
     * millisecond timing in tracklogs. 
     *  
     */
    private java.util.Date _time;

    /**
     * Magnetic variation (in degrees) at the point
     *  
     */
    private java.math.BigDecimal _magvar;

    /**
     * Height (in meters) of geoid (mean sea level) above WGS84
     * earth ellipsoid. As defined in NMEA GGA message.
     *  
     */
    private java.math.BigDecimal _geoidheight;

    /**
     * The GPS name of the waypoint. This field will be transferred
     * to and from the GPS. GPX does not place restrictions on the
     * length of this field or the characters contained in it. It
     * is up to the receiving application to validate the field
     * before sending it to the GPS.
     *  
     */
    private java.lang.String _name;

    /**
     * GPS waypoint comment. Sent to GPS as comment. 
     *  
     */
    private java.lang.String _cmt;

    /**
     * A text description of the element. Holds additional
     * information about the element intended for the user, not the
     * GPS.
     *  
     */
    private java.lang.String _desc;

    /**
     * Source of data. Included to give user some idea of
     * reliability and accuracy of data. "Garmin eTrex", "USGS quad
     * Boston North", e.g.
     *  
     */
    private java.lang.String _src;

    /**
     * Link to additional information about the waypoint.
     *  
     */
    private java.util.List<net.sourceforge.gpstools.gpx.WptLink> _wptLinkList;

    /**
     * Text of GPS symbol name. For interchange with other
     * programs, use the exact spelling of the symbol as displayed
     * on the GPS. If the GPS abbreviates words, spell them out.
     *  
     */
    private java.lang.String _sym;

    /**
     * Type (classification) of the waypoint.
     *  
     */
    private java.lang.String _type;

    /**
     * Type of GPX fix.
     *  
     */
    private net.sourceforge.gpstools.gpx.types.FixType _fix;

    /**
     * Number of satellites used to calculate the GPX fix.
     *  
     */
    private long _sat;

    /**
     * keeps track of state for field: _sat
     */
    private boolean _has_sat;

    /**
     * Horizontal dilution of precision.
     *  
     */
    private java.math.BigDecimal _hdop;

    /**
     * Vertical dilution of precision.
     *  
     */
    private java.math.BigDecimal _vdop;

    /**
     * Position dilution of precision.
     *  
     */
    private java.math.BigDecimal _pdop;

    /**
     * Number of seconds since last DGPS update.
     *  
     */
    private java.math.BigDecimal _ageofdgpsdata;

    /**
     * ID of DGPS station used in differential correction.
     *  
     */
    private long _dgpsid;

    /**
     * keeps track of state for field: _dgpsid
     */
    private boolean _has_dgpsid;

    /**
     * You can add extend GPX by adding your own elements from
     * another schema here.
     *  
     */
    private net.sourceforge.gpstools.gpx.WptExtensions _wptExtensions;


      //----------------/
     //- Constructors -/
    //----------------/

    public WptType() {
        super();
        this._wptLinkList = new java.util.ArrayList<net.sourceforge.gpstools.gpx.WptLink>();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * 
     * 
     * @param vWptLink
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addWptLink(
            final net.sourceforge.gpstools.gpx.WptLink vWptLink)
    throws java.lang.IndexOutOfBoundsException {
        this._wptLinkList.add(vWptLink);
    }

    /**
     * 
     * 
     * @param index
     * @param vWptLink
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addWptLink(
            final int index,
            final net.sourceforge.gpstools.gpx.WptLink vWptLink)
    throws java.lang.IndexOutOfBoundsException {
        this._wptLinkList.add(index, vWptLink);
    }

    /**
     */
    public void deleteDgpsid(
    ) {
        this._has_dgpsid= false;
    }

    /**
     */
    public void deleteSat(
    ) {
        this._has_sat= false;
    }

    /**
     * Method enumerateWptLink.
     * 
     * @return an Enumeration over all possible elements of this
     * collection
     */
    public java.util.Enumeration<net.sourceforge.gpstools.gpx.WptLink> enumerateWptLink(
    ) {
        return java.util.Collections.enumeration(this._wptLinkList);
    }

    /**
     * Returns the value of field 'ageofdgpsdata'. The field
     * 'ageofdgpsdata' has the following description: Number of
     * seconds since last DGPS update.
     *  
     * 
     * @return the value of field 'Ageofdgpsdata'.
     */
    public java.math.BigDecimal getAgeofdgpsdata(
    ) {
        return this._ageofdgpsdata;
    }

    /**
     * Returns the value of field 'cmt'. The field 'cmt' has the
     * following description: GPS waypoint comment. Sent to GPS as
     * comment. 
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
     * following description: A text description of the element.
     * Holds additional information about the element intended for
     * the user, not the GPS.
     *  
     * 
     * @return the value of field 'Desc'.
     */
    public java.lang.String getDesc(
    ) {
        return this._desc;
    }

    /**
     * Returns the value of field 'dgpsid'. The field 'dgpsid' has
     * the following description: ID of DGPS station used in
     * differential correction.
     *  
     * 
     * @return the value of field 'Dgpsid'.
     */
    public long getDgpsid(
    ) {
        return this._dgpsid;
    }

    /**
     * Returns the value of field 'ele'. The field 'ele' has the
     * following description: Elevation (in meters) of the point.
     *  
     * 
     * @return the value of field 'Ele'.
     */
    public java.math.BigDecimal getEle(
    ) {
        return this._ele;
    }

    /**
     * Returns the value of field 'fix'. The field 'fix' has the
     * following description: Type of GPX fix.
     *  
     * 
     * @return the value of field 'Fix'.
     */
    public net.sourceforge.gpstools.gpx.types.FixType getFix(
    ) {
        return this._fix;
    }

    /**
     * Returns the value of field 'geoidheight'. The field
     * 'geoidheight' has the following description: Height (in
     * meters) of geoid (mean sea level) above WGS84 earth
     * ellipsoid. As defined in NMEA GGA message.
     *  
     * 
     * @return the value of field 'Geoidheight'.
     */
    public java.math.BigDecimal getGeoidheight(
    ) {
        return this._geoidheight;
    }

    /**
     * Returns the value of field 'hdop'. The field 'hdop' has the
     * following description: Horizontal dilution of precision.
     *  
     * 
     * @return the value of field 'Hdop'.
     */
    public java.math.BigDecimal getHdop(
    ) {
        return this._hdop;
    }

    /**
     * Returns the value of field 'lat'. The field 'lat' has the
     * following description: The latitude of the point. Decimal
     * degrees, WGS84 datum.
     *  
     * 
     * @return the value of field 'Lat'.
     */
    public java.math.BigDecimal getLat(
    ) {
        return this._lat;
    }

    /**
     * Returns the value of field 'lon'. The field 'lon' has the
     * following description: The latitude of the point. Decimal
     * degrees, WGS84 datum.
     *  
     * 
     * @return the value of field 'Lon'.
     */
    public java.math.BigDecimal getLon(
    ) {
        return this._lon;
    }

    /**
     * Returns the value of field 'magvar'. The field 'magvar' has
     * the following description: Magnetic variation (in degrees)
     * at the point
     *  
     * 
     * @return the value of field 'Magvar'.
     */
    public java.math.BigDecimal getMagvar(
    ) {
        return this._magvar;
    }

    /**
     * Returns the value of field 'name'. The field 'name' has the
     * following description: The GPS name of the waypoint. This
     * field will be transferred to and from the GPS. GPX does not
     * place restrictions on the length of this field or the
     * characters contained in it. It is up to the receiving
     * application to validate the field before sending it to the
     * GPS.
     *  
     * 
     * @return the value of field 'Name'.
     */
    public java.lang.String getName(
    ) {
        return this._name;
    }

    /**
     * Returns the value of field 'pdop'. The field 'pdop' has the
     * following description: Position dilution of precision.
     *  
     * 
     * @return the value of field 'Pdop'.
     */
    public java.math.BigDecimal getPdop(
    ) {
        return this._pdop;
    }

    /**
     * Returns the value of field 'sat'. The field 'sat' has the
     * following description: Number of satellites used to
     * calculate the GPX fix.
     *  
     * 
     * @return the value of field 'Sat'.
     */
    public long getSat(
    ) {
        return this._sat;
    }

    /**
     * Returns the value of field 'src'. The field 'src' has the
     * following description: Source of data. Included to give user
     * some idea of reliability and accuracy of data. "Garmin
     * eTrex", "USGS quad Boston North", e.g.
     *  
     * 
     * @return the value of field 'Src'.
     */
    public java.lang.String getSrc(
    ) {
        return this._src;
    }

    /**
     * Returns the value of field 'sym'. The field 'sym' has the
     * following description: Text of GPS symbol name. For
     * interchange with other programs, use the exact spelling of
     * the symbol as displayed on the GPS. If the GPS abbreviates
     * words, spell them out.
     *  
     * 
     * @return the value of field 'Sym'.
     */
    public java.lang.String getSym(
    ) {
        return this._sym;
    }

    /**
     * Returns the value of field 'time'. The field 'time' has the
     * following description: Creation/modification timestamp for
     * element. Date and time in are in Univeral Coordinated Time
     * (UTC), not local time! Conforms to ISO 8601 specification
     * for date/time representation. Fractional seconds are allowed
     * for millisecond timing in tracklogs. 
     *  
     * 
     * @return the value of field 'Time'.
     */
    public java.util.Date getTime(
    ) {
        return this._time;
    }

    /**
     * Returns the value of field 'type'. The field 'type' has the
     * following description: Type (classification) of the
     * waypoint.
     *  
     * 
     * @return the value of field 'Type'.
     */
    public java.lang.String getType(
    ) {
        return this._type;
    }

    /**
     * Returns the value of field 'vdop'. The field 'vdop' has the
     * following description: Vertical dilution of precision.
     *  
     * 
     * @return the value of field 'Vdop'.
     */
    public java.math.BigDecimal getVdop(
    ) {
        return this._vdop;
    }

    /**
     * Returns the value of field 'wptExtensions'. The field
     * 'wptExtensions' has the following description: You can add
     * extend GPX by adding your own elements from another schema
     * here.
     *  
     * 
     * @return the value of field 'WptExtensions'.
     */
    public net.sourceforge.gpstools.gpx.WptExtensions getWptExtensions(
    ) {
        return this._wptExtensions;
    }

    /**
     * Method getWptLink.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the
     * net.sourceforge.gpstools.gpx.WptLink at the given index
     */
    public net.sourceforge.gpstools.gpx.WptLink getWptLink(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._wptLinkList.size()) {
            throw new IndexOutOfBoundsException("getWptLink: Index value '" + index + "' not in range [0.." + (this._wptLinkList.size() - 1) + "]");
        }
        
        return (net.sourceforge.gpstools.gpx.WptLink) _wptLinkList.get(index);
    }

    /**
     * Method getWptLink.Returns the contents of the collection in
     * an Array.  <p>Note:  Just in case the collection contents
     * are changing in another thread, we pass a 0-length Array of
     * the correct type into the API call.  This way we <i>know</i>
     * that the Array returned is of exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public net.sourceforge.gpstools.gpx.WptLink[] getWptLink(
    ) {
        net.sourceforge.gpstools.gpx.WptLink[] array = new net.sourceforge.gpstools.gpx.WptLink[0];
        return (net.sourceforge.gpstools.gpx.WptLink[]) this._wptLinkList.toArray(array);
    }

    /**
     * Method getWptLinkCount.
     * 
     * @return the size of this collection
     */
    public int getWptLinkCount(
    ) {
        return this._wptLinkList.size();
    }

    /**
     * Method hasDgpsid.
     * 
     * @return true if at least one Dgpsid has been added
     */
    public boolean hasDgpsid(
    ) {
        return this._has_dgpsid;
    }

    /**
     * Method hasSat.
     * 
     * @return true if at least one Sat has been added
     */
    public boolean hasSat(
    ) {
        return this._has_sat;
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
     * Method iterateWptLink.
     * 
     * @return an Iterator over all possible elements in this
     * collection
     */
    public java.util.Iterator<net.sourceforge.gpstools.gpx.WptLink> iterateWptLink(
    ) {
        return this._wptLinkList.iterator();
    }

    /**
     */
    public void removeAllWptLink(
    ) {
        this._wptLinkList.clear();
    }

    /**
     * Method removeWptLink.
     * 
     * @param vWptLink
     * @return true if the object was removed from the collection.
     */
    public boolean removeWptLink(
            final net.sourceforge.gpstools.gpx.WptLink vWptLink) {
        boolean removed = _wptLinkList.remove(vWptLink);
        return removed;
    }

    /**
     * Method removeWptLinkAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public net.sourceforge.gpstools.gpx.WptLink removeWptLinkAt(
            final int index) {
        java.lang.Object obj = this._wptLinkList.remove(index);
        return (net.sourceforge.gpstools.gpx.WptLink) obj;
    }

    /**
     * Sets the value of field 'ageofdgpsdata'. The field
     * 'ageofdgpsdata' has the following description: Number of
     * seconds since last DGPS update.
     *  
     * 
     * @param ageofdgpsdata the value of field 'ageofdgpsdata'.
     */
    public void setAgeofdgpsdata(
            final java.math.BigDecimal ageofdgpsdata) {
        this._ageofdgpsdata = ageofdgpsdata;
    }

    /**
     * Sets the value of field 'cmt'. The field 'cmt' has the
     * following description: GPS waypoint comment. Sent to GPS as
     * comment. 
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
     * following description: A text description of the element.
     * Holds additional information about the element intended for
     * the user, not the GPS.
     *  
     * 
     * @param desc the value of field 'desc'.
     */
    public void setDesc(
            final java.lang.String desc) {
        this._desc = desc;
    }

    /**
     * Sets the value of field 'dgpsid'. The field 'dgpsid' has the
     * following description: ID of DGPS station used in
     * differential correction.
     *  
     * 
     * @param dgpsid the value of field 'dgpsid'.
     */
    public void setDgpsid(
            final long dgpsid) {
        this._dgpsid = dgpsid;
        this._has_dgpsid = true;
    }

    /**
     * Sets the value of field 'ele'. The field 'ele' has the
     * following description: Elevation (in meters) of the point.
     *  
     * 
     * @param ele the value of field 'ele'.
     */
    public void setEle(
            final java.math.BigDecimal ele) {
        this._ele = ele;
    }

    /**
     * Sets the value of field 'fix'. The field 'fix' has the
     * following description: Type of GPX fix.
     *  
     * 
     * @param fix the value of field 'fix'.
     */
    public void setFix(
            final net.sourceforge.gpstools.gpx.types.FixType fix) {
        this._fix = fix;
    }

    /**
     * Sets the value of field 'geoidheight'. The field
     * 'geoidheight' has the following description: Height (in
     * meters) of geoid (mean sea level) above WGS84 earth
     * ellipsoid. As defined in NMEA GGA message.
     *  
     * 
     * @param geoidheight the value of field 'geoidheight'.
     */
    public void setGeoidheight(
            final java.math.BigDecimal geoidheight) {
        this._geoidheight = geoidheight;
    }

    /**
     * Sets the value of field 'hdop'. The field 'hdop' has the
     * following description: Horizontal dilution of precision.
     *  
     * 
     * @param hdop the value of field 'hdop'.
     */
    public void setHdop(
            final java.math.BigDecimal hdop) {
        this._hdop = hdop;
    }

    /**
     * Sets the value of field 'lat'. The field 'lat' has the
     * following description: The latitude of the point. Decimal
     * degrees, WGS84 datum.
     *  
     * 
     * @param lat the value of field 'lat'.
     */
    public void setLat(
            final java.math.BigDecimal lat) {
        this._lat = lat;
    }

    /**
     * Sets the value of field 'lon'. The field 'lon' has the
     * following description: The latitude of the point. Decimal
     * degrees, WGS84 datum.
     *  
     * 
     * @param lon the value of field 'lon'.
     */
    public void setLon(
            final java.math.BigDecimal lon) {
        this._lon = lon;
    }

    /**
     * Sets the value of field 'magvar'. The field 'magvar' has the
     * following description: Magnetic variation (in degrees) at
     * the point
     *  
     * 
     * @param magvar the value of field 'magvar'.
     */
    public void setMagvar(
            final java.math.BigDecimal magvar) {
        this._magvar = magvar;
    }

    /**
     * Sets the value of field 'name'. The field 'name' has the
     * following description: The GPS name of the waypoint. This
     * field will be transferred to and from the GPS. GPX does not
     * place restrictions on the length of this field or the
     * characters contained in it. It is up to the receiving
     * application to validate the field before sending it to the
     * GPS.
     *  
     * 
     * @param name the value of field 'name'.
     */
    public void setName(
            final java.lang.String name) {
        this._name = name;
    }

    /**
     * Sets the value of field 'pdop'. The field 'pdop' has the
     * following description: Position dilution of precision.
     *  
     * 
     * @param pdop the value of field 'pdop'.
     */
    public void setPdop(
            final java.math.BigDecimal pdop) {
        this._pdop = pdop;
    }

    /**
     * Sets the value of field 'sat'. The field 'sat' has the
     * following description: Number of satellites used to
     * calculate the GPX fix.
     *  
     * 
     * @param sat the value of field 'sat'.
     */
    public void setSat(
            final long sat) {
        this._sat = sat;
        this._has_sat = true;
    }

    /**
     * Sets the value of field 'src'. The field 'src' has the
     * following description: Source of data. Included to give user
     * some idea of reliability and accuracy of data. "Garmin
     * eTrex", "USGS quad Boston North", e.g.
     *  
     * 
     * @param src the value of field 'src'.
     */
    public void setSrc(
            final java.lang.String src) {
        this._src = src;
    }

    /**
     * Sets the value of field 'sym'. The field 'sym' has the
     * following description: Text of GPS symbol name. For
     * interchange with other programs, use the exact spelling of
     * the symbol as displayed on the GPS. If the GPS abbreviates
     * words, spell them out.
     *  
     * 
     * @param sym the value of field 'sym'.
     */
    public void setSym(
            final java.lang.String sym) {
        this._sym = sym;
    }

    /**
     * Sets the value of field 'time'. The field 'time' has the
     * following description: Creation/modification timestamp for
     * element. Date and time in are in Univeral Coordinated Time
     * (UTC), not local time! Conforms to ISO 8601 specification
     * for date/time representation. Fractional seconds are allowed
     * for millisecond timing in tracklogs. 
     *  
     * 
     * @param time the value of field 'time'.
     */
    public void setTime(
            final java.util.Date time) {
        this._time = time;
    }

    /**
     * Sets the value of field 'type'. The field 'type' has the
     * following description: Type (classification) of the
     * waypoint.
     *  
     * 
     * @param type the value of field 'type'.
     */
    public void setType(
            final java.lang.String type) {
        this._type = type;
    }

    /**
     * Sets the value of field 'vdop'. The field 'vdop' has the
     * following description: Vertical dilution of precision.
     *  
     * 
     * @param vdop the value of field 'vdop'.
     */
    public void setVdop(
            final java.math.BigDecimal vdop) {
        this._vdop = vdop;
    }

    /**
     * Sets the value of field 'wptExtensions'. The field
     * 'wptExtensions' has the following description: You can add
     * extend GPX by adding your own elements from another schema
     * here.
     *  
     * 
     * @param wptExtensions the value of field 'wptExtensions'.
     */
    public void setWptExtensions(
            final net.sourceforge.gpstools.gpx.WptExtensions wptExtensions) {
        this._wptExtensions = wptExtensions;
    }

    /**
     * 
     * 
     * @param index
     * @param vWptLink
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setWptLink(
            final int index,
            final net.sourceforge.gpstools.gpx.WptLink vWptLink)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._wptLinkList.size()) {
            throw new IndexOutOfBoundsException("setWptLink: Index value '" + index + "' not in range [0.." + (this._wptLinkList.size() - 1) + "]");
        }
        
        this._wptLinkList.set(index, vWptLink);
    }

    /**
     * 
     * 
     * @param vWptLinkArray
     */
    public void setWptLink(
            final net.sourceforge.gpstools.gpx.WptLink[] vWptLinkArray) {
        //-- copy array
        _wptLinkList.clear();
        
        for (int i = 0; i < vWptLinkArray.length; i++) {
                this._wptLinkList.add(vWptLinkArray[i]);
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

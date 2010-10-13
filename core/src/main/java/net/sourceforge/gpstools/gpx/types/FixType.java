/*
 * This class was automatically generated with 
 * <a href="http://www.castor.org">Castor 1.1.2</a>, using an XML
 * Schema.
 * $Id: FixType.java 358 2008-11-24 19:06:17Z ringler $
 */

package net.sourceforge.gpstools.gpx.types;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import java.util.Hashtable;

/**
 * Type of GPS fix. none means GPS had no fix. To signify "the fix
 * info is unknown, leave out fixType entirely. pps = military
 * signal used
 *  
 * 
 * @version $Revision: 358 $ $Date: 2007-07-16 16:55:47 +0200 (Mo, 16 Jul 2007) $
 */
@SuppressWarnings("serial")
public class FixType implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * The none type
     */
    public static final int VALUE_0_TYPE = 0;

    /**
     * The instance of the none type
     */
    public static final FixType VALUE_0 = new FixType(VALUE_0_TYPE, "none");

    /**
     * The 2d type
     */
    public static final int VALUE_1_TYPE = 1;

    /**
     * The instance of the 2d type
     */
    public static final FixType VALUE_1 = new FixType(VALUE_1_TYPE, "2d");

    /**
     * The 3d type
     */
    public static final int VALUE_2_TYPE = 2;

    /**
     * The instance of the 3d type
     */
    public static final FixType VALUE_2 = new FixType(VALUE_2_TYPE, "3d");

    /**
     * The dgps type
     */
    public static final int VALUE_3_TYPE = 3;

    /**
     * The instance of the dgps type
     */
    public static final FixType VALUE_3 = new FixType(VALUE_3_TYPE, "dgps");

    /**
     * The pps type
     */
    public static final int VALUE_4_TYPE = 4;

    /**
     * The instance of the pps type
     */
    public static final FixType VALUE_4 = new FixType(VALUE_4_TYPE, "pps");

    /**
     * Field _memberTable.
     */
    private static java.util.Hashtable<Object,Object> _memberTable = init();

    /**
     * Field type.
     */
    private final int type;

    /**
     * Field stringValue.
     */
    private java.lang.String stringValue = null;


      //----------------/
     //- Constructors -/
    //----------------/

    private FixType(final int type, final java.lang.String value) {
        super();
        this.type = type;
        this.stringValue = value;
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * Method enumerate.Returns an enumeration of all possible
     * instances of FixType
     * 
     * @return an Enumeration over all possible instances of FixType
     */
    public static java.util.Enumeration<java.lang.Object> enumerate(
    ) {
        return _memberTable.elements();
    }

    /**
     * Method getType.Returns the type of this FixType
     * 
     * @return the type of this FixType
     */
    public int getType(
    ) {
        return this.type;
    }

    /**
     * Method init.
     * 
     * @return the initialized Hashtable for the member table
     */
    private static java.util.Hashtable<Object,Object> init(
    ) {
        Hashtable<Object, Object> members = new Hashtable<Object, Object>();
        members.put("none", VALUE_0);
        members.put("2d", VALUE_1);
        members.put("3d", VALUE_2);
        members.put("dgps", VALUE_3);
        members.put("pps", VALUE_4);
        return members;
    }

    /**
     * Method readResolve. will be called during deserialization to
     * replace the deserialized object with the correct constant
     * instance.
     * 
     * @return this deserialized object
     */
    private java.lang.Object readResolve(
    ) {
        return valueOf(this.stringValue);
    }

    /**
     * Method toString.Returns the String representation of this
     * FixType
     * 
     * @return the String representation of this FixType
     */
    public java.lang.String toString(
    ) {
        return this.stringValue;
    }

    /**
     * Method valueOf.Returns a new FixType based on the given
     * String value.
     * 
     * @param string
     * @return the FixType value of parameter 'string'
     */
    public static net.sourceforge.gpstools.gpx.types.FixType valueOf(
            final java.lang.String string) {
        java.lang.Object obj = null;
        if (string != null) {
            obj = _memberTable.get(string);
        }
        if (obj == null) {
            String err = "" + string + " is not a valid FixType";
            throw new IllegalArgumentException(err);
        }
        return (FixType) obj;
    }

}

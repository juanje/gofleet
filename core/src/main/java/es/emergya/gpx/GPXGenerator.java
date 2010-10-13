/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.emergya.gpx;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import net.sourceforge.gpstools.gpx.Gpx;
import net.sourceforge.gpstools.gpx.GpxType;
import net.sourceforge.gpstools.gpx.Metadata;
import net.sourceforge.gpstools.gpx.Trk;
import net.sourceforge.gpstools.gpx.Trkpt;
import net.sourceforge.gpstools.gpx.Trkseg;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.handlers.DateFieldHandler;

import com.vividsolutions.jts.geom.Geometry;

import es.emergya.bbdd.bean.HistoricoGPS;

/**
 * 
 * @author jlrodriguez
 */
public class GPXGenerator {

	private static final Log LOG = LogFactory.getLog(GPXGenerator.class);
	private static final String CREATOR = "TelMMA";
	public final static String ISO_DATE = "yyyy-MM-dd'T'HH:mm:ss";
	private final static Locale LOCALE = new Locale("es", "ES");

	public final static TimeZone UTC = TimeZone.getTimeZone("UTC");
	private String recurso;

	public GPXGenerator(String recurso) {
		this.recurso = recurso;
	}

	public GpxType generaGPX(List<HistoricoGPS> historico) throws IOException {

		Metadata metadata = new Metadata();
		metadata.setDesc("Recorrido de " + recurso);
		metadata.setTime(new Date());
		GpxType gpx = new Gpx();
		gpx.setCreator(CREATOR);
		gpx.setMetadata(metadata);

		Trk track = new Trk();
		track.setName(recurso);
		Trkseg segmento = new Trkseg();
		for (HistoricoGPS hist : historico) {
			Geometry geom = hist.getGeom();
			if (geom != null && geom.getCentroid().getX() != 0.0d
					&& geom.getCentroid().getY() != 0.0d) {

				Trkpt punto = new Trkpt();
				punto.setLon(BigDecimal.valueOf(geom.getCentroid().getX()));
				punto.setLat(BigDecimal.valueOf(geom.getCentroid().getY()));
				punto.setTime(hist.getMarcaTemporal());
				punto.setName(recurso
						+ " "
						+ DateFormat.getDateInstance(DateFormat.SHORT, LOCALE)
								.format(new Date(hist.getMarcaTemporal()
										.getTime()))
						+ " "
						+ DateFormat.getTimeInstance(DateFormat.MEDIUM, LOCALE)
								.format(new Date(hist.getMarcaTemporal()
										.getTime())));

				segmento.addTrkpt(punto);
			}

		}
		track.addTrkseg(segmento);
		gpx.addTrk(track);
		return gpx;

	}

	/**
	 * 
	 * @param gpx
	 * @param out
	 * @throws IOException
	 * @author Moritz Ringler.
	 */
	public void writeGPX(GpxType gpx, Writer out) throws IOException {

		// The default timezone for GPX is UTC

		DateFieldHandler.setSuppressMillis(true);
		// This should do the job but it does not
		// see http://jira.codehaus.org/browse/CASTOR-2220
		// Fixed in v1.2. We are using castor-xml-1.3
		DateFieldHandler.setDefaultTimeZone(UTC);

		try {
			Marshaller m = new Marshaller(new BufferedWriter(out));
			m.marshal(gpx);
		} catch (Exception ex) {
			IOException ioex = new IOException("Error writing GPX.");
			ioex.initCause(ex);
			throw ioex;
		} finally {
		}
	}

	// public static Geometry transform(com.vividsolutions.jts.geom.Geometry
	// geom,
	// final String sourceSRID, final String targetSRID) {
	// Geometry p = geom;
	// try {
	// CoordinateReferenceSystem sourceCRS = CRS.decode(sourceSRID);
	// CoordinateReferenceSystem targetCRS = CRS.decode(targetSRID);
	// MathTransform transform = CRS.findMathTransform(sourceCRS,
	// targetCRS);
	// com.vividsolutions.jts.geom.Geometry targetGeometry = JTS.transform(geom,
	// transform);
	// p = targetGeometry;
	// if (targetSRID.indexOf(":") > 0) {
	// p.setSRID(new Integer(targetSRID.substring(targetSRID.indexOf(":") +
	// 1)));
	// }
	// } catch (Throwable t) {
	// LOG.error("Error al transformar la proyeccion", t);
	// }
	// return p;
	// }
}

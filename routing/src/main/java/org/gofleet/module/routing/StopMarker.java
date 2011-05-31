/**
 * Copyright (C) 2010, Emergya (http://www.emergya.es)
 *
 * @author <a href="mailto:marias@emergya.es">María Arias de Reyna</a>
 *
 * This file is part of GoFleet
 *
 * This software is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *
 * As a special exception, if you link this library with other files to
 * produce an executable, this library does not by itself cause the
 * resulting executable to be covered by the GNU General Public License.
 * This exception does not however invalidate any other reasons why the
 * executable file might be covered by the GNU General Public License.
 */
package org.gofleet.module.routing;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;

import org.openstreetmap.josm.data.coor.LatLon;
import org.openstreetmap.josm.gui.MapView;
import org.openstreetmap.josm.gui.layer.markerlayer.Marker;
import org.openstreetmap.josm.gui.layer.markerlayer.MarkerLayer;

public class StopMarker extends Marker {
	private boolean paintIcon = true;

	public boolean isPaintIcon() {
		return paintIcon;
	}

	public void setPaintIcon(boolean paintIcon) {
		this.paintIcon = paintIcon;
	}

	public StopMarker(LatLon ll, String string, String string2,
			MarkerLayer stops, int i, int j, Color color) {
		super(ll, string, string2, stops, i, j);
		this.color = color;
	}

	public void paint(Graphics g, MapView mv, boolean mousePressed, String show) {
		Point screen = mv.getPoint(eastNorth);
		int ih;
		int radio = ih = 4;
		if (paintIcon) {
			g.setColor(color.darker());
			g.drawOval(screen.x - radio, screen.y - radio, radio * 2, radio * 2);
			g.setColor(color.brighter());
			g.fillOval(screen.x - radio, screen.y - radio, radio * 2, radio * 2);
		}
		g.setColor(color);
		g.setFont(g.getFont().deriveFont(Font.BOLD, g.getFont().getSize()));
		FontMetrics fm = g.getFontMetrics(g.getFont());
		int w = fm.stringWidth(text) + 4;
		int h = fm.getHeight() + 4;
		g.setColor(bgcolor);
		g.fillRect(screen.x - w / 2, screen.y - ih / 2 - h - 2, w, h);
		g.setColor(color);
		g.drawRect(screen.x - w / 2, screen.y - ih / 2 - h - 2, w, h);
		g.drawString(text, screen.x - w / 2 + 2, screen.y - (ih + h) / 2 + 4);
	}

}

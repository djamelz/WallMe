/*
 * Copyright (c) 2010-2011, Martin Pernollet
 * All rights reserved. 
 *
 * Redistribution in binary form, with or without modification, is permitted.
 * Edition of source files is allowed.
 * Redistribution of original or modified source files is FORBIDDEN.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, 
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. 
 * IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, 
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, 
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) 
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT 
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, 
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package wall.me.main;

import java.util.ArrayList;
import java.util.List;

import org.jzy3d.analysis.AbstractAnalysis;
import org.jzy3d.analysis.AnalysisLauncher;
import org.jzy3d.chart.Chart;
import org.jzy3d.chart.ChartScene;
import org.jzy3d.colors.Color;
import org.jzy3d.colors.ColorMapper;
import org.jzy3d.colors.colormaps.ColorMapRainbow;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.plot3d.primitives.Point;
import org.jzy3d.plot3d.primitives.Polygon;
import org.jzy3d.plot3d.primitives.Scatter;
import org.jzy3d.plot3d.primitives.Shape;
import org.jzy3d.plot3d.rendering.canvas.Quality;

import wall.me.data.MeshReader;
import wall.me.data.PolarMesh;
import wall.me.data.PolarSample;


public class Display extends AbstractAnalysis {
	private PolarMesh mesh;
	
	public Display(PolarMesh mesh){
		this.mesh = mesh;
	}
	
	public void run() throws Exception {
		AnalysisLauncher.open(this);		
	}
	
	public void init() {
		chart = new Chart(Quality.Advanced, "newt");
		makePolys(chart.getScene());
	}
	
	private void makeScatter(ChartScene scene) {
		int nPoints = mesh.getNPoints();
		int curPoint = 0;
		Coord3d coord;
		Color[] colors = new Color[nPoints];
		Coord3d[] points = new Coord3d[nPoints];
		
		for (PolarSample[] row : mesh.getColumns()) {
			for (PolarSample sample : row) {
				coord = sample.toCoord3d();
				points[curPoint] = coord;
				colors[curPoint] = new Color(coord.x, coord.y, coord.z, .25f);
				curPoint++;
			}
		}
        
		scene.add(new Scatter(points, colors));
	}
	
	private void makePolys(ChartScene scene) {
		List<Polygon> polys = new ArrayList<Polygon>();
		List<PolarSample[]> columns = mesh.getColumns();
		System.out.println(columns.size() + " rows");
		
		// TVI : -1 -> -2
		for (int i = 0; i < columns.size() - 2; i++) {
			PolarSample[] col1 = columns.get(i);
			PolarSample[] col2 = columns.get(i + 1);
			
			for (int j = 0; j < col1.length - 1; j++) {
				if (j == 0) {
					System.out.println(col1.length + " cols");
				}
				
				Polygon poly = new Polygon();
				poly.add(new Point(col1[j].toCoord3d()));
				poly.add(new Point(col2[j].toCoord3d()));
				poly.add(new Point(col2[j + 1].toCoord3d()));
				poly.add(new Point(col1[j + 1].toCoord3d()));
				
				printPS(col1[j]);
				printPS(col2[j]);
				printPS(col2[j + 1]);
				printPS(col1[j + 1]);
				System.out.println();
				
				polys.add(poly);
			}
		}
        
		Shape surface = new Shape(polys);
		surface.setColorMapper(new ColorMapper(new ColorMapRainbow(), surface.getBounds().getZmin(), surface.getBounds().getZmax(), new org.jzy3d.colors.Color(1,1,1,1f)));
		surface.setWireframeDisplayed(true);
		surface.setWireframeColor(org.jzy3d.colors.Color.BLACK);
		
		scene.add(surface);
	}
	
	private void printPS(PolarSample p) {
		double x = deg(p.alpha);
		double y = deg(p.beta);
		double z = p.distance * MeshReader.ZSCALE;
		System.out.println(x + " - " + y + " - " + z);
	}
	
	private double deg(double rad) {
		return rad * 180 / Math.PI;
	}
}
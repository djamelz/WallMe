package wall.me.data;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.StringReader;

public class MeshReader {
	public static final double ZSCALE = 400.0;

	private int alphaRange = -1;
	private BufferedReader reader;

	// Raw string or @fileName
	private String source;
	
	public MeshReader(String source) throws Exception {
		this.source = source;
		open();
	}
	
	private void open() throws Exception {
		if (source.charAt(0) == '@') {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(source.substring(1))));
		}
		else {
			reader = new BufferedReader(new StringReader(source));
		}
	}
	
	private void reopen() throws Exception {
		reader.close();
		open();
	}
	
	public int getAlphaRange() throws Exception {
		
		if (alphaRange == -1) {
			double alpha = nextSample().alpha;
			double oldAlpha;
			alphaRange = 0;

			do {
				oldAlpha = alpha;
				alpha = nextSample().alpha;
				alphaRange++;
			} while (alpha == oldAlpha);
			reopen();
		}
		
		return alphaRange;
	}
	
	public PolarSample nextSample() throws Exception {
		String line = reader.readLine();
		if (line == null) {
			return null;
		}
		
		String[] fields = line.split(";");
		
		if ("180".equals(fields[1])) {
			line = reader.readLine();
			if (line == null) {
				return null;
			}
			fields = line.split(";");
		}
		
		if (fields.length != 3) {
			return null;
		}
		
		return new PolarSample(rad(Double.parseDouble(fields[0])), rad(Double.parseDouble(fields[1])), Double.parseDouble(fields[2]) / ZSCALE);
	}
	
	public void dispose() throws Exception {
		reader.close();
	}
	
	private static double rad(double deg) {
		return Math.PI * deg / 180.0;
	}
}

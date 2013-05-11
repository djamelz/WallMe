package wall.me.data;

import java.io.PrintWriter;

public class SampleGenerator {
	public static void main(String[] args) throws Exception {
		String fileName = "/tmp/sample.txt";
		if (args.length >= 1) {
			fileName = args[0];
		}
		
		System.out.println("Generating " + fileName);
		PrintWriter pw = new PrintWriter(fileName);
		
		for (int beta = 10; beta < 60; beta++) {
			for (int alpha = 0; alpha < 120; alpha++) {
				int distance = 300;
				pw.println(alpha + ";" + beta + ";" + distance);
			}
		}
		
		pw.close();
		System.out.println("Done");
	}
}

//package us.sc.k12;
import java.io.*;
import java.util.*;
import javax.swing.JFileChooser;

public class RunCompiler {
	// Mary and Tia
	// September 23, 2013

	public static void main(String[] args) throws IOException {
		ArrayList<String> lines = new ArrayList<String>();
		SimpleCompiler compiler = new SimpleCompiler();
		JFileChooser chooser = new JFileChooser();
		int returnVal = chooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File myfile = chooser.getSelectedFile();
			BufferedReader infile = new BufferedReader(new FileReader(myfile));
			String line = infile.readLine();
			while (line != null) {
				lines.add(line);
				line = infile.readLine();
			}
			compiler.evaluate(lines);
			infile.close();
		}
		System.out.println("You did a great job!");
	}

}

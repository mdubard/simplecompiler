//package us.sc.k12;

import java.util.ArrayList;
import java.util.Stack;
import java.io.*;

public class SimpleCompiler {
	// Mary and Tia
	// September 23, 2013
	private static int instructionIndex;
	private static int dataIndex;
	private int lastLine;
	private static ArrayList<SymbolRow> symbols;
	private ArrayList<Integer> CommandFile;
	private final String constant = "CONST";
	private final String variable = "VAR";
	private final String lineNumber = "LN";
	private static String[] lineNumbers;
	private int simpleLine;

	public SimpleCompiler() {
		instructionIndex = 0;
		dataIndex = 99;
		lastLine = -1;
		symbols = new ArrayList<SymbolRow>();
		lineNumbers = new String[100];
		CommandFile = new ArrayList<Integer>();
		for (int x = 0; x < 100; x++) {
			lineNumbers[x] = "0";
		}
	}

	public void evaluate(ArrayList<String> lines) throws IOException {
		while (dataIndex != instructionIndex) {
			String[] fileLine;
			for (int index = 0; index < lines.size(); index++) {
				simpleLine = index;
				fileLine = lines.get(index).split(" ");
				if (!isNumeric(fileLine[0])) // if line number isn't made of
												// numbers
				{
					System.out
							.println("Cannot find line number. Please try another file.");
					System.exit(0);
				} else {
					if (Integer.parseInt(fileLine[0]) <= lastLine) // if line
																	// number is
																	// less than
																	// the last
																	// line
																	// number
					{
						System.out
								.println("File does not contain correct line numbers. Please try another file.");
						System.exit(0);
					} else if (fileLine.length >= 2) { // checks to make sure
														// it's a valid length
						String comm = fileLine[1];
						int command = evaluateCommand(comm);
						symbols.add(new SymbolRow(lineNumber, fileLine[0],
								instructionIndex)); // adds line number to
													// symbol table
						if (fileLine.length > 2 && !isNumeric(fileLine[2])
								&& isWord(fileLine[2])
								&& search(fileLine[2], variable) < 0) { // checks
																		// if
																		// variable
																		// is a
																		// word
																		// or
																		// letter
							symbols.add(new SymbolRow(variable, fileLine[2],
									dataIndex));
							dataIndex--;
						} else if (fileLine.length > 2 && fileLine.length != 7
								&& !isWord(fileLine[2])) {
							System.out
									.println("Incorrect variable: please enter letters.");
							System.exit(0);
						} else if (command >= 0) { // if it's a valid command
							if (command == 0 && fileLine.length == 7) // if the
																		// command
																		// is if
							{
								if (isNumeric(fileLine[2])) { // if first
																// operand is a
																// constant
									if (search(fileLine[2], constant) < 0) // if
																			// constant
																			// doesn't
																			// exist,
																			// make
																			// it
									{
										symbols.add(new SymbolRow(constant,
												fileLine[2], dataIndex)); // adds
																			// constant
																			// to
																			// symbol
																			// table
										int x = Integer.parseInt(fileLine[2]);
										boolean negative = false;
										if (x < 0) {
											x = x * -1;
											negative = true;
										}
										int asd = 2200 + x;
										CommandFile.add(asd); // loads the
																// number
										instructionIndex++;
										CommandFile.add(2100 + dataIndex); // stores
																			// the
																			// number
																			// into
																			// SML
										instructionIndex++;
										dataIndex--;
										if (negative) {
											CommandFile.add(3100 + dataIndex++);
											instructionIndex++;
											CommandFile.add(3100 + dataIndex++);
											instructionIndex++;
										}
									}
									if (isNumeric(fileLine[4])) { // if second
																	// operand
																	// is a
																	// constant
										if (search(fileLine[4], constant) < 0) // if
																				// constant
																				// doesn't
																				// exist,
																				// make
																				// it
										{
											symbols.add(new SymbolRow(constant,
													fileLine[2], dataIndex)); // adds
																				// constant
																				// to
																				// symbol
																				// table
											int x = Integer
													.parseInt(fileLine[2]);
											boolean negative = false;
											if (x < 0) {
												x = x * -1;
												negative = true;
											}
											int asd = 2200 + x;
											CommandFile.add(asd); // loads the
																	// number
											instructionIndex++;
											CommandFile.add(2100 + dataIndex); // stores
																				// the
																				// number
																				// into
																				// SML
											instructionIndex++;
											dataIndex--;
											if (negative) {
												CommandFile
														.add(3100 + dataIndex++);
												instructionIndex++;
												CommandFile
														.add(3100 + dataIndex++);
												instructionIndex++;
											}
										}
									} else if (isWord(fileLine[4])) { // if
																		// second
																		// operand
																		// is a
																		// variable
										if (search(fileLine[4], variable) < 0) { // if
																					// variable
																					// doesn't
																					// exist,
																					// quit
																					// program
											System.out
													.println("File tried to use variable that doesn't exist. Please try another file.");
											System.exit(0);
										}
									}
								} else if (isWord(fileLine[2])) { // if first
																	// operand
																	// is a
																	// variable
									if (search(fileLine[2], variable) < 0) { // if
																				// variable
																				// doesn't
																				// exist,
																				// quit
																				// program
										System.out
												.println("File tried to use variable that doesn't exist. Please try another file.");
										System.exit(0);
									}
									if (isNumeric(fileLine[4])) { // if second
																	// operand
																	// is a
																	// constant
										if (search(fileLine[4], constant) < 0) // if
																				// constant
																				// doesn't
																				// exist,
																				// make
																				// it
										{
											int x = Integer
													.parseInt(fileLine[2]);
											boolean negative = false;
											if (x < 0) {
												x = x * -1;
												negative = true;
											}
											symbols.add(new SymbolRow(constant,
													fileLine[4], dataIndex)); // store
																				// constant
																				// in
																				// symbol
																				// table
											int asd = 2200 + Integer
													.parseInt(fileLine[4]);
											CommandFile.add(asd); // load
																	// constant
																	// to SML
											instructionIndex++;
											CommandFile.add(2100 + dataIndex); // store
																				// constant
																				// in
																				// SML
											instructionIndex++;
											dataIndex--;
											if (negative) {
												CommandFile
														.add(3100 + dataIndex++);
												instructionIndex++;
												CommandFile
														.add(3100 + dataIndex++);
												instructionIndex++;
											}
										}
									} else if (isWord(fileLine[4])) { // if
																		// second
																		// operand
																		// is a
																		// variable
										if (search(fileLine[4], variable) < 0) { // if
																					// variable
																					// doesn't
																					// exist,
																					// quit
																					// program
											System.out
													.println("File tried to use variable that doesn't exist. Please try another file.");
											System.exit(0);
										}
									}
								} else if (!fileLine[5].equals("goto")) // if
																		// the
																		// if
																		// statement
																		// doesn't
																		// have
																		// goto
																		// as
																		// later
																		// command
								{
									System.out
											.println("If statement doesn't use 'goto'. Please try another file.");
									System.exit(0);
								} else if (!isNumeric(fileLine[6])) // if the if
																	// statement
																	// doesn't
																	// contain a
																	// correct
																	// line
																	// number to
																	// go to
								{
									System.out
											.println("If statement doesn't go to a line number. Please try another file.");
									System.exit(0);
								}
								if (isRelOp(fileLine[3]) > 0) { // checks if
																// it's a valid
																// relational
																// operator
									int rel = isRelOp(fileLine[3]);
									switch (rel) {
									case 1: // greater than
										if (isWord(fileLine[4])
												&& isWord(fileLine[2])) // if
																		// both
																		// operands
																		// are
																		// variables
										{
											CommandFile.add(2000 + search(
													fileLine[4], variable)); // load
																				// the
																				// second
																				// operand
											instructionIndex++;
											CommandFile.add(3100 + search(
													fileLine[2], variable)); // subtract
																				// the
																				// first
																				// operand
											instructionIndex++;
											if (search(fileLine[6], lineNumber) >= 0) { // if
																						// the
																						// line
																						// number
																						// already
																						// exists
												CommandFile
														.add(4100 + search(
																fileLine[6],
																lineNumber)); // if
																				// the
																				// difference
																				// is
																				// negative
																				// go
																				// to
																				// the
																				// line
																				// number
												instructionIndex++;
											} else { // if the line number
														// doesn't exist
												CommandFile.add(4100);
												lineNumbers[instructionIndex] = fileLine[6]; // use
																								// instruction
																								// index
																								// to
																								// place
																								// simple
																								// line
																								// number
																								// with
																								// it
												instructionIndex++;
											}
										} else if (isWord(fileLine[4])
												&& isNumeric(fileLine[2])) { // if
																				// the
																				// first
																				// operand
																				// is
																				// a
																				// variable
																				// and
																				// the
																				// second
																				// operand
																				// is
																				// a
																				// constant
											CommandFile.add(2000 + search(
													fileLine[4], variable)); // load
																				// the
																				// second
																				// operand
											instructionIndex++;
											CommandFile.add(3100 + search(
													fileLine[2], constant)); // subtract
																				// the
																				// first
																				// operand
											instructionIndex++;
											if (search(fileLine[6], lineNumber) >= 0) { // if
																						// the
																						// line
																						// number
																						// exists
												CommandFile
														.add(4100 + search(
																fileLine[6],
																lineNumber)); // if
																				// the
																				// difference
																				// is
																				// negative
																				// go
																				// to
																				// the
																				// line
																				// number
												instructionIndex++;
											} else { // if the line number
														// doesn't exist
												CommandFile.add(4100);
												lineNumbers[instructionIndex] = fileLine[6]; // use
																								// instruction
																								// index
																								// to
																								// place
																								// simple
																								// line
																								// number
																								// with
																								// it
												instructionIndex++;
											}
										} else if (isNumeric(fileLine[4])
												&& isWord(fileLine[2])) { // if
																			// the
																			// first
																			// operand
																			// is
																			// a
																			// constant
																			// and
																			// the
																			// second
																			// operand
																			// is
																			// a
																			// variable
											CommandFile.add(2000 + search(
													fileLine[4], constant)); // load
																				// the
																				// second
																				// operand
											instructionIndex++;
											CommandFile.add(3100 + search(
													fileLine[2], variable)); // subtract
																				// the
																				// first
																				// operand
											instructionIndex++;
											if (search(fileLine[6], lineNumber) >= 0) { // if
																						// the
																						// line
																						// number
																						// exists
												CommandFile
														.add(4100 + search(
																fileLine[6],
																lineNumber)); // if
																				// the
																				// difference
																				// is
																				// negative
																				// go
																				// to
																				// the
																				// line
																				// number
												instructionIndex++;
											} else { // if the line number
														// doesn't exist
												CommandFile.add(4100);
												lineNumbers[instructionIndex] = fileLine[6]; // use
																								// instruction
																								// index
																								// to
																								// place
																								// simple
																								// line
																								// number
																								// with
																								// it
												instructionIndex++;
											}
										} else if (isNumeric(fileLine[4])
												&& isNumeric(fileLine[2])) { // if
																				// both
																				// operands
																				// are
																				// constants
											CommandFile.add(2000 + search(
													fileLine[4], constant)); // load
																				// the
																				// second
																				// operand
											instructionIndex++;
											CommandFile.add(3100 + search(
													fileLine[2], constant)); // subtract
																				// the
																				// first
																				// operand
											instructionIndex++;
											if (search(fileLine[6], lineNumber) >= 0) { // if
																						// the
																						// line
																						// number
																						// exists
												CommandFile
														.add(4100 + search(
																fileLine[6],
																lineNumber)); // if
																				// the
																				// difference
																				// is
																				// negative
																				// go
																				// to
																				// the
																				// line
																				// number
												instructionIndex++;
											} else { // if the line number
														// doesn't exist
												CommandFile.add(4100);
												lineNumbers[instructionIndex] = fileLine[6]; // use
																								// instruction
																								// index
																								// to
																								// place
																								// simple
																								// line
																								// number
																								// with
																								// it
												instructionIndex++;
											}
										}
										break;
									case 2: // less than
										if (isWord(fileLine[2])
												&& isWord(fileLine[4])) // if
																		// both
																		// operands
																		// are
																		// variables
										{
											CommandFile.add(2000 + search(
													fileLine[2], variable)); // load
																				// the
																				// first
																				// operand
											instructionIndex++;
											CommandFile.add(3100 + search(
													fileLine[4], variable)); // subtract
																				// the
																				// second
																				// operand
											instructionIndex++;
											if (search(fileLine[6], lineNumber) >= 0) { // if
																						// the
																						// line
																						// number
																						// exists
												CommandFile
														.add(4100 + search(
																fileLine[6],
																lineNumber)); // if
																				// the
																				// difference
																				// is
																				// negative
																				// go
																				// to
																				// the
																				// line
																				// number
												instructionIndex++;
											} else { // if the line number
														// doesn't exist
												CommandFile.add(4100);
												lineNumbers[instructionIndex] = fileLine[6]; // use
																								// instruction
																								// index
																								// to
																								// place
																								// simple
																								// line
																								// number
																								// with
																								// it
												instructionIndex++;
											}
										} else if (isWord(fileLine[2])
												&& isNumeric(fileLine[4])) { // if
																				// the
																				// first
																				// operand
																				// is
																				// a
																				// variable
																				// and
																				// the
																				// second
																				// variable
																				// is
																				// a
																				// constant
											CommandFile.add(2000 + search(
													fileLine[2], variable)); // load
																				// the
																				// first
																				// operand
											instructionIndex++;
											CommandFile.add(3100 + search(
													fileLine[4], constant)); // subtract
																				// the
																				// second
																				// operand
											instructionIndex++;
											if (search(fileLine[6], lineNumber) >= 0) { // if
																						// the
																						// line
																						// number
																						// exists
												CommandFile
														.add(4100 + search(
																fileLine[6],
																lineNumber)); // if
																				// the
																				// difference
																				// is
																				// negative
																				// go
																				// to
																				// the
																				// line
																				// number
												instructionIndex++;
											} else { // if the line number
														// doesn't exist
												CommandFile.add(4100);
												lineNumbers[instructionIndex] = fileLine[6]; // use
																								// instruction
																								// index
																								// to
																								// place
																								// simple
																								// line
																								// number
																								// with
																								// it
												instructionIndex++;
											}
										} else if (isNumeric(fileLine[2])
												&& isWord(fileLine[4])) { // if
																			// the
																			// first
																			// operand
																			// is
																			// a
																			// constant
																			// and
																			// the
																			// second
																			// operand
																			// is
																			// a
																			// variable
											CommandFile.add(2000 + search(
													fileLine[2], constant));
											instructionIndex++;
											CommandFile.add(3100 + search(
													fileLine[4], variable));
											instructionIndex++;
											if (search(fileLine[6], lineNumber) >= 0) {
												CommandFile
														.add(4100 + search(
																fileLine[6],
																lineNumber));
												instructionIndex++;
											} else {
												CommandFile.add(4100);
												lineNumbers[instructionIndex] = fileLine[6]; // use
																								// instruction
																								// index
																								// to
																								// place
																								// simple
																								// line
																								// number
																								// with
																								// it
												instructionIndex++;
											}
										} else if (isNumeric(fileLine[2])
												&& isNumeric(fileLine[4])) { // if
																				// the
																				// both
																				// operands
																				// are
																				// constants
											CommandFile.add(2000 + search(
													fileLine[2], constant));
											instructionIndex++;
											CommandFile.add(3100 + search(
													fileLine[4], constant));
											instructionIndex++;
											if (search(fileLine[6], lineNumber) >= 0) {
												CommandFile
														.add(4100 + search(
																fileLine[6],
																lineNumber));
												instructionIndex++;
											} else {
												CommandFile.add(4100);
												lineNumbers[instructionIndex] = fileLine[6]; // use
																								// instruction
																								// index
																								// to
																								// place
																								// simple
																								// line
																								// number
																								// with
																								// it
												instructionIndex++;
											}
										}
										break;
									case 3: // less than or equal to
										if (isWord(fileLine[2])
												&& isWord(fileLine[4])) // if
																		// both
																		// operands
																		// are
																		// variables
										{
											CommandFile.add(2000 + search(
													fileLine[2], variable)); // load
																				// the
																				// first
																				// operand
											instructionIndex++;
											CommandFile.add(3100 + search(
													fileLine[4], variable)); // subtract
																				// the
																				// second
																				// operand
											instructionIndex++;
											if (search(fileLine[6], lineNumber) >= 0) { // if
																						// line
																						// number
																						// exists
												CommandFile
														.add(4100 + search(
																fileLine[6],
																lineNumber)); // if
																				// the
																				// difference
																				// is
																				// negative
																				// go
																				// to
																				// the
																				// line
																				// number
												instructionIndex++;
												CommandFile
														.add(4200 + search(
																fileLine[6],
																lineNumber)); // if
																				// the
																				// difference
																				// is
																				// 0
																				// go
																				// to
																				// the
																				// line
																				// number
												instructionIndex++;
											} else { // if line number doesn't
														// exist
												CommandFile.add(4100);
												lineNumbers[instructionIndex] = fileLine[6]; // use
																								// instruction
																								// index
																								// to
																								// place
																								// simple
																								// line
																								// number
																								// with
																								// it
												instructionIndex++;
												CommandFile.add(4200);
												lineNumbers[instructionIndex] = fileLine[6]; // use
																								// instruction
																								// index
																								// to
																								// place
																								// simple
																								// line
																								// number
																								// with
																								// it
												instructionIndex++;
											}
										} else if (isWord(fileLine[2])
												&& isNumeric(fileLine[4])) { // if
																				// first
																				// operand
																				// is
																				// a
																				// variable
																				// and
																				// second
																				// operand
																				// is
																				// a
																				// constant
											CommandFile.add(2000 + search(
													fileLine[2], variable));
											instructionIndex++;
											CommandFile.add(3100 + search(
													fileLine[4], constant));
											instructionIndex++;
											if (search(fileLine[6], lineNumber) >= 0) {
												CommandFile
														.add(4100 + search(
																fileLine[6],
																lineNumber));
												instructionIndex++;
												CommandFile
														.add(4200 + search(
																fileLine[6],
																lineNumber));
												instructionIndex++;
											} else {
												CommandFile.add(4100);
												lineNumbers[instructionIndex] = fileLine[6]; // use
																								// instruction
																								// index
																								// to
																								// place
																								// simple
																								// line
																								// number
																								// with
																								// it
												instructionIndex++;
												CommandFile.add(4200);
												lineNumbers[instructionIndex] = fileLine[6]; // use
																								// instruction
																								// index
																								// to
																								// place
																								// simple
																								// line
																								// number
																								// with
																								// it
												instructionIndex++;
											}
										} else if (isNumeric(fileLine[2])
												&& isWord(fileLine[4])) { // if
																			// first
																			// operand
																			// is
																			// a
																			// constant
																			// and
																			// second
																			// operand
																			// is
																			// a
																			// variable
											CommandFile.add(2000 + search(
													fileLine[2], constant));
											instructionIndex++;
											CommandFile.add(3100 + search(
													fileLine[4], variable));
											instructionIndex++;
											if (search(fileLine[6], lineNumber) >= 0) {
												CommandFile
														.add(4100 + search(
																fileLine[6],
																lineNumber));
												instructionIndex++;
												CommandFile
														.add(4200 + search(
																fileLine[6],
																lineNumber));
												instructionIndex++;
											} else {
												CommandFile.add(4100);
												lineNumbers[instructionIndex] = fileLine[6]; // use
																								// instruction
																								// index
																								// to
																								// place
																								// simple
																								// line
																								// number
																								// with
																								// it
												instructionIndex++;
												CommandFile.add(4200);
												lineNumbers[instructionIndex] = fileLine[6]; // use
																								// instruction
																								// index
																								// to
																								// place
																								// simple
																								// line
																								// number
																								// with
																								// it
												instructionIndex++;
											}
										} else if (isNumeric(fileLine[2])
												&& isNumeric(fileLine[4])) { // if
																				// both
																				// operands
																				// are
																				// constants
											CommandFile.add(2000 + search(
													fileLine[2], constant));
											instructionIndex++;
											CommandFile.add(3100 + search(
													fileLine[4], constant));
											instructionIndex++;
											if (search(fileLine[6], lineNumber) >= 0) {
												CommandFile
														.add(4100 + search(
																fileLine[6],
																lineNumber));
												instructionIndex++;
												CommandFile
														.add(4200 + search(
																fileLine[6],
																lineNumber));
												instructionIndex++;
											} else {
												CommandFile.add(4100);
												lineNumbers[instructionIndex] = fileLine[6]; // use
																								// instruction
																								// index
																								// to
																								// place
																								// simple
																								// line
																								// number
																								// with
																								// it
												instructionIndex++;
												CommandFile.add(4200);
												lineNumbers[instructionIndex] = fileLine[6]; // use
																								// instruction
																								// index
																								// to
																								// place
																								// simple
																								// line
																								// number
																								// with
																								// it
												instructionIndex++;
											}
										}
										break;
									case 4: // greater than or equal to
										if (isWord(fileLine[4])
												&& isWord(fileLine[2])) // if
																		// both
																		// operands
																		// are
																		// variables
										{
											CommandFile.add(2000 + search(
													fileLine[4], variable)); // load
																				// the
																				// second
																				// operand
											instructionIndex++;
											CommandFile.add(3100 + search(
													fileLine[2], variable)); // subtract
																				// the
																				// first
																				// operand
											instructionIndex++;
											if (search(fileLine[6], lineNumber) >= 0) { // if
																						// line
																						// number
																						// exists
												CommandFile
														.add(4100 + search(
																fileLine[6],
																lineNumber)); // if
																				// difference
																				// is
																				// negative
																				// go
																				// to
																				// the
																				// line
																				// number
												instructionIndex++;
												CommandFile
														.add(4200 + search(
																fileLine[6],
																lineNumber)); // if
																				// difference
																				// is
																				// 0
																				// go
																				// to
																				// the
																				// line
																				// number
												instructionIndex++;
											} else { // if line number doesn't
														// exist
												CommandFile.add(4100);
												lineNumbers[instructionIndex] = fileLine[6]; // use
																								// instruction
																								// index
																								// to
																								// place
																								// simple
																								// line
																								// number
																								// with
																								// it
												instructionIndex++;
												CommandFile.add(4200);
												lineNumbers[instructionIndex] = fileLine[6]; // use
																								// instruction
																								// index
																								// to
																								// place
																								// simple
																								// line
																								// number
																								// with
																								// it
												instructionIndex++;
											}
										} else if (isWord(fileLine[4])
												&& isNumeric(fileLine[2])) { // if
																				// second
																				// operand
																				// is
																				// a
																				// variable
																				// and
																				// the
																				// first
																				// operand
																				// is
																				// a
																				// constant
											CommandFile.add(2000 + search(
													fileLine[4], variable));
											instructionIndex++;
											CommandFile.add(3100 + search(
													fileLine[2], constant));
											instructionIndex++;
											if (search(fileLine[6], lineNumber) >= 0) {
												CommandFile
														.add(4100 + search(
																fileLine[6],
																lineNumber));
												instructionIndex++;
												CommandFile
														.add(4200 + search(
																fileLine[6],
																lineNumber));
												instructionIndex++;
											} else {
												CommandFile.add(4100);
												lineNumbers[instructionIndex] = fileLine[6]; // use
																								// instruction
																								// index
																								// to
																								// place
																								// simple
																								// line
																								// number
																								// with
																								// it
												instructionIndex++;
												CommandFile.add(4200);
												lineNumbers[instructionIndex] = fileLine[6]; // use
																								// instruction
																								// index
																								// to
																								// place
																								// simple
																								// line
																								// number
																								// with
																								// it
												instructionIndex++;
											}
										} else if (isNumeric(fileLine[4])
												&& isWord(fileLine[2])) { // if
																			// second
																			// operand
																			// is
																			// a
																			// constant
																			// and
																			// first
																			// operand
																			// is
																			// a
																			// variable
											CommandFile.add(2000 + search(
													fileLine[4], constant));
											instructionIndex++;
											CommandFile.add(3100 + search(
													fileLine[2], variable));
											instructionIndex++;
											if (search(fileLine[6], lineNumber) >= 0) {
												CommandFile
														.add(4100 + search(
																fileLine[6],
																lineNumber));
												instructionIndex++;
												CommandFile
														.add(4200 + search(
																fileLine[6],
																lineNumber));
												instructionIndex++;
											} else {
												CommandFile.add(4100);
												lineNumbers[instructionIndex] = fileLine[6]; // use
																								// instruction
																								// index
																								// to
																								// place
																								// simple
																								// line
																								// number
																								// with
																								// it
												instructionIndex++;
												CommandFile.add(4200);
												lineNumbers[instructionIndex] = fileLine[6]; // use
																								// instruction
																								// index
																								// to
																								// place
																								// simple
																								// line
																								// number
																								// with
																								// it
												instructionIndex++;
											}
										} else if (isNumeric(fileLine[4])
												&& isNumeric(fileLine[2])) { // if
																				// both
																				// operands
																				// are
																				// constants
											CommandFile.add(2000 + search(
													fileLine[4], constant));
											instructionIndex++;
											CommandFile.add(3100 + search(
													fileLine[2], constant));
											instructionIndex++;
											if (search(fileLine[6], lineNumber) >= 0) {
												CommandFile
														.add(4100 + search(
																fileLine[6],
																lineNumber));
												instructionIndex++;
												CommandFile
														.add(4200 + search(
																fileLine[6],
																lineNumber));
												instructionIndex++;
											} else {
												CommandFile.add(4100);
												lineNumbers[instructionIndex] = fileLine[6]; // use
																								// instruction
																								// index
																								// to
																								// place
																								// simple
																								// line
																								// number
																								// with
																								// it
												instructionIndex++;
												CommandFile.add(4200);
												lineNumbers[instructionIndex] = fileLine[6]; // use
																								// instruction
																								// index
																								// to
																								// place
																								// simple
																								// line
																								// number
																								// with
																								// it
												instructionIndex++;
											}
										}
										break;
									case 5: // equals
										if (isWord(fileLine[4])
												&& isWord(fileLine[2])) // if
																		// both
																		// operands
																		// are
																		// variables
										{
											CommandFile.add(2000 + search(
													fileLine[4], variable)); // load
																				// the
																				// second
																				// operand
											instructionIndex++;
											CommandFile.add(3100 + search(
													fileLine[2], variable)); // subtract
																				// the
																				// first
																				// operand
											instructionIndex++;
											if (search(fileLine[6], lineNumber) >= 0) { // if
																						// line
																						// number
																						// exists
												CommandFile
														.add(4200 + search(
																fileLine[6],
																lineNumber)); // if
																				// the
																				// difference
																				// is
																				// 0
																				// go
																				// to
																				// the
																				// line
																				// number
												instructionIndex++;
											} else { // if the line number
														// doesn't exist
												CommandFile.add(4200);
												lineNumbers[instructionIndex] = fileLine[6]; // use
																								// instruction
																								// index
																								// to
																								// place
																								// simple
																								// line
																								// number
																								// with
																								// it
												instructionIndex++;
											}
										} else if (isWord(fileLine[4])
												&& isNumeric(fileLine[2])) { // if
																				// second
																				// operand
																				// is
																				// a
																				// variable
																				// and
																				// first
																				// operand
																				// is
																				// a
																				// constant
											CommandFile.add(2000 + search(
													fileLine[4], variable));
											instructionIndex++;
											CommandFile.add(3100 + search(
													fileLine[2], constant));
											instructionIndex++;
											if (search(fileLine[6], lineNumber) >= 0) {
												CommandFile
														.add(4200 + search(
																fileLine[6],
																lineNumber));
												instructionIndex++;
											} else {
												CommandFile.add(4200);
												lineNumbers[instructionIndex] = fileLine[6]; // use
																								// instruction
																								// index
																								// to
																								// place
																								// simple
																								// line
																								// number
																								// with
																								// it
												instructionIndex++;
											}
										} else if (isNumeric(fileLine[4])
												&& isWord(fileLine[2])) { // if
																			// second
																			// operand
																			// is
																			// a
																			// constant
																			// and
																			// first
																			// operand
																			// is
																			// a
																			// variable
											CommandFile.add(2000 + search(
													fileLine[4], constant));
											instructionIndex++;
											CommandFile.add(3100 + search(
													fileLine[2], variable));
											instructionIndex++;
											if (search(fileLine[6], lineNumber) >= 0) {
												CommandFile
														.add(4200 + search(
																fileLine[6],
																lineNumber));
												instructionIndex++;
											} else {
												CommandFile.add(4200);
												lineNumbers[instructionIndex] = fileLine[6]; // use
																								// instruction
																								// index
																								// to
																								// place
																								// simple
																								// line
																								// number
																								// with
																								// it
												instructionIndex++;
											}
										} else if (isNumeric(fileLine[4])
												&& isNumeric(fileLine[2])) { // if
																				// both
																				// operands
																				// are
																				// constants
											CommandFile.add(2000 + search(
													fileLine[4], constant));
											instructionIndex++;
											CommandFile.add(3100 + search(
													fileLine[2], constant));
											instructionIndex++;
											if (search(fileLine[6], lineNumber) >= 0) {
												CommandFile
														.add(4200 + search(
																fileLine[6],
																lineNumber));
												instructionIndex++; // if
																	// negative
																	// go to
																	// desired
																	// line
																	// number
											} else {
												CommandFile.add(4200);
												lineNumbers[instructionIndex] = fileLine[6]; // use
																								// instruction
																								// index
																								// to
																								// place
																								// simple
																								// line
																								// number
																								// with
																								// it
												instructionIndex++;
											}
										}
										break;
									case 6: // doesn't equal
										if (isWord(fileLine[4])
												&& isWord(fileLine[2])) // if
																		// both
																		// operands
																		// are
																		// variables
										{
											CommandFile.add(2000 + search(
													fileLine[4], variable)); // load
																				// the
																				// second
																				// operand
											instructionIndex++;
											CommandFile.add(3100 + search(
													fileLine[2], variable)); // subtract
																				// the
																				// first
																				// operand
											instructionIndex++;
											if (search(fileLine[6], lineNumber) >= 0) { // if
																						// line
																						// number
																						// exists
												CommandFile
														.add(4100 + search(
																fileLine[6],
																lineNumber));
												instructionIndex++; // if
																	// negative
																	// go to
																	// desired
																	// line
																	// number
												CommandFile.add(2000 + search(
														fileLine[2], variable));
												instructionIndex++; // if not
																	// negative
																	// load the
																	// other
																	// number
												CommandFile.add(3100 + search(
														fileLine[4], variable));
												instructionIndex++; // subtract
																	// the other
																	// number
												CommandFile
														.add(4100 + search(
																fileLine[6],
																lineNumber));
												instructionIndex++; // if
																	// negative
																	// go to
																	// desired
																	// line
																	// number
											} else { // if line number doesn't
														// exist
												CommandFile.add(4100);
												lineNumbers[instructionIndex] = fileLine[6]; // use
																								// instruction
																								// index
																								// to
																								// place
																								// simple
																								// line
																								// number
																								// with
																								// it
												instructionIndex++;
												CommandFile.add(2000 + search(
														fileLine[2], variable));
												instructionIndex++; // if not
																	// negative
																	// load the
																	// other
																	// number
												CommandFile.add(3100 + search(
														fileLine[4], variable));
												instructionIndex++; // subtract
																	// the other
																	// number
												CommandFile.add(4100);
												lineNumbers[instructionIndex] = fileLine[6]; // use
																								// instruction
																								// index
																								// to
																								// place
																								// simple
																								// line
																								// number
																								// with
																								// it
												instructionIndex++;
											}
										} else if (isWord(fileLine[4])
												&& isNumeric(fileLine[2])) { // if
																				// second
																				// operand
																				// is
																				// a
																				// variable
																				// and
																				// first
																				// operand
																				// is
																				// a
																				// constant
											CommandFile.add(2000 + search(
													fileLine[4], variable));
											instructionIndex++;
											CommandFile.add(3100 + search(
													fileLine[2], constant));
											instructionIndex++;
											if (search(fileLine[6], lineNumber) >= 0) {
												CommandFile
														.add(4100 + search(
																fileLine[6],
																lineNumber));
												instructionIndex++;
												CommandFile.add(2000 + search(
														fileLine[2], constant));
												instructionIndex++; // if not
																	// negative
																	// load the
																	// other
																	// number
												CommandFile.add(3100 + search(
														fileLine[4], variable));
												instructionIndex++; // subtract
																	// the other
																	// number
												CommandFile
														.add(4100 + search(
																fileLine[6],
																lineNumber));
												instructionIndex++; // if
																	// negative
																	// go to
																	// desired
																	// line
																	// number
											} else {
												CommandFile.add(4100);
												lineNumbers[instructionIndex] = fileLine[6]; // use
																								// instruction
																								// index
																								// to
																								// place
																								// simple
																								// line
																								// number
																								// with
																								// it
												instructionIndex++;
												CommandFile.add(2000 + search(
														fileLine[2], variable));
												instructionIndex++; // if not
																	// negative
																	// load the
																	// other
																	// number
												CommandFile.add(3100 + search(
														fileLine[4], variable));
												instructionIndex++; // subtract
																	// the other
																	// number
												CommandFile.add(4100);
												lineNumbers[instructionIndex] = fileLine[6]; // use
																								// instruction
																								// index
																								// to
																								// place
																								// simple
																								// line
																								// number
																								// with
																								// it
												instructionIndex++;
											}
										} else if (isNumeric(fileLine[4])
												&& isWord(fileLine[2])) { // if
																			// second
																			// operand
																			// is
																			// a
																			// constant
																			// and
																			// the
																			// first
																			// operand
																			// is
																			// a
																			// variable
											CommandFile.add(2000 + search(
													fileLine[4], constant));
											instructionIndex++;
											CommandFile.add(3100 + search(
													fileLine[2], variable));
											instructionIndex++;
											if (search(fileLine[6], lineNumber) >= 0) {
												CommandFile
														.add(4100 + search(
																fileLine[6],
																lineNumber));
												instructionIndex++;
												CommandFile.add(2000 + search(
														fileLine[2], variable));
												instructionIndex++; // if not
																	// negative
																	// load the
																	// other
																	// number
												CommandFile.add(3100 + search(
														fileLine[4], constant));
												instructionIndex++; // subtract
																	// the other
																	// number
												CommandFile
														.add(4100 + search(
																fileLine[6],
																lineNumber));
												instructionIndex++; // if
																	// negative
																	// go to
																	// desired
																	// line
																	// number
											} else {
												CommandFile.add(4100);
												lineNumbers[instructionIndex] = fileLine[6]; // use
																								// instruction
																								// index
																								// to
																								// place
																								// simple
																								// line
																								// number
																								// with
																								// it
												instructionIndex++;
												CommandFile.add(2000 + search(
														fileLine[2], variable));
												instructionIndex++; // if not
																	// negative
																	// load the
																	// other
																	// number
												CommandFile.add(3100 + search(
														fileLine[4], variable));
												instructionIndex++; // subtract
																	// the other
																	// number
												CommandFile.add(4100);
												lineNumbers[instructionIndex] = fileLine[6]; // use
																								// instruction
																								// index
																								// to
																								// place
																								// simple
																								// line
																								// number
																								// with
																								// it
												instructionIndex++;
											}
										} else if (isNumeric(fileLine[4])
												&& isNumeric(fileLine[2])) { // if
																				// both
																				// operands
																				// are
																				// constants
											CommandFile.add(2000 + search(
													fileLine[4], constant));
											instructionIndex++;
											CommandFile.add(3100 + search(
													fileLine[2], constant));
											instructionIndex++;
											if (search(fileLine[6], lineNumber) >= 0) {
												CommandFile
														.add(4100 + search(
																fileLine[6],
																lineNumber));
												instructionIndex++;
												CommandFile.add(2000 + search(
														fileLine[2], constant));
												instructionIndex++; // if not
																	// negative
																	// load the
																	// other
																	// number
												CommandFile.add(3100 + search(
														fileLine[4], constant));
												instructionIndex++; // subtract
																	// the other
																	// number
												CommandFile
														.add(4100 + search(
																fileLine[6],
																lineNumber));
												instructionIndex++; // if
																	// negative
																	// go to
																	// desired
																	// line
																	// number
											} else {
												CommandFile.add(4100);
												lineNumbers[instructionIndex] = fileLine[6]; // use
																								// instruction
																								// index
																								// to
																								// place
																								// simple
																								// line
																								// number
																								// with
																								// it
												instructionIndex++;
												CommandFile.add(2000 + search(
														fileLine[2], variable));
												instructionIndex++; // if not
																	// negative
																	// load the
																	// other
																	// number
												CommandFile.add(3100 + search(
														fileLine[4], variable));
												instructionIndex++; // subtract
																	// the other
																	// number
												CommandFile.add(4100);
												lineNumbers[instructionIndex] = fileLine[6]; // use
																								// instruction
																								// index
																								// to
																								// place
																								// simple
																								// line
																								// number
																								// with
																								// it
												instructionIndex++;
											}
										} else {
											System.out
													.println("If statement has errors. Please try another file.");
											System.exit(0);
										}
										break;
									}
								}
							} else if (command == 100 && fileLine.length >= 5) // if
																				// the
																				// command
																				// is
																				// let
							{
								if (isNumeric(fileLine[0])
										&& isWord(fileLine[1])
										&& fileLine[1].equals("let")
										&& isWord(fileLine[2])
										&& fileLine[3].equals("=")) {
									// if there's a line number, the let
									// command, the variable after let exists,
									// and there's an equal sign
									if (search(fileLine[2], variable) < 0) {
										symbols.add(new SymbolRow(variable,
												fileLine[2], dataIndex));
										dataIndex--;
									}
									for (int x = 4; x < fileLine.length; x++) { // goes
																				// through
																				// the
																				// statement
																				// after
																				// the
																				// equal
																				// sign
										if (isNumeric(fileLine[x])) { // if
																		// first
																		// part
																		// of
																		// expression
																		// is a
																		// constant
											if (search(fileLine[x], constant) == -1) { // if
																						// constant
																						// doesn't
																						// exist
																						// in
																						// symbol
																						// table
												symbols.add(new SymbolRow(
														constant, fileLine[x],
														dataIndex));
											}
											int y = Integer
													.parseInt(fileLine[2]);
											boolean negative = false;
											if (y < 0) {
												y = y * -1;
												negative = true;
											}
											int asd = 2200 + y;
											CommandFile.add(asd); // loads
																	// constant
																	// to SML
											instructionIndex++;
											CommandFile.add(2100 + dataIndex); // stores
																				// constant
																				// in
																				// SML
											instructionIndex++;
											dataIndex--;
											if (negative) {
												CommandFile
														.add(3100 + dataIndex++);
												instructionIndex++;
												CommandFile
														.add(3100 + dataIndex++);
												instructionIndex++;
											}
										}
									}
									Stack<String> one = new Stack<String>(); // stack
																				// for
																				// expression
									String postfix = ""; // postfix version of
															// expression
									for (int x = 4; x < fileLine.length; x++) { // goes
																				// through
																				// expression
																				// after
																				// equal
																				// sign
										if (isNumeric(fileLine[x])
												|| isWord(fileLine[x])) { // if
																			// part
																			// of
																			// expression
																			// is
																			// a
																			// constant
																			// or
																			// a
																			// variable
											postfix = postfix + fileLine[x]
													+ " ";
										} else if (typeOperator(fileLine[x]) != -1
												&& one.empty()) { // if part of
																	// expression
																	// is an
																	// operator
																	// and there
																	// are no
																	// other
																	// operators
																	// in stack
											one.push(fileLine[x]); // push to
																	// stack
										} else if (typeOperator(fileLine[x]) != -1
												&& !one.empty()) { // if part of
																	// expression
																	// is an
																	// operator
																	// and there
																	// are
																	// operators
																	// in stack
											if (typeOperator(fileLine[x]) <= typeOperator(one
													.peek())) { // if operator
																// is less than
																// other
																// operator in
																// the stack
												one.push(fileLine[x]); // push
																		// to
																		// stack
											} else { // if operator has higher
														// precedence than other
														// operator
												while (!one.empty()) { // while
																		// stack
																		// isn't
																		// empty
													postfix = postfix
															+ one.pop() + " "; // add
																				// statements
																				// in
																				// stack
																				// to
																				// postfix
																				// expression
												}
												one.push(fileLine[x]); // push
																		// operator
																		// to
																		// stack
											}
										} else { // if not a variable, constant,
													// or operator
											System.out
													.print("Error in let statement. Please review file.");
											System.exit(0); // quit
										}
									}
									if (!one.empty()) { // after going through
														// statement, if stack
														// isn't empty
										while (!one.empty()) { // while stack
																// isn't empty
											postfix = postfix + one.pop() + " "; // add
																					// operators
																					// in
																					// stack
																					// to
																					// postfix
																					// expression
										}
									}

									int address = search(fileLine[2], variable); // sets
																					// address
																					// as
																					// sml
																					// of
																					// variable
																					// being
																					// changed
									String[] post = postfix.split(" "); // splits
																		// postfix
																		// into
																		// pieces
									for (int x = 0; x < post.length; x++) { // goes
																			// through
																			// postfix
																			// pieces
										if (isNumeric(post[x])) { // if it's a
																	// constant
											CommandFile.add(2000 + search(
													post[x], constant)); // load
																			// constant
											instructionIndex++;
											CommandFile.add(2100 + dataIndex); // store
																				// constant
																				// into
																				// SML
											instructionIndex++;
											dataIndex--;
										} else if (isWord(post[x])) { // if it's
																		// a
																		// variable
											CommandFile.add(2000 + search(
													post[x], variable)); // load
																			// variable
											instructionIndex++;
											CommandFile.add(2100 + dataIndex); // store
																				// constant
																				// into
																				// SML
											instructionIndex++;
											dataIndex--;
										} else if (typeOperator(post[x]) > 0) { // if
																				// it's
																				// an
																				// operator
											CommandFile
													.add(2000 + dataIndex + 2); // loads
																				// first
																				// operand
											instructionIndex++;
											int operator = -1;
											switch (post[x]) {
											case ("*"): // multiplies
												operator = 3300;
												break;
											case ("/"): // divides
												operator = 3200;
												break;
											case ("-"): // subtracts
												operator = 3100;
												break;
											case ("+"): // adds
												operator = 3000;
												break;
											}
											if (operator == -1) { // if operator
																	// doesn't
																	// exist
												System.out
														.println("Let statement contains wrong operator. Please try another file.");
												System.exit(0);
											} else { // if operator exists
												CommandFile.add(operator
														+ dataIndex + 1); // perform
																			// operation
																			// between
																			// two
																			// operands
												instructionIndex++;
												dataIndex = dataIndex + 2; // resets
																			// dataIndex
											}
											CommandFile.add(2100 + dataIndex); // load
																				// answer
																				// into
																				// SML
											dataIndex--;
										}
									}
									CommandFile.add(2000 + dataIndex + 1); // load
																			// answer
									CommandFile.add(2100 + address); // store
																		// into
																		// variable
																		// being
																		// changed
								} else {
									System.out
											.println("Error in let statement. Please try another file.");
									System.exit(0);
								}
							} else // if it's another command
							{
								switch (command) {
								case 1100: // prints out variable in certain
											// location (write)
									if (isNumeric(fileLine[2])
											&& search(fileLine[2], constant) != -1)
										command = command
												+ search(fileLine[2], constant);
									else if (!isNumeric(fileLine[2])
											&& isWord(fileLine[2])
											&& search(fileLine[2], variable) != -1)
										command = command
												+ search(fileLine[2], variable);
									else {
										System.out
												.println("File does not contain correct instructions. Please try another file.");
										System.exit(0);
									}
									break;
								case 4000: // going to certain location (branch)
									if (isNumeric(fileLine[2])
											&& search(fileLine[2], lineNumber) != -1)
										command = command
												+ Integer.parseInt(fileLine[2]);
									else if (isNumeric(fileLine[2])
											&& search(fileLine[2], lineNumber) == -1)
										lineNumbers[instructionIndex] = fileLine[2]; // use
																						// instruction
																						// index
																						// to
																						// place
																						// simple
																						// line
																						// number
																						// with
																						// it
									instructionIndex++;
									break;
								case 4300: // quitting program (HALT)
									break;
								}
							}
						}
						if (command > 0 && command != 100) // if command exists
															// and isn't let
							CommandFile.add(command);
						else if (command < 0) { // if command doesn't exist
							System.out
									.println("File does not contain correct command. Please use valid commands.");
							System.exit(0);
						}
						instructionIndex++;
					} else {
						System.out
								.println("File does not contain correct instructions; one line is too short. Please try another file.");
						System.exit(0);
					}
				}
				if (isNumeric(fileLine[0])) // sets lastLine as line number for
											// next iteration
					lastLine = Integer.parseInt(fileLine[0]);
			}
			for (int x = 0; x < 100; x++) { // goes through lineNumbers array
				if (Integer.parseInt(lineNumbers[x]) != 0) { // if it's not
																// equal to zero
					CommandFile.set(
							x,
							CommandFile.get(x)
									+ Integer.parseInt(lineNumbers[x])); // sets
																			// branches
																			// to
																			// unknown
																			// lineNumbers
																			// to
																			// their
																			// correct
																			// spots
				}
			}
			String filename = "SMLcode"; // names file as SMLcode
			PrintWriter outfile;
			outfile = new PrintWriter(new FileWriter(new File(filename)));
			for (int x = 0; x < CommandFile.size(); x++) { // goes through
															// CommandFile
															// ArrayList
				outfile.println(CommandFile.get(x)); // prints commands into
														// file
			}
			outfile.close();
		}
		if (dataIndex == instructionIndex || dataIndex < instructionIndex) {
			System.out
					.println("Out of memory. File only includes commands up to SIMPLE line number: "
							+ simpleLine + ". Please try another file.");
			System.exit(0);
		}
	}

	public static boolean isNumeric(String str) { // checks if string is numeric
		for (char c : str.toCharArray()) {
			if (!Character.isDigit(c))
				return false;
		}
		return true;
	}

	public static boolean isWord(String str) { // checks if string is a word
		String alpha = "abcdefghijklmnopqrstuvwxyz";
		boolean word = true;
		for (int x = 0; x < str.length(); x++) {
			if (alpha.contains(str.substring(x, x + 1))) {
				word = true;
			} else {
				word = false;
				return word;
			}
		}
		return word;
	}

	public static int evaluateCommand(String com) { // gives commands a
													// numerical value
		int x = 0;
		switch (com) {
		case "input":
			x = 1000 + dataIndex;
			break;
		case "print":
			x = 1100;
			break;
		case "goto":
			x = 4000;
			break;
		case "if":
			x = 0;
			break;
		case "let":
			x = 100;
			break;
		case "end":
			x = 4300;
			break;
		default:
			x = -1;
			break;
		}
		return x;
	}

	public static int search(String value, String type) { // returns sml value
		for (int m = 0; m < symbols.size(); m++) {
			if ((symbols.get(m)).type().equals(type)
					&& (symbols.get(m)).value().equals(value)) {
				return (symbols.get(m)).sml();
			}
		}
		return -1;
	}

	public static int searchIndex(String value, String type) { // returns index
																// in symbols
																// array
		for (int m = 0; m < symbols.size(); m++) {
			if ((symbols.get(m)).type().equals(type)
					&& (symbols.get(m)).value().equals(value)) {
				return m;
			}
		}
		return -1;
	}

	public static int isRelOp(String rel) { // returns numerical value of
											// relational operators
		switch (rel) {
		case ">":
			return 1;
		case "<":
			return 2;
		case "<=":
			return 3;
		case ">=":
			return 4;
		case "==":
			return 5;
		case "!=":
			return 6;
		default:
			return -1;
		}
	}

	public static int typeOperator(String op) { // returns numerical value of
												// operators for math
		switch (op) {
		case "*":
			return 4;
		case "/":
			return 4;
		case "+":
			return 2;
		case "-":
			return 2;
		default:
			return -1;
		}
	}
}

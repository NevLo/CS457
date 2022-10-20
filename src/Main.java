import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/*
 * DBMS Project
 * UNR CS457 Database Management
 * 
 * Author: Christian Pilley
 * Project Creation date: 9/17/22
 * 
 * Part 1: Metadata Management (Due: 10/10/2022)
 * 	-Database Creation
 *  -Database Deletion
 * 	-Table Creation
 *  -Table Deletion
 *  -Table Update
 *  -Table Query
 * Part 2: __ (Due: __/__/2022)
 * Part 3: __ (Due: __/__/2022)
 * Part 4: __ (Due: __/__/2022)
 */
public class Main {
	public static void main(String[] args) throws FileNotFoundException {
		System.out.println("DBMS system launched.\n");
		System.out.println(args.length + "\n");
		if (args.length == 1) {
			if (!args[0].endsWith(".sql")) {
				args[0] += ".sql";
			}
			Scanner scan = new Scanner(new File(args[0]));
			while (scan.hasNext()) {
				parse(scan.nextLine());
			}

			scan.close();
			return;
		}
		Scanner inputStream = new Scanner(System.in);
		while (inputStream.hasNext()) {
			String input = inputStream.nextLine();
			parse(input);
		}

		inputStream.close();
	}

	/*
	 * SQL parser.
	 * 
	 */
	public static void parse(String lineToParse) {
		// check to see if the line is a comment, if so return (does not support multi
		// line comments yet)
		if (lineToParse.startsWith("--"))
			return;
		// see if the line is a command (.exit, .header, etc)
		if (lineToParse.startsWith(".")) {
			parseCMD(lineToParse.substring(1));
			return;
		}
		// check to see if there is no semicolon (syntax error)
		if (lineToParse.indexOf(';') == -1) {
			System.out.println("!Invalid Syntax, missing ';'");
			return;
		}
		// see if the line has multiple commands in it (CREATE DATABASE DB_1; USE DB_1;)
		// this is done because String.indexOf() checks for the first instance of a
		// character.
		if (lineToParse.indexOf(";") != lineToParse.length() - 1) {
			// breaks the string down into multiple at the location of semicolons.
			String[] listOfLines = lineToParse.split(";");
			// parse the lines (adding a semicolon because it was removed in the split)
			for (String s : listOfLines) {
				parse(s + ";");
			}
		}
		if (lineToParse.indexOf(";") != -1) {
			lineToParse = lineToParse.substring(0, lineToParse.length() - 1);
		}
		// create an arraylist of the substrings.
		ArrayList<String> parseTree = new ArrayList<String>(Arrays.asList(lineToParse.split(" ")));
		for (String str : parseTree) {
			str = str.toLowerCase();
		}
		// check to see if the cmd is valid.
		String CMD = parseTree.remove(0);
		if (CMD.equalsIgnoreCase("create"))
			DBMS.create(parseTree);
		else if (CMD.equalsIgnoreCase("drop"))
			DBMS.drop(parseTree);
		else if (CMD.equalsIgnoreCase("select"))
			DBMS.select(parseTree);
		else if (CMD.equalsIgnoreCase("use"))
			DBMS.use(parseTree);
		else if (CMD.equalsIgnoreCase("alter"))
			DBMS.alter(parseTree);
		else if (CMD.equalsIgnoreCase("insert"))
			DBMS.insert(parseTree);
		else
			System.out.println("!Command Not Recognized!");
		// Just to make sure memory gets clear properly.
		parseTree.clear();
	}

	/*
	 * SQL Command Parser
	 * 
	 * 
	 */
	public static void parseCMD(String lineToParse) {
		// TODO Auto-generated method stub
		String[] splits = lineToParse.split(" ");
		String cmd = splits[0];
		if (cmd.equalsIgnoreCase("exit")) {
			System.out.println("All done.");
			System.exit(0);
		} else if (cmd.equalsIgnoreCase("header")) {
			System.out.println("!Command not implemented!");
		} else if (cmd.equalsIgnoreCase("mode")) {
			System.out.println("!Command not implemented!");
		} else if (cmd.equalsIgnoreCase("nullValue")) {
			System.out.println("!Command not implemented!");
		}
	}
}
/*
 * Change Log:
 * 
 * | * * * * | + Created project.
 * | 9/17/22 | + Created parse method.
 * | * * * * | + Created parseCMD method.
 * + Added Method Stubs for Create,Drop,Select,Alter,Use.
 * + Worked on Use and Create implementation.
 * ------------------------------------------------------------------
 * | * * * * |
 * | 9/21/22 |
 * | * * * * |
 * |
 */
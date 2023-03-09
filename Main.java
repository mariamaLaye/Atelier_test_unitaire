package com.example;
import java.io.*;
import java.util.*;

public class Main {

	public static void main(String[] args) {

		try (Scanner scanner = new Scanner(System.in)) {
			Scanner fileIn; // input file connection
			PrintWriter fileOut; // HTML file connection
			String filenameInList; // original file's name
			String filenameOut; // new HTML file's name
			int dotIndex; // position of . in filename
			String line = null; // a line from the input file

			// 1. ask user for a file name (or file path)

			System.out.println("Enter file name or path");
			filenameInList = "list.txt";
			// 2. check if file exists

			try {

				// 3. rename .txt as .html
				fileIn = new Scanner(new FileReader(filenameInList));
				dotIndex = filenameInList.lastIndexOf(".");
				if (dotIndex == -1) {
					filenameOut = filenameInList + ".html";
				} else {
					filenameOut = filenameInList.substring(0, dotIndex) + ".html";
				}
				fileOut = new PrintWriter(filenameOut);

				// 4. determine if file is empty

				try {
					line = fileIn.nextLine();
				} catch (NoSuchElementException e) {
					System.out.println("Error: " + e.getMessage());
				}
				if (line == null) {
					System.out.println("This file is empty :(");
				} else {
					// 5. read each line and insert necessary <tags>
					fileOut.println("<html>");
					fileOut.println("<head>");
					fileOut.println("<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css' integrity='sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm' crossorigin='anonymous'>");
					fileOut.println("<script src='https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js' integrity='sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p' crossorigin='anonymous'></script>");
					fileOut.println("</head>");
					fileOut.println("<body'>");
					fileOut.println("<div class='list-group'>");
					fileOut.println("<a  class='list-group-item list-group-item-action list-group-item-primary'  href='site_MSPR/index.html'>");
					fileOut.println(line);
					fileOut.println("</a>");
					while (fileIn.hasNextLine()) {

						fileOut.println("<br>");
						line = fileIn.nextLine();

						if (line.isEmpty()) {
							fileOut.println("<br>");
						} else {
							fileOut.println("<a class='list-group-item list-group-item-action list-group-item-primary' href='site_MSPR/index.html'>");
							fileOut.println(line);
							fileOut.println("</a>");
						}

					}
					fileOut.println("</div>");
					fileOut.println("</body>");
					fileOut.println("</html>");

					System.out.println("Le fichier HTML est genere :)");
				}
				fileIn.close();
				fileOut.close();
			} catch (FileNotFoundException e) {
				System.out.println("File not found");
			}
		}

	}
}

import java.io.*;
import java.util.Scanner;

/**
 * Driver class containing the only main method of this program
 * @author Cody Fingerson
 */
public class Driver {
    /**
     * One and only main method
     * @param args unused
     * @throws IOException if an error occurs writing to a file, reading a file, or scanning a file
     */
    public static void main(String[] args) throws IOException {
        // Initialize MinPQ that takes in CovidData as it's type.
        MinPQ<CovidData> pq = new MinPQ<CovidData>(3);

        /*
          Binary search tree initialization.
          Key - Long (will be the number of new cases for each country)
          Value - CovidData (all the read in data) - obtained from the minimum priority queue
          */
        BinarySearchTree<Long, CovidData> bst = new BinarySearchTree<>();

        // CovidData variable to store the data from the CSV.
        CovidData cdata;

        // Open the file
        File data = new File("resources/owid-covid-data.csv");

        String[] line = null; // Array to manage the data in the CSV

        Scanner scanner = new Scanner(data); // Read the file
        scanner.nextLine();  // Skip over the CSV headers

        line = scanner.nextLine().split(","); // Split the csv by commas
        String prevCountry = line[1]; // Keep track of the previously read country

        while(scanner.hasNextLine()) {
            String[] nextLine = scanner.nextLine().split(","); //this will get the current line
            /*
            Line array breakdown:
                line[0] -> continent
                line[1] -> location (country)
                line[2] -> date
                line[3] -> total cases
                line[4] -> new cases
                line[5] -> population
             */
            cdata = new CovidData(line[0], line[1], line[2], Long.parseLong(line[3]), Long.parseLong(line[4]), Long.parseLong(line[5])); // Parse the file and add to CovidData class

            pq.insert(cdata); // Insert the current data for the current country

            if (nextLine[1].equals(prevCountry)){ // Check if the current country is the same as the previous country
                line = nextLine; // if it is, set the previous line to the current line
            } else {
                pq.delMin(); // Get rid of unneeded countries
                while(pq.size() > 0) {
                    // If the size of the MinPQ is more than 0, delete the minimum key and add it to the binary search tree
                    CovidData placeholder = pq.delMin();
                    bst.put(placeholder.getNewCases(), placeholder);
                }
            }

            // Manage unneeded data
            if(pq.size() > 3) {
                pq.delMin();
            }

            // Move to the next country
            prevCountry = nextLine[1];
        }

        // Writer for writing to the output file
        Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("resources/prgm1out.txt"), "utf-8"));
        writer.write("Inorder traversal\n");
        for (Long i: bst.inorderTraversal()) { // traverse the tree in order
            CovidData d = bst.get(i);
            System.out.println("Wrote data for " + d.getLocation() + "/" + d.getContinent() + " to output file");
            writer.write("New Cases: " + i + " at " + d.getLocation() + "/" + d.getContinent() + " on " + d.getDate() + " Total: " + d.getTotalCases() + " Pop: " + d.getPopulation() + "\n");
        }
    }
}
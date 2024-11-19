/*
https://www.bls.gov/productivity/tables/
labor-productivity-major-sectors.xlsx downloaded from website
Data released September 5, 2024

Job types being measured: Non-farm business sector, business sector, Non-financial corporate sector,
Manufacturing sector, Durable manufacturing sector, and Non-durable manufacturing sector

I will be graphing data on the measurement: Employment in millions from the spreadsheet.
Planned three filters: Manufacturing jobs, business jobs, corporate jobs
 */

import org.jfree.chart.JFreeChart;
import org.jfree.chart.swing.ChartPanel;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.io.File;

public class InputStream {
    String sector;
    String yearMillions;

    public InputStream() {
    }

    //public void Streamer(String file) {
    public static void main(String[] args) {
        String file1;   //file object
        String line;
        String delimiter = ","; //for acknowledging the separation between columns

        file1 = "data.csv";

        ArrayList<DataAggregate> sectorInformationAggregate = new ArrayList<>(); //for storing data to put on TablePanel
        ArrayList<String> sectors = new ArrayList<>();
        ArrayList<String> basisArray = new ArrayList<>();

        //Need to restructure logic to send all data points to the DataAggregate class. This will make updating my panels easier.
        //These temporary variables will be used to create a DataAggregate object containing all necessary data.
        String currentSector = "";
        String currentBasis = "";
        double currentEmploymentCount = 0.0;
        double currentAverageWeeklyHours = 0.0;
        double currentEmploymentPercentChange = 0.0;
        double currentAverageDollarsPerHour = 0.0;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file1))) {
            while ((line = bufferedReader.readLine()) != null) { //repeats while there are still lines to read
                String[] columns = line.split(delimiter); // The whole line is segmented based on comma locations. Allows each cell to be separately tracked.
                String sector = columns[0]; // different job types I'll be graphing
                String basis = columns[1]; // basis of the data: can be either employees or all workers
                String measure = columns[2]; //String: "Employment" within csv file
                String units = columns[3]; // column holding string: "Millions of jobs"
                String yearMillions = columns[80]; //tracks the column that counts the Employees, for year 2023.

                if ("Employment".equals(measure) && "Millions of jobs".equals(units)) {
                    System.out.println("Sector: " + sector);
                    System.out.println("National Employees (in millions): " + yearMillions);
                    currentSector = sector;
                    currentBasis = basis;
                    currentEmploymentCount = Double.parseDouble(yearMillions);
                    sectors.add(sector);
                    basisArray.add(basis);
                }
                if("Employment".equals(measure)&&"% Change from previous year".equals(units)) { // when column says Employment and other column says % change, add to array list
                    currentEmploymentPercentChange = Double.parseDouble(columns[80]);
                }
                if("Average weekly hours".equals(measure)&&"Hours worked per job per week".equals(units)) {
                    currentAverageWeeklyHours = (Double.parseDouble(columns[80]));
                }
                if("Hourly compensation".equals(measure)&&"Current dollars per hour worked".equals(units)) {
                    currentAverageDollarsPerHour = (Double.parseDouble(columns[80]));
                }
                if(!currentSector.isEmpty() && !currentBasis.isEmpty() && currentEmploymentPercentChange!=0 && currentAverageWeeklyHours!=0 && currentEmploymentCount!=0) { // Makes sure that all values for an industry data point are filled
                    DataAggregate data = new DataAggregate(currentSector, currentEmploymentCount, currentBasis, currentAverageWeeklyHours, currentEmploymentPercentChange, currentAverageDollarsPerHour);
                    sectorInformationAggregate.add(data);

                    currentSector = ""; //reset values for next DataAggregate data point.
                    currentEmploymentCount = 0.0;
                    currentAverageWeeklyHours = 0.0;
                    currentEmploymentPercentChange = 0.0;
                    currentAverageDollarsPerHour = 0.0;

                }


            }

        } catch (IOException e) { // used in case the program cannot find the file; results in a thrown error
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        //new table panel
        //new stats panel

        JFrame frame = new JFrame(); // First JFrame featuring TablePanel + filters and sorts
        frame.setTitle("Sector Employment Information");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());

        TablePanel tablePanel = new TablePanel(sectorInformationAggregate);
        frame.getContentPane().add(tablePanel, BorderLayout.CENTER);

        //sizing settings for better output
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = size.width;
        int screenHeight = size.height;
        int leftX = 0;
        frame.setSize(screenWidth/3, screenHeight/4);
        frame.setLocation(leftX, screenHeight/10 ); //sets location to upper left area of screen

        frame.setVisible(true);



    }
}

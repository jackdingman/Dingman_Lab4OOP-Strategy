import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class StatsPanel extends JFrame implements Observer {

    JPanel jPanel;

    public StatsPanel(ArrayList<DataAggregate> sectorInformationAggregate) {

        setTitle("Stats Panel"); // title of panel
        setSize(600,400); // size of frame
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(sectorInformationAggregate.size()+1, 4,100,0));
        jPanel.add(new JLabel("Sector"));
        jPanel.add(new JLabel("Previous Year (2022) Employment (in millions)"));
        jPanel.add(new JLabel("Total Sector Employee Payout (in millions)"));
        jPanel.add(new JLabel("Average Dollars per Worker per Week"));
        updateStats(sectorInformationAggregate);

        /*My Decorator Pattern: Allows me to keep the Stats Panel window smaller, by using a JScrollPane.
        Decorator patterns add new behavior to an object without changing structure. In this sense,
        JScrollPane decorates the component, JPanel by adding new functionality.

         */
        JScrollPane jScrollPanel = new JScrollPane(jPanel); // wrapped the jPanel in the JScrollPane.
        add(jScrollPanel, BorderLayout.CENTER); //added the jScrollPanel to the jFrame
        setVisible(true);

        //addition of some formatting to easily layout on the screen
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = size.width;
        int screenHeight = size.height;
        setSize(screenWidth/3, screenHeight/4);
        setLocation(0, screenHeight/2 ); // sets location to lower left of screen


    }

    public void updateStats(ArrayList<DataAggregate> filteredDataPoints) {
        jPanel.removeAll(); //Clears all of the components from the frame.

        jPanel.setLayout(new GridLayout(filteredDataPoints.size()+1, 4, 100, 0));
        jPanel.add(new JLabel("Sector"));
        jPanel.add(new JLabel("Previous Year (2022) Employment (in millions)"));
        jPanel.add(new JLabel("Total Sector Employee Payout (in millions)"));
        jPanel.add(new JLabel("Average Dollars per Worker per Week"));

        for (DataAggregate data : filteredDataPoints) {
            double weeklyHours = data.getAverageWeeklyHours();
            double employmentPercent = data.getEmploymentPercentChange();
            double averageDollars = data.getAverageDollarsPerHour();
            double employmentCount = data.getYearMillions();
            String sectorName = data.getSector();

            // Calculations for stats
            double previousEmploymentCount = employmentCount - (employmentCount * (employmentPercent / 100));
            double oneHourEmployeePayout = employmentCount * averageDollars;
            double averageDollarPerWorkerPerWeek = weeklyHours * averageDollars;

            // Add all of the calculated stats to panel
            jPanel.add(new JLabel(sectorName));
            jPanel.add(new JLabel(String.format("%.2f", previousEmploymentCount)));
            jPanel.add(new JLabel(String.format("%.2f", oneHourEmployeePayout)));
            jPanel.add(new JLabel(String.format("%.2f", averageDollarPerWorkerPerWeek)));
        }

        jPanel.revalidate(); // Revalidate and repaint panels after making changes
        jPanel.repaint();


        }

    @Override
    public void update(ArrayList<DataAggregate> filteredDataPoints) {
        updateStats(filteredDataPoints);
    }
}


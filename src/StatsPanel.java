import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class StatsPanel extends JPanel implements Observer {

    private JLabel totalEmployeesLabel;
    private JLabel averageJobsLabel;

    public StatsPanel(ArrayList<DataAggregate> sectorInformationAggregate) {

        updateStats(sectorInformationAggregate/*, sectors, averageWeeklyHours, employmentPercentChange, averageDollarsPerHour*/);
        setLayout(new GridLayout(sectorInformationAggregate.size() + 1, 3));
        add(totalEmployeesLabel);
        add(averageJobsLabel);

    }

    public void updateStats(ArrayList<DataAggregate> sectorInformationAggregate) {
        removeAll();

        /*Below will be the formulas for the different stats I am including.
          They are as follows:
          Previous year (2022) employment number = Employment # - Employment #(%change)
          Total Employee payout on one hour of work = Employment #(current $ per hour worked)
          Average $ per worker per week = Average weekly hours (current $ per hour worked)
         */
         // layout for panel - 4 columns include Sector, and three different calculated data points
        //Header labels for data columns
       /* add(new JLabel("Sector: "));
        add(new JLabel("Previous Year (2022) Employment Number (in millions): "));
        add(new JLabel("Total Sector Employee Payout on one hour of work (in millions): "));
        add(new JLabel("Average Dollars per worker per week: "));

        for (DataAggregate data : sectorInformationAggregate) { //iterating through ArrayList, retrieving values for the calculated stats on the StatsPanel.

            double weeklyHours = data.getAverageWeeklyHours();
            double employmentPercent = data.getEmploymentPercentChange();
            double averageDollars = data.getAverageDollarsPerHour();
            double employmentCount = data.getYearMillions();
            String sectorName = data.getSector();

            double previousEmploymentCount = employmentCount - (employmentCount * (employmentPercent / 100));
            double oneHourEmployeePayout = employmentCount * averageDollars;
            double averageDollarPerWorkerPerWeek = weeklyHours * averageDollars;

            //need to add action listeners to each button here.
            add(new JLabel(sectorName));
            add(new JLabel("" + previousEmploymentCount));
            add(new JLabel("" + oneHourEmployeePayout));
            add(new JLabel("" + averageDollarPerWorkerPerWeek));*/
        double totalEmployees = sectorInformationAggregate.stream()
                        .mapToDouble(DataAggregate::getYearMillions)
                                .sum();
        double averageJobs = totalEmployees / sectorInformationAggregate.size();

        totalEmployeesLabel.setText(String.format("%.2f", totalEmployees));
        averageJobsLabel.setText(String.format("%.2f", averageJobs));

            revalidate();
            repaint();

        }

    @Override
    public void update(ArrayList<DataAggregate> filteredDataPoints) {
        updateStats(filteredDataPoints);
    }
}

//for initial PUSH

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DetailsPanel extends JFrame {

    JTextArea detailsArea; //text box that will display stats

    public DetailsPanel() {
        setTitle("Details Panel"); // title of panel
        setSize(300,200); // size of frame
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        detailsArea = new JTextArea(); //initialize textarea object
        detailsArea.setEditable(false); // cannot be changed with normal typing function once outputted
        detailsArea.setLineWrap(true); // lines wrap based on the frame size
        detailsArea.setWrapStyleWord(true); //wraps words so they don't cut by character

        add(new JScrollPane(detailsArea), BorderLayout.CENTER); // adds area to a scrollpane

    }
    public void displayDetails (DataAggregate sectorInformationAggregate){

    StringBuilder builder = new StringBuilder(); // object that organizes the detailsArea
    builder.append("Sector: ").append(sectorInformationAggregate.getSector()).append("\n");
    builder.append("Millions of Employees: ").append(sectorInformationAggregate.getYearMillions()).append("\n");
    builder.append("Basis: ").append(sectorInformationAggregate.getBasis()).append("\n");
    builder.append("Average Weekly Hours: ").append(sectorInformationAggregate.getAverageWeeklyHours()).append("\n");
    builder.append("Change in Employment Percentage from prior year: ").append(sectorInformationAggregate.getEmploymentPercentChange()).append("%").append("\n");
    detailsArea.setText(builder.toString());

    }




}

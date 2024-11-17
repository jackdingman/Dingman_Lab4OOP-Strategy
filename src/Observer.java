import java.util.ArrayList;

//I am applying an observer pattern, in hopes to make the dependencies within
//my code more clear

public interface Observer {
    void update(ArrayList<DataAggregate> filteredDataPoints);
}

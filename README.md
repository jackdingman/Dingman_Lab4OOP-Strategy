Patterns that I chose to implement: Observer and xxx

The first pattern that I chose to implement was the observer pattern. The purpose of the pattern
is to enable a one-to-many relationship between objects. When the state of one object changes,
so do the others.

In my code, the object I wanted to change was the ArrayList containing data
from my .csv files. This information was to be outputted on the chart and table,
but also needed the capacity to be filtered. I used the observer pattern to bring synchronization
between my TablePanel, StatsPanel, and ChartPanel. The StatsPanel and ChartPanel were observers of the
TablePanel which contained the logic to filter. When the data changes, the observer automatically
notifies all registered observers.

To do this, I implemented the following methods:

public void update(ArrayList<DataAggregate> filteredDataPoints) { //implemented observer method
updateChart(filteredDataPoints);

public void setObservers(Observer observer) {
observers.add(observer);
}

public void observerUpdater(){
    for(Observer observer : observers){
    observer.update(filteredDataPoints);
    }
}





How to run my program using JFreeChart:

In Intellij, click -> File -> Project Structure -> Modules _within_ Project Settings.
You will see a plus button. Click it. 
Select-> Add import module -> find _jfreechart-master_.

Now, the program should be ready to run!


Patterns that I chose to implement: Observer and Decorator Patterns

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

The second pattern I implemented in my code was a decorator pattern. A decorator pattern
extends new responsibilities to an object without changing their structure. I used the commonly
used, JScrollPane as a decorator to my Stats JPanel. The scroll pane adds a scrollbar for easier
navigation on a small window. The component decorated, the JPanel, does not know how the JScrollPane 
works, which is a fundamental concept of decorator patterns.

    JScrollPane jScrollPanel = new JScrollPane(jPanel); // wrapped the jPanel in the JScrollPane.
    
    add(jScrollPanel, BorderLayout.CENTER); //added the jScrollPanel to the jFrame




How to run my program using JFreeChart:

In Intellij, click -> File -> Project Structure -> Modules _within_ Project Settings.
You will see a plus button. Click it. 
Select-> Add import module -> find _jfreechart-master_.

Now, the program should be ready to run!


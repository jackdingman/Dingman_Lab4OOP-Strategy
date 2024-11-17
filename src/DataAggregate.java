public class DataAggregate {
    String sector;
    Double yearMillions;
    String basis;
    Double averageWeeklyHours;
    Double employmentPercentChange;
    Double averageDollarsPerHour;



    public DataAggregate(String sector, Double yearMillions, String basis, Double averageWeeklyHours, Double employmentPercentChange, Double averageDollarsPerHour) {
        this.sector = sector;
        this.yearMillions = yearMillions;
        this.basis = basis;
        this.averageWeeklyHours = averageWeeklyHours;
        this.employmentPercentChange = employmentPercentChange;
        this.averageDollarsPerHour = averageDollarsPerHour;

    }
    public String getSector() {
        return sector;
    }
    public Double getYearMillions() {
        return yearMillions;
    }
    public String getBasis() {
        return basis;
    }
    public Double getAverageWeeklyHours() {
        return averageWeeklyHours;
    }
    public Double getEmploymentPercentChange() {
        return employmentPercentChange;
    }
    public Double getAverageDollarsPerHour() {
        return averageDollarsPerHour;
    }
    @Override
    public String toString() {
        return "Sector: " + getSector() + "Millions in 2023: " + getYearMillions() + "Basis: " + getBasis();
    }
}

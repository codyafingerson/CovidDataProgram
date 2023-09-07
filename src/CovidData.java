/**
 * The CovidData class represents a set of data given in owid-covid-data.csv
 * Allows for easier management and control of data
 * @author Cody Fingerson
 */
public class CovidData implements Comparable<CovidData> {
    // CSV Structure: continent,location,date,total_cases,new_cases,population
    private String continent;
    private String location;
    private String date;
    private Long totalCases;
    private Long newCases;
    private Long population;

    /**
     * Initialize CovidData object
     * @param continent
     * @param location
     * @param date
     * @param totalCases
     * @param newCases
     * @param population
     */
    public CovidData(String continent, String location, String date, Long totalCases, Long newCases, Long population){
        this.continent = continent;
        this.location = location;
        this.date = date;
        this.totalCases = totalCases;
        this.newCases = newCases;
        this.population = population;
    }

    // Setters

    /**
     * Set the continent
     * @param continent the continent
     */
    public void setContinent(String continent) {
        this.continent = continent;
    }

    /**
     * Set the location
     * @param location the location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Set the date
     * @param date the date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Set the number of total cases
     * @param totalCases the number of total cases
     */
    public void setTotalCases(Long totalCases) {
        this.totalCases = totalCases;
    }

    /**
     * Set the number of new cases
     * @param newCases number of new cases
     */
    public void setNewCases(Long newCases) {
        this.newCases = newCases;
    }

    /**
     * Set the population
     * @param population the population
     */
    public void setPopulation(Long population) {
        this.population = population;
    }

    // Getters

    /**
     * Get the continent
     * @return the continent
     */
    public String getContinent() {
        return continent;
    }

    /**
     * Get the location
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Get the date
     * @return the date
     */
    public String getDate() {
        return this.date;
    }

    /**
     * Get the number of total cases
     * @return number of total cases
     */
    public Long getTotalCases() {
        return totalCases;
    }

    /**
     * Get the number of new cases
     * @return number of new cases
     */
    public Long getNewCases() {
        return newCases;
    }

    /**
     * Get the population
     * @return the population
     */
    public Long getPopulation() {
        return population;
    }

    /**
     * Compare the number of new cases
     * @param toCompare the object to be compared.
     * @return -1 if next newCases is greater; 1 if the previous newCases is greater; 0 if equal
     */
    public int compareTo(CovidData toCompare) {
        if(this.newCases < toCompare.newCases) {
            return -1;
        }
        if(this.newCases > toCompare.newCases){
            return 1;
        }
        return 0;
    }
}
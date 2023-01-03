package clontz.inventorymanagementapplication;

/**
 * Outsourced
 *
 * A representation of a part that is created by a third-party
 * and has a company name instead of a machine ID assigned to it
 * in addition to the inherited Part fields and methods.
 *
 * @author Chris Clontz
 */
public class Outsourced extends Part {
    private String companyName;

    /**
     * Constructor for Outsourced part that includes companyName
     * @param id - part id
     * @param name - part name
     * @param price - part price
     * @param stock - parts in-stock
     * @param min - minimum amount of parts required to be in stock
     * @param max - maximum amount of parts allowed to be in stock
     * @param companyName - name of company responsible for making and providing part
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Getter for companyName
     * @return companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Setter for companyName
     * @param companyName - name to set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}

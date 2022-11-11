package model;


/** Model for Outsourced Part(s).
 *
 * @author Bryan Yang
 * */
public class Outsourced extends Part{

    /** Company Name of the Part. */
    private String companyName;

    /** Constructor for new instance of an Outsourced Part. *
     *
     * @param id The ID of the Part.
     * @param name The Name of the Part.
     * @param price The Price of the Part.
     * @param stock The Inventory Level of the Part.
     * @param min The Minimum Level of the Part.
     * @param max The Maximum Level of the Part.
     * @param companyName The Company Name of the Part.
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName){
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /** Getter for the Outsourced Company Name
     *
     * @return The Outsourced Company Name
     * */
    public String getCompanyName() {
        return companyName;
    }

    /** Setter for the Outsourced Company Name
     *
     * @param companyName The Outsourced Company Name.
     * */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}

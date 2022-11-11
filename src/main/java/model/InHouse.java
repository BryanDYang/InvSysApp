package model;

/** Model for InHouse parts.
 *
 * @author Bryan Yang.
 * */
public class InHouse extends Part{

    /** Machine ID for the part. */
    private int machineId;

    /** A constructor for a new instance of InHouse objects.
     *
     * @param id the ID of the part.
     * @param name the Name of the part.
     * @param price the Price of the part.
     * @param stock the Inventory Level of the part.
     * @param min the Minimum Level of the part.
     * @param max the Maximum Level of the part.
     * */

    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId){
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /** Getter for the machineId.
     * @return machineId of the part.
     * */
    public int getMachineId(){
        return machineId;
    }

    /** Setter for the machineId.
     *
     * @param machineId the Machine ID of the part.
     * */
    public void setMachineId(){
        this.machineId = machineId;
    }

}

package clontz.inventorymanagementapplication;

/**
 * InHouse
 *
 * A representation of a part that is created in-house and
 * therefore has a machine ID assigned to it in addition
 * to the inherited Part fields and methods.
 *
 * @author Chris Clontz
 */
public class InHouse extends Part {
    private int machineId;

    /**
     * Constructor for InHouse part with added "machineId" field
     * @param id - part ID
     * @param name - part name
     * @param price - part price
     * @param stock - part stock/inventory amount
     * @param min - part minimum stock/inventory requirement
     * @param max - part maximum stock/inventory requirement
     * @param machineId - machine ID for in-house parts
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * Getter for machineId
     * @return - machineId
     */
    public int getMachineId() {
        return machineId;
    }

    /**
     * Setter for machineId
     * @param machineId - machineId to set
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}

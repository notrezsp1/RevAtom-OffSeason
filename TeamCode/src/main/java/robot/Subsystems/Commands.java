package robot.Subsystems;


public class Commands {

    public static final int LONGEST = 2100;
    public static final int ZERO = 0;


    private int reference = ZERO;
    private boolean forced = false;
    public void setReference(int newReference) {
        newReference = Math.min(newReference, LONGEST);
        newReference = Math.max(newReference, ZERO);
//        newReference = Math.min(newReference, LONGEST);
        this.reference = newReference;
    }

    public void setTarget(int newTarget) {
        setReference(newTarget);
    }

}

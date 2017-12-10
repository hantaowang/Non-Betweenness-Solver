import java.util.HashMap;

/** A CONSTRAINT that contains three WIZARDS A, B, and C. WIZARD C cannot be
* between WIZARDS A or B for this constraint to be satisfied.
*/
public class Constraint {
    /** Where a, b, c represents WIZARDS. */
    private String a, b, c;

    /** Constraint Constructor */
    public Constraint(String a, String b, String c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    /** Returns WIZARD A. */
    public String getA() {
        return a;
    }

    /** Returns WIZARD B. */
    public String getB() {
        return b;
    }

    /** Returns WIZARD C. */
    public String getC() {
        return c;
    }

    /** Method that swaps locations of WIZARDS X and Y in ORDER. */
    public void swap(HashMap<String, Integer> order, String x, String y) {
        int temp = order.get(x);
        order.put(x, order.get(y));
        order.put(y, temp);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof Constraint)) {
            return false;
        }
        Constraint c = (Constraint) o;
        return c.getC().equals(getC()) &&
                ((c.getA().equals(getA()) && c.getB().equals(getB())) ||
                (c.getA().equals(getB()) && c.getB().equals(getA())));
    }

    /** Returns 1 if CONSTRAINT is satisfied and 0 otherwise. */
    public int satisfy(HashMap<String, Integer> order) {
        int aI = order.get(a);
        int bI = order.get(b);
        int cI = order.get(c);

        if ((aI < cI && cI < bI) || (bI < cI && cI < aI)) {
            return 0;
        }
        return 1;
    }
}

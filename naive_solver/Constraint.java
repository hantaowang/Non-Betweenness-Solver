import java.util.HashMap;

public class Constraint {

  private String a, b, c;
  private HashMap<String, Integer> order;
  private boolean left = true;

  public Constraint(HashMap<String, Integer> order, String a, String b, String c) {
    this.a = a;
    this.b = b;
    this.c = c;
    this.order = order;
  }

  private void swap(String x, String y) {
    int xI = order.get(x);
    int yI = order.get(y);
    order.put(x, yI);
    order.put(y, xI);
  }

  public int satisfy() {
    int aI = order.get(a);
    int bI = order.get(b);
    int cI = order.get(c);
    if ((aI < cI && cI < bI) || (bI < cI && cI < aI)) {
      if (Math.random() < 0.5) {
        swap(a, c);
        left = false;
      } else {
        swap(c, b);
        left = true;
      }
      return 0;
    }
    return 1;
  }

}

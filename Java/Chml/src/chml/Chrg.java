package chml;


public class Chrg {
    public int x;
    public int y;
    public float v;
    boolean del;
    boolean selected;
    Chrg(int x, int y, float v) {
        this.x = x;
        this.y = y;
        this.v = v;
        del = false;
        selected = false;
    }

    Chrg() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

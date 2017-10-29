package chml;

/**
 *
 * @author Ilya
 */
public class Chrgs {
    public Chrg arr[] = new Chrg[512];
    public int count = 0;
    public void add(int x, int y, float v)
    {
        arr[count] = new Chrg(x, y, v);
        count++;
    }
    
    public boolean isCTC(int x, int y, double r)
    {
        for (int i = 0; i < count; i++)
        {
            if ((x-arr[i].x)*(x-arr[i].x) + (y-arr[i].y)*(y-arr[i].y) < r*r)
                return true;
        }
        return false;
    }
    public int numCTC(int x, int y, double r)
    {
        for (int i = 0; i < count; i++)
        {
            if ((x-arr[i].x)*(x-arr[i].x) + (y-arr[i].y)*(y-arr[i].y) < r*r)
                return i;
        }
        return -1;
    }
   /* public void del(int n)
    {
        arr[count] = new Chrg(x, y, v);
        count++;
    }
    public void set(int n)
    {
        arr[count] = new Chrg(x, y, v);
        count++;
    }*/
}

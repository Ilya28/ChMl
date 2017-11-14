/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chml;

/**
 *
 * @author Student
 */
public class cSettings {
    public int[] iCnst;
    public double[] fCnst;
    
    public int curType;

    public cSettings() {
        this.fCnst = new double[64];
        this.iCnst = new int[64];
    }
    
    public boolean LoadFromFile() {
     
        return true;
    }
    public boolean SaveToFile() {
     
        return true;
    }
    
    public void ReturnToDef() {    
        
    }
}

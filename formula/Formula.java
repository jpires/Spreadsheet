/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ppc.formula;

import java.util.ArrayList;
import java.util.List;
import ppc.Celula;

/**
 *
 * @author user
 */
public abstract class Formula {
    /***
     * 
     */
    protected Formula left = null;
    protected Formula right = null;
    protected ArrayList<Celula> dependence = null;
    protected int result;


    public abstract int getResult();

    public List<Celula> getDependence(){
        return this.dependence;
    }

    public abstract void generateResult();
}

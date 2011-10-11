/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ppc.formula;

import java.util.ArrayList;
import ppc.Celula;

/**
 *
 * @author pem
 */
public class FormulaSum extends Formula{

    /*@ requires left != null; @*/
    /*@ requires right != null; @*/
    public FormulaSum(Formula left, Formula right){
        this.left = left;
        this.right = right;
        this.dependence = new ArrayList<Celula>();
        generateDependence();
        generateResult();
    }

    private void generateDependence(){
        for (Celula cel : this.left.getDependence()) {
            this.dependence.add(cel);
        }
        for (Celula cel : this.right.getDependence()) {
            this.dependence.add(cel);
        }
    }

    @Override
    public int getResult() {
        return this.result;
    }

    @Override
    public final void generateResult() {
        this.left.generateResult();
        this.right.generateResult();
        this.result = left.getResult() + right.getResult();
    }

    @Override
    public String toString(){
        return("FormulaSum: "+this.result);
    }
}

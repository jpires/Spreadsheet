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
public class FormulaMul extends Formula {

    /*@ requires left != null; @*/
    /*@ requires right != null; @*/
    public FormulaMul(Formula left, Formula right) {
        this.left = left;
        this.right = right;
        this.dependence = new ArrayList<Celula>();
        generateDependence();
        generateResult();
    }

    @Override
    public int getResult() {
        return this.result;
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
    public final void generateResult() {
        this.left.generateResult();
        this.right.generateResult();
        this.result = left.getResult() * right.getResult();
    }
}

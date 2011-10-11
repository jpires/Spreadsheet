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
public class FormulaNumber extends Formula{

    private int operand;

    public FormulaNumber(int operand){
        this.operand = operand;
        this.dependence = new ArrayList<Celula>();
        generateResult();
    }
    
    @Override
    public int getResult() {
        return this.result;
    }

    @Override
    public final void generateResult() {
        this.result = this.operand;
    }

    @Override
    public String toString(){
        return "FormulaNumber: "+ this.result;
    }

}

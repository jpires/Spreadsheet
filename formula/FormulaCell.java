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
public class FormulaCell extends Formula{
    private Celula cell;

    /*@ requires cell != null; @*/
    public FormulaCell(Celula cell){
        this.cell = cell;
        this.dependence = new ArrayList<Celula>();
        generateDependece();
        generateResult();
    }
    
    @Override
    public int getResult() {
        return this.result;
    }

    private void generateDependece(){
        this.dependence.add(cell);
    }

    @Override
    public final void generateResult() {
        this.result = cell.getResult();
    }

    @Override
    public String toString(){
        return("FormulaCell: " + this.result);
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ppc;

import java.util.ArrayList;
import java.util.List;
import ppc.formula.Formula;
import ppc.formula.FormulaNumber;

/**
 *
 * @author user
 */
public class Celula {
    /***
     * Lista de Celulas que dependem desta celula.
     */
    private ArrayList<Celula> dependent;
    /***
     * Formula associada a Celula.
     */
    private Formula formula;
    /***
     * Lista de Celulas de que esta Celula depende.
     */
    private ArrayList<Celula> dependence;
    private int row;
    private int col;
    private int result;

    /*@ requires row >= 0; @*/
    /*@ requires col >= 0; @*/
    public Celula(int row, int col){
        this.dependent = new ArrayList<Celula>();
        this.dependence = new ArrayList<Celula>();
        this.formula = new FormulaNumber(0);
        this.row = row;
        this.col = col;
    }

    /*@ requires row >= 0; @*/
    /*@ requires col >= 0; @*/
    /*@ requires formula != null; @*/
    public Celula(int row, int col, Formula formula){
        this.dependent = new ArrayList<Celula>();
        this.dependence = (ArrayList<Celula>) formula.getDependence();
        this.formula = formula;
        this.row = row;
        this.col = col;
        refreshResult();
    }

    /*@ requires row >= 0; @*/
    /*@ requires col >= 0; @*/
    /*@ requires dependent != null; @*/
    /*@ requires formula != null; @*/
    public Celula(int row, int col, List<Celula> dependent, Formula formula){
        this.dependent = (ArrayList<Celula>) dependent;
        this.dependence = (ArrayList<Celula>) formula.getDependence();
        this.formula = formula;
        this.row = row;
        this.col = col;
        addToDependents();
        refreshResult();
    }

    private void addToDependents(){
        for(Celula cel: dependence){
            cel.addDependent(this);
        }
    }

    public void removeFromDependents(){
        for(Celula cel: dependence){
            cel.removeDependent(this);
        }
    }
    /*@ requires formula != null; @*/
    public void setFormula(Formula formula){
        this.formula = formula;
        this.dependence = (ArrayList<Celula>) formula.getDependence();
        addToDependents();
        refreshResult();
    }

    public final void refreshResult(){
        this.formula.generateResult();
        this.result = this.formula.getResult();
    }

    public int getResult(){
        return(this.result);
    }

   /*@ requires cel != null; @*/
    public void addDependent(Celula cel){
        this.dependent.add(cel);
    }

    /*@ requires cel != null; @*/
    public void removeDependent(Celula cel){
        this.dependent.remove(cel);
    }

    /*@ requires cel != null; @*/
    public boolean existeDependent(Celula cel){
        return (this.dependent.contains(cel));
    }

    public List<Celula> getDependent(){
        return this.dependent;
    }

    public void printPosition(){
        System.out.print(row+"x"+col+" ");
    }
}

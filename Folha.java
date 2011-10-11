/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ppc;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import ppc.formula.Formula;
/**
 *
 * @author user
 */
public class Folha {

    private String name = "Folha1";
    private int rows = 64;
    private int cols = 64;

    public  Celula[][] cells;

    public Folha(){
        cells = new Celula[this.rows][this.cols];
        for(int i = 0; i < this.rows; i++)
            for(int j = 0; j < this.cols; j++)
                cells[i][j] = new Celula(i,j);
    }

    /*@ requires nome != null; @*/
    public Folha(String nome){
        this.name = nome;
        cells = new Celula[this.rows][this.cols];
        for(int i = 0; i < this.rows; i++)
            for(int j = 0; j < this.cols; j++)
                cells[i][j] = new Celula(i,j);
    }

    /*@ requires row > 0; @*/
    /*@ requires col > 0; @*/
    public Folha(int row, int col){
        this.rows = row;
        this.cols = col;
        cells = new Celula[row][col];
        for(int i = 0; i < this.rows; i++)
            for(int j = 0; j < this.cols; j++)
                cells[i][j] = new Celula(i,j);
    }

    /*@ requires nome != null; @*/
    /*@ requires row > 0; @*/
    /*@ requires col > 0; @*/
    public Folha(String nome, int row, int col){
        this.name = nome;
        this.rows = row;
        this.cols = col;
        cells = new Celula[row][col];
        for(int i = 0; i < this.rows; i++)
            for(int j = 0; j < this.cols; j++)
                cells[i][j] = new Celula(i,j);
    }

    /*@ requires row >= 0; @*/
    /*@ requires col >= 0; @*/
    /*@ requires row < this.rows; @*/
    /*@ requires col < this.cols; @*/
    /*@ requires cmd != null; @*/
    public void changeCell(int row, int col, Formula cmd){
        cells[row][col].removeFromDependents();

        cells[row][col].setFormula(cmd);

        ArrayList<Celula> sortList = (ArrayList<Celula>) sortCell(cells[row][col]);

        for(Celula aux:sortList)
            aux.refreshResult();
    }

    /*@ requires initialCell != null; @*/
    public List<Celula> sortCell(Celula initialCell){
        ArrayList<Celula> sortList = new ArrayList<Celula>();
        Deque<Celula> unsortList = new ArrayDeque<Celula>();
        for(Celula auxCell: initialCell.getDependent()){
            unsortList.add(auxCell);
        }

        while(!unsortList.isEmpty()){
            Celula auxCell = unsortList.pop();
            sortList.add(auxCell);
            for(Celula aux:auxCell.getDependent()){
                unsortList.push(aux);
            }
        }
        return sortList;
    }

    /*@ requires row >= 0; @*/
    /*@ requires col >= 0; @*/
    /*@ requires row < this.rows; @*/
    /*@ requires col < this.cols; @*/
    public Celula getCell(int row, int col){
        return cells[row][col];
    }

    public void print(){
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                System.out.print(cells[i][j].getResult());
                System.out.print(" ");
            }
            System.out.print('\n');
        }
    }
}

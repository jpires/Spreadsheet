/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ppc;

import java.util.Scanner;
import ppc.formula.FormulaFactory;
import ppc.formula.Formula;
/**
 *
 * @author user
 */
public class Main {

    /**
     * @param args the command line arguments
     */

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String cmd;
        int colunas;
        int linhas;
        String[] argumentos;
                
        System.out.print("Introduza o numero de linhas que pretende para a folha de calculo.\nIntroduza da seguinte forma 4x4 para uma folha de 4 linhas por 4 colunas\n");
        cmd = scanner.nextLine();

        do{
            argumentos = cmd.split("[x ]");
            if(argumentos.length == 2){
                linhas = Integer.parseInt(argumentos[0]);
                colunas = Integer.parseInt(argumentos[1]);
                break;
            }else{
                System.out.println("Numero de linhas ou numero de colunas invalido");
                cmd = scanner.nextLine();
            }
        }while(true);

        Folha folha = new Folha(linhas,colunas);
        System.out.println();
        folha.print();
        boolean exit = false;
        while(!exit){
            System.out.println("Introduza uma formula:");
            cmd = scanner.nextLine();
            if(cmd.equals("exit")){
                exit=true;
            }
            argumentos = cmd.split("=");
            if(argumentos.length != 2){
                System.out.print("formula incorecta\n");
                continue;
            }
            String celula = argumentos[0];
            String[] celulaCordenadas = celula.split("[x ]");
            if(celulaCordenadas.length != 2){
                System.out.print("Celula de destino incorrecta incorecta\n");
                continue;
            }
            FormulaFactory fFactory = new FormulaFactory(folha);
            Formula formula = fFactory.createFormulaFromIN(argumentos[1]);

            int linha = Integer.parseInt(celulaCordenadas[0]);
            int coluna = Integer.parseInt(celulaCordenadas[1]);

            folha.changeCell(linha, coluna, formula);
            folha.print();

        }
        
    }
}

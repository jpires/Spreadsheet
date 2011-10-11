/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ppc.formula;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import ppc.*;
/**
 *
 * @author pem
 */
public class FormulaFactory {
    
    protected Folha folha;
    static private String ops = "[+*//-]";
    ArrayList<Celula> dep;

    /***
     * Construtor da classe. Tem de receber a folha de calculo a qual as formulas vao estar associadas.
     * @param folha folha de calculo.
     */
    /*@ requires folha != null; @*/
    public FormulaFactory(Folha folha){
        this.folha = folha;
        this.dep = new ArrayList<Celula>();
    }

    public ArrayList<Celula> getDep(){
        return this.dep;
    }

    /***
     *  Cria uma Formula com base numa expressao Infix Notation.
     * @param cmd A expressao em Infix Notation.
     * @return A formula que implementa a expressao.
     */
    /*@ requires cmd != null; @*/
    public Formula createFormulaFromIN(String cmd){
        String RPN = infixToRPN(cmd);
        return(createFormulaFromRPN(RPN));
    }

    /***
     * Cria uma Formula com base numa expressao Reverse Polish Notation.
     * @param RPN A expressao em Reverse Polish Notation.
     * @return A formula que implementa a RPN.
     */
    /*@ requires RPN != null; @*/
    public Formula createFormulaFromRPN(String RPN){
        Deque<Formula> stack = new ArrayDeque<Formula>();
        int i=0;
        String[] arrayTokens = RPN.split(" ");

        while(i < arrayTokens.length){
            if(arrayTokens[i].matches("[0-9]+|[0-9]+x[0-9]+")){
                Formula operand = factoryFormulaSring(arrayTokens[i]);
                stack.push(operand);
            }else{
                if(arrayTokens[i].matches(ops)){
                    Formula formulaOp2 = stack.pop();
                    Formula formulaOp1 = stack.pop();
                    Formula result = factoryFormula(arrayTokens[i], formulaOp1, formulaOp2);
                    stack.push(result);
                }
            }
            i++;
        }
        return(stack.pop());
    }

    /*
     * Cria uma Formula que pode ser de dois tipos, Formula com um numero ou com uma celula.
     */
    /*@ requires operand != null;@*/
    private Formula factoryFormulaSring(String operand){
        Formula result = null;
        int op;
        int row, col;
        if(operand.matches("[0-9]+")){
            op = Integer.parseInt(operand);
            result = new FormulaNumber(op);
        } else {
            if(operand.matches("[0-9]+x[0-9]+")){
                String[] cell = operand.split("x");
                row = Integer.parseInt(cell[0]);
                col = Integer.parseInt(cell[1]);
                Celula celula = folha.getCell(row, col);
                dep.add(celula);
                result = new FormulaCell(celula);
            }
        }
        return result;
    }
    
    /*
     * Cria uma Formula baseada na operação (op)
     */
    /*@ requires op !=null;
    /*@ requires left != null;
    /*@ requires right != null;
     @*/
    private Formula factoryFormula(String op, Formula left, Formula right){
        Formula result = null;
        switch(op.charAt(0)){
            case '+':
                result = new FormulaSum(left, right);
                break;
            case '*':
                result = new FormulaMul(left, right);
                break;
        }
        return result;
    }

    /*
     * Transforma uma expressao em infix Notationa para Reverse Polish Notation.
     */
    /*@ requires cmd != null;@*/
    private String infixToRPN(String cmd){
        String RPN=new String();
        Deque<String> stack = new ArrayDeque<String>();

        int i=0;
        while(i < cmd.length()){
            String token = "" + cmd.charAt(i);
            if(token.matches("[ ]")){
                i++;
                continue;
            }

            if(token.matches("[0-9x]")){
                RPN += token;
            }else{
                if(token.matches(ops)){
                    String operator2;
                    while(!stack.isEmpty()){
                        operator2 = stack.pop();
                        if(isOperator(operator2) && (precedence(token) <= precedence(operator2))){
                            RPN += (" " + operator2 + " ");
                        }else{
                            stack.push(operator2);
                            break;
                        }
                    }
                    RPN += " ";
                    stack.push(token);
                }else{
                    if(token.matches("\\(")){
                        stack.push(token);

                    }else{
                        if(token.matches("\\)")){
                            boolean nParenthesis = false;
                            while(!stack.isEmpty()){
                                String leftParenthesis = stack.pop();
                                if(!leftParenthesis.matches("\\(")){
                                    RPN += (" " + leftParenthesis + " ");
                                }else{
                                    nParenthesis = true;
                                    break;
                                }
                            }
                            if(nParenthesis == false)
                               throw new RuntimeException("Erro: Numero de parentecis errado");
                        }
                    }
                }
            }
            i++;
        }
        while(!stack.isEmpty()){
            String aux = stack.pop();
            if(aux.matches("\\("))
                throw new RuntimeException("Erro: Numero de parentecies errado");
            RPN += (" " + aux + " ");
        }
        return RPN;
    }

    /*@ requires op != null;@*/
    private int precedence(String op){
        switch(op.charAt(0)){
            case '*':  case '/': case '%':
                return 3;
            case '+': case '-':
                return 2;
            case '=':
                return 1;
        }
        return 0;
    }

    /*@ requires op != null;@*/
    private boolean isOperator(String op){
        return(op.matches(ops));
    }

}

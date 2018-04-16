import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class LastExpressionTest {
    public LastExpressionTest() {
        super();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String middleExpression = new String(); 
        String lastExpression = new String();
        ArrayList<String> lastExpressionArraylist = new ArrayList<>();
        double result = 0;
        middleExpression = scanner.nextLine();
        scanner.close();    //关闭输入流
        lastExpressionArraylist = MiddleToLast( middleExpression ); //中缀表达式转为后缀表达式
        result = caculateLast(lastExpressionArraylist);//通过后缀表达式计算结果
        System.out.println("the former Expression is : " + middleExpression);       
        System.out.println("the result is : " + result);
    }

    public static ArrayList<String> MiddleToLast(String middle)//last携带后缀表达式
    {
        String last = "";
        StringBuilder sbLast = new StringBuilder(); //后缀表达式字符builder
        StringBuilder tempSb = new StringBuilder(); //Double类型数字临时builder
        ArrayList lastExpressionArrayList = new ArrayList<>();
        Stack<String> operatorStack = new Stack<>();//操作符栈
        char[]  middleChar = middle.toCharArray();
        for(char c : middleChar)
        {
            if( Character.isDigit(c) || String.valueOf(c).equals(".")) //如果是数字或者小数点，直接添加到后缀表达式字符串Builder，同时添加到tempSb中
            {
                tempSb.append(c);
                sbLast.append(c);
            }else 
            {
                if(tempSb.length()!=0)
                {
                    lastExpressionArrayList.add(tempSb.toString()); //将tempSb转化为字符串，并加入到ArrayList中，此时tempList代表一个操作数
                    tempSb.delete(0, tempSb.length());    //清空tempBuilder                
                } 
                String cString = String.valueOf(c);
                //若栈空，则直接入栈；否则，继续判断
                if( operatorStack.isEmpty())
                {
                    operatorStack.push(cString);
                }else if( cString.equals("(")) //如果是“（” ，压入操作符栈
                {   
                    operatorStack.push(cString);
                }else if( cString.equals(")"))//如果是“）”，将操作符栈内元素出栈，并输出到后缀表达式字符串中，直到“（”,最后将操作符栈中的“（”出栈
                {
                    do
                    {
                        String temp = operatorStack.pop();
                        sbLast.append(temp);
                        lastExpressionArrayList.add(temp); //将操作符加入ArrayList中
                    }while(!operatorStack.peek().equals("("));
                    operatorStack.pop();//将“（”出栈
                }else if(getPriority(cString) > getPriority(operatorStack.peek())) //若栈外操作符优先级高于栈顶元素，则将栈外操作符入栈
                {
                    operatorStack.push(cString);
                }else   //若栈外元素优先级低于或等于栈顶元素优先级，则将栈顶元素出栈，直到 1.栈空 2.栈外元素优先级高于栈顶元素优先级 3.遇到“(”
                {
                    do
                    {
                        lastExpressionArrayList.add(operatorStack.peek()); //
                        sbLast.append(operatorStack.pop());
                    }while(!operatorStack.isEmpty() && getPriority(cString) <= getPriority(operatorStack.peek()) && !operatorStack.peek().equals("("));
                    operatorStack.push(cString);
                } 
            }
        }
        //最后将操作符栈整体出栈
        while( !operatorStack.isEmpty() )
        {
            lastExpressionArrayList.add(operatorStack.peek());
            sbLast.append(operatorStack.pop());
        }
        last = sbLast.toString();
        System.out.println("the lastExpression is : " + last);
        System.out.println(lastExpressionArrayList.toString());
        return lastExpressionArrayList;
    }

    //判断操作符的优先级
    /*
        优先级分类： 1. "(":
                    2.  "+"、 "-";
                    3.  "*"、 "-".
    */

    public static int getPriority(String operatorString) {
        int priority = -1 ;
        switch( operatorString )
        {
            case "(" : priority = 1 ;
                        break;
            case "+" : priority = 2 ;
                        break;
            case "-" : priority = 2 ;
                        break;
            case "*" : priority = 3 ;
                        break;
            case "/": priority = 3;
                        break;
            default :  priority = -1;
        }
        return priority;
    }

    //计算后缀表达式的值
    public static double caculateLast(ArrayList<String> lastExpressionArrayList) {
        double result = 0;
        double num1 = 0;
        double num2 = 0;
        Stack<String> LastExpressionStack = new Stack<>();
        for(String string :lastExpressionArrayList)
        {
            if( string.equals("+") ) 
            {
                num2 = Double.valueOf(LastExpressionStack.pop());
                num1 = Double.valueOf(LastExpressionStack.pop());
                System.out.println(""+ num1 +"  + "+ num2 +" = " + Double.toString(num1 + num2));
                LastExpressionStack.push(Double.toString(num1 + num2));
            }else if( string.equals("-") )
            {
                num2 = Double.valueOf(LastExpressionStack.pop());
                num1 = Double.valueOf(LastExpressionStack.pop());
                System.out.println(""+ num1 +"  - "+ num2 +" = " + Double.toString(num1 - num2));
                LastExpressionStack.push(Double.toString(num1 - num2));
            }else if( string.equals("*") )
            {
                num2 = Double.valueOf(LastExpressionStack.pop());
                num1 = Double.valueOf(LastExpressionStack.pop());
                System.out.println(""+ num1 +"  * "+ num2 +" = " + Double.toString(num1 * num2));
                LastExpressionStack.push(Double.toString(num1 * num2));
            }else if( string.equals("/") )
            {
                num2 = Double.valueOf(LastExpressionStack.pop());
                num1 = Double.valueOf(LastExpressionStack.pop());
                System.out.println(""+ num1 +"  / "+ num2 +" = " + Double.toString(num1 / num2));
                LastExpressionStack.push(Double.toString(num1 / num2));
            }else //如果string不是 + - * / 操作符，则入栈
            {
                LastExpressionStack.push(string);
            }
        }
        
        result = Double.valueOf(LastExpressionStack.pop());
        return result;
    }
}
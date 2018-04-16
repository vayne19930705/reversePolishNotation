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
        scanner.close();    //�ر�������
        lastExpressionArraylist = MiddleToLast( middleExpression ); //��׺���ʽתΪ��׺���ʽ
        result = caculateLast(lastExpressionArraylist);//ͨ����׺���ʽ������
        System.out.println("the former Expression is : " + middleExpression);       
        System.out.println("the result is : " + result);
    }

    public static ArrayList<String> MiddleToLast(String middle)//lastЯ����׺���ʽ
    {
        String last = "";
        StringBuilder sbLast = new StringBuilder(); //��׺���ʽ�ַ�builder
        StringBuilder tempSb = new StringBuilder(); //Double����������ʱbuilder
        ArrayList lastExpressionArrayList = new ArrayList<>();
        Stack<String> operatorStack = new Stack<>();//������ջ
        char[]  middleChar = middle.toCharArray();
        for(char c : middleChar)
        {
            if( Character.isDigit(c) || String.valueOf(c).equals(".")) //��������ֻ���С���㣬ֱ����ӵ���׺���ʽ�ַ���Builder��ͬʱ��ӵ�tempSb��
            {
                tempSb.append(c);
                sbLast.append(c);
            }else 
            {
                if(tempSb.length()!=0)
                {
                    lastExpressionArrayList.add(tempSb.toString()); //��tempSbת��Ϊ�ַ����������뵽ArrayList�У���ʱtempList����һ��������
                    tempSb.delete(0, tempSb.length());    //���tempBuilder                
                } 
                String cString = String.valueOf(c);
                //��ջ�գ���ֱ����ջ�����򣬼����ж�
                if( operatorStack.isEmpty())
                {
                    operatorStack.push(cString);
                }else if( cString.equals("(")) //����ǡ����� ��ѹ�������ջ
                {   
                    operatorStack.push(cString);
                }else if( cString.equals(")"))//����ǡ���������������ջ��Ԫ�س�ջ�����������׺���ʽ�ַ����У�ֱ��������,��󽫲�����ջ�еġ�������ջ
                {
                    do
                    {
                        String temp = operatorStack.pop();
                        sbLast.append(temp);
                        lastExpressionArrayList.add(temp); //������������ArrayList��
                    }while(!operatorStack.peek().equals("("));
                    operatorStack.pop();//����������ջ
                }else if(getPriority(cString) > getPriority(operatorStack.peek())) //��ջ����������ȼ�����ջ��Ԫ�أ���ջ���������ջ
                {
                    operatorStack.push(cString);
                }else   //��ջ��Ԫ�����ȼ����ڻ����ջ��Ԫ�����ȼ�����ջ��Ԫ�س�ջ��ֱ�� 1.ջ�� 2.ջ��Ԫ�����ȼ�����ջ��Ԫ�����ȼ� 3.������(��
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
        //��󽫲�����ջ�����ջ
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

    //�жϲ����������ȼ�
    /*
        ���ȼ����ࣺ 1. "(":
                    2.  "+"�� "-";
                    3.  "*"�� "-".
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

    //�����׺���ʽ��ֵ
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
            }else //���string���� + - * / ������������ջ
            {
                LastExpressionStack.push(string);
            }
        }
        
        result = Double.valueOf(LastExpressionStack.pop());
        return result;
    }
}
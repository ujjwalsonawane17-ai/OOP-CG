import java.util.Scanner;

class calculator{

static int add(int num1,int num2){
    return num1+num2;
}

    static int sub(int num1,int num2){
    return num1-num2;
    }
   static int div(int num1,int num2){
    if (num2==0)
     {
    return -1;
    }
    return num1/num2;
   }
    static int mul(int num1,int num2){
    return num1*num2;
    }
    static int mod(int num1,int num2){
        return num1%num2;
    }



    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        System.out.println("enter number 1");
        int num1=sc.nextInt();
        System.out.println("enter number 2");
        int num2=sc.nextInt();
        System.out.println("enter the operator +,-,*,/,%");
        String operator=sc.next();


       switch (operator) {
        case "+":
        System.out.println(add(num1, num2));
        break;
        case "-":
        System.out.println(sub(num1, num2));
        break;
        case "*":
        System.out.println(mul(num1, num2));
        break;
        case "/":
        System.out.println(div(num1, num2));
        break;
         case "%":
        System.out.println(mod(num1, num2));
        break;
            
            
        
    }
    sc.close();
            
    }
        }
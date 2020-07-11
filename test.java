public class test{
    public static void main(String[] args){
        int num = 2*3*4*5*6*7*11;
        int denom = 2*3*4*5*6*7*11*11;
        boolean isNegative = (num<0 && denom>0) || (num>=0 && denom<0);
        int simplifiedNumerator = Math.abs(num);
        int simplifiedDenominator = Math.abs(denom);
        int sqrtOfNumerator = (int)Math.sqrt(simplifiedNumerator);

        // a fun way to find all of the roots in a number fairly efficiently :)
        // have to make a separate check to see if it is divisible by two;
        while(simplifiedNumerator%2 == 0 && simplifiedDenominator%2 == 0){
            System.out.println(simplifiedNumerator+", "+simplifiedDenominator+", "+isNegative);
          simplifiedNumerator = simplifiedNumerator/2;
          simplifiedDenominator = simplifiedDenominator/2;
        }  
        int i = 3;
        while(i < sqrtOfNumerator){
            System.out.println(simplifiedNumerator+", "+simplifiedDenominator+", "+isNegative);
          //int a = (int)Math.pow(i , 2.0);
          //int b = (int)Math.pow(i+1 , 2.0);
          int mod = i;//b-a;
          if(simplifiedNumerator%mod == 0 && simplifiedDenominator%mod == 0){
            simplifiedNumerator = simplifiedNumerator/mod;
            simplifiedDenominator = simplifiedDenominator/mod;
          } else {
            i+=2;
          }
          if(simplifiedNumerator == 1 || simplifiedDenominator == 1){
              break;
          }
        }
        System.out.println(simplifiedNumerator+", "+simplifiedDenominator+", "+isNegative);
    }
}
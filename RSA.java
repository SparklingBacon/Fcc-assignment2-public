import java.math.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class RSA {
    private static BigInteger x;
    private static BigInteger y;

    //getters
    public static BigInteger getx(){
        return x;
    }

    public static BigInteger gety(){
        return y;
    }

    //setters
    public static void setx(BigInteger new_x){
        x = new_x;
    }

    public static void sety(BigInteger new_y){
        y = new_y;
    }
    


    public static void main(String[] args) {

        // BigInteger input = new BigInteger("8971430138903472848690672");
        // // int input = 79;
        // boolean percent = PrimeTest(input);
        // System.out.printf("The probability of %d being prime is: %b\n", input, percent);
        

        // BigInteger b,e,m;
        // b = new BigInteger("631879029234023");
        // e = new BigInteger("3284033123985");
        // m = new BigInteger("164201640187510402");

        // System.out.println("BinaryModularExponentiation: " + BinaryModularExponentiation(b,e,m));

        BigInteger e = new BigInteger("36163"), phi = new BigInteger("1058");
        // BigInteger e = new BigInteger("19328409128342435"), phi = new BigInteger("5982435023450245");
        // AtomicInteger x = new AtomicInteger(), y = new AtomicInteger();
        setx(BigInteger.valueOf(1));
        sety(BigInteger.valueOf(1));
        System.out.println("ExtendedEuclideanAlgorithm: " + ExtendedEuclideanAlgorithm(e,phi,getx(),gety()));
        System.out.println("x: " + getx());
        System.out.println("y: " + gety());
    
    }

    

    // generates the private and public key for RSA
    public static void KeyScheduler() {
        boolean prime_p = false;
        boolean prime_q = false;

        //step 1
        byte[] bytes = new byte[8+1];
        bytes[0] = 1;
        BigInteger base = new BigInteger(bytes);//base = 2^64

        BigInteger p_add = new BigInteger(32, new Random());
        BigInteger q_add = new BigInteger(33, new Random());

        BigInteger p = base.add(p_add);//number larger than 2^64
        BigInteger q = base.add(q_add);//number larger than 2^64

        //checks whether both p and q are prime
        do{
            prime_p = PrimeTest(p);
            if(prime_p == false){
                p = p.add(BigInteger.valueOf(1));
            }
            prime_q = PrimeTest(q);
            if(prime_q == false){
                q = q.add(BigInteger.valueOf(1));
            }

        }while(prime_p == false && prime_q == false);

        //step 2
        BigInteger n = p.multiply(q);

        //step 3
        BigInteger phi_n = p.subtract(BigInteger.valueOf(1)).multiply(q.subtract(BigInteger.valueOf(1)));

        //step 4
        BigInteger e = BigInteger.valueOf(0);
        int a = 0, b = 0;
        AtomicInteger x = new AtomicInteger(), y = new AtomicInteger();

        // do{
        //     // ExtendedEuclideanAlgorithm()
        // }while();

        
        // return keys;
    }

    //checks if number is prime
    public static boolean PrimeTest(BigInteger p) {
        // Random rand = new Random();
        int t = 5;
        double probofprime = 0.0;
        boolean res = false;
        BigInteger a,b1,b2;
        BigInteger r = new BigInteger("0");
        a = BigInteger.valueOf(3);
        b1 = p.subtract(BigInteger.valueOf(1));
        b2 = b1.divide(BigInteger.valueOf(2));//resulting base

        for (int i = 1; i <= t; i++) {
            if (a.compareTo(b1) <= 0){
                r = BinaryModularExponentiation(a, b2, p);
            }

            if (r.compareTo(BigInteger.valueOf(1)) == 0 || r.compareTo(b1) == 0){
                probofprime = 1 - (1 / (Math.pow(2, i)));

                a = a.add(BigInteger.valueOf(1));
            } 
            else if (r.compareTo(BigInteger.valueOf(1)) != 0 || r.compareTo(b1) != 0){
                probofprime = 0.0;
                i = t + 1;
            }
        }
        
        probofprime = probofprime * 100;
        if((int)probofprime == 0){
            res = false;
        }
        else{
            res = true;
        }
        return res;// returns the double percentage of primality
    }


    // public static BigInteger ExtendedEuclideanAlgorithm(BigInteger e, BigInteger phi, AtomicInteger x, AtomicInteger y) {
    //     // if(a == 0)
    //     if(e.compareTo(BigInteger.valueOf(0)) == 0){
    //         x.set(0);
    //         y.set(1);
    //         return phi;
    //     }

    //     AtomicInteger _x = new AtomicInteger(), _y = new AtomicInteger();
    //     // int gcd = ExtendedEuclideanAlgorithm(b % a, a, _x, _y);
    //     BigInteger gcd = ExtendedEuclideanAlgorithm(phi.mod(e), e, _x, _y);
        
    //     // x.set(_y.get() - (b/a) * _x.get());
    //     BigInteger phi_div_e = phi.divide(e);
    //     x.set(BigInteger.valueOf(_y.get()).subtract(phi_div_e.multiply(BigInteger.valueOf(_x.get()))).intValue()) ;
    //     y.set(_x.get());
    //     return gcd;
    // }

    public static BigInteger ExtendedEuclideanAlgorithm(BigInteger e, BigInteger phi, BigInteger x, BigInteger y) {
        // if(a == 0)
        if(e.compareTo(BigInteger.valueOf(0)) == 0){
            setx(BigInteger.valueOf(0));
            sety(BigInteger.valueOf(1));

            return phi;
        }

        BigInteger _x = BigInteger.valueOf(0);
        BigInteger _y = BigInteger.valueOf(0);

        BigInteger gcd = ExtendedEuclideanAlgorithm(phi.mod(e), e, _x, _y);

        setx(_y.subtract(phi.divide(e).multiply(_x)));
        sety(_x);

        return gcd;
    }


    public static BigInteger BinaryModularExponentiation(BigInteger base, BigInteger exponent, BigInteger modulus) {

        BigInteger result = new BigInteger("1");
        BigInteger one = new BigInteger("1");
        BigInteger zero = new BigInteger("0");

        if (modulus.compareTo(one) == 0) {
            result = BigInteger.valueOf(0);
        }
        base = base.mod(modulus);

        while (exponent.compareTo(zero) > 0){
            if (exponent.mod(BigInteger.valueOf(2)).compareTo(one) == 0 ) {
                result = result.multiply(base).mod(modulus);
            }
            exponent = exponent.shiftRight(1);
            base = base.multiply(base).mod(modulus);
        }
        return result; // returns the result of a^b mod p
    }
}
import java.math.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.lang.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.SecureRandom;
import java.io.IOException;
import java.io.FileWriter;



public class RSA {
    public static void main(String[] args) {

        // BigInteger input = new BigInteger("8971430138903472848690672");
        // // int input = 79;
        // boolean percent = PrimeTest(input);
        // System.out.printf("The probability of %d being prime is: %b\n", input,
        // percent);

        // BigInteger b,e,m;
        // b = new BigInteger("631879029234023");
        // e = new BigInteger("3284033123985");
        // m = new BigInteger("164201640187510402");

        // System.out.println("BinaryModularExponentiation: " +
        // BinaryModularExponentiation(b,e,m));

        // BigInteger e = new BigInteger("36163"), phi = new BigInteger("1058");
        // BigInteger e = new BigInteger("19328409128342435"), phi = new BigInteger("5982435023450245");
        // // AtomicInteger x = new AtomicInteger(), y = new AtomicInteger();
        // BigInteger val[] = ExtendedEuclideanAlgorithm(e,phi);
        // System.out.println("ExtendedEuclideanAlgorithm: " + val[0]);
        // System.out.println("x: " + val[1]);
        // System.out.println("y: " + val[2]);

        // String text = "Hello world";
        // String text = "HOW ARE YOU roda>?";
        // System.out.println("text: " + text);

        // String hex = TexttoHex(text);
        // System.out.println("TESTING TexttoHex: " + hex);

        // BigInteger dec = HextoDec(hex);
        // System.out.println("TESTING HextoDec: " + dec);

        // String hex2 = DectoHex(dec);
        // System.out.println("TESTING DectoHex: " + hex2);

        // String text2 = HextoText(hex2);
        // System.out.println("TESTING HextoDec: " + text2);

        //[0],[1],[2] = n, e, d
        // System.out.println("TEST");
        // BigInteger keys[] = KeyScheduler();
        // System.out.println("TEST FOR n GENERATOR:" + keys[0]);
        // System.out.println("TEST FOR e GENERATOR:" + keys[1]);
        // System.out.println("TEST FOR d GENERATOR:" + keys[2]);

        // String cipherhex = encryption(text, keys);
        // System.out.println("CIPHERHEX: " + cipherhex);

        // String plaintext = decryption(cipherhex, keys);
        // System.out.println("PLAINTEXT: " + plaintext);

        // BigInteger keys[] = KeyScheduler();
        // System.out.println("TEST FOR n GENERATOR:" + keys[0]);
        // System.out.println("TEST FOR e GENERATOR:" + keys[1]);
        // System.out.println("TEST FOR d GENERATOR:" + keys[2]);
        
        // BigInteger e = new BigInteger("65537");
        // BigInteger phi = new BigInteger("340282366920938464348818322969826689456");
        // BigInteger eea[] = ExtendedEuclideanAlgorithm(e, phi);
        // if(eea[1].compareTo(BigInteger.ZERO) <= 0){//if x/d is <= 0
        //     eea[1] = eea[1].add(phi);
        // }
        // System.out.println("GCD:" + eea[0]);
        // System.out.println("x:" + eea[1]);
        // System.out.println("y:" + eea[2]);

        // *RMB ABOUT THE M < N

        // String text = "HOW ARE YOU roda>?";
        // BigInteger val = new BigInteger("340282366920938464385711811117245792737");
        // String arr[] = TexttoHex(text, val);

        // for (int i = 0; i < arr.length; i++) {
            
        //     System.out.println("HEX TEXT: " + arr[i]);
        // }

        BigInteger keys[] = KeyScheduler();

        String text = readfile("RSA-test.txt");

        String cipherhex = encryption(text, keys);
        // System.out.println("CIPHERHEX: " + cipherhex);

        writefile(decryption(cipherhex, keys), "DECRYPTED.TXT");
        // System.out.println("PLAINTEXT: " + plaintext);

    }

    public static String[] TexttoHex(String text, BigInteger n){
        // String hex="", temp="";
        String partmhex[] = new String[1];
        // int j=0;

        // for (int i = 0; i < text.length(); i++) {
        //     hex = hex + String.format("%02x", (int)text.charAt(i));
        //     System.out.println("HEX: " + hex);
        //     System.out.println("checkmn: " + checkMsmallerN(HextoDec(hex), n));
        //     if(checkMsmallerN(HextoDec(hex), n)){
        //         temp = hex;//is the string just before m > n
        //         System.out.println("temp: " + temp);
        //     }
        //     else{
        //         partmhex[j] = temp;
        //         j++;
        //         partmhex = Arrays.copyOf(partmhex, j + 1);
        //         hex= temp.replace(temp, "");
        //         System.out.println("AFTER HEX: " + hex);
        //     }
        // }

        String hex = "";
        String temp = "";
        // StringBuilder hex = new StringBuilder();
        // StringBuilder temp = new StringBuilder();
        int j=0;

        for(int i =0; i<text.length(); i++){
            temp = hex;
            hex += Integer.toHexString(text.charAt(i));
            // System.out.println("B4TEMP: " + temp);
            // System.out.println("B4HEX: " + hex);
            // System.out.println("B4CHECK: " + checkMsmallerN(HextoDec(hex), n));
            
            if(!checkMsmallerN(HextoDec(hex), n)){
                partmhex[j] = temp;
                // System.out.println("TEMP: " + temp);
                // System.out.println("HEX: " + hex);
                j++;
                partmhex = Arrays.copyOf(partmhex, j + 1);
                i--;
                hex= "";
            }
            else if(i == text.length()-1){
                partmhex[j] = hex;
                // System.out.println("123TEMP: " + partmhex[j]);
            }
        }
        return partmhex;
    }

    public static boolean checkMsmallerN(BigInteger m, BigInteger n){
        boolean res = false;

        if(m.compareTo(n) < 0){
            res = true;
        }
        else{
            res = false;
        }
        
        return res;
    }

    public static BigInteger HextoDec(String hex){
        BigInteger dec = new BigInteger(hex, 16);
        return dec;
    }

    public static String DectoHex(BigInteger dec){
        String hexstr = dec.toString(16);

        return hexstr;
    }

    public static String HextoText(String hex){
        // String text ="";
        // for (int i = 0; i < hex.length(); i+=2) {
        //     String h = ""+hex.charAt(i)+""+hex.charAt(i+1);
        //     char t = (char) Integer.parseInt(h,16);
        //     text += t;
        // }

        // return text.toString();
//TAKEN FROM cvarta.(Apr 18, 2012). cvarta/Hex2StringMain.java . https://gist.github.com/cvarta/2408925
        // byte[] hexbyte = new byte[hex.length()/2];
        // int j=0;
        // for (int i = 0; i < hex.length(); i+=2) {
        //     System.out.println("B4 HEXBYTE: " +  hexbyte[j]);
        //     hexbyte[j++] = Byte.parseByte(hex.substring(i, i + 2), 16);
        //     System.out.println("AFTER HEXBYTE: " +  hexbyte[j]);
        // }

        // return new String(hexbyte);

        StringBuilder output = new StringBuilder("");
    
        for (int i = 0; i < hex.length(); i += 2) {
            String str = hex.substring(i, i + 2);
            output.append((char) Integer.parseInt(str, 16));
        }
        
        return output.toString();
    }
//----------------------------------------------------------------------------------------------------------------


    // generates the private and public key for RSA
    public static BigInteger[] KeyScheduler() {
        boolean prime_p = false;
        boolean prime_q = false;

        // step 1
        byte[] bytes = new byte[8 + 1];
        bytes[0] = 1;
        BigInteger base = new BigInteger(bytes);// base = 2^64

        BigInteger p_add = new BigInteger(5, new Random());
        BigInteger q_add = new BigInteger(10, new Random());

        BigInteger p = base.add(p_add);// number larger than 2^64
        BigInteger q = base.add(q_add);// number larger than 2^64
        
        // BigInteger p = new BigInteger("7");
        // BigInteger q = new BigInteger("13");
        
        // System.out.println("base: " + base);
        // System.out.println("p: " + p);
        // System.out.println("q: " + q);
        // checks whether both p and q are prime
        do {
            prime_p = PrimeTest(p);
            if (prime_p == false) {
                p = p.add(BigInteger.valueOf(1));
            }
            prime_q = PrimeTest(q);
            if (prime_q == false) {
                q = q.add(BigInteger.valueOf(1));
            }

        } while (prime_p == false || prime_q == false);
        
        // step 2
        BigInteger n = p.multiply(q);

        // step 3
        BigInteger phi_n = p.subtract(BigInteger.valueOf(1)).multiply(q.subtract(BigInteger.valueOf(1)));

        // step 4, 5 
        // BigInteger e = BigInteger.valueOf(1);
        // Random srand = new SecureRandom();
        // Random rand = new Random();
        // BigInteger e = new BigInteger("1");
        BigInteger e = new BigInteger("65537");
        

        BigInteger checkgcd[] = new BigInteger[3];
        // BigInteger bigInteger = phi_n.subtract(BigInteger.valueOf(1));
        
        //problem : FIND AN E SUCH TAHT GCD(E, PHI(N)) == 1
        //[0] = gcd, [1] = x/d, [2] = y
        // checkgcd = ExtendedEuclideanAlgorithm(e, phi_n);
        
        do{
            // e = e.add(BigInteger.valueOf(1));
            // e = e.probablePrime(phi_n.bitLength(), rand);
            // if (e.compareTo(BigInteger.valueOf(1)) < 0)
            //     e = e.add(BigInteger.valueOf(1));
            // if (e.compareTo(bigInteger) >= 0)
            //     e = e.mod(bigInteger).add(BigInteger.valueOf(1));

            // System.out.println("b4PHI-N: " + phi_n);
            // System.out.println("b4THIS IS E: " + e);
            if(PrimeTest(e) == true){
                // System.out.println("PHI-N: " + phi_n);
                // System.out.println("THIS IS E: " + e);
                checkgcd = ExtendedEuclideanAlgorithm(e, phi_n);
                // System.out.println("gcd: " + checkgcd[0].equals(1));
                // System.out.println("x: " + checkgcd[1]);
            }
            else{
                checkgcd[0] = BigInteger.valueOf(0);
            }
        }while(checkgcd[0].equals(BigInteger.valueOf(1)) == false);//gcd != 1
        // System.out.println("WENNT OUT OF LOOP");
        //step 6
        BigInteger keys[] = new BigInteger[3];
        keys[0] = n;
        keys[1] = e;
        if(checkgcd[1].compareTo(BigInteger.ZERO) <= 0){//if x/d is <= 0
            checkgcd[1] = checkgcd[1].add(phi_n);
        }
        keys[2] = checkgcd[1];

        return keys;
    }

    // checks if number is prime
    public static boolean PrimeTest(BigInteger p) {
        // Random rand = new Random();
        int t = 5;
        double probofprime = 0.0;
        boolean res = false;
        BigInteger a, b1, b2;
        BigInteger r = new BigInteger("0");
        a = BigInteger.valueOf(3);
        b1 = p.subtract(BigInteger.valueOf(1));
        b2 = b1.divide(BigInteger.valueOf(2));// resulting base

        for (int i = 1; i <= t; i++) {
            if (a.compareTo(b1) <= 0) {
                r = BinaryModularExponentiation(a, b2, p);
            }

            if (r.compareTo(BigInteger.valueOf(1)) == 0 || r.compareTo(b1) == 0) {
                probofprime = 1 - (1 / (Math.pow(2, i)));

                a = a.add(BigInteger.valueOf(1));
            } else if (r.compareTo(BigInteger.valueOf(1)) != 0 || r.compareTo(b1) != 0) {
                probofprime = 0.0;
                i = t + 1;
            }
        }

        probofprime = probofprime * 100;
        if ((int) probofprime == 0) {
            res = false;
        } else {
            res = true;
        }
        return res;// returns the double percentage of primality
    }

//TAKEN FROM Aivean (Nov 1, 2021). BigInteger Extended Euclidean Algorithm recursion error. https://stackoverflow.com/questions/69802076/biginteger-extended-euclidean-algorithm-recursion-error
    public static BigInteger[] ExtendedEuclideanAlgorithm(BigInteger e, BigInteger phi) {
    //     BigInteger[] res = new BigInteger[3];
        
    //     if (e.compareTo(BigInteger.valueOf(0)) == 0) {
    //         res[0] = phi;
    //         res[1] = BigInteger.valueOf(0);
    //         res[2] = BigInteger.valueOf(1);
    //     }
    //     else{
    //         BigInteger[] res2 = ExtendedEuclideanAlgorithm(phi.mod(e), e);
    //         res[0] = res2[0];
    //         res[1] = res2[2].subtract(phi.divide(e).multiply(res2[1]));
    //         res[2] = res2[1];
    //         //[0] = gcd, [1] = x/d, [2] = y(dh to care about this)
    //     }

    //     if(res[1].compareTo(BigInteger.valueOf(0)) <= 0){//if x/d is <= 0
    //         res[1] = res[1].add(phi);
    //     }
    //     return res;
   

        BigInteger[] res = new BigInteger[3];

        if (phi.equals(BigInteger.ZERO)) {
            res[0] = e;
            res[1] = BigInteger.ONE;
            res[2] = BigInteger.ZERO;
        } else {
            BigInteger[] res2 = ExtendedEuclideanAlgorithm(phi, e.mod(phi));
            res[0] = res2[0];
            res[1] = res2[2];
            res[2] = res2[1].subtract(e.divide(phi).multiply(res2[2]));
        }
        return res;
    }
//-----------------------------------------------------------------------------------------------------------------------------------------

    public static BigInteger BinaryModularExponentiation(BigInteger base, BigInteger exponent, BigInteger modulus) {

        BigInteger result = new BigInteger("1");
        BigInteger one = new BigInteger("1");
        BigInteger zero = new BigInteger("0");

        if (modulus.compareTo(one) == 0) {
            result = BigInteger.valueOf(0);
        }
        base = base.mod(modulus);

        while (exponent.compareTo(zero) > 0) {
            if (exponent.mod(BigInteger.valueOf(2)).compareTo(one) == 0) {
                result = result.multiply(base).mod(modulus);
            }
            exponent = exponent.shiftRight(1);
            base = base.multiply(base).mod(modulus);
        }
        return result; // returns the result of a^b mod p
    }

    public static String encryption(String plaintext, BigInteger keys[]){
        String plaintexthex[] = TexttoHex(plaintext,keys[0]);
        String cipherhex="";
        for(int i=0; i<plaintexthex.length; i++){
            BigInteger plaintextdec = HextoDec(plaintexthex[i]);
            // System.out.println("PLAINTEXTDEC ENC: "+ plaintextdec);
            // System.out.println("keys[1], e: "+ keys[1]);
            // System.out.println("keys[0], n: "+ keys[0]);
    
            BigInteger c = BinaryModularExponentiation(plaintextdec, keys[1], keys[0]);
            // System.out.println("C AFTER BME: " + c);
            cipherhex += DectoHex(c);

            // System.out.println("plaintexthex["+i+"]: " + plaintexthex[i]);
        }


        // System.out.println("plaintext: " + plaintext);
        // System.out.println("plaintexthex: " + plaintexthex);
        // System.out.println("exponenent: " + keys[1]);
        // System.out.println("modulus: " + keys[0]);
        // System.out.println("cipherhex: " + cipherhex);

        return cipherhex;
    }

    public static String decryption(String cipherhex, BigInteger keys[]){
        String hex ="", temp="";
        String partchex[] = new String[1];
        int j=0;
        for(int i =0; i<cipherhex.length(); i++){
            temp = hex;
            hex += cipherhex.charAt(i);
            // System.out.println("B4TEMP: " + temp);
            // System.out.println("B4HEX: " + hex);
            // System.out.println("B4CHECK: " + checkMsmallerN(HextoDec(hex), keys[0]));
            
            if(!checkMsmallerN(HextoDec(hex), keys[0])){
                // System.out.println("TEMP: " + temp);
                // System.out.println("HEX: " + hex);
                partchex[j] = temp;
                j++;
                partchex = Arrays.copyOf(partchex, j + 1);
                i--;
                hex= "";
            }
            else if(i == cipherhex.length()-1){
                partchex[j] = hex;
                // System.out.println("123TEMP: " + partchex[j]);

            }
        }
        for (int i = 0; i < partchex.length; i++) {
            // System.out.println("PARTCHEX["+i+"]: " + partchex[i]);
        }
        String plaintexthex="";
        for (int z =0; z<partchex.length; z++){
            BigInteger plaintextdec = HextoDec(partchex[z]);
            // System.out.println("plaintextdec:" + plaintextdec);
            // System.out.println("plaintexthex:" + DectoHex(plaintextdec));
    
            BigInteger m = BinaryModularExponentiation(plaintextdec, keys[2], keys[0]);
            plaintexthex += DectoHex(m);
        }
        // System.out.println("PLAINTEXTHEX: " + plaintexthex);
        String plaintext = HextoText(plaintexthex);



        // BigInteger plaintextdec = HextoDec(cipherhex);
        // // System.out.println("plaintextdec:" + plaintextdec);
        // // System.out.println("plaintexthex:" + DectoHex(plaintextdec));

        // BigInteger m = BinaryModularExponentiation(plaintextdec, keys[2], keys[0]);
        // String plaintexthex = DectoHex(m);
        // System.out.println("PLAINTEXTHEX: " + plaintexthex);
        // String plaintext = HextoText(plaintexthex);

        return plaintext;
    }

    public static String readfile(String filename){
        String text = "";
        try {
            Path path = Path.of(filename);
            text = Files.readString(path);

        } catch (IOException e) {
            System.out.println("Error occured during reading file");
            System.exit(1);
        }

        return text;
    }

    public static void writefile(String letter, String filename) {
        try {
            FileWriter out = new FileWriter(filename);
            out.write(letter);
            out.close();
        } catch (IOException e) {
            System.out.println("Error occurred in writing to file.");
            e.printStackTrace();
        }
    }
}
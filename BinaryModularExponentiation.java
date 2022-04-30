import java.util.*;

public class BinaryModularExponentiation {
    public static int modular_pow(int base, int exponent, int modulus) {

        int result = 1;

        if (modulus == 1) {
            result = 0;
        }
        base = base % modulus;

        while (exponent > 0) {
            if (exponent % 2 == 1) {
                result = (result * base) % modulus;
            }
            exponent = exponent >> 1;
            base = (base * base) % modulus;
        }
        return result;
    }
}

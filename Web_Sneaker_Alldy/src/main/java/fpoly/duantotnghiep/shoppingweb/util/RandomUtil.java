package fpoly.duantotnghiep.shoppingweb.util;

import java.util.Random;

public class RandomUtil {
    private static final String chars = "1234567890qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";
    private static Random random = new Random();

    public static String randomPassword(){
        StringBuilder password = new StringBuilder();
        final Integer maxLengthPass = 5;
        for (int i=0;i<maxLengthPass;i++){
            char charRandom = chars.charAt(random.nextInt(chars.length()));
            password.append(charRandom);
        }
        return password.toString();
    }

    public static void main(String[] args) {
        System.out.println(randomPassword());
    }
}

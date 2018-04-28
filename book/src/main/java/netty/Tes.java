package netty;

/**
 * User: 20160301301
 * Date: 2018/2/27 19:59
 * Comment:
 */
public class Tes {
    public static void main(String[] args) {
        System.out.println(add("1532561","9"));

    }

    public static String add(String str1, String str2) {
        if (str1 == null)
            return str2;
        if (str2 == null)
            return str1;
        StringBuilder s1 = new StringBuilder(str1).reverse();
        StringBuilder s2 = new StringBuilder(str2).reverse();
        StringBuffer res = new StringBuffer();
        int len1 = s1.length();
        int len2 = s2.length();
        int len;
        if (len1 < len2) {
            len = len2;
            int count = len2 - len1;
            while (count-- > 0)
                s1.append('0');
        } else {
            len = len1;
            int count = len1 - len2;
            while (count-- > 0)
                s2.append('0');
        }
        int overflow = 0;
        int num;
        for (int i = 0; i < len; i++) {
            num = s1.charAt(i) - '0' + s2.charAt(i) - '0' + overflow;
            if (num >= 10) {
                overflow = 1;
                num -= 10;
            } else {
                overflow = 0;
            }
            res.append(String.valueOf(num));
        }
        if (overflow == 1)
            res.append(1);

        return res.reverse().toString();
    }
}

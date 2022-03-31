public class Test {
    public static void main(String[] args) throws UnsupportedEncodingException {
        String base32Chars = "5CDEFGHIJKLMNOPQRSTUVWXYZ234567";
        System.out.println(binaryOut(base32Chars));

    }
    /**
     * 如果位数超过8位，则过滤掉
     * 位数不足8位就前面自动补0
     * */
    public static String zeroComplement(String input){
        String result="00000000";
        if (input == null){
            return result;
        }
        if (input.length()<8){
            return result.substring(input.length())+input;
        }else {
            return "";
        }
    }
    /**
     * 获取字符对应的二进制
     * 不足8位的补齐8位
     * 以字符串形式返回字符串的二进制数
     * */
    public static String binaryOut(String input){
    StringBuilder stringBuilder=new StringBuilder();
        byte[] bytes=input.getBytes();
        for (int i = 0; i < bytes.length; i++) {
            stringBuilder.append(zeroComplement(Integer.toBinaryString(bytes[i])));
        }
        return stringBuilder.toString();
    }
}

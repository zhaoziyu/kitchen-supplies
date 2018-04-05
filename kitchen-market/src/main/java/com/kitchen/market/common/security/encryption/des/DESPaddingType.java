package com.kitchen.market.common.security.encryption.des;

/**
 * DES算法的填充方式
 * 因为DES是块加密，数据长度必须是8的倍数，然而实际上加密前的明文getBytes()后基本不会恰好是8的倍数，所以一般需要进行填充，只需要设置参数 PKCS5Padding ，JDK就帮你填充了，若不填充，且数据长度不是8倍数，则会抛异常；
 * 对于解密，一般来说加密的数据长度本身就是8的倍数，所以只需要NoPadding就可以了，若加密的数据长度不是8，就需要用PKCS5Padding，否则解密出来后的明文尾巴的会比原明文的尾巴多出好几位填充数据。
 *
 * NoPadding    —— API或算法本身不对数据进行处理，加密数据由加密双方约定填补算法。例如若对字符串数据进行加解密，可以补充\0或者空格，然后trim。
 * PKCS5Padding —— 加密前：数据字节长度对8取余，余数为m，若m>0,则补足8-m个字节，字节数值为8-m，即差几个字节就补几个字节，字节数值即为补充的字节数，若为0则补充8个字节的8
 *
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2017-09-07
 */
enum DESPaddingType {
    NoPadding("NoPadding"),
    PKCS5Padding("PKCS5Padding");

    private String name;

    DESPaddingType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}

package com.yanxiu.ce.common.yanxiuapi;

/**
 * @author richardzhao
 *进行字符操作的工具类
 */
public class CharacterSetToolkit {
	/**[tips]使用Java将中文字符转换成Unicode编码 
	这两天操作XML使用到了Jdom，在创建XML文件并输出到硬盘的时候遇到一个中文编码的问题：Jdom默认输出的XML编码是UTF-8，
	但是文档中如果出现中文字符那么该中文字符就会变成乱码，造成XML文件无法被正确解析。

	UTF-8应该是可以用来表示中文的吧？我不知道这是不是Jdom的一个BUG（Jdom 1.0，beta了10次的产物哦！）。
	一个比较好的解决办法是先把中文转换成Unicode编码在直接输出，程序解析XML后的时候再把Unicode编码转回中文就没有问题了。

	于是我查看了JDK的文档，截至Java 5好像都没有做类似转换的类可以直接使用，但是我发现一个类 java.util.Properties，
	它的源代码里有两个私有（private）方法 loadConvert (char[] in, int off, int len, char[] convtBuf) 和 s
	aveConvert(String theString, boolean escapeSpace) 其实就是做特殊字符和Unicode编码字符间转换的，我把它们
	提取出来，单独包装到一个类里就可以使用了。*/
	    
	    /** Creates a new instance of CharacterSetToolkit */
	    public CharacterSetToolkit() {
	    }
	    
	    private static final char[] hexDigit = {
	        '0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'
	    };
	    
	    private static char toHex(int nibble) {
	        return hexDigit[(nibble & 0xF)];
	    }
	    
	    /**
	     * 将字符串编码成 Unicode 。
	     * @param theString 待转换成Unicode编码的字符串。
	     * @param escapeSpace 是否忽略空格。
	     * @return 返回转换后Unicode编码的字符串。
	     */
	    public static String toUnicode(String theString, boolean escapeSpace) {
	        int len = theString.length();
	        int bufLen = len * 2;
	        if (bufLen < 0) {
	            bufLen = Integer.MAX_VALUE;
	        }
	        StringBuffer outBuffer = new StringBuffer(bufLen);

	        for(int x=0; x<len; x++) {
	            char aChar = theString.charAt(x);
	            // Handle common case first, selecting largest block that
	            // avoids the specials below
	            if ((aChar > 61) && (aChar < 127)) {
	                if (aChar == '\\') {
	                    outBuffer.append('\\'); outBuffer.append('\\');
	                    continue;
	                }
	                outBuffer.append(aChar);
	                continue;
	            }
	            switch(aChar) {
	                case ' ':
	                    if (x == 0 || escapeSpace) 
	                        outBuffer.append('\\');
	                    outBuffer.append(' ');
	                    break;
	                case '\t':outBuffer.append('\\'); outBuffer.append('t');
	                          break;
	                case '\n':outBuffer.append('\\'); outBuffer.append('n');
	                          break;
	                case '\r':outBuffer.append('\\'); outBuffer.append('r');
	                          break;
	                case '\f':outBuffer.append('\\'); outBuffer.append('f');
	                          break;
	                case '=': // Fall through
	                case ':': // Fall through
	                case '#': // Fall through
	                case '!':
	                    outBuffer.append('\\'); outBuffer.append(aChar);
	                    break;
	                default:
	                    if ((aChar < 0x0020) || (aChar > 0x007e)) {
	                        outBuffer.append('\\');
	                        outBuffer.append('u');
	                        outBuffer.append(toHex((aChar >> 12) & 0xF));
	                        outBuffer.append(toHex((aChar >>  8) & 0xF));
	                        outBuffer.append(toHex((aChar >>  4) & 0xF));
	                        outBuffer.append(toHex( aChar        & 0xF));
	                    } else {
	                        outBuffer.append(aChar);
	                    }
	            }
	        }
	        return outBuffer.toString();
	    }
	    
	    /**
	     * 从 Unicode 码转换成编码前的特殊字符串。
	     * @param in Unicode编码的字符数组。
	     * @param off 转换的起始偏移量。
	     * @param len 转换的字符长度。
	     * @param convtBuf 转换的缓存字符数组。
	     * @return 完成转换，返回编码前的特殊字符串。
	     */
	    public String fromUnicode(char[] in, int off, int len, char[] convtBuf) {
	        if (convtBuf.length < len) {
	            int newLen = len * 2;
	            if (newLen < 0) {
	                newLen = Integer.MAX_VALUE;
	            }
	            convtBuf = new char[newLen];
	        }
	        char aChar;
	        char[] out = convtBuf;
	        int outLen = 0;
	        int end = off + len;

	        while (off < end) {
	            aChar = in[off++];
	            if (aChar == '\\') {
	                aChar = in[off++];
	                if (aChar == 'u') {
	                    // Read the xxxx
	                    int value = 0;
	                    for (int i = 0; i < 4; i++) {
	                        aChar = in[off++];
	                        switch (aChar) {
	                        case '0':
	                        case '1':
	                        case '2':
	                        case '3':
	                        case '4':
	                        case '5':
	                        case '6':
	                        case '7':
	                        case '8':
	                        case '9':
	                            value = (value << 4) + aChar - '0';
	                            break;
	                        case 'a':
	                        case 'b':
	                        case 'c':
	                        case 'd':
	                        case 'e':
	                        case 'f':
	                            value = (value << 4) + 10 + aChar - 'a';
	                            break;
	                        case 'A':
	                        case 'B':
	                        case 'C':
	                        case 'D':
	                        case 'E':
	                        case 'F':
	                            value = (value << 4) + 10 + aChar - 'A';
	                            break;
	                        default:
	                            throw new IllegalArgumentException(
	                                    "Malformed \\uxxxx encoding.");
	                        }
	                    }
	                    out[outLen++] = (char) value;
	                } else {
	                    if (aChar == 't') {
	                        aChar = '\t';
	                    } else if (aChar == 'r') {
	                        aChar = '\r';
	                    } else if (aChar == 'n') {
	                        aChar = '\n';
	                    } else if (aChar == 'f') {
	                        aChar = '\f';
	                    }
	                    out[outLen++] = aChar;
	                }
	            } else {
	                out[outLen++] = (char) aChar;
	            }
	        }
	        return new String(out, 0, outLen);
	    }
	    
	    /**
		 * @param args
		 */
		/**
	     * 判断一个字符是Ascill字符还是其它字符（如汉，日，韩文字符）
	     * 
	     * @param char
	     *            c, 需要判断的字符
	     * @return boolean, 返回true,Ascill字符
	     */
	    public static boolean isLetter(char c) {
	        int k = 0x80;
	        return c / k == 0 ? true : false;
	    }
	    
	    /**
	     * 得到一个字符串的长度,显示的长度,一个汉字或日韩文长度为2,英文字符长度为1
	     * 
	     * @param String
	     *            s ,需要得到长度的字符串
	     * @return int, 得到的字符串长度
	     */
	    public static int length(String s) {
	        if (s == null)
	            return 0;
	        char[] c = s.toCharArray();
	        int len = 0;
	        for (int i = 0; i < c.length; i++) {
	            len++;
	            if (!isLetter(c[i])) {
	                len++;
	            }
	        }
	        //System.out.println("length is :"+len);
	        return len;
	    }
	    
	    /**
	     * 得到一个字符串的长度,显示的长度,一个汉字或日韩文长度为2,英文字符长度为1
	     * 
	     * @param String
	     *            s ,需要得到长度的字符串
	     * @return int, 得到的字符串长度
	     */
	    public static int _length(String s) {
	        if (s == null)
	            return 0;
	        
	        int len = 0;
	        try {
	        	len=s.getBytes("GBK").length;
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
	        return len;
	    }

	    /**
	     * 截取一段字符的长度,不区分中英文,如果数字不正好，则少取一个字符位
	     * 
	     * @author patriotlml
	     * @param String
	     *            origin, 原始字符串
	     * @param int
	     *            len, 截取长度(一个汉字长度按2算的)
	     * @return String, 返回的字符串
	     */
	    public static String substring(String origin, int len) {
	        if (origin == null || origin.equals("")||len<1)
	            return "";
	       
	        if (len > length(origin)){
	            return origin;}
	        
	        byte[] strByte = new byte[len];
	        System.arraycopy(origin.getBytes(), 0, strByte, 0, len);
	        int count = 0;
	        for (int i = 0; i < len; i++) {
	            int value = (int) strByte[i];
	            if (value < 0) {
	                count++;
	            }
	        }
	        if (count % 2 != 0) {
	            len = (len == 1) ? ++len : --len;
	        }
	        return new String(strByte, 0, len);
	    }
	}

	


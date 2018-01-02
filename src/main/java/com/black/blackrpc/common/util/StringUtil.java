package com.black.blackrpc.common.util;

public class StringUtil {

   /**
    * 判断字符串是否为空
    * @param str
    * @return
    */
	public static boolean isNotEmpty(String str){
		return str!=null&&!"".equals(str);
	}
	
    /**
     * .替换为/
     * @param name
     * @return
     * 例："com.original.web" -> "com/original/web"
     */
    public static String dotToSplash(String name) {
        return name.replaceAll("\\.", "/");
    }

    /**
     * 去除文件后缀
     * @param name
     * @return
     * 例："Apple.class" -> "Apple"
     */
    public static String trimExtension(String name) {
        int pos = name.indexOf('.');
        if (-1 != pos) {
            return name.substring(0, pos);
        }

        return name;
    }

    /**
     * /application/home -> /home
     * @param uri
     * @return
     */
    public static String trimURI(String uri) {
        String trimmed = uri.substring(1);
        int splashIndex = trimmed.indexOf('/');
        return trimmed.substring(splashIndex);
    }
    
    /**
     * 字符串连接
     * 例:String[] arr ={"1","2","3","4","5"} ->(3) = 123
     * @param arr
     * @param index
     * @param connectStr 连接值 
     * @return
     */
    public static String stringConnect(String[] arr,int index,String connectStr) {
    	if(index>=arr.length){
    		index=arr.length;
    	}
    	if(connectStr==null){
    		connectStr="";
    	}
    	String str="";
        for(int i=0;i<index;i++){
        	if(i==0){
        		str+=arr[i];
        	}else{
        		str+=connectStr+arr[i];
        	}    	
        }
        return str;
    }
 
    /**
     * char数组截取
     * @param arg0
     * @param arg1
     * @return
     */
    public static String arrSubSequence(char[] arr,int arg0,int arg1){
    	String str="";
    	for(int i=arg0;i<=arg1;i++){
    		str+=arr[i];
    	}
    	return str;
    }
    
    /**
     * 统计特定字符串里字符的个数
     * @param str
     * @param char_
     * @return
     */
    public static int charNum(String str,char char_){
    	if(str.indexOf(char_)==-1){
    		return 0;
    	}
    	char[] arr=str.toCharArray();
    	int sum =0;
    	for(char i:arr){
    		if(i==char_){
    			sum++;	
    		}
    	}
    	return sum;
    }
    
    /**
     * 生成多少个字符串
     * 例:("q",3,",") = q,q,q
     * @param str 需要生成的字符串
     * @param num 个数
     * @param connectStr 中间连接符号
     * @return
     */
    public static String makeNumStr(String str,int num,String connectStr){
    	if(num==0){
    		return "";
    	}
    	String str_="";
    	for(int i=0;i<num;i++){
    		if(i==0){
    			str_+=str;
    		}else{
    			str_+=connectStr+str;
    		}  		
    	}   	
    	return str_;
    }
}
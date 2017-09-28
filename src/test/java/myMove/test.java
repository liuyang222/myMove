package myMove;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String s = "xXccxxxXX";  
        // 从头开始查找是否存在指定的字符         //结果如下   
        System.out.println(s.indexOf("c"));     //2  
        // 从第四个字符位置开始往后继续查找，包含当前位置  
        System.out.println(s.indexOf("c", 3));  //3  
        //若指定字符串中没有该字符则系统返回-1  
        System.out.println(s.indexOf("y"));     //-1  
        System.out.println(s.lastIndexOf("x")); //6  
	}

}

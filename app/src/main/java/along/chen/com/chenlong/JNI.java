package along.chen.com.chenlong;

/**
 * Created by Chen on 2016/7/27.
 */
public class JNI {
    public JNI() {
        System.loadLibrary("hello");
    }
    public native String helloWorld();
    public native void callBackJava();
    public String hello() {
        return "I am from Java";
    }
}

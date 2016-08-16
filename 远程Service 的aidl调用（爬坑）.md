## 爬坑：
#### 1.调用方和被调用方aidl文件所在包的包名要相同
#### 2.调用方隐式调用远程Service在5.0以上的系统需要 设置包名
```
Intent in = new Intent("com.yjjc.cn.SERVICE");
if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
    in.setPackage("along.chen.com.chenlong");
}
bindService(in, mConnection, Context.BIND_AUTO_CREATE);
```


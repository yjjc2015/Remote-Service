package along.chen.com.chenlong;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.cl.cn.MyStub;

/**
 * Created by Chen on 2016/8/16.
 */
public class MyService extends Service {
    private String info = "i just wait";

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(MyService.this, "启动服务成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean bindService(Intent service, ServiceConnection conn, int flags) {
        Toast.makeText(MyService.this, "绑定服务成功", Toast.LENGTH_SHORT).show();
        return super.bindService(service, conn, flags);
    }

    @Override
    public void unbindService(ServiceConnection conn) {
        super.unbindService(conn);
        Toast.makeText(MyService.this, "解除服务绑定成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        info = intent.getStringExtra("info");
        Toast.makeText(MyService.this, "向服务传递值成功", Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(MyService.this, "服务被销毁", Toast.LENGTH_SHORT).show();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent pIntent) {
        return new MyBinder();
    }

    private class MyBinder extends MyStub.Stub {

        @Override
        public String getString() throws RemoteException {
            return info;
        }

        @Override
        public int calculate(int x, int y) throws RemoteException {
            return x * 3 + y * 2;
        }
    }
}

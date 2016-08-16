package along.chen.com.chenlong;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Chen on 2016/8/16.
 */
public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context pContext, Intent pIntent) {
        Toast.makeText(pContext, "我监听到了：别人发来的广播", Toast.LENGTH_LONG).show();
    }
}

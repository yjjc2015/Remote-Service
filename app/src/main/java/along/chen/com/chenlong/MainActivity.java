package along.chen.com.chenlong;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import along.chen.com.chenlong.databinding.ActivityMainBinding;

/**
 * Created by Chen on 2016/7/24.
 */
public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mBinding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mBinding.btnStartService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String info = mBinding.etVal.getText().toString();
                Intent intent = new Intent(MainActivity.this, MyService.class);
                intent.putExtra("info", info);
                startService(intent);
            }
        });
        mBinding.btnEndService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                stopService(new Intent(MainActivity.this, MyService.class));
            }
        });
    }
}

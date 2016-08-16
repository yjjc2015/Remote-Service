package along.chen.com.chenlong.rxjava;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import along.chen.com.chenlong.R;
import along.chen.com.chenlong.databinding.ActivityRxjavaBinding;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Chen on 2016/7/27.
 */
public class RxJavaActivity extends AppCompatActivity {
    private ActivityRxjavaBinding mBinding;
    private ObservableField<String> mTitle = new ObservableField<>("Hello my life: ");
    private Subscription mSubscription;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_rxjava);
        mBinding.setMTitle(mTitle);

        mBinding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rxjava1();
            }
        });
        mBinding.buttonUnScribed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mSubscription != null && isSubscribe(mSubscription)) {
                    mSubscription.unsubscribe();
                    isSubscribe(mSubscription);
                }
            }
        });
    }

    private void addToTitle(String s) {
        mTitle.set(mTitle.get() + "," + s);
    }

    private Observable<String> formatTitle(String s) {
        return Observable.just("<" + s + ">");
    }

    /**线程调度*/
    private void rxjava1() {
        mSubscription = Observable.fromCallable(new Callable<List<String>>() {
                    @Override
                    public List<String> call() throws Exception {
                        List<String> res = new ArrayList<String>();
                        Thread.sleep(2000);
                        res.add("111");
                        Thread.sleep(500);
                        res.add(null);
                        Thread.sleep(1000);
                        res.add("222");
                        Thread.sleep(500);
                        res.add(null);
                        Thread.sleep(3000);
                        res.add("333");
                        return res;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<List<String>, Observable<String>>() {
                    @Override
                    public Observable<String> call(List<String> strings) {
                        return Observable.from(strings);
                    }
                })
                .flatMap(new Func1<String, Observable<String>>() {
                    @Override
                    public Observable<String> call(String s) {
                        return formatTitle(s);
                    }
                })
                .doOnNext(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        addToTitle(s);
                    }
                })
                .filter(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {
                        return s!=null;
                    }
                })
                .take(2)
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Toast.makeText(RxJavaActivity.this, s, Toast.LENGTH_SHORT).show();
                    }
                });
        isSubscribe(mSubscription);
    }

    /**是否订阅*/
    private boolean isSubscribe(Subscription p_subscription) {
        boolean res = !p_subscription.isUnsubscribed();
        Toast.makeText(RxJavaActivity.this, res?"Subscribed" : "unSubscribed", Toast.LENGTH_LONG).show();
        return res;
    }
}

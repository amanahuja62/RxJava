package com.example.rx1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*

class MainActivity : AppCompatActivity() {
    private val TAG = "hello"
    lateinit var textView: TextView
    lateinit var observable : Observable<String>
    lateinit var observer: Observer<String>
    var disposable: Disposable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.textView)
        observable = Observable.just("Hello Rx Java","Gaurav","Pranav","Aman")
        observable.subscribeOn(Schedulers.io())
        observable.observeOn(AndroidSchedulers.mainThread())
        observable.subscribe(object : Observer<String>{
            override fun onSubscribe(d: Disposable?) {
               disposable = d
            }

            override fun onNext(t: String?) {
                textView.setText(t)
            }

            override fun onError(e: Throwable?) {
                Log.i(TAG,e.toString())
            }

            override fun onComplete() {
                Log.i(TAG,"Task has been completed")
            }

        })
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }
}
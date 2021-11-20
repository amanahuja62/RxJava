package com.example.rx1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.rx1.databinding.ActivityMainBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*

class MainActivity : AppCompatActivity() {
    private val TAG = "hello"
    private lateinit var binding : ActivityMainBinding
    lateinit var observable : Observable<String>
    lateinit var compositeDisposable: CompositeDisposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observable = Observable.just("Hello Rx Java","Gaurav","Pranav","Aman")
        observable.subscribeOn(Schedulers.io())
        observable.observeOn(AndroidSchedulers.mainThread())
        val observer1 = getDisposableObserver(binding.textView,)
        val observer2 = getDisposableObserver(binding.textView2)
        compositeDisposable = CompositeDisposable()
        compositeDisposable.add(observer1)
        compositeDisposable.add(observer2)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    private fun getDisposableObserver(textView: TextView) : DisposableObserver<String>{
        val obj = object : DisposableObserver<String>(){
            override fun onNext(t: String?) {
                textView.setText(t)
                Thread.sleep(1000)
            }

            override fun onError(e: Throwable?) {
                Log.i(TAG,e.toString())
            }

            override fun onComplete() {
                Log.i(TAG,"this task has been completed")
            }

        }
        observable.subscribe(obj)
        return obj
    }
}
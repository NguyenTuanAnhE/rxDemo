package com.rxandroid.anhnt.rxdemo.data.iterator;

import com.rxandroid.anhnt.rxdemo.Track;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class RxIterator {
    public RxIterator() {

    }

    public Observable<ArrayList<Track>> getObservable(String url) {
        return Observable.just(url).flatMap(new Function<String, ObservableSource<ArrayList<Track>>>() {
            @Override
            public ObservableSource<ArrayList<Track>> apply(String s) throws Exception {
                ArrayList<Track> tracks = new ArrayList<>();
                // TODO: 01/06/2018
                return Observable.just(tracks);
            }
        });
    }

    public void getTrack() {
        CompositeDisposable compositeDisposable = new CompositeDisposable();

        Disposable disposable = getObservable("Asd")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        // TODO: 01/06/2018 show dialog
                    }
                })
                .subscribe(new Consumer<ArrayList<Track>>() {
                    @Override
                    public void accept(ArrayList<Track> tracks) throws Exception {
                        // TODO: 01/06/2018 show track
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        // TODO: 01/06/2018 show error
                    }
                });

        compositeDisposable.add(disposable);
    }


}



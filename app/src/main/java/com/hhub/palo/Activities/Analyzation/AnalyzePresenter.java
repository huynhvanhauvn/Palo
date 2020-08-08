package com.hhub.palo.Activities.Analyzation;

import com.hhub.palo.Models.Table;
import com.hhub.palo.Services.APIService;
import com.hhub.palo.Services.Service;

import java.util.ArrayList;

import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AnalyzePresenter implements AnalyzeInterface {

    private AnalyzeView view;
    private Service service = APIService.getService();

    public AnalyzePresenter(AnalyzeView view) {
        this.view = view;
    }

    @Override
    public void getNationData() {
        Observable<ArrayList<Table>> observable = service.analyzeNation();
        observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<Table>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ArrayList<Table> tables) {
                        if (tables != null) {
                            float total = 0;
                            for (Table table : tables) {
                                total += Float.parseFloat(table.getValue());
                            }
                            view.showNationChart(tables, total);
                        }
                    }
                });
    }
}

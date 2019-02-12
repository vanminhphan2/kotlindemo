package com.example.rio.kottlin_demo.utils.rx;

import io.reactivex.Scheduler;

public interface SchedulerProvider {
    Scheduler computation();

    Scheduler io();

    Scheduler ui();
}

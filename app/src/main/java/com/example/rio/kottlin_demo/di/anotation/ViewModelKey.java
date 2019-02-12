package com.example.rio.kottlin_demo.di.anotation;

import android.arch.lifecycle.ViewModel;
import dagger.MapKey;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@MapKey
public @interface ViewModelKey {
    @SuppressWarnings("unused")
    Class<? extends ViewModel> value();
}
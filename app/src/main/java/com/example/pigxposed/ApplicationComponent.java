package com.example.pigxposed;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = AndroidModule.class)
public interface  ApplicationComponent {
    void inject(MainActivity mainActivity);
}

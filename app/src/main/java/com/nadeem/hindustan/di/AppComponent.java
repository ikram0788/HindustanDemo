package com.nadeem.hindustan.di;

import android.app.Application;
import com.nadeem.hindustan.HindustanApplication;
import com.nadeem.hindustan.ui.activities.BaseActivity;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

/**
 * Created by ikram on 7/03/2018.
 */
@Singleton
@Component(modules = {
        AppModule.class,
        AndroidInjectionModule.class,
        FragmentBuilderModule.class,
        ActivityBuilderModule.class})
public interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }

    void inject(HindustanApplication aaApp);
    void inject(BaseActivity baseActivity);

}

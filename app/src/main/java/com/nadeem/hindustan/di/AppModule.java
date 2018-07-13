package com.nadeem.hindustan.di;

import android.app.Application;
import com.nadeem.hindustan.HindustanApplication;
import com.nadeem.hindustan.database.AppDatabase;
import com.nadeem.hindustan.database.AppExecutors;
import com.nadeem.hindustan.utils.AppPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import okhttp3.Cache;

/**
 * Created by ikram on 7/03/2018.
 */
@Module(includes = ViewModelModule.class)
public class  AppModule {
    /*

        @Provides
        @Singleton
        OkHttpClient provideOkHttpClient() {
            OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
            okHttpClient.connectTimeout(ApiConstants.TIMEOUT_IN_SEC, TimeUnit.SECONDS);
            okHttpClient.readTimeout(ApiConstants.TIMEOUT_IN_SEC, TimeUnit.SECONDS);
            okHttpClient.addInterceptor(new RequestInterceptor());
            return okHttpClient.build();
        }

        @Provides
        @Singleton
        MovieDBService provideRetrofit(OkHttpClient okHttpClient) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ApiConstants.ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();

            return retrofit.create(MovieDBService.class);
        }

        @Provides
        @Singleton
        MovieDatabase provideMovieDatabase(Application application) {
            return Room.databaseBuilder(application, MovieDatabase.class, "aa.db").build();
        }

        @Provides
        @Singleton
        MovieDao provideMovieDao(MovieDatabase movieDatabase) {
            return movieDatabase.movieDao();
        }
    */
   /* @Provides
    @Singleton
    GuidetApplication getGuidetApplication(GuidetApplication application) {
        return application;
    }
    */
   @Provides
    @Singleton
   AppPreferences provideAppPreferences(HindustanApplication application) {
        return AppPreferences.getInstance();
    }

    @Provides
    @Singleton
    AppDatabase getDatabase(Application application, AppExecutors executors) {
        return AppDatabase.getInstance(application, executors);
    }

    @Provides
    @Singleton
    Cache provideOkHttpCache(Application application) {
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(application.getCacheDir(), cacheSize);
        return cache;
    }

    @Provides
    @Singleton
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }
    @Provides
    @Singleton
    AppExecutors provideAppExecutors() {
        return new AppExecutors();
    }
}

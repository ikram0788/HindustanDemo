package com.nadeem.hindustan.interfaces;

import android.view.View;

/**
 * Created by ikram on 19/2/18.
 */

public interface ViewListner {
    void onViewClick(View view);
    void sendData(View view,String type, Object data);
    void setLoading(boolean enableLoading);
}

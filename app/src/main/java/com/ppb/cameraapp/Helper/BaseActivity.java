package com.ppb.cameraapp.Helper;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import cn.pedant.SweetAlert.SweetAlertDialog;
import com.ppb.cameraapp.Retrofit.ApiServer;
import retrofit2.Retrofit;

public class BaseActivity extends AppCompatActivity {
    private SweetAlertDialog dialogProgress, dialogWarning, dialogError, dialogInfo;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Retrofit retrofit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        retrofit = ApiServer.builder();
    }

    @Override
    protected void onDestroy() {
        hideError();
        hideWarning();
        hideProgress();
        hideInfo();
        super.onDestroy();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    public void hideProgress(){
        if (dialogProgress!=null && dialogProgress.isShowing())
            dialogProgress.dismiss();
    }

    public void hideWarning(){
        if (dialogWarning!=null && dialogWarning.isShowing())
            dialogWarning.dismiss();
    }

    public void showInfo(String titleText, @Nullable String contentText){
        hideInfo();
        dialogInfo = new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE);
        dialogInfo.setTitleText(titleText);
        if (contentText!=null)
            dialogInfo.setContentText(contentText);
        dialogInfo.show();
    }

    public void hideInfo(){
        if (dialogInfo!=null && dialogInfo.isShowing())
            dialogInfo.dismiss();
    }

    public void showError(String titleText, @Nullable String contentText){
        hideError();
        dialogError = new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE);
        dialogError.setTitleText(titleText);
        if (contentText!=null)
            dialogError.setContentText(contentText);
        dialogError.show();
    }

    public void hideError(){
        if (dialogError!=null && dialogError.isShowing())
            dialogError.dismiss();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}

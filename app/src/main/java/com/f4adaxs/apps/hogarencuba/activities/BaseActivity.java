package com.f4adaxs.apps.hogarencuba.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.f4adaxs.apps.hogarencuba.R;
import com.f4adaxs.apps.hogarencuba.config.AppConfig;
import com.f4adaxs.apps.hogarencuba.util.CodeType;
import com.f4adaxs.apps.hogarencuba.util.DialogHelper;

import java.io.File;


/**
 * Created by rigo on 9/15/17.
 */

public class BaseActivity extends AppCompatActivity {
    protected ProgressDialog mProgress;
    protected AlertDialog alert;
    protected String externalDirPhotosPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.initExternalDirectory();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        this.hideDialog();
    }

    private SharedPreferences getSharedPreferences() {
        return this.getSharedPreferences(AppConfig.PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
    }

    public String getPreferenceByName(String name) {
        SharedPreferences sharedPreferences = this.getSharedPreferences();
        return sharedPreferences.getString(name, null);
    }

    public boolean getBooleanPreferenceByName(String name) {
        SharedPreferences sharedPreferences = this.getSharedPreferences();
        return sharedPreferences.getBoolean(name, false);
    }

    public void setPreferenceByNameValue(String name, String value) {
        SharedPreferences sharedPreferences = this.getSharedPreferences();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(name, value);
        editor.commit();
    }

    public void showSnackbar(View view, String text, final SnackbarDismissedListener listener) {
        Snackbar snackbar = Snackbar.make(view, text, Snackbar.LENGTH_LONG);
        snackbar.addCallback(new Snackbar.Callback(){
            @Override
            public void onDismissed(Snackbar transientBottomBar, @DismissEvent int event) {
                if(listener != null) {
                    listener.onDismissed();
                }
            }
        });
        snackbar.show();
    }

    public interface SnackbarDismissedListener {
        void onDismissed();
    }

    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private void setTaskBarColored() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            Window w = this.getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            int statusBarHeight = getStatusBarHeight();

            View view = new View(this);
            view.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            view.getLayoutParams().height = statusBarHeight;
            ((ViewGroup) w.getDecorView()).addView(view);
            view.setBackgroundResource(R.color.drawerColorPrimary);
        }
    }

    protected void createNetErrorDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Necesitas coneccion para el uso de esta aplicacion. Por favor activa los datos mobiles o la wifi en preferencias del dispositivo.")
                .setTitle("Imposible conectarse")
                .setCancelable(false)
                .setPositiveButton("Preferencias", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(Settings.ACTION_SETTINGS);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    protected void confirmDialog(String title, String message, final OnConfirmDialogResultListener listener) {
        this.confirmDialogCustom(title, message, listener, "Si", "No");
    }

    protected void confirmDialogCustom(String title, String message, final OnConfirmDialogResultListener listener, String positiveLabel, String negativeLabel) {
        this.hideDialog();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(title);
        builder.setMessage(message);

        builder.setPositiveButton(positiveLabel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog
                if(listener != null) {
                    listener.onConfirmDialogResult(true);
                }
                dialog.dismiss();
            }
        });

        builder.setNegativeButton(negativeLabel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
                if(listener != null) {
                    listener.onConfirmDialogResult(false);
                }
                dialog.dismiss();
            }
        });

        this.alert = builder.create();
        this.alert.show();
    }

    private void hideDialog() {
        if(this.alert != null) {
            this.alert.dismiss();
            this.alert = null;
        }
    }

    public interface OnConfirmDialogResultListener {
        void onConfirmDialogResult(boolean result);
    }

//    public void animateActivity(View view) {
//        Animation animFadein = AnimationUtils.loadAnimation(this, R.anim.fab_scale_up);
//        view.startAnimation(animFadein);
//    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
    }

    /**
     * Overrides the pending Activity transition by performing the "Enter" animation.
     */
//    protected void overridePendingTransitionEnter() {
//        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
//    }

    /**
     * Overrides the pending Activity transition by performing the "Exit" animation.
     */
//    protected void overridePendingTransitionExit() {
//        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
//    }

    protected boolean needRequestRuntimePermissions() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    public void processRequestError(String data) {
        String msg = data;
        int error = CodeType.getError(data);
        if(error >= 0) {
            msg = getString(error);
        }

        if(!data.equals(CodeType.error_token_expired)) {
            DialogHelper.showErrorDialog(this, R.string.title_error, msg, null);
        }
    }

    public void showProgressDialog(DialogInterface.OnCancelListener onCancelListener) {
        if(this.mProgress == null) {
            this.mProgress = new ProgressDialog(this);
            this.mProgress.setCancelable(false);
            this.mProgress.setIndeterminate(false);
            this.mProgress.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    if(onCancelListener != null) {
                        onCancelListener.onCancel(dialog);
                    }
                }
            });
            this.mProgress.show();
            this.mProgress.setContentView(R.layout.default_progress);
        }
    }

    public void hideProgressDialog() {
        if (this.mProgress != null) {
            this.mProgress.dismiss();
            this.mProgress = null;
        }
    }

    protected void initExternalDirectory() {
        String externalDir = "";
        File externalFilesDir = getExternalFilesDir(null);
        if (externalFilesDir != null) {
            externalDir = externalFilesDir.toString();
        }
        File file = new File(externalDir + AppConfig.PHOTO_PATH);
        if (!file.exists()) {
            file.mkdirs();
        }
        this.externalDirPhotosPath = externalDir + AppConfig.PHOTO_PATH;
    }
}

package com.f4adaxs.apps.hogarencuba.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.f4adaxs.apps.hogarencuba.R;


/**
 * Created by rigo on 2/4/17.
 */

public class DialogHelper {
    public static void showErrorDialog(Context context, int title, int message, final DialogInterface.OnClickListener onClickPositiveListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.action_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (onClickPositiveListener != null) {
                    onClickPositiveListener.onClick(dialogInterface, i);
                }
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    public static void showErrorDialog(Context context, int title, String message, final DialogInterface.OnClickListener onClickPositiveListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.action_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (onClickPositiveListener != null) {
                    onClickPositiveListener.onClick(dialogInterface, i);
                }
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }
}

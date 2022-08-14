package com.tuanna21.mockproject_tuanna21.receiver;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.tuanna21.mockproject_tuanna21.player.MyPlayerController;

public class ChargerReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        if (MyPlayerController.getInstance().isPlaying()) {
            createDialog(context);
        }
    }

    private void createDialog(Context context) {
        Intent intent = new Intent(context,ChargingDialogActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}

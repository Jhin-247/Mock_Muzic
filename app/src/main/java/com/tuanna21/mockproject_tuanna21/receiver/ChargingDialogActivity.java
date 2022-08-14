package com.tuanna21.mockproject_tuanna21.receiver;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.tuanna21.mockproject_tuanna21.player.MyPlayerController;

public class ChargingDialogActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this)
                .setTitle("Phát hiện cắm sạc")
                .setMessage("Bạn có muốn dừng nghe để bảo vệ pin?")
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    MyPlayerController.getInstance().clearControllerObservers();
                    finish();
                })
                .setNegativeButton(android.R.string.no, (dialog, which) -> finish())
                .setIcon(android.R.drawable.ic_dialog_alert);
        AlertDialog mDialog = mBuilder.create();
        mDialog.show();
    }
}

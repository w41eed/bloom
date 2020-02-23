package com.bloom;

import android.support.v7.app.AppCompatDialogFragment;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

public class TimerCancelDialog extends AppCompatDialogFragment {
    private TimerCancelDialogListener listener;
    @Override

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder warning_builder = new AlertDialog.Builder(getActivity());
        warning_builder.setTitle("Are You Sure to Cancel Timer?")
                .setMessage("There will be a withered flower in your garden")
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.ClickYes();
                    }
                });
        return warning_builder.create();
    }
    public interface TimerCancelDialogListener{
        void ClickYes();
    }

    @Override
    public void onAttach(Context context) { // if forget to implement the dialog in main activity, throw this exception
        super.onAttach(context);
        try{
            listener = (TimerCancelDialogListener)context;
        } catch (ClassCastException ex){
            throw new ClassCastException(context.toString()+"You have to implement TimerCancelDialogListener");
        }
    }
}

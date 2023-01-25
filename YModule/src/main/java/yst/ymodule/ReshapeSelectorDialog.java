package yst.ymodule;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;

import ir.yst.ymodule.R;


public class ReshapeSelectorDialog extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dlg_reshape_selector);
        TextView lblNoReshape = findViewById(R.id.lblNoReshape);
        TextView lblReshape = findViewById(R.id.lblReshape);
        TextView lblReshapeReverse = findViewById(R.id.lblReshapeReverse);

        lblNoReshape.setText(getString(R.string.reshape_msg));
        lblReshape.setText(PersianReshape.reshapeBase(getString(R.string.reshape_msg)));
        lblReshapeReverse.setText(PersianReshape.reshape_reverse(getString(R.string.reshape_msg)));
        lblNoReshape.setTypeface(UIHelper.getTypeFace(this));
        lblReshape.setTypeface(UIHelper.getTypeFace(this));
        lblReshapeReverse.setTypeface(UIHelper.getTypeFace(this));


        lblNoReshape.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                PersianReshape.Reshape_Type_Index = 0;
                SharedPrefrencesHelper.setIntPref(getApplicationContext(), "ReshapeIDx", PersianReshape.Reshape_Type_Index);
                setResult(130901);
                finish();
            }
        });

        lblReshape.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                PersianReshape.Reshape_Type_Index = 1;
                SharedPrefrencesHelper.setIntPref(getApplicationContext(), "ReshapeIDx", PersianReshape.Reshape_Type_Index);
                setResult(130901);
                finish();
            }
        });
        lblReshapeReverse.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                PersianReshape.Reshape_Type_Index = 2;
                SharedPrefrencesHelper.setIntPref(getApplicationContext(), "ReshapeIDx", PersianReshape.Reshape_Type_Index);
                setResult(130901);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
    }
}

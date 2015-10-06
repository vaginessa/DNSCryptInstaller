package io.github.otakuchiyan.dnscryptinstaller;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.math.BigInteger;

import io.github.otakuchiyan.dnscryptinstaller.BinaryManager;

public class InstallActivity extends Activity {
    private static final int BINARY_SELECT_CODE = 123;
    private static final int LIBRARY_SELECT_CODE = 234;
    private EditText binPathEdit;
    private EditText libPathEdit;
    private String binPath = "";
    private String libPath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setTitle(R.string.action_install);
        getActionBar().setDisplayHomeAsUpEnabled(true);


        setContentView(R.layout.activity_install);
    }

    @Override
    protected void onActivityResult(int reqCode, int resCode, Intent data){
        binPathEdit = (EditText) findViewById(R.id.bin_path_Edit);
        libPathEdit = (EditText) findViewById(R.id.lib_path_Edit);

        switch (reqCode){
            case BINARY_SELECT_CODE:
                if(resCode == RESULT_OK){
                    binPath = data.getData().getPath();
                    binPathEdit.setText(binPath);
                }
                break;
            case LIBRARY_SELECT_CODE:
                if(resCode == RESULT_OK){
                    libPath = data.getData().getPath();
                    libPathEdit.setText(libPath);
                }
                break;
        }
        super.onActivityResult(reqCode, resCode, data);
    }

    public void onInstallClick(View v){
        BinaryManager bm = new BinaryManager();
        binPathEdit = (EditText) findViewById(R.id.bin_path_Edit);
        libPathEdit = (EditText) findViewById(R.id.lib_path_Edit);
        if(binPathEdit.getText().toString().equals("")){
            binPathEdit.setError("");
            return;
        }
        if(libPathEdit.getText().toString().equals("")){
            libPathEdit.setError("");
            return;
        }

        bm.install(this, binPath, libPath);
    }

    public void onBinaryClick(View v){
        onChooseFileBtn(v, false);
    }

    public void onLibraryClick(View v){
        onChooseFileBtn(v, true);
    }

    private void onChooseFileBtn(View v, boolean isLib){
        int code;
        String hint = "";
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.setDataAndType(Uri.parse(Environment.getExternalStorageDirectory().getPath()), "application/octet-stream");
        i.addCategory(Intent.CATEGORY_OPENABLE);
        if(isLib){
            code = LIBRARY_SELECT_CODE;
        }else{
            code = BINARY_SELECT_CODE;
        }
        startActivityForResult(Intent.createChooser(i, hint), code);
    }
}

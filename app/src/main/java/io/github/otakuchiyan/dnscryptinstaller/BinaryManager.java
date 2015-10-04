package io.github.otakuchiyan.dnscryptinstaller;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import eu.chainfire.libsuperuser.Shell;

/**
 * Created by yugen on 10/4/15.
 */
public class BinaryManager {
    private static String binPath;
    private static String libPath;
    private static String pkgPath;
    private static List<String> installCmds = new ArrayList<String>();

    public void install(Context c, String bin, String lib){
        binPath = bin;
        libPath = lib;
        try {
            pkgPath = c.getPackageManager().getPackageInfo(c.getPackageName(), 0).applicationInfo.dataDir;
        }catch (Exception e){
            Log.w("DNSCrpyt Installer", "{BinaryManager} Package name not found.", e);
        }
        installCmds.add("cp " + bin + " " + pkgPath);
        installCmds.add("cp " + lib + " " + pkgPath + "/lib");
        (new installTask()).execute();
    }

    private class installTask extends AsyncTask<Void, Void, Void>{
        @Override
        public Void doInBackground(Void[] p1){
            Shell.SU.run(installCmds);
            return null;
        }
    }

}

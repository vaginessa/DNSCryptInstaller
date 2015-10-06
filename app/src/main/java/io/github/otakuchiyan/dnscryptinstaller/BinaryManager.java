package io.github.otakuchiyan.dnscryptinstaller;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
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
    private static List<String> startCmds = new ArrayList<String>();
    private static SharedPreferences sp;

    public boolean install(Context c, String bin, String lib){
        binPath = bin;
        libPath = lib;
        try {
            pkgPath = c.getPackageManager().getPackageInfo(c.getPackageName(), 0).applicationInfo.dataDir;
        }catch (Exception e){
            Log.w("DNSCrpyt Installer", "{BinaryManager} Package name not found.", e);
        }
        installCmds.add("cp " + bin + " " + pkgPath);
        installCmds.add("cp " + lib + " " + pkgPath + "/lib");
        installCmds.add("chmod 755 " + binPath);
        installCmds.add("chmod 755 " + libPath);
        (new runCmdsTask()).execute(installCmds.toString());
        return true;
    }

    public void start(Context c){
        sp = PreferenceManager.getDefaultSharedPreferences(c.getApplicationContext());
        startCmds.add("export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:" + libPath);
        startCmds.add(binPath + " -a " + sp.getString("localAddr", ""));
        (new runCmdsTask()).execute(startCmds.get(0), startCmds.get(1));
    }

    private class runCmdsTask extends AsyncTask<String, Void, Void>{
        @Override
        public Void doInBackground(String... cmds){
            Shell.SU.run(cmds);
            return null;
        }
    }



}

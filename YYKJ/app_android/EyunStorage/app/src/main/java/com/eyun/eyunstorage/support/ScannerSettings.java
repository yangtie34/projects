package com.eyun.eyunstorage.support;

import android.device.ScanManager;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.widget.Toast;

import com.eyun.eyunstorage.R;

public class ScannerSettings extends PreferenceActivity {
    
    private static final int TYPE_SOUNDS = 1;
    private static final int TYPE_VIBRATE = 2;
    private static final int TYPE_ENTER = 3;
    private static final int BROADCAST_OUTPUT_MODE = 0;
    private static final int KEYBOARD_OUTPUT_MODE = 1;

    private static final String KEY_OPRN_SCAN = "open_scanner";
    private static final String KEY_SCAN_LOCK = "lock_scan_key";
    private static final String KEY_KEYBOARD_MODE = "scanner_keyboard_output";
    private static final String KEY_SCAN_SOUNDS = "scanner_beep";
    private static final String KEY_SCAN_VIBRATE = "scanner_vibrate";
    private static final String KEY_SCAN_ENTER = "scanner_enter";
    private static final String KEY_RESET_SCAN = "reset_def";
    private CheckBoxPreference mScanner;
    private CheckBoxPreference mScanKey;
    private CheckBoxPreference mScanOutput;
    private CheckBoxPreference mScanSounds;
    private CheckBoxPreference mScanVibrate;
    private CheckBoxPreference mScanEnter;
    private Preference mResetScan;
    PreferenceScreen root;
    
    private ScanManager mScanManager;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        root = this.getPreferenceScreen();
        if (root != null) {
            root.removeAll();
        }
        addPreferencesFromResource(R.xml.scanner_settings);
        root = this.getPreferenceScreen();
        mScanner = (CheckBoxPreference) getPreferenceScreen().findPreference(KEY_OPRN_SCAN);
        mScanKey = (CheckBoxPreference) getPreferenceScreen().findPreference(KEY_SCAN_LOCK);
        mScanOutput = (CheckBoxPreference) getPreferenceScreen().findPreference(KEY_KEYBOARD_MODE);
        mScanSounds = (CheckBoxPreference) getPreferenceScreen().findPreference(KEY_SCAN_SOUNDS);
        mScanVibrate = (CheckBoxPreference) getPreferenceScreen().findPreference(KEY_SCAN_VIBRATE);
        mScanEnter = (CheckBoxPreference) getPreferenceScreen().findPreference(KEY_SCAN_ENTER);
        mResetScan = (Preference) getPreferenceScreen().findPreference(KEY_RESET_SCAN);
    }
    
    private void updateState() {
        if(mScanManager == null) return;
        int type = mScanManager.getScannerType();
        if(type == 3) {
            if (root != null) {
                root.removePreference(mScanKey);
            }
        }
        if(type == 4) {
            if (root != null) {
                root.removePreference(mResetScan);
            }
        }
        
        mScanner.setChecked(mScanManager.getScannerState() ? true : false);
        mScanKey.setChecked(mScanManager.getTriggerLockState() ? false : true);
        
        mScanOutput.setChecked(mScanManager.getOutputMode() == 1);
        mScanSounds.setChecked(mScanManager.getOutputParameter(TYPE_SOUNDS) == 1);
        mScanVibrate.setChecked(mScanManager.getOutputParameter(TYPE_VIBRATE) == 1);
        mScanEnter.setChecked(mScanManager.getOutputParameter(TYPE_ENTER) == 1);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        mScanManager = null;
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        mScanManager = new ScanManager();
        updateState();
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        // TODO Auto-generated method stub
        if(preference == mScanner) {
            if(mScanner.isChecked()) {
                mScanManager.openScanner();
            } else {
                mScanManager.closeScanner();
            }
        } else if (preference == mScanKey){
            if(mScanKey.isChecked()) {
                mScanManager.unlockTriggler();
            } else {
                mScanManager.lockTriggler();
            }
        } else if (preference == mScanOutput){
            mScanManager.switchOutputMode(mScanOutput.isChecked() ? KEYBOARD_OUTPUT_MODE : BROADCAST_OUTPUT_MODE);

        } else if (preference == mScanSounds){
            mScanManager.setOutputParameter(TYPE_SOUNDS, mScanSounds.isChecked() ? 1 : 0);
                
        } else if (preference == mScanVibrate){
            if(mScanVibrate.isChecked()) {
                mScanManager.setOutputParameter(TYPE_VIBRATE, 1);
            } else {
                mScanManager.setOutputParameter(TYPE_VIBRATE, 0);
            }
        } else if (preference == mScanEnter){
            if(mScanEnter.isChecked()) {
                mScanManager.setOutputParameter(TYPE_ENTER, 1);
            } else {
                mScanManager.setOutputParameter(TYPE_ENTER, 0);
            }
        } else if (preference == mResetScan){
            
            mScanManager.resetScannerParameters();
            Toast.makeText(ScannerSettings.this,
                    R.string.scanner_toast, Toast.LENGTH_LONG).show();
        }
        
        return true;
    }

}

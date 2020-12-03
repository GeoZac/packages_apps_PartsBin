/*
* Copyright (C) 2018 The OmniROM Project
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 2 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program. If not, see <http://www.gnu.org/licenses/>.
*
*/
package com.aicp.device;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.preference.PreferenceFragment;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceManager;
import androidx.preference.PreferenceScreen;
import androidx.preference.TwoStatePreference;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.util.Log;

public class PanelSettings extends PreferenceFragment implements RadioGroup.OnCheckedChangeListener {
    private RadioGroup mRadioGroup;
    private Context mContext;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getContext();
        mRadioGroup = (RadioGroup) view.findViewById(R.id.radio_group);
        updateRadioButtonState(view.findViewById(R.id.dci_mode), DCIModeSwitch.isSupported(mContext));
        updateRadioButtonState(view.findViewById(R.id.srgb_mode), SRGBModeSwitch.isSupported(mContext));
        updateRadioButtonState(view.findViewById(R.id.wide_mode), WideModeSwitch.isSupported(mContext));
        updateRadioButtonState(view.findViewById(R.id.oneplus_mode), OnePlusModeSwitch.isSupported(mContext));
        updateRadioButtonState(view.findViewById(R.id.night_mode), NightModeSwitch.isSupported(mContext));

        int checkedButtonId = R.id.off_mode;
        if (WideModeSwitch.isCurrentlyEnabled(mContext)) {
            checkedButtonId = R.id.wide_mode;
        } else if (DCIModeSwitch.isCurrentlyEnabled(mContext)) {
            checkedButtonId = R.id.dci_mode;
        } else if (SRGBModeSwitch.isCurrentlyEnabled(mContext)) {
            checkedButtonId = R.id.srgb_mode;
        } else if (OnePlusModeSwitch.isCurrentlyEnabled(mContext)) {
            checkedButtonId = R.id.oneplus_mode;
        } else if (NightModeSwitch.isCurrentlyEnabled(mContext)) {
            checkedButtonId = R.id.night_mode;
        }
        mRadioGroup.check(checkedButtonId);
        mRadioGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.panel_modes, container, false);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.srgb_mode) {
            Utils.writeValue(NightModeSwitch.getFile(mContext), "0");
            Settings.System.putInt(getContext().getContentResolver(), NightModeSwitch.SETTINGS_KEY, 0);
            Utils.writeValue(DCIModeSwitch.getFile(mContext), "0");
            Settings.System.putInt(getContext().getContentResolver(), DCIModeSwitch.SETTINGS_KEY, 0);
            Utils.writeValue(WideModeSwitch.getFile(mContext), "0");
            Settings.System.putInt(getContext().getContentResolver(), WideModeSwitch.SETTINGS_KEY, 0);
            Utils.writeValue(OnePlusModeSwitch.getFile(mContext), "0");
            Settings.System.putInt(getContext().getContentResolver(), OnePlusModeSwitch.SETTINGS_KEY, 0);
            Utils.writeValue(SRGBModeSwitch.getFile(mContext), "1");
            Settings.System.putInt(getContext().getContentResolver(), SRGBModeSwitch.SETTINGS_KEY, 1);
        } else if (checkedId == R.id.dci_mode) {
            Utils.writeValue(NightModeSwitch.getFile(mContext), "0");
            Settings.System.putInt(getContext().getContentResolver(), NightModeSwitch.SETTINGS_KEY, 0);
            Utils.writeValue(DCIModeSwitch.getFile(mContext), "1");
            Settings.System.putInt(getContext().getContentResolver(), DCIModeSwitch.SETTINGS_KEY, 1);
            Utils.writeValue(WideModeSwitch.getFile(mContext), "0");
            Settings.System.putInt(getContext().getContentResolver(), WideModeSwitch.SETTINGS_KEY, 0);
            Utils.writeValue(OnePlusModeSwitch.getFile(mContext), "0");
            Settings.System.putInt(getContext().getContentResolver(), OnePlusModeSwitch.SETTINGS_KEY, 0);
            Utils.writeValue(SRGBModeSwitch.getFile(mContext), "0");
            Settings.System.putInt(getContext().getContentResolver(), SRGBModeSwitch.SETTINGS_KEY, 0);
        } else if (checkedId == R.id.wide_mode) {
            Utils.writeValue(NightModeSwitch.getFile(mContext), "0");
            Settings.System.putInt(getContext().getContentResolver(), NightModeSwitch.SETTINGS_KEY, 0);
            Utils.writeValue(DCIModeSwitch.getFile(mContext), "0");
            Settings.System.putInt(getContext().getContentResolver(), DCIModeSwitch.SETTINGS_KEY, 0);
            Utils.writeValue(WideModeSwitch.getFile(mContext), "1");
            Settings.System.putInt(getContext().getContentResolver(), WideModeSwitch.SETTINGS_KEY, 1);
            Utils.writeValue(OnePlusModeSwitch.getFile(mContext), "0");
            Settings.System.putInt(getContext().getContentResolver(), OnePlusModeSwitch.SETTINGS_KEY, 0);
            Utils.writeValue(SRGBModeSwitch.getFile(mContext), "0");
            Settings.System.putInt(getContext().getContentResolver(), SRGBModeSwitch.SETTINGS_KEY, 0);
        } else if (checkedId == R.id.night_mode) {
            Utils.writeValue(NightModeSwitch.getFile(mContext), "1");
            Settings.System.putInt(getContext().getContentResolver(), NightModeSwitch.SETTINGS_KEY, 1);
            Utils.writeValue(DCIModeSwitch.getFile(mContext), "0");
            Settings.System.putInt(getContext().getContentResolver(), DCIModeSwitch.SETTINGS_KEY, 0);
            Utils.writeValue(WideModeSwitch.getFile(mContext), "0");
            Settings.System.putInt(getContext().getContentResolver(), WideModeSwitch.SETTINGS_KEY, 0);
            Utils.writeValue(OnePlusModeSwitch.getFile(mContext), "0");
            Settings.System.putInt(getContext().getContentResolver(), OnePlusModeSwitch.SETTINGS_KEY, 0);
            Utils.writeValue(SRGBModeSwitch.getFile(mContext), "0");
            Settings.System.putInt(getContext().getContentResolver(), SRGBModeSwitch.SETTINGS_KEY, 0);
        } else if (checkedId == R.id.oneplus_mode) {
            Utils.writeValue(NightModeSwitch.getFile(mContext), "0");
            Settings.System.putInt(getContext().getContentResolver(), NightModeSwitch.SETTINGS_KEY, 0);
            Utils.writeValue(DCIModeSwitch.getFile(mContext), "0");
            Settings.System.putInt(getContext().getContentResolver(), DCIModeSwitch.SETTINGS_KEY, 0);
            Utils.writeValue(WideModeSwitch.getFile(mContext), "0");
            Settings.System.putInt(getContext().getContentResolver(), WideModeSwitch.SETTINGS_KEY, 0);
            Utils.writeValue(OnePlusModeSwitch.getFile(mContext), "1");
            Settings.System.putInt(getContext().getContentResolver(), OnePlusModeSwitch.SETTINGS_KEY, 1);
            Utils.writeValue(SRGBModeSwitch.getFile(mContext), "0");
            Settings.System.putInt(getContext().getContentResolver(), SRGBModeSwitch.SETTINGS_KEY, 0);
        } else if (checkedId == R.id.off_mode) {
            Utils.writeValue(NightModeSwitch.getFile(mContext), "0");
            Settings.System.putInt(getContext().getContentResolver(), NightModeSwitch.SETTINGS_KEY, 0);
            Utils.writeValue(DCIModeSwitch.getFile(mContext), "0");
            Settings.System.putInt(getContext().getContentResolver(), DCIModeSwitch.SETTINGS_KEY, 0);
            Utils.writeValue(WideModeSwitch.getFile(mContext), "0");
            Settings.System.putInt(getContext().getContentResolver(), WideModeSwitch.SETTINGS_KEY, 0);
            Utils.writeValue(OnePlusModeSwitch.getFile(mContext), "0");
            Settings.System.putInt(getContext().getContentResolver(), OnePlusModeSwitch.SETTINGS_KEY, 0);
            Utils.writeValue(SRGBModeSwitch.getFile(mContext), "0");
            Settings.System.putInt(getContext().getContentResolver(), SRGBModeSwitch.SETTINGS_KEY, 0);
        }
    }

    private void updateRadioButtonState(RadioButton button, boolean isSupported) {
        if (isSupported) {
            button.setVisibility(View.VISIBLE);
        } else {
            button.setVisibility(View.GONE);
        }
    }
}

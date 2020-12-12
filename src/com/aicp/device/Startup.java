/*
* Copyright (C) 2013 The OmniROM Project
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

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import androidx.preference.PreferenceManager;
import android.provider.Settings;
import android.text.TextUtils;

public class Startup extends BroadcastReceiver {
    private static void restore(String file, boolean enabled) {
        if (file == null) {
            return;
        }
        Utils.writeValue(file, enabled ? "1" : "0");
    }

    private static void restore(String file, String value) {
        if (file == null) {
            return;
        }
        Utils.writeValue(file, value);
    }

    private static void restoreDual(String file, String value) {
        if (file == null) {
            return;
        }
        Utils.writeValueDual(file, value);
    }

    private static String getGestureFile(String key) {
        return GestureSettings.getGestureFile(key);
    }

    private void maybeImportOldSettings(Context context) {
        ContentResolver resolver = context.getContentResolver();
        boolean imported = Settings.System.getInt(resolver, "omni_device_setting_imported", 0) != 0;
        if (!imported) {
            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
            boolean enabled = sharedPrefs.getBoolean(DeviceSettings.KEY_SRGB_SWITCH, false);
            Settings.System.putInt(resolver, SRGBModeSwitch.SETTINGS_KEY, enabled ? 1 : 0);

            enabled = sharedPrefs.getBoolean(DeviceSettings.KEY_ADOBERGB_SWITCH, false);
            Settings.System.putInt(resolver, AdobeRGBModeSwitch.SETTINGS_KEY, enabled ? 1 : 0);

            enabled = sharedPrefs.getBoolean(DeviceSettings.KEY_HBM_SWITCH, false);
            Settings.System.putInt(resolver, HBMModeSwitch.SETTINGS_KEY, enabled ? 1 : 0);

            enabled = sharedPrefs.getBoolean(DeviceSettings.KEY_STAP_SWITCH, false);
            Settings.System.putInt(resolver, SingleTapSwitch.SETTINGS_KEY, enabled ? 1 : 0);

            enabled = sharedPrefs.getBoolean(DeviceSettings.KEY_FASTCHARGE_SWITCH, false);
            Settings.System.putInt(resolver, FastChargeSwitch.SETTINGS_KEY, enabled ? 1 : 0);

            enabled = sharedPrefs.getBoolean(DeviceSettings.KEY_DT2W_SWITCH, false);
            Settings.System.putInt(resolver, DoubleTapToWakeSwitch.SETTINGS_KEY, enabled ? 1 : 0);

            enabled = sharedPrefs.getBoolean(DeviceSettings.KEY_S2S_SWITCH, false);
            Settings.System.putInt(resolver, SweepToSleepSwitch.SETTINGS_KEY, enabled ? 1 : 0);

            enabled = sharedPrefs.getBoolean(DeviceSettings.KEY_S2W_SWITCH, false);
            Settings.System.putInt(resolver, SweepToWakeSwitch.SETTINGS_KEY, enabled ? 1 : 0);

            enabled = sharedPrefs.getBoolean(DeviceSettings.KEY_DCD_SWITCH, false);
            Settings.System.putInt(resolver, DCDModeSwitch.SETTINGS_KEY, enabled ? 1 : 0);

            enabled = sharedPrefs.getBoolean(DeviceSettings.KEY_DCI_SWITCH, false);
            Settings.System.putInt(resolver, DCIModeSwitch.SETTINGS_KEY, enabled ? 1 : 0);

            enabled = sharedPrefs.getBoolean(DeviceSettings.KEY_WIDE_SWITCH, false);
            Settings.System.putInt(resolver, WideModeSwitch.SETTINGS_KEY, enabled ? 1 : 0);

            enabled = sharedPrefs.getBoolean(DeviceSettings.KEY_NIGHT_SWITCH, false);
            Settings.System.putInt(resolver, NightModeSwitch.SETTINGS_KEY, enabled ? 1 : 0);

            String vibrSystemStrength = sharedPrefs.getString(DeviceSettings.KEY_SYSTEM_VIBSTRENGTH, VibratorSystemStrengthPreference.getDefaultValue(context));
            Settings.System.putString(resolver, VibratorSystemStrengthPreference.SETTINGS_KEY, vibrSystemStrength);

            String vibrCallStrength = sharedPrefs.getString(DeviceSettings.KEY_CALL_VIBSTRENGTH, VibratorCallStrengthPreference.getDefaultValue(context));
            Settings.System.putString(resolver, VibratorCallStrengthPreference.SETTINGS_KEY, vibrCallStrength);

            String vibrNotifStrength = sharedPrefs.getString(DeviceSettings.KEY_NOTIF_VIBSTRENGTH, VibratorNotifStrengthPreference.getDefaultValue(context));
            Settings.System.putString(resolver, VibratorNotifStrengthPreference.SETTINGS_KEY, vibrNotifStrength);

            String audioEarpieceGain = sharedPrefs.getString(DeviceSettings.KEY_EARPIECE_GAIN, EarpieceGainPreference.getDefaultValue(context));
            Settings.System.putString(resolver, EarpieceGainPreference.SETTINGS_KEY, audioEarpieceGain);
            restore(EarpieceGainPreference.getFile(context), audioEarpieceGain);

            String audioMicGain = sharedPrefs.getString(DeviceSettings.KEY_MIC_GAIN, MicGainPreference.getDefaultValue(context));
            Settings.System.putString(resolver, MicGainPreference.SETTINGS_KEY, audioMicGain);
            restore(MicGainPreference.getFile(context), audioMicGain);

            String audioSpeakerGain = sharedPrefs.getString(DeviceSettings.KEY_SPEAKER_GAIN, SpeakerGainPreference.getDefaultValue(context));
            Settings.System.putString(resolver, SpeakerGainPreference.SETTINGS_KEY, audioSpeakerGain);
            restore(SpeakerGainPreference.getFile(context), audioSpeakerGain);

            String audioHeadphoneGain = sharedPrefs.getString(DeviceSettings.KEY_HEADPHONE_GAIN, HeadphoneGainPreference.getDefaultValue(context));
            Settings.System.putString(resolver, HeadphoneGainPreference.SETTINGS_KEY, audioHeadphoneGain);
            restoreDual(HeadphoneGainPreference.getFile(context), audioHeadphoneGain);

            Settings.System.putInt(resolver, "omni_device_setting_imported", 1);
        }
    }

    @Override
    public void onReceive(final Context context, final Intent bootintent) {
        maybeImportOldSettings(context);
        restoreAfterUserSwitch(context);
    }

    public static void restoreAfterUserSwitch(Context context) {
        boolean supportsGestures = context.getResources().getBoolean(R.bool.config_device_supports_gestures);
        ContentResolver resolver = context.getContentResolver();
        boolean enabled;
        if (supportsGestures) {
            // music playback
            final boolean musicPlaybackEnabled = Settings.System.getInt(resolver,
                    "Settings.System."+DeviceSettings.GESTURE_MUSIC_PLAYBACK_SETTINGS_VARIABLE_NAME, 0) == 1;
            restore(getGestureFile(GestureSettings.KEY_MUSIC_START), musicPlaybackEnabled);
            restore(getGestureFile(GestureSettings.KEY_MUSIC_TRACK_NEXT), musicPlaybackEnabled);
            restore(getGestureFile(GestureSettings.KEY_MUSIC_TRACK_PREV), musicPlaybackEnabled);

            // circle -> camera
            String mapping = GestureSettings.DEVICE_GESTURE_MAPPING_1;
            String value = Settings.System.getString(resolver, mapping);
            if (TextUtils.isEmpty(value)) {
                value = AppSelectListPreference.CAMERA_ENTRY;
                Settings.System.putString(resolver, mapping, value);
            }
            enabled = !value.equals(AppSelectListPreference.DISABLED_ENTRY);
            restore(getGestureFile(GestureSettings.KEY_CIRCLE_APP), enabled);

            // down arrow -> flashlight
            mapping = GestureSettings.DEVICE_GESTURE_MAPPING_2;
            value = Settings.System.getString(resolver, mapping);
            if (TextUtils.isEmpty(value)) {
                value = AppSelectListPreference.TORCH_ENTRY;
                Settings.System.putString(resolver, mapping, value);
            }
            enabled = !value.equals(AppSelectListPreference.DISABLED_ENTRY);
            restore(getGestureFile(GestureSettings.KEY_DOWN_ARROW_APP), enabled);

            // M Gesture
            value = Settings.System.getString(resolver, GestureSettings.DEVICE_GESTURE_MAPPING_3);
            enabled = !TextUtils.isEmpty(value) && !value.equals(AppSelectListPreference.DISABLED_ENTRY);
            restore(getGestureFile(GestureSettings.KEY_M_GESTURE_APP), enabled);

            // down swipe
            value = Settings.System.getString(resolver, GestureSettings.DEVICE_GESTURE_MAPPING_6);
            enabled = !TextUtils.isEmpty(value) && !value.equals(AppSelectListPreference.DISABLED_ENTRY);
            restore(getGestureFile(GestureSettings.KEY_DOWN_SWIPE_APP), enabled);

            // up swipe
            value = Settings.System.getString(resolver, GestureSettings.DEVICE_GESTURE_MAPPING_7);
            enabled = !TextUtils.isEmpty(value) && !value.equals(AppSelectListPreference.DISABLED_ENTRY);
            restore(getGestureFile(GestureSettings.KEY_UP_SWIPE_APP), enabled);

            // left swipe
            value = Settings.System.getString(resolver, GestureSettings.DEVICE_GESTURE_MAPPING_8);
            enabled = !TextUtils.isEmpty(value) && !value.equals(AppSelectListPreference.DISABLED_ENTRY);
            restore(getGestureFile(GestureSettings.KEY_LEFT_SWIPE_APP), enabled);

            // right swipe
            value = Settings.System.getString(resolver, GestureSettings.DEVICE_GESTURE_MAPPING_9);
            enabled = !TextUtils.isEmpty(value) && !value.equals(AppSelectListPreference.DISABLED_ENTRY);
            restore(getGestureFile(GestureSettings.KEY_RIGHT_SWIPE_APP), enabled);

            // S Gesture
            value = Settings.System.getString(resolver, GestureSettings.DEVICE_GESTURE_MAPPING_10);
            enabled = !TextUtils.isEmpty(value) && !value.equals(AppSelectListPreference.DISABLED_ENTRY);
            restore(getGestureFile(GestureSettings.KEY_S_GESTURE_APP), enabled);

            // W Gesture
            value = Settings.System.getString(resolver, GestureSettings.DEVICE_GESTURE_MAPPING_11);
            enabled = !TextUtils.isEmpty(value) && !value.equals(AppSelectListPreference.DISABLED_ENTRY);
            restore(getGestureFile(GestureSettings.KEY_W_GESTURE_APP), enabled);
        }

        enabled = Settings.System.getInt(resolver, SRGBModeSwitch.SETTINGS_KEY, 0) != 0;
        restore(SRGBModeSwitch.getFile(context), enabled);

        enabled = Settings.System.getInt(resolver, AdobeRGBModeSwitch.SETTINGS_KEY, 0) != 0;
        restore(AdobeRGBModeSwitch.getFile(context), enabled);

        enabled = Settings.System.getInt(resolver, DCDModeSwitch.SETTINGS_KEY, 0) != 0;
        restore(DCDModeSwitch.getFile(context), enabled);

        enabled = Settings.System.getInt(resolver, DCIModeSwitch.SETTINGS_KEY, 0) != 0;
        restore(DCIModeSwitch.getFile(context), enabled);

        enabled = Settings.System.getInt(resolver, WideModeSwitch.SETTINGS_KEY, 0) != 0;
        restore(WideModeSwitch.getFile(context), enabled);

        enabled = Settings.System.getInt(resolver, HBMModeSwitch.SETTINGS_KEY, 0) != 0;
        restore(HBMModeSwitch.getFile(context), enabled);

        enabled = Settings.System.getInt(resolver, NightModeSwitch.SETTINGS_KEY, 0) != 0;
        restore(NightModeSwitch.getFile(context), enabled);

        enabled = Settings.System.getInt(resolver, FastChargeSwitch.SETTINGS_KEY, 0) != 0;
        restore(FastChargeSwitch.getFile(context), enabled);

        enabled = Settings.System.getInt(resolver, SingleTapSwitch.SETTINGS_KEY, 0) != 0;
        restore(SingleTapSwitch.getFile(context), enabled);

        enabled = Settings.System.getInt(resolver, DoubleTapToWakeSwitch.SETTINGS_KEY, 0) != 0;
        restore(DoubleTapToWakeSwitch.getFile(context), enabled);

        enabled = Settings.System.getInt(resolver, SweepToSleepSwitch.SETTINGS_KEY, 0) != 0;
        restore(SweepToSleepSwitch.getFile(context), enabled);

        enabled = Settings.System.getInt(resolver, SweepToWakeSwitch.SETTINGS_KEY, 0) != 0;
        restore(SweepToWakeSwitch.getFile(context), enabled);

        restore(EarpieceGainPreference.getFile(context), Settings.System.getString(resolver,
                      EarpieceGainPreference.SETTINGS_KEY));
        restore(MicGainPreference.getFile(context), Settings.System.getString(resolver,
                      MicGainPreference.SETTINGS_KEY));
        restoreDual(HeadphoneGainPreference.getFile(context), Settings.System.getString(resolver,
                      HeadphoneGainPreference.SETTINGS_KEY));
        restore(SpeakerGainPreference.getFile(context), Settings.System.getString(resolver,
                      SpeakerGainPreference.SETTINGS_KEY));
    }
}

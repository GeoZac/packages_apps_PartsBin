# PartsBin
[![License: GPL v3](https://img.shields.io/badge/License-GPL%20v3-blue.svg)](./LICENSE.txt)

*A configurable collection of settings for OnePlus phones.*

### Currently supported devices:

  * Google Nexus 5: Hammerhead
  * OnePlus 5:      cheeseburger
  * OnePlus 5T:     dumpling
  * OnePlus 6:      enchilada
  * OnePlus 6T:     fajita
  * OnePlus 7:      guacamoleb
  * OnePlus 7 Pro:  guacamole
  * OnePlus 7T Pro: hotdog

### Description

Based on OMNIROMs DeviceParts package, the idea is to manage device specific
settings from a common code base while allowing features/implementations
to be unique to individual devices to coexist with common ones.
Feature availability is controlled by adding various overlays to the device
tree for this package. Overlays are mostly sysfs paths controlling that particular
feature.
This application must have proper file access and sepolicy security contexts to those sysfs paths.

Resource strings are used from https://github.com/AICP/packages_resources_deviceparts

### Currently supported features include:

**Toggles and Paths**

_Note that defining paths needs support in the kernel! Thus the features might not work, if you are using a custom kernel._

	HWKSwitch: Hardware keys swap toggle (supported on devices hw nav keys)

	Offscreen-gestures: Only set to true, if the device supports Screengestures while the display is off.

	Doubletap to wake (DT2W): En-/disable waking up the display by tapping on it two times.

	Sweep to wake (S2W): En-/disable waking up the display by swiping from left to right in the lower section.

	Sweep to sleep (S2S): En-/disable to put the display to sleep by swiping from left to right in the lower section.

	SoundTuner: En-/disable the proprietary SoundTuner (OnePlus specific)

	HBMSwitch: High Brightness Mode toggle with configurable off-on values with additional QS tile

	DCDSwitch: DC-Dimming toggle with additional QS tile

	Displaypanel Color Modes: sRGB, Adobe RGB, DCI-P3, WideColor, OnePlus, Nightmode. With additional QS tile

	Display Refreshrate: Automatic, Manual (60Hz, 90Hz) with additional QS tile
	[Note: Requires setting the kernel node for "dynamic_fps" and a few other bools.]

	Vibration Modes: System, Calls, Notifications
	[Note: For a vibration to work the corresponding integer vibrator overlays must be defined.]

	Fastcharge: En-/disable USB charging with max. 900mA

**Screen-Off Gestures (with optional haptic feedback)**

	SingleTap - to display ambient mode

	Music Control - Play/Pause ("||"), skip to previous ("<") or next (">") track

	Letter "O", "W", "M", "S", "V", "A" Gestures - configurable

	Left-Right-Up-Down Swipes - configurable

**Fingerprintreader Swiping Gestures (on non-FOD devices)**

	Left-Right-Up-Down Fingerprint-Swipes - configurable
	[Note: Down-Swipe can also be disabled in favour of the AOSP build-in gesture.]

**AlertSlider**

	Top-Center-Bottom positions with position bubbles

	Possible selections:

	  - Ringer: ring, vibrate, silent
	  - Zen mode: priority only, alarms only, do not disturb
	  - Flashlight

**Configurable overlays**

`<!-- Whether the device has hardware navigation buttons (true/false) -->`<br />
`<bool name="config_device_has_hw_nav_buttons"> </bool>`<br />

`<!-- Whether the device supports offscreen-gestures (true/false) -->`<br />
`<bool name="config_device_supports_gestures"> </bool>`<br />

`<!-- Whether the device supports the prebuilt SoundTuner (true/false) -->`<br />
`<bool name="config_device_supports_soundtuner"> </bool>`<br />

`<!-- Whether device supports switching display refreshrates (true/false) -->`<br />
`<bool name="config_device_supports_switch_refreshrate"> </bool>`<br />

`<!-- Show/hide the QS tile, if device supports DC Dimming or not. Default value is false. -->`<br />
`<bool name="enableDCDTile">false</bool>`<br />

`<!-- Show/hide the QS tile, if device supports switching display refreshrates or not. Default value is false. -->`<br />
`<bool name="enableRefreshrateTile">false</bool>`<br />

`<!-- Show/hide the QS tile, if device supports switching Displaymodes or not. Default value is false. -->`<br />
`<bool name="enablePanelModeTile">false</bool>`<br />

`<!-- Show/hide the QS tile, if device supports switching HighBrightness mode or not. Default value is false. -->`<br />
`<bool name="enableHBMModeTile">false</bool>`<br />

`<!-- Whether device supports disabling hwkeys -->`<br />
`<string name="pathHWKToggle"> </string>`<br />

`<!-- Path to devices single-tap toggle file -->`<br />
`<string name="pathSTapToggle"> </string>`<br />

`<!-- Path to devices doubletap to wake toggle file -->`<br />
`<string name="pathDoubleTapToWakeToggle"> </string>`<br />

`<!-- Path to devices sweep to sleep toggle file -->`<br />
`<string name="pathSweepToSleepToggle"> </string>`<br />

`<!-- Path to devices sweep to wake toggle file -->`<br />
`<string name="pathSweepToWakeToggle"> </string>`<br />

`<!-- Path to devices High Brightness Mode toggle file -->`<br />
`<string name="pathHBMModeToggle"> </string>`<br />
`<string name="hbmOFF">"0"</string>`<br />
`<string name="hbmON">"1"</string>`<br />

`<!-- Path to devices OnePlus Mode toggle file -->`<br />
`<string name="pathOnePlusModeToggle"> </string>`<br />

`<!-- Path to devices SRGBMode toggle file -->`<br />
`<string name="pathSRGBModeToggle"> </string>`<br />

`<!-- Path to devices AdobeRGB toggle file -->`<br />
`<string name="pathAdobeRGBModeToggle"> </string>`<br />

`<!-- Path to devices DCI-P3 Mode toggle file -->`<br />
`<string name="pathDCIModeToggle"> </string>`<br />

`<!-- Path to devices Nightmode toggle file -->`<br />
`<string name="pathNightModeToggle"> </string>`<br />

`<!-- Path to devices DC-Dimming Mode toggle file -->`<br />
`<string name="pathDCDModeToggle"> </string>`<br />

`<!-- Path to devices WideMode toggle file -->`<br />
`<string name="pathWideModeToggle"> </string>`<br />

`<!-- Path to devices system vibrationlevels -->`<br />
`<string name="pathSystemVibStrength"> </string>`<br />

`<!-- Path to devices calls vibrationlevels -->`<br />
`<string name="pathCallVibStrength"> </string>`<br />

`<!-- Path to devices notification vibrationlevels -->`<br />
`<string name="pathNotifVibStrength"> </string>`<br />

`<!-- Device vibrator min-max-default values -->`<br />
`<integer name="vibratorMinMV"> </integer>`<br />
`<integer name="vibratorMaxMV"> </integer>`<br />
`<integer name="vibratorDefaultMV"> </integer>`<br />

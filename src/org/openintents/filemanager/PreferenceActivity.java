/* 
 * Copyright (C) 2008 OpenIntents.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openintents.filemanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceManager;

public class PreferenceActivity extends android.preference.PreferenceActivity
                                implements OnSharedPreferenceChangeListener {

	public static final String PREFS_MEDIASCAN = "mediascan";
	/**
	 * @since 2011-09-30
	 */
	public static final String PREFS_SHOWALLWARNING = "showallwarning";
	
	
	public static final String PREFS_DISPLAYHIDDENFILES = "displayhiddenfiles";
 	
	public static final String PREFS_SORTBY = "sortby";
	
	public static final String PREFS_ASCENDING = "ascending";
	
	@Override
	protected void onCreate(Bundle icicle) {
		
		super.onCreate(icicle);

		addPreferencesFromResource(R.xml.preferences);
		
		/* Register the onSharedPreferenceChanged listener to update the SortBy ListPreference summary */
		getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
		/* Set the onSharedPreferenceChanged listener summary to its initial value */
		changeListPreferenceSummaryToCurrentValue((ListPreference)findPreference("sortby"));
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	static boolean getMediaScanFromPreference(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context)
					.getBoolean(PREFS_MEDIASCAN, false);
	}

	/**
	 * @since 2011-09-30
	 */
	static void setShowAllWarning(Context context, boolean enabled) {
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean(PREFS_SHOWALLWARNING, enabled);
		editor.commit();
	}

	/**
	 * @since 2011-09-30
	 */
	static boolean getShowAllWarning(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context)
				.getBoolean(PREFS_SHOWALLWARNING, true);
	}
	

	
	static void setDisplayHiddenFiles(Context context, boolean enabled) {
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean(PREFS_DISPLAYHIDDENFILES, enabled);
		editor.commit();
	}


	static boolean getDisplayHiddenFiles(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context)
				.getBoolean(PREFS_DISPLAYHIDDENFILES, true);
	}
	
	
	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		if(key.equals("sortby")){
			changeListPreferenceSummaryToCurrentValue((ListPreference)findPreference(key));
		}
	}
	
	private void changeListPreferenceSummaryToCurrentValue(ListPreference listPref){
		listPref.setSummary(listPref.getEntry());
	}
	

	static int getSortBy(Context context) {
		/* entryValues must be a string-array while we need integers */
		return Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(context)
								 .getString(PREFS_SORTBY, "1"));
	}
	
	static boolean getAscending(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context)
				.getBoolean(PREFS_ASCENDING, true);
	}
}

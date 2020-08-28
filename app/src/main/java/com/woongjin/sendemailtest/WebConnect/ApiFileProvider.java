package com.woongjin.sendemailtest.WebConnect;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;

import androidx.core.content.FileProvider;

import java.io.File;
import java.util.List;

/**
 * Created by david on 2019-03-29
 * Copyright (c) GINU Co., Ltd. All rights reserved.
 */

public class ApiFileProvider {

	/**
	 *
	 * @param context
	 * @param file
	 * @return
	 */
	public static Uri getUri(Context context, String applicationID, File file)
	{
		Uri uri = null;
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
		{	// API 27
			//uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID, file);
			uri = FileProvider.getUriForFile(context, applicationID, file);
		}
		else
		{
			uri = Uri.fromFile(file);
		}
		return uri;
	}

	/**
	 *
	 * @param activity
	 * @param intent
	 * @param uri
	 */
	public static void clientPermission(Activity activity, Intent intent, Uri uri)
	{
		List<ResolveInfo> resInfoList = activity.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
		for (ResolveInfo resolveInfo : resInfoList) {
			String packageName = resolveInfo.activityInfo.packageName;
			activity.grantUriPermission(packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
		}
	}

	//

}

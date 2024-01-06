package com.radha.bhaktiguru.learning

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class AppLaunchReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Toast.makeText(context, "ram ji", Toast.LENGTH_LONG).show()
        Log.e("hari", "radhe radhe")
        if (intent?.action == Intent.ACTION_PACKAGE_ADDED || intent?.action == Intent.ACTION_PACKAGE_REPLACED) {
            // Handle app installation or update
            val packageName = intent.data?.schemeSpecificPart
            if (isAppLocked(context, packageName)) {
                launchLockScreen(context, packageName)
            }
        }
    }

    private fun isAppLocked(context: Context?, packageName: String?): Boolean {
        // Implement logic to check if the app is locked
        // You can use SharedPreferences or a database to store lock information
        return true
    }

    private fun launchLockScreen(context: Context?, packageName: String?) {
        if (context != null && packageName != null) {
            val lockIntent = Intent(context, PatternActivity::class.java)
            lockIntent.putExtra("packageName", packageName)
            lockIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(lockIntent)
        }
    }
}
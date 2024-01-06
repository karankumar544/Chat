package com.radha.bhaktiguru.learning

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.radha.bhaktiguru.R
import com.radha.bhaktiguru.learning.model.AppInfo

class AppAdapter(private val context: Context, private val appList: List<AppInfo>) :
    RecyclerView.Adapter<AppAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_app, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val appInfo = appList[position]

        holder.appName.text = appInfo.appName
        holder.packageName.text = appInfo.packageName
        holder.appIcon.setImageDrawable(context.packageManager.getApplicationIcon(appInfo.packageName))

        if (appInfo.isSystemApp) {
            holder.systemAppIndicator.visibility = View.VISIBLE
        } else {
            holder.systemAppIndicator.visibility = View.GONE
        }

        // Toggle lock state
        holder.itemView.setOnClickListener {
            appInfo.isLocked = !appInfo.isLocked
            updateLockIcon(holder.lockIcon, appInfo.isLocked)
        }

        // Update lock icon based on the current state
        updateLockIcon(holder.lockIcon, appInfo.isLocked)
    }

    override fun getItemCount(): Int {
        return appList.size
    }

    private fun updateLockIcon(lockIcon: ImageView, isLocked: Boolean) {
        if (isLocked) {
            lockIcon.setImageResource(R.drawable.baseline_lock_open_24)
        } else {
            lockIcon.setImageResource(R.drawable.baseline_lock_outline_24)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val appName: TextView = itemView.findViewById(R.id.txtAppName)
        val packageName: TextView = itemView.findViewById(R.id.txtPackageName)
        val appIcon: ImageView = itemView.findViewById(R.id.imgAppIcon)
        val systemAppIndicator: ImageView = itemView.findViewById(R.id.imgSystemAppIndicator)
        val lockIcon: ImageView = itemView.findViewById(R.id.imgLock)
    }
}
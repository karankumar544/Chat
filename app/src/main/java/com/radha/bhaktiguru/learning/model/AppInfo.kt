package com.radha.bhaktiguru.learning.model

data class AppInfo(
    val appName: String,
    val packageName: String,
    val icon: Int,
    val isSystemApp: Boolean,
    var isLocked: Boolean
)
package com.radha.bhaktiguru.learning

import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.radha.bhaktiguru.R
import com.radha.bhaktiguru.databinding.FragmentFirstBinding
import com.radha.bhaktiguru.learning.model.AppInfo

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!


    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                loadApps()
            } else {
                Log.e("MainActivity", "Permission denied")
                // Handle the case where the permission is denied
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (true) {
            loadApps()
        } else {
            // Request the QUERY_ALL_PACKAGES permission
            requestPermissionLauncher.launch(android.Manifest.permission.QUERY_ALL_PACKAGES)
        }




        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadApps() {
        val text = getInstalledApps().sortedBy { it.appName }.filter {
            it.packageName != "com.radha.bhaktiguru"
        }

        binding.apps.layoutManager = LinearLayoutManager(context)
        val adapter = AppAdapter(context = requireContext(), text)
        binding.apps.adapter = adapter
    }

    private fun hasQueryAllPackagesPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.QUERY_ALL_PACKAGES
        ) == PackageManager.PERMISSION_GRANTED
    }


    private fun getInstalledApps(): List<AppInfo> {
        val packageManager: PackageManager = requireActivity().packageManager
        val intent = Intent(Intent.ACTION_MAIN, null)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        val apps = packageManager.queryIntentActivities(intent, 0)

//        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//            packageManager.getInstalledApplications(PackageManager.MATCH_ALL)
//                .map {
//                    AppInfo(
//                        appName = packageManager.getApplicationLabel(it).toString(),
//                        packageName = it.packageName,
//                        icon = it.icon
//                    )
//                }
//        } else {
        return apps.map {
            val appInfo = it.activityInfo.applicationInfo
            AppInfo(
                appName = packageManager.getApplicationLabel(appInfo).toString(),
                packageName = appInfo.packageName,
                icon = appInfo.icon,
                isSystemApp = isSystemApp(appInfo),
                isLocked = false // Default to unlocked
            )
        }
    }

    private fun isSystemApp(applicationInfo: ApplicationInfo): Boolean {
        return applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM != 0
    }
}

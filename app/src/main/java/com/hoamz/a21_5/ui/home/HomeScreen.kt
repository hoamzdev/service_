package com.hoamz.a21_5.ui.home

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.hoamz.a21_5.databinding.FragmentHomeScreenBinding
import com.hoamz.a21_5.service.MyService


class HomeScreen : Fragment() {
    private val CLASS_NAME = javaClass.simpleName
    private lateinit var context: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.context = context
    }

    private var _binding: FragmentHomeScreenBinding? = null
    private val binding get() = _binding!!

    private val registerPostNotificationLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            startService()
        } else {
            Toast.makeText(context, "Quyen khong duoc cap", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            //receive data
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnStartService.setOnClickListener {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.TIRAMISU) {
                    checkAndRequestPermissionPostNotification()
                }
            }

            btnStopService.setOnClickListener {
                val intent = Intent(context, MyService::class.java)
                context.stopService(intent)
            }
        }
    }

    private fun checkAndRequestPermissionPostNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                ContextCompat.checkSelfPermission(
                    context, Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED -> {
                    //permission is granted
                    startService()
                }

                ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(), Manifest.permission.POST_NOTIFICATIONS
                ) -> {
                    AlertDialog.Builder(context).setTitle("Yeu cau cap quyen thong bao")
                        .setMessage("Ung dung can quyen thong bao, vui long cap quyen")
                        .setPositiveButton("Accept") { _, _ ->
                            registerPostNotificationLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                        }.setNegativeButton("Deny") { _, _ ->
                            Log.e(CLASS_NAME, "User khong cap quyen")
                        }.show()
                }

                else -> {
                    registerPostNotificationLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        } else {
            startService()
        }
    }

    private fun startService() {
        val intent = Intent(context, MyService::class.java)
        intent.putExtra("SV", "data to noti")
        context.startService(intent)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
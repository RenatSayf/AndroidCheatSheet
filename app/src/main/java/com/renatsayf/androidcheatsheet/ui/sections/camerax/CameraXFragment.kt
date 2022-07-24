package com.renatsayf.androidcheatsheet.ui.sections.camerax

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.renatsayf.androidcheatsheet.R
import com.renatsayf.androidcheatsheet.databinding.FragmentCameraXBinding

class CameraXFragment : Fragment() {

    private lateinit var binding: FragmentCameraXBinding

    private val cameraVM: CameraXViewModel by lazy {
        ViewModelProvider(this)[CameraXViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCameraXBinding.inflate(inflater, container, false)
        return binding.root
    }



}
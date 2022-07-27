package com.renatsayf.androidcheatsheet.ui.sections.camerax.photos

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.renatsayf.androidcheatsheet.databinding.FragmentPhotosBinding
import com.renatsayf.androidcheatsheet.ui.sections.camerax.CameraXViewModel

class PhotosFragment : Fragment() {

    private lateinit var binding: FragmentPhotosBinding

    private val photosVM: CameraXViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPhotosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        photosVM.photoUriList.observe(viewLifecycleOwner) { list ->
            list.forEach { uri ->
                val originUri = uri
                val path = uri.encodedPath
                val uriStr = uri.toString()
                val fromPath = Uri.parse(path)
                val fromString = Uri.parse(uriStr)
                fromString
            }
        }

        photosVM.getAppPictures().observe(viewLifecycleOwner) { list ->
            list.forEach { file ->
                photosVM.addUriToList(file.toUri())
            }
        }



    }

}
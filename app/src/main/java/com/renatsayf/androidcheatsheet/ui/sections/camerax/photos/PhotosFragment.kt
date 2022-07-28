package com.renatsayf.androidcheatsheet.ui.sections.camerax.photos

import android.content.ContentResolver
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.renatsayf.androidcheatsheet.R
import com.renatsayf.androidcheatsheet.databinding.FragmentPhotosBinding
import com.renatsayf.androidcheatsheet.models.PhotoModel
import com.renatsayf.androidcheatsheet.ui.sections.camerax.CameraXViewModel

class PhotosFragment : Fragment() {

    private lateinit var binding: FragmentPhotosBinding

    private val photosVM: CameraXViewModel by activityViewModels()
    private val photosAdapter: PhotosAdapter by lazy {
        PhotosAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPhotosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            rvPhotos.adapter = photosAdapter
            photosVM.getAppPictures().observe(viewLifecycleOwner) { list ->
                val photos = list?.map {
                    PhotoModel(it)
                }
                photos?.let { photosAdapter.addItems(it) }
            }
        }


    }



}


@file:Suppress("ObjectLiteralToLambda")

package com.renatsayf.androidcheatsheet.ui.sections.camerax

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.renatsayf.androidcheatsheet.BuildConfig
import com.renatsayf.androidcheatsheet.R
import com.renatsayf.androidcheatsheet.databinding.FragmentCameraXBinding
import com.renatsayf.androidcheatsheet.ui.sections.extentions.appPicturesDirName
import com.renatsayf.androidcheatsheet.ui.sections.extentions.showSnackBar
import java.text.SimpleDateFormat
import java.util.*

class CameraXFragment : Fragment() {

    companion object {
        private const val TAG = "CameraXApp"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = mutableListOf (
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO
            ).apply {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    add(Manifest.permission.READ_EXTERNAL_STORAGE)
                }
            }.toTypedArray()
    }

    private lateinit var binding: FragmentCameraXBinding

    private val cameraVM: CameraXViewModel by activityViewModels()

    private var imageCapture: ImageCapture? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCameraXBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val isGranted = allPermissionsGranted()
        if (isGranted) {
            startCamera().observe(viewLifecycleOwner) {
                imageCapture = it
                if (imageCapture is ImageCapture) {
                    cameraVM.setState(CameraXViewModel.State.OnReady)
                }
                else cameraVM.setState(CameraXViewModel.State.NotReady)
            }
        }
        else {
            requestPermissions(REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }

        with(binding) {

            cameraVM.state.observe(viewLifecycleOwner) { state ->
                when(state) {
                    CameraXViewModel.State.NotReady -> {
                        imageCaptureButton.isEnabled = false
                        videoCaptureButton.isEnabled = false
                        btnZoomPlus.isEnabled = false
                        btnZoomMinus.isEnabled = false
                    }
                    CameraXViewModel.State.OnReady -> {
                        imageCaptureButton.isEnabled = true
                        videoCaptureButton.isEnabled = true
                        btnZoomPlus.isEnabled = true
                        btnZoomMinus.isEnabled = true
                    }
                    is CameraXViewModel.State.OnPhotoCompleted -> {
                        state.uri?.let {
                            cameraVM.addUriToList(it)
                            findNavController().navigate(R.id.action_cameraXFragment_to_photosFragment)
                        }
                        progressBar.visibility = View.GONE
                    }
                    CameraXViewModel.State.OnStartPhoto -> {
                        progressBar.visibility = View.VISIBLE
                    }
                }
            }

            imageCaptureButton.setOnClickListener {
                imageCapture?.let {
                    cameraVM.setState(CameraXViewModel.State.OnStartPhoto)
                    takePhoto(it)
                }
            }

            btnZoomPlus.setOnClickListener {
                imageCapture?.let { ic -> zoomPlus(ic, value = 0.25F) }
            }

            btnZoomMinus.setOnClickListener {
                imageCapture?.let { ic -> zoomMinus(ic, value = -0.25f) }
            }
        }


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            val isGranted = allPermissionsGranted()
            if (isGranted) {
                startCamera().observe(viewLifecycleOwner) {
                    imageCapture = it
                    if (imageCapture is ImageCapture) {
                        cameraVM.setState(CameraXViewModel.State.OnReady)
                    }
                    else cameraVM.setState(CameraXViewModel.State.NotReady)
                }
            }
            else {
                showSnackBar("Permissions not granted by the user.")
            }
        }
    }

    private fun allPermissionsGranted(): Boolean {
        return REQUIRED_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun startCamera(): LiveData<ImageCapture?> {



        val imageCapture = MutableLiveData<ImageCapture>(null)
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener(object : Runnable {

            override fun run() {

                val cameraProvider = cameraProviderFuture.get()
                val preview = Preview.Builder()
                    .build().also {
                        it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
                    }
                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                try {
                    imageCapture.value = ImageCapture.Builder().build()
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        this@CameraXFragment,
                        cameraSelector,
                        preview,
                        imageCapture.value
                    )

                } catch (e: Exception) {
                    imageCapture.value = null
                    showSnackBar("Use case binding failed")
                    if (BuildConfig.DEBUG) e.printStackTrace()
                }
            }
        }, requireActivity().mainExecutor)
        return imageCapture
    }

    private fun takePhoto(imageCapture: ImageCapture) {

        val fileName = SimpleDateFormat(
            FILENAME_FORMAT,
            Locale.getDefault()
        ).format(System.currentTimeMillis())

        val contentValues = ContentValues().apply {

            val dir = Environment.getExternalStoragePublicDirectory(
                appPicturesDirName
            )
            if (!dir.exists()) {
                dir.mkdir()
            }
            put(MediaStore.Images.Media.DATA, "$dir/$fileName")

            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Images.Media.RELATIVE_PATH, appPicturesDirName)
            }
        }

        val outPutOptions = ImageCapture.OutputFileOptions.Builder(
            requireContext().contentResolver,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        ).build()

        imageCapture.takePicture(
            outPutOptions,
            requireActivity().mainExecutor,
            object : ImageCapture.OnImageSavedCallback {

                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    cameraVM.setState(CameraXViewModel.State.OnPhotoCompleted(outputFileResults.savedUri))
                    val msg = "Photo capture succeeded: ${outputFileResults.savedUri}"
                    showSnackBar(msg)
                }
                override fun onError(exception: ImageCaptureException) {
                    cameraVM.setState(CameraXViewModel.State.OnPhotoCompleted(null))
                    showSnackBar("Photo capture failed: ${exception.message}")
                }
            }
        )
    }

    private fun zoomMinus(imageCapture: ImageCapture, value: Float = -1.0f) {
        zoomPlus(imageCapture, value)
    }

    private fun zoomPlus(imageCapture: ImageCapture, value: Float = 1.0f) {
        val camera = imageCapture.camera
        val zoomState = camera?.cameraInfo?.zoomState?.value
        val maxZoomRatio = zoomState?.maxZoomRatio ?: 1.0f
        val minZoomRatio = zoomState?.minZoomRatio ?: 0.0f
        val zoomRatio = zoomState?.zoomRatio ?: 0.0f
        val newRatio = zoomRatio + value
        if (newRatio in minZoomRatio..maxZoomRatio) {
            camera?.cameraControl?.setZoomRatio(newRatio)
        }
    }





}
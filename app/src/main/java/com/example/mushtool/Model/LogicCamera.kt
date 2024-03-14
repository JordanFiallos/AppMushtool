package com.example.mushtool.Model

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Matrix
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cameraswitch
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mushtool.Galeri_Photo_Content
import com.example.mushtool.PhotoSizeViewModel
import com.example.mushtool.PhotoWithComment
import com.example.mushtool.ui.theme.MushToolTheme
import kotlinx.coroutines.launch

class logicCamera : ComponentActivity(){
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!Verif_Permissions()){
            ActivityCompat.requestPermissions(
                this, Show_Permissos, 0
            )
        }
        setContent {
            MushToolTheme{

                //Permite mostrar el comentario
                val showCommentBox = remember { mutableStateOf(false) }
                val selectedPhoto = remember { mutableStateOf<Bitmap?>(null) }
                val selectedPhotoComment = remember { mutableStateOf("") }

                val scope = rememberCoroutineScope()
                val scaffoldState = rememberBottomSheetScaffoldState()
                val controller = remember {
                    LifecycleCameraController(applicationContext).apply {
                        setEnabledUseCases(
                            CameraController.IMAGE_CAPTURE or
                                    CameraController.VIDEO_CAPTURE
                        )
                    }
                }

                val viewModel = viewModel<PhotoSizeViewModel>()
                val photos by viewModel.photos.collectAsState()

                BottomSheetScaffold(
                    scaffoldState = scaffoldState,
                    sheetPeekHeight = 0.dp,
                    sheetContent = {
                        Galeri_Photo_Content(
                            bitmaps = photos,
                            modifier = Modifier.fillMaxWidth(),
                            onPhotoSelected = { photoWithComment: PhotoWithComment ->
                                selectedPhoto.value = photoWithComment.bitmap
                                selectedPhotoComment.value = photoWithComment.comment
                                showCommentBox.value = true
                            },
                            viewModel = viewModel
                        )
                    }
                )  { padding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                    ) {
                        //Muestra la vista previa de la Cámara
                        CameraPreview(
                            controller = controller,
                            modifier = Modifier
                                .fillMaxSize()
                        )

                        //Boton para cambiar de camara FRONT-BACK
                        IconButton(
                            onClick = {
                                controller.cameraSelector =
                                    if (controller.cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) {
                                        CameraSelector.DEFAULT_FRONT_CAMERA
                                    } else CameraSelector.DEFAULT_BACK_CAMERA
                            },
                            modifier = Modifier
                                .offset(16.dp, 16.dp),
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = Color.Gray.copy(alpha = 0.5f) // Color del ícono cuando está presionado
                            )
                        ) {
                            //Icono para cambiar de camara FRONT-BACK
                            Icon(
                                imageVector = Icons.Default.Cameraswitch,
                                contentDescription = "Switch camera"
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.BottomCenter)
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {

                            //Icono para abrir galería
                            IconButton(
                                onClick = {
                                    //Mustra la información que se esta realizando en ese momento
                                    scope.launch {
                                        scaffoldState.bottomSheetState.expand()
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Photo,
                                    modifier = Modifier
                                        .background(shape = CircleShape, color = Color.Gray.copy(alpha = 0.5f))
                                        .padding(8.dp),
                                    contentDescription = "Open gallery"
                                )
                            }

                            //Icono para tomar foto
                            IconButton(
                                onClick = {
                                    takePhoto(
                                        controller = controller,
                                        onPhotoTaken = { bitmap ->
                                            viewModel.onTakePhoto(bitmap, selectedPhotoComment.value)
                                        },
                                    )
                                }
                            ) {
                                Box(
                                    modifier = Modifier
                                        .background(shape = CircleShape, color = Color.Gray.copy(alpha = 0.5f))
                                        .padding(8.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.PhotoCamera,
                                        contentDescription = "Take photo",
                                    )
                                }
                            }
                        }
                    }

                }
            }
        }
    }

    //Funcion para tomar fotos
    private fun takePhoto(
        controller: LifecycleCameraController,
        onPhotoTaken: (Bitmap) -> Unit,
    ) {
        controller.takePicture(
            ContextCompat.getMainExecutor(applicationContext),
            object : ImageCapture.OnImageCapturedCallback() {
                override fun onCaptureSuccess(image: ImageProxy) {
                    super.onCaptureSuccess(image)

                    val matrix = Matrix().apply {
                        postRotate(image.imageInfo.rotationDegrees.toFloat())
                    }
                    val rotatedBitmap = Bitmap.createBitmap(
                        image.toBitmap(),
                        0,
                        0,
                        image.width,
                        image.height,
                        matrix,
                        true
                    )

                    onPhotoTaken(rotatedBitmap)
                }

                override fun onError(exception: ImageCaptureException) {
                    super.onError(exception)
                    Log.e("Camera", "No se puedo tamar ninguna foto: ", exception)
                }
            }
        )
    }


    //Verifica los permisos
    private fun Verif_Permissions() : Boolean{
        return Show_Permissos.all {
            ContextCompat.checkSelfPermission(
                applicationContext, it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }


    //Se muestra los permisos que tenemos
    companion object{
        private val Show_Permissos = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.INTERNET
        )
    }



    //Mustra la vista previa y getiona la lógica de la cámara
    @Composable
    fun CameraPreview(
        controller: LifecycleCameraController,
        modifier: Modifier = Modifier
    ) {
        val lifecycleOwner = LocalLifecycleOwner.current
        AndroidView(
            factory = {
                PreviewView(it).apply {
                    this.controller = controller
                    controller.bindToLifecycle(lifecycleOwner)
                }
            },
            modifier = modifier
        )
    }

}
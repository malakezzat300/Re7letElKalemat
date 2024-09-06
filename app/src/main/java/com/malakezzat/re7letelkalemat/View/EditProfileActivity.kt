package com.malakezzat.re7letelkalemat.View

import android.content.ContentResolver
import android.content.Context
import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.AnyRes
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.malakezzat.re7letelkalemat.R
import com.malakezzat.re7letelkalemat.databinding.ActivityEditProfileBinding


class EditProfileActivity : AppCompatActivity() {
    lateinit var db: ActivityEditProfileBinding
    private var pickMediaLauncher: ActivityResultLauncher<PickVisualMediaRequest>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(db.root)
        val user = FirebaseAuth.getInstance().currentUser

        Glide.with(applicationContext).load(user?.photoUrl)
            .apply(RequestOptions().override(200, 200))
            .placeholder(R.drawable.vector__1_)
            .into(db.profileImg)

        pickMediaLauncher = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri: Uri? ->
            if (uri != null) {
                val profileUpdates = UserProfileChangeRequest.Builder()
                    .setPhotoUri(uri)
                    .build()
                user?.updateProfile(profileUpdates)
                    ?.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(applicationContext, getString(R.string.change_image_done), Toast.LENGTH_SHORT).show()
                            Glide.with(applicationContext)
                                .load(user.photoUrl)
                                .apply(RequestOptions().override(200, 200))
                                .placeholder(R.drawable.vector__1_)
                                .into(db.profileImg)
                        } else {
                            Toast.makeText(applicationContext, getString(R.string.change_image_failed), Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Log.d("PhotoPicker", "No media selected")
            }
        }

        db.changePicButton.setOnClickListener {
            pickMediaLauncher?.launch(
                PickVisualMediaRequest.Builder()
                    .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    .build()
            )
        }

        db.changeNameButton.setOnClickListener {
            if(db.usernameChangeEditText.text.isNotBlank()) {
                val profileUpdates =
                    UserProfileChangeRequest.Builder()
                        .setDisplayName(db.usernameChangeEditText.text.toString())
                        .build()
                user?.updateProfile(profileUpdates)
                    ?.addOnCompleteListener(OnCompleteListener<Void?> { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                applicationContext,
                                getString(R.string.change_name_done),
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                applicationContext,
                                applicationContext.getString(R.string.change_name_failed),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
            } else {
                Toast.makeText(applicationContext, "من فضلك قم بأدخال اسم", Toast.LENGTH_SHORT).show()
            }
        }

        db.backImage.setOnClickListener {
            finish()
        }

        db.girl1Button.setOnClickListener {
            val uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + packageName + '/' + getResources().getResourceTypeName(R.drawable.girl1) + '/' + getResources().getResourceEntryName(R.drawable.girl1) )
            val profileUpdates = UserProfileChangeRequest.Builder()
                .setPhotoUri(uri)
                .build()
            user?.updateProfile(profileUpdates)
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(applicationContext, getString(R.string.change_image_done), Toast.LENGTH_SHORT).show()
                        Glide.with(applicationContext)
                            .load(user.photoUrl)
                            .apply(RequestOptions().override(200, 200))
                            .placeholder(R.drawable.vector__1_)
                            .into(db.profileImg)
                    } else {
                        Toast.makeText(applicationContext, getString(R.string.change_image_failed), Toast.LENGTH_SHORT).show()
                    }
                }
        }

        db.girl2Button.setOnClickListener {
            val uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + packageName + '/' + getResources().getResourceTypeName(R.drawable.girl2) + '/' + getResources().getResourceEntryName(R.drawable.girl2) )
            val profileUpdates = UserProfileChangeRequest.Builder()
                .setPhotoUri(uri)
                .build()
            user?.updateProfile(profileUpdates)
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(applicationContext, getString(R.string.change_image_done), Toast.LENGTH_SHORT).show()
                        Glide.with(applicationContext)
                            .load(user.photoUrl)
                            .apply(RequestOptions().override(200, 200))
                            .placeholder(R.drawable.vector__1_)
                            .into(db.profileImg)
                    } else {
                        Toast.makeText(applicationContext, getString(R.string.change_image_failed), Toast.LENGTH_SHORT).show()
                    }
                }
        }

        db.boy1Button.setOnClickListener {
            val uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + packageName + '/' + getResources().getResourceTypeName(R.drawable.boy1) + '/' + getResources().getResourceEntryName(R.drawable.boy1) )
            val profileUpdates = UserProfileChangeRequest.Builder()
                .setPhotoUri(uri)
                .build()
            user?.updateProfile(profileUpdates)
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(applicationContext, getString(R.string.change_image_done), Toast.LENGTH_SHORT).show()
                        Glide.with(applicationContext)
                            .load(user.photoUrl)
                            .apply(RequestOptions().override(200, 200))
                            .placeholder(R.drawable.vector__1_)
                            .into(db.profileImg)
                    } else {
                        Toast.makeText(applicationContext, getString(R.string.change_image_failed), Toast.LENGTH_SHORT).show()
                    }
                }
        }

        db.boy2Button.setOnClickListener {
            val uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + packageName + '/' + getResources().getResourceTypeName(R.drawable.boy2) + '/' + getResources().getResourceEntryName(R.drawable.boy2) )
            val profileUpdates = UserProfileChangeRequest.Builder()
                .setPhotoUri(uri)
                .build()
            user?.updateProfile(profileUpdates)
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(applicationContext, getString(R.string.change_image_done), Toast.LENGTH_SHORT).show()
                        Glide.with(applicationContext)
                            .load(user.photoUrl)
                            .apply(RequestOptions().override(200, 200))
                            .placeholder(R.drawable.vector__1_)
                            .into(db.profileImg)
                    } else {
                        Toast.makeText(applicationContext, getString(R.string.change_image_failed), Toast.LENGTH_SHORT).show()
                    }
                }
        }

    }

}
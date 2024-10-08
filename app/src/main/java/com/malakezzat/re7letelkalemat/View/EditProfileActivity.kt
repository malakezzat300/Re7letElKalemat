package com.malakezzat.re7letelkalemat.View

import android.content.ContentResolver
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.net.toUri
import androidx.transition.Visibility
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.malakezzat.re7letelkalemat.Model.User
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
        db.usernameChangeEditText.setText(user?.displayName)
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
                            // Update image URL in Firebase Database
                            updateUserPhoto(user.email, user.photoUrl.toString())
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
                            updateUserName(FirebaseAuth.getInstance().currentUser?.email?.substringBefore("."),db.usernameChangeEditText.text.toString() )
                            finish()
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
                        // Update image URL in Firebase Database
                        updateUserPhoto(user.email, user.photoUrl.toString())
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
                        // Update image URL in Firebase Database
                        updateUserPhoto(user.email, user.photoUrl.toString())
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
                        // Update image URL in Firebase Database
                        updateUserPhoto(user.email, user.photoUrl.toString())
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
                        // Update image URL in Firebase Database
                        updateUserPhoto(user.email, user.photoUrl.toString())
                    } else {
                        Toast.makeText(applicationContext, getString(R.string.change_image_failed), Toast.LENGTH_SHORT).show()
                    }
                }
        }
        getUserScore(FirebaseAuth.getInstance().currentUser?.email?.substringBefore("."))
    }


    // Function to retrieve the user's score
    fun getUserScore(userId: String?) {
        var score : String = "0"
        // Get Firebase Realtime Database reference
        val database = FirebaseDatabase.getInstance()
        val userRef = database.getReference("users").child(userId!!)



        // Attach a listener to read the data
        userRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user2 = task.result.getValue(User::class.java)
                score = user2?.score?.toString() ?: "0"
                //db.scoreText.text = score
            } else {
                // Handle potential errors, you can pass a default score in case of failure
            }
        }
    }


    fun updateUserName(userEmail: String?, userName: String?) {
        // Get Firebase Realtime Database reference
        val database = FirebaseDatabase.getInstance()
        val userId = userEmail?.substringBefore(".")
        val usersRef = database.getReference("users").child(userId ?: "")

        // Assuming User is a data class that has a name field
        // Create or update the User object with the new name
        usersRef.child("name").setValue(userName).addOnSuccessListener {
            // Success logic, e.g., show a message
            Log.d("Firebase", "User name updated successfully")
        }.addOnFailureListener { exception ->
            // Failure logic, handle errors
            Log.e("Firebase", "Failed to update user name", exception)
        }

    }

    fun updateUserPhoto(userEmail: String?, imageUrl: String?) {
        // Get Firebase Realtime Database reference
        val database = FirebaseDatabase.getInstance()
        val userId = userEmail?.substringBefore(".")
        val usersRef = database.getReference("users").child(userId ?: "")

        // Upload the image and update the photo URL
        val uri = imageUrl?.toUri() ?: AppCompatResources.getDrawable(applicationContext, R.drawable.vector__1_)
            .toString().toUri()

        usersRef.child("imageUrl").setValue(uri.toString())
        uploadImage(uri)

        // Assuming uploadImage function uploads the image and returns the URL

    }

    // Function to upload image
    fun uploadImage(imageUri: Uri/*, callback: UploadCallback*/) {
        val storageRef = FirebaseStorage.getInstance().reference
        val imageUrl = imageUri.toString().substringAfterLast("/")
        val userImagesRef = storageRef.child("user_images/${imageUrl}.jpg")

        val uploadTask = userImagesRef.putFile(imageUri)
        uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            // Get the download URL
            userImagesRef.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result
                // Store this URL in Firebase Realtime Database
            } else {
                // Handle failures
            }
        }
    }
}
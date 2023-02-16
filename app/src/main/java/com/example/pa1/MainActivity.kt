package com.example.pa1

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import java.io.ByteArrayOutputStream
import java.util.*


//Create variables to hold the three strings
private var sFirstName: String? = null
private var sLastName: String? = null
private var sMiddleName: String? = null

//Create variables for the UI elements that we need to control
private var textFirstName: TextView? = null
private var textLastName: TextView? = null
private var ButtonSubmit: Button? = null
private var ButtonCamera: Button? = null
private var etFirstName: EditText? = null
private var etMiddleName: EditText? = null
private var etLastName: EditText? = null

private var imageThumbnail: ImageView? = null

//Define a bitmap
private var mThumbnailImage: Bitmap? = null

//Define a global intent variable
private var mDisplayIntent: Intent? = null

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Get the buttons
        ButtonSubmit = findViewById<View>(R.id.submitButton) as Button
        ButtonCamera = findViewById<View>(R.id.cameraButton) as Button
        imageThumbnail = findViewById<View>(R.id.cameraImage) as ImageView

        //Say that this class itself contains the listener.
        ButtonSubmit!!.setOnClickListener(this)
        ButtonCamera!!.setOnClickListener(this)

        //Create the intent but don't start the activity yet
        mDisplayIntent = Intent(this, DisplayActivity::class.java)
    }

    override fun onClick(p0: View?) {
        if (p0 != null) {
            when (p0.id){
                R.id.submitButton -> {
                    //Get Names From Edit Texts
                    etFirstName = findViewById<View>(R.id.firstName) as EditText
                    etMiddleName = findViewById<View>(R.id.middleName) as EditText
                    etLastName = findViewById<View>(R.id.lastName) as EditText
                    sFirstName = etFirstName!!.text.toString()
                    sLastName = etLastName!!.text.toString()

                    if (sFirstName.isNullOrBlank() || sLastName.isNullOrBlank()) {
                        Toast.makeText(this@MainActivity, "Enter a first and last name before submitting!", Toast.LENGTH_SHORT)
                            .show()
                    }
                    else {
                        mDisplayIntent!!.putExtra("FN_data", sFirstName)
                        mDisplayIntent!!.putExtra("LN_data", sLastName)
                        startActivity(mDisplayIntent)
                    }
                }

                R.id.cameraButton -> {

                    //The button press should open a camera
                    val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    try {
                        cameraLauncher.launch(cameraIntent)
                    } catch (ex: ActivityNotFoundException) {
                        //Do something here
                    }
                }

            }
        }
    }

    private var cameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val extras = result.data!!.extras
            mThumbnailImage = extras!!["data"] as Bitmap?
            imageThumbnail!!.setImageBitmap(mThumbnailImage)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {

        //Save the view hierarchy
        super.onSaveInstanceState(outState)

        etFirstName = findViewById<View>(R.id.firstName) as EditText
        etMiddleName = findViewById<View>(R.id.middleName) as EditText
        etLastName = findViewById<View>(R.id.lastName) as EditText
        imageThumbnail = findViewById<View>(R.id.cameraImage) as ImageView


        //Get the strings
        sFirstName = etFirstName!!.text.toString()
        sMiddleName = etMiddleName!!.text.toString()
        sLastName = etLastName!!.text.toString()


        //Put them in the outgoing Bundle
        outState.putString("FN_TEXT", sFirstName)
        outState.putString("MN_TEXT", sMiddleName)
        outState.putString("LN_TEXT", sLastName)
        outState.putParcelable("IMAGE", mThumbnailImage)

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {

        //Restore the view hierarchy automatically
        super.onRestoreInstanceState(savedInstanceState)

        //Restore stuff
        etFirstName!!.setText(savedInstanceState.getString("FN_TEXT"))
        etMiddleName!!.setText(savedInstanceState.getString("MN_TEXT"))
        etLastName!!.setText(savedInstanceState.getString("LN_TEXT"))

        mThumbnailImage = savedInstanceState.getParcelable("IMAGE")
        imageThumbnail!!.setImageBitmap(mThumbnailImage)
    }
}
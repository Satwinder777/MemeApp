package com.example.memeapp

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class MainActivity : AppCompatActivity() {
    var currentImageurl:String?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadMeme()
    }


    //the function are starts from here
        private fun loadMeme(){
            val ProgressBar= findViewById<ProgressBar>(R.id.progressBar) as ProgressBar
             ProgressBar.visibility = View.VISIBLE
            val url = "https://meme-api.herokuapp.com/gimme"
            val jsonObjectRequest = JsonObjectRequest(Request.Method.GET,url,null,Response.Listener {
                response ->
                currentImageurl= response.getString("url")
                val imageView2:ImageView = findViewById(R.id.imageView2)

                Glide.with(this).load(currentImageurl).listener(object :RequestListener<Drawable>{


                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        ProgressBar.visibility= View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        ProgressBar.visibility= View.GONE
                        return false
                    }
                }).into(imageView2)

            }) {
                Toast.makeText(this, "something went wrong", Toast.LENGTH_LONG).show()
            }

            MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
        }
    fun shareFunction(view: View){
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"hey you can send your meme now $currentImageurl")
        val chooser = Intent.createChooser(intent,"Share This Massage Using...")

        startActivity(chooser)
        println("Singh")
    }
    fun nextFunction(view: View){
        loadMeme()
        println("mrSingh")
    }
}
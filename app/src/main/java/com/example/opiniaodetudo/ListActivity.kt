package com.example.opiniaodetudo

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.review_list_item_layout.*



class ListActivity : AppCompatActivity(){

    private fun initList(listView: ListView) {
        object : AsyncTask<Void, Void, ArrayAdapter<Review>>() {
            override fun doInBackground(vararg params: Void): ArrayAdapter<Review> {
                val reviews = ReviewRepository(this@ListActivity.applicationContext)
                    .listAll()

                val adapter = object : ArrayAdapter<Review>(
                    this@ListActivity, -1, reviews
                ) {
                    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                        val itemView = layoutInflater.inflate(R.layout.review_list_item_layout, null)
                        val item = reviews[position]

                        val textViewName = itemView
                            .findViewById<TextView>(R.id.item_name)
                        val textViewReview = itemView
                            .findViewById<TextView>(R.id.item_review)

                        textViewName.text = item.name
                        textViewReview.text = item.Review

                        return itemView
                    }
                }
                return adapter
            }

            override fun onPostExecute(adapter: ArrayAdapter<Review>) {
                listView.adapter = adapter
            }
        }.execute()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_review_layout)

        val listView = findViewById<ListView>(R.id.list_recyclerview)
        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initList(listView)
    }

//        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)


//        val listView = findViewById<ListView>(R.id.list_recyclerview)
//        val reviewRepository = ReviewRepository.instance.listAll()
//
//        val adapter = object : ArrayAdapter<Review>(this, -1, reviewRepository) {
//            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
//
//                val itemView = layoutInflater.inflate(R.layout.review_list_item_layout, null)
//                val item = reviewRepository[position]
//
//                val textViewName = itemView.findViewById<TextView>(R.id.item_name)
//                val textViewReview = itemView.findViewById<TextView>(R.id.item_review)
//
//                textViewName.text  = item.name
//                textViewReview.text = item.Review
//
//                return itemView
//
//
//
//            }
//
//        }
//
//        listView.adapter = adapter


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item?.itemId == R.id.menu_list_reviews){
            startActivity(Intent(this,ListActivity::class.java))
            return true
        }
        return false
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}





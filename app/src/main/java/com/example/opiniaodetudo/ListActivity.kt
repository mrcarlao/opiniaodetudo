package com.example.opiniaodetudo

import android.content.Intent
import android.media.MediaMetadataRetriever
import android.os.AsyncTask
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.review_list_item_layout.*



class ListActivity : AppCompatActivity(){

    private lateinit var reviews : MutableList<Review>

    private fun initList(listView: ListView) {
        object : AsyncTask<Void, Void, ArrayAdapter<Review>>() {
            override fun doInBackground(vararg params: Void?): ArrayAdapter<Review> {

                val adapter = object : ArrayAdapter<Review>(
                    this@ListActivity, -1, reviews ) {
                    override  fun getView(
                        position: Int,
                        convertView: View?,
                        parent: ViewGroup
                    ): View {
                        val itemView = layoutInflater.inflate(R.layout.review_list_item_layout, null)
                        val item = reviews[position]

                        val textViewName = itemView
                            .findViewById<TextView>(R.id.item_name)
                        val textViewReview = itemView
                            .findViewById<TextView>(R.id.item_review)

                        textViewName.text = item.name
                        textViewReview.text = item.review

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

    private fun delete(item: Review){
        object: AsyncTask<Unit, Void, Unit>(){
            override fun doInBackground(vararg params: Unit?) {
                ReviewRepository(this@ListActivity.applicationContext).delete(item)
                reviews.remove(item)
            }

            override fun onPostExecute(result: Unit?) {
                val listView = findViewById<ListView>(R.id.list_recyclerview)
                val adapter = listView.adapter as ArrayAdapter<Review>
                adapter.notifyDataSetChanged()
            }
        }.execute()
    }

    private fun configureOnLongClick(listView: ListView?) {
        listView?.setOnItemLongClickListener{ parent, view, position, id ->
            val popupMenu = PopupMenu(this@ListActivity, view)
            popupMenu.inflate(R.menu.list_review_item_menu)
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId){
                    R.id.item_list_delete -> this@ListActivity.delete(reviews[position])
                }
                true
            }
            popupMenu.show()
            true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_review_layout)

        val repo = ReviewRepository(this@ListActivity.applicationContext)
        reviews = repo.listAll().toMutableList()

        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val listView = findViewById<ListView>(R.id.list_recyclerview)
        initList(listView)
        configureOnLongClick(listView)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
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





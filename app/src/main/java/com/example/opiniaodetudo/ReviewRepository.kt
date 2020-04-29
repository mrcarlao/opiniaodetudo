package com.example.opiniaodetudo

import android.content.Context
import java.util.*

class ReviewRepository{

    private val reviewDao: ReviewDao
    constructor(context: Context){
        val reviewDatabase = ReviewDatabase.getInstance(context)
        reviewDao = reviewDatabase.reviewDao()
    }

    fun save(name: String, review: String){
        reviewDao.save(Review(UUID.randomUUID().toString(), name, review))
    }

    fun listAll(): List<Review> {
        return reviewDao.listAll()
    }

    fun delete(item: Review) {
        TODO("Not yet implemented")
    }

}
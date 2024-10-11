package com.example.universalyoga.DBHelper

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.universalyoga.Models.Course
import com.example.universalyoga.R
import java.io.ByteArrayOutputStream

class CourseDBHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME,null, DATABASE_VERSION){

    companion object{
        private const val DATABASE_NAME = "yoga.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "courses"
        private const val COLUMN_ID = "course_id"
        private const val COLUMN_DAY_OF_WEEK = "day_of_week"
        private const val COLUMN_TYPE_OF_CLASS = "type_of_class"
        private const val COLUMN_TIME = "time_of_course"
        private const val COLUMN_DURATION = "duration"
        private const val COLUMN_CAPACITY = "capacity"
        private const val COLUMN_PRICE = "price_per_class"
        private const val COLUMN_DESCRIPTION = "description"
        private const val COLUMN_LOCATION = "location"
        private const val COLUMN_IMG = "images"
    }




    //insert new courses
    fun addCourse(course: Course){
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_DAY_OF_WEEK,course.day_of_week)
            put(COLUMN_TYPE_OF_CLASS,course.type_of_class)
            put(COLUMN_TIME,course.time_of_course)
            put(COLUMN_CAPACITY, course.capacity)
            put(COLUMN_DURATION,course.duration)
            put(COLUMN_PRICE,course.price_per_class)
            put(COLUMN_DESCRIPTION,course.description)
            put(COLUMN_LOCATION,course.location)
            put(COLUMN_IMG,course.images)
        }
        db.insert(TABLE_NAME,null,values)
        db.close()

    }
    override fun onCreate(db: SQLiteDatabase?) {
        val createTBL = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY,$COLUMN_DAY_OF_WEEK TEXT,$COLUMN_TYPE_OF_CLASS TEXT,$COLUMN_TIME TEXT, $COLUMN_DURATION INT,$COLUMN_CAPACITY INT,$COLUMN_PRICE DOUBLE,$COLUMN_DESCRIPTION TEXT,$COLUMN_LOCATION TEXT,$COLUMN_IMG BLOB)"
        db?.execSQL(createTBL)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)

    }

    //get all course from db
    fun getAllCourse(): List<Course>{

        val course = mutableListOf<Course>()
        val db = this.readableDatabase
        val cursor = db.query(TABLE_NAME,null,null,null,null,null,"$COLUMN_ID DESC")
        if(cursor.moveToFirst()){
            do {
                val courses = Course(
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TYPE_OF_CLASS)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DAY_OF_WEEK)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIME)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CAPACITY)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_DURATION)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRICE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LOCATION)),
                    cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_IMG)),
                )
                course.add(courses)
            }while (cursor.moveToNext())
        }
        cursor.close()
        return course
    }

    //update course
    fun updateCourse(course: Course): Int{
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_DAY_OF_WEEK,course.day_of_week)
            put(COLUMN_TYPE_OF_CLASS,course.type_of_class)
            put(COLUMN_TIME,course.time_of_course)
            put(COLUMN_CAPACITY, course.capacity)
            put(COLUMN_DURATION,course.duration)
            put(COLUMN_PRICE,course.price_per_class)
            put(COLUMN_DESCRIPTION,course.description)
            put(COLUMN_LOCATION,course.location)
            put(COLUMN_IMG,course.images)
        }
        //where query and update course
        return db.update(TABLE_NAME,values,"$COLUMN_ID = ?", arrayOf(course.course_id.toString()))
    }

    //delete Course
    fun deleteCourse(courseID: Int):Int {
        val db = this.writableDatabase
        //where query and delete task
        return db.delete(TABLE_NAME,"$COLUMN_ID = ?", arrayOf(courseID.toString()))
    }

    fun deleteAll(){
        val db = this.writableDatabase
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)


    }




}


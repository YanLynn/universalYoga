package com.example.universalyoga.Models

import java.sql.Blob

class Course (
       val type_of_class: String = "",
       val course_id:Int = 0,

       val day_of_week: String = "",
       val time_of_course: String = "",

       val duration:Int = 0,
       val capacity: Int = 0,
       val price_per_class: Double = 0.0,
       val description: String = "",
       val location: String = "",
       val images: ByteArray
)
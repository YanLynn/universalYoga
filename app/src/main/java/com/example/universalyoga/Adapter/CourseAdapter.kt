package com.example.universalyoga.Adapter


import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.universalyoga.Activity.DetailCourseActivity
import com.example.universalyoga.DBHelper.CourseDBHelper
import com.example.universalyoga.Models.Course
import com.example.universalyoga.byteArrayToBitmap
import com.example.universalyoga.databinding.CardViewBinding

class CourseAdapter(private val course: List<Course>,private val dbHelper: CourseDBHelper) :RecyclerView.Adapter<CourseAdapter.CourseViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CourseAdapter.CourseViewHolder {
        val binding = CardViewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CourseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val course = course[position]
        holder.bind(course)
    }

    override fun getItemCount(): Int = course.size

 inner class CourseViewHolder(private val binding: CardViewBinding) : RecyclerView.ViewHolder(binding.root){
     fun bind(course: Course) {
         val bitmap = byteArrayToBitmap(course.images)
         binding.courseImage.setImageBitmap(bitmap)
         "${course.type_of_class} | ${course.day_of_week} | ${course.time_of_course}".also { binding.classDayTime.text = it }
         "Duration - ${course.duration} | Capacity - ${course.capacity}".also { binding.DurationCapacity.text = it }
         "Price - $${course.price_per_class.toString()}".also { binding.price.text = it }
         binding.description.text = course.description
         binding.location.text = course.location

         binding.cardView.setOnClickListener{
            val intent = Intent(binding.root.context, DetailCourseActivity::class.java)
                intent.putExtra("COURSE_ID",course.course_id)
                
                binding.root.context.startActivity(intent)
         }
     }
 }

}
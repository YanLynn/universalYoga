package com.example.universalyoga.Adapter


import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.universalyoga.Activity.Course.DetailCourseActivity
import com.example.universalyoga.DBHelper.CourseDBHelper
import com.example.universalyoga.Models.Course
import com.example.universalyoga.byteArrayToBitmap
import com.example.universalyoga.databinding.CardViewBinding

class CourseAdapter(private val course: List<Course>,private val dbHelper: CourseDBHelper) :RecyclerView.Adapter<CourseAdapter.CourseViewHolder>(){
    private var filterCourseList: List<Course> = course
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



    override fun getItemCount(): Int {
        return filterCourseList.size
    }


    //to display on cardView
    inner class CourseViewHolder(private val binding: CardViewBinding) : RecyclerView.ViewHolder(binding.root){
     @SuppressLint("SuspiciousIndentation")
     fun bind(course: Course) {
         val bitmap = byteArrayToBitmap(course.images)
         binding.courseImage.setImageBitmap(bitmap)
         "${course.type_of_class} | ${course.day_of_week} | ${course.time_of_course}".also { binding.classDayTime.text = it }
         "Duration - ${course.duration} | Capacity - ${course.capacity}".also { binding.DurationCapacity.text = it }
         "Price - $${course.price_per_class.toString()}".also { binding.price.text = it }
         binding.description.text = course.description
         binding.location.text = course.location

         //cardView onClick action
         binding.cardView.setOnClickListener{
            val intent = Intent(binding.root.context, DetailCourseActivity::class.java)
                intent.putExtra("COURSE_ID",course.course_id)
                
                binding.root.context.startActivity(intent)
         }
     }
 }

    //filter search in appbar
    @SuppressLint("NotifyDataSetChanged")
    fun filter(query: String) {
        filterCourseList = if(query.isEmpty()){
            course
        }else{
            course.filter {
                it.location.contains(query,ignoreCase = true) ||
                it.description.contains(query, ignoreCase = true) ||
                it.type_of_class.contains(query, ignoreCase = true) ||
                it.day_of_week.contains(query, ignoreCase = true)
            }
        }
        notifyDataSetChanged()
    }

}
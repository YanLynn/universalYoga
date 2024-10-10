package com.example.universalyoga.Adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.universalyoga.DBHelper.CourseDBHelper
import com.example.universalyoga.Models.Course
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

     }

 }

}
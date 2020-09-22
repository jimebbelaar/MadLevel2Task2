package com.example.madlevel2task2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.madlevel2task2.adapter.QuestionAdapter
import com.example.madlevel2task2.databinding.ActivityMainBinding
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val questions = arrayListOf<Question>()
    private val questionAdapter = QuestionAdapter(questions)
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        binding.rvQuestions.layoutManager =
            StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        binding.rvQuestions.adapter = questionAdapter

        for (i in Question.QUESTIONS.indices) {
            questions.add(Question(Question.QUESTIONS[i], Question.ANSWERS[i]))
        }
        questionAdapter.notifyDataSetChanged()
        createItemTouchHelper().attachToRecyclerView(binding.rvQuestions)
    }

    private fun createItemTouchHelper(): ItemTouchHelper {

        // Callback which is used to create the ItemTouch helper. Only enables left swipe.
        // Use ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) to also enable right swipe.
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            // Enables or Disables the ability to move items up and down.
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            // Callback triggered when a user swiped an item.
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition

                if ((direction == ItemTouchHelper.LEFT && !questions[position].answer) ||
                    (direction == ItemTouchHelper.RIGHT && questions[position].answer)
                ) {
                    Snackbar.make(
                        rvQuestions,
                        getString(R.string.correct, questions[position].answer),
                        Snackbar.LENGTH_SHORT
                    ).show()
                    questions.removeAt(position)
                    questionAdapter.notifyDataSetChanged()
                } else {
                    Snackbar.make(
                        rvQuestions,
                        getString(R.string.incorrect, questions[position].answer),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }

                questionAdapter.notifyItemChanged(position)
            }
        }
        return ItemTouchHelper(callback)
    }
}
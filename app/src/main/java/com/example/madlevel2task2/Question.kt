package com.example.madlevel2task2

data class Question (
    var question: String,
    var answer: Boolean
    ) {
    companion object {
        val QUESTIONS = arrayOf (
            "The current year is 2020",
            "1 + 1 = 3",
            "China is the country with the most inhabitants"
        )

        val ANSWERS = arrayOf(
            true,
            false,
            true
        )
    }
}
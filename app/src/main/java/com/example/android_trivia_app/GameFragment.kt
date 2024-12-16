package com.example.android_trivia_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.android_trivia_app.databinding.FragmentGameBinding

class GameFragment : Fragment() {
    private lateinit var binding: FragmentGameBinding

    data class Question(val text: String, val answers: List<String>)
    private val questions: MutableList<Question> = mutableListOf(
        Question(
            text = "What is Android Jetpack?",
            answers = listOf("all of these", "tools", "documentation", "libraries")
        ),
        Question(
            text = "Base class for Layout?",
            answers = listOf("ViewGroup", "ViewSet", "ViewCollection", "ViewRoot")
        ),
        Question(
            text = "Layout for complex Screens?",
            answers = listOf("ConstraintLayout", "GridLayout", "LinearLayout", "FrameLayout")
        ),
        Question(
            text = "Pushing structured data into a Layout?",
            answers = listOf("Data Binding", "Data Pushing", "Set Text", "OnClick")
        ),
        Question(
            text = "Inflate layout in fragments?",
            answers = listOf("onCreateView", "onViewCreated", "onCreateLayout", "onInflateLayout")
        ),
        Question(
            text = "Build system for Android?",
            answers = listOf("Gradle", "Graddle", "Grodle", "Groyle")
        ),
        Question(
            text = "Android vector format?",
            answers = listOf(
                "VectorDrawable",
                "AndroidVectorDrawable",
                "DrawableVector",
                "AndroidVector"
            )
        ),
        Question(
            text = "Android Navigation Component?",
            answers = listOf("NavController", "NavCentral", "NavMaster", "NavSwitcher")
        ),
        Question(
            text = "Registers app with launcher?",
            answers = listOf("intent-filter", "app-registry", "launcher-registry", "app-launcher")
        ),
        Question(
            text = "Mark a layout for Data Binding?",
            answers = listOf("<layout>", "<binding>", "<data-binding>", "<dbinding>")
        )
    )

    lateinit var currentQuestion: Question
    lateinit var answers: MutableList<String>

    private var questionIndex = 0
    private val numQuestions = Math.min((questions.size + 1) / 2, 3)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentGameBinding>(
            inflater, R.layout.fragment_game, container, false
        )

        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.appToolbar.setupWithNavController(navController, appBarConfiguration)

        randomizeQuestions()
        binding.game = this

        binding.apply {
            submitButton.setOnClickListener {
                val checkedButtonId = questionRadioGroup.checkedRadioButtonId
                if (checkedButtonId == -1) return@setOnClickListener

                var answerIndex = 0
                when (checkedButtonId) {
                    secondAnswerRadioButton.id -> answerIndex = 1
                    thirdAnswerRadioButton.id -> answerIndex = 2
                    fourthAnswerRadioButton.id -> answerIndex = 3
                }

                val correctAnswer = currentQuestion.answers[0]
                if (answers[answerIndex] != correctAnswer) {
                    navController.navigate(R.id.action_gameFragment_to_gameOverFragment)
                    return@setOnClickListener
                }

                questionIndex++

                if (questionIndex == numQuestions) {
                    navController.navigate(R.id.action_gameFragment_to_gameWonFragment)
                    return@setOnClickListener
                }

                setQuestion()
                invalidateAll()
            }
        }

        return binding.root
    }

    private fun randomizeQuestions() {
        questions.shuffle()
        questionIndex = 0
        setQuestion()
    }

    private fun setQuestion() {
        currentQuestion = questions[questionIndex]
        answers = currentQuestion.answers.toMutableList()
        answers.shuffle()

        binding.appToolbar.title =
            getString(R.string.title_android_trivia_question, questionIndex + 1, numQuestions)
    }
}
























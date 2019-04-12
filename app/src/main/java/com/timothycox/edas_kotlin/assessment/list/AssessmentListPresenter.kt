package com.timothycox.edas_kotlin.assessment.list

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.timothycox.edas_kotlin.model.Assessment
import com.timothycox.edas_kotlin.model.Question
import com.timothycox.edas_kotlin.model.User
import com.timothycox.edas_kotlin.util.Firebase

import java.util.ArrayList
import java.util.HashMap

internal class AssessmentListPresenter(private val view: AssessmentListContract.View, private val user: User) :
    AssessmentListContract.Presenter {
    private val firebase: Firebase


    init {
        firebase = Firebase.instance
    }

    override fun create() {
        val databaseReference = firebase.databaseReference
            .child("server")
            .child("users")
            .child(user.uid!!)
            .child("examinees")

        //todo fix this
        firebase.access(true, databaseReference, object : Firebase.OnGetDataListener {
            override fun onSuccess(dataSnapshot: DataSnapshot) {
                var assessmentLevel: String?
                var examinee: String? = ""
                var timestamp: String? = ""
                val assessmentList = ArrayList<Assessment>()
                val questionList = ArrayList<Question>()
                val examinees = dataSnapshot.children
                var savedAssessments: Iterable<DataSnapshot>
                var savedQuestionAnswers: Iterable<DataSnapshot>

                for (currentExaminee in examinees) {
                    examinee = currentExaminee.child("name").getValue(String::class.java)
                    savedAssessments = currentExaminee.child("savedAssessments").children
                    for (currentSavedAssessment in savedAssessments) {
                        timestamp = currentSavedAssessment.child("timestamp").getValue(String::class.java)
                        assessmentLevel = currentSavedAssessment.child("assessmentLevel").getValue(String::class.java)
                        when (assessmentLevel) {
                            "12-month" -> {
                                run {
                                    savedQuestionAnswers = currentSavedAssessment.child("12-month").children
                                    for (questionAnswer in savedQuestionAnswers) {
                                        questionList.add(questionAnswer.getValue(Question::class.java)!!)
                                    }
                                }
                                run {
                                    savedQuestionAnswers = currentSavedAssessment.child("24-month").children
                                    for (questionAnswer in savedQuestionAnswers) {
                                        questionList.add(questionAnswer.getValue(Question::class.java)!!)
                                    }
                                }
                                run {
                                    savedQuestionAnswers = currentSavedAssessment.child("36-month").children
                                    for (questionAnswer in savedQuestionAnswers) {
                                        questionList.add(questionAnswer.getValue(Question::class.java)!!)
                                    }
                                }
                                run {
                                    savedQuestionAnswers = currentSavedAssessment.child("60-month").children
                                    for (questionAnswer in savedQuestionAnswers) {
                                        questionList.add(questionAnswer.getValue(Question::class.java)!!)
                                    }
                                }
                            }
                            "24-month" -> {
                                run {
                                    savedQuestionAnswers = currentSavedAssessment.child("24-month").children
                                    for (questionAnswer in savedQuestionAnswers) {
                                        questionList.add(questionAnswer.getValue(Question::class.java)!!)
                                    }
                                }
                                run {
                                    savedQuestionAnswers = currentSavedAssessment.child("36-month").children
                                    for (questionAnswer in savedQuestionAnswers) {
                                        questionList.add(questionAnswer.getValue(Question::class.java)!!)
                                    }
                                }
                                run {
                                    savedQuestionAnswers = currentSavedAssessment.child("60-month").children
                                    for (questionAnswer in savedQuestionAnswers) {
                                        questionList.add(questionAnswer.getValue(Question::class.java)!!)
                                    }
                                }
                            }
                            "36-month" -> {
                                run {
                                    savedQuestionAnswers = currentSavedAssessment.child("36-month").children
                                    for (questionAnswer in savedQuestionAnswers) {
                                        questionList.add(questionAnswer.getValue(Question::class.java)!!)
                                    }
                                }
                                run {
                                    savedQuestionAnswers = currentSavedAssessment.child("60-month").children
                                    for (questionAnswer in savedQuestionAnswers) {
                                        questionList.add(questionAnswer.getValue(Question::class.java)!!)
                                    }
                                }
                            }
                            "60-month" -> {
                                savedQuestionAnswers = currentSavedAssessment.child("60-month").children
                                for (questionAnswer in savedQuestionAnswers) {
                                    questionList.add(questionAnswer.getValue(Question::class.java)!!)
                                }
                            }
                        }

                        savedQuestionAnswers = currentSavedAssessment.child("common").children
                        for (questionAnswer in savedQuestionAnswers) {
                            var map = HashMap<String, String>()
                            map = questionAnswer.value as HashMap<String, String>
                            val question = Question()

                            question.setId(Integer.parseInt(map["id"]!!))
                            question.importance = map["importance"]
                            question.questionText = map["questionText"]

                            questionList.add(question)
                        }
                        //todo fix this
                        val completed = true
                        val result = 100
                        //                        assessmentList.add(new Assessment(questionList, assessmentLevel, examinee, timestamp, completed, result));
                    }
                }
                view.setRecyclerViewAdapter(AssessmentRecyclerViewAdapter(assessmentList))
            }


            override fun onFailure(databaseError: DatabaseError) {

            }
        })
    }
}

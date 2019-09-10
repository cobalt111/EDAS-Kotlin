package com.timothycox.edas_kotlin.assessment.list

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.timothycox.edas_kotlin.model.Assessment
import com.timothycox.edas_kotlin.model.Question
import com.timothycox.edas_kotlin.model.User
import com.timothycox.edas_kotlin.util.Firebase

internal class AssessmentListPresenter(
    private val view: AssessmentListContract.View,
    private val user: User) :
    AssessmentListContract.Presenter {

    private val firebase: Firebase = Firebase.instance

    override fun create() {
        val databaseReference = firebase.databaseReference
            .child("server")
            .child("users")
            .child(user.uid!!)
            .child("examinees")

        //todo fix this
        firebase.access(true, databaseReference, object : Firebase.OnGetDataListener {
            override fun onSuccess(dataSnapshot: DataSnapshot) {
                val assessments = mutableListOf<MutableList<Assessment>>()
                dataSnapshot.children.forEach { existingExaminee ->
                    existingExaminee.child("assessments").children.forEach { currentAssessment ->
                        val assessment = Assessment(
                            currentAssessment.child("category").getValue(String::class.java),
                            existingExaminee.child("name").getValue(String::class.java),
                            currentAssessment.child("timestamp").getValue(String::class.java)
                        )
                        assessment.isCompleted = currentAssessment.child("isCompleted").getValue(Boolean::class.java)
                        assessment.result = currentAssessment.child("result").getValue(Double::class.java)
                        currentAssessment.child("assessment").children.forEach { category ->
                            category.children.forEach { currentQuestion ->
                                assessment.questions[category.getValue(String::class.java)]!![currentQuestion.key!!.toInt()] = currentQuestion.getValue(Question::class.java)
                            }
                        }
//                        currentAssessment.child("answeredList").children.forEach { currentCategory ->
//                            currentCategory.children.forEach { currentAnswerStatus ->
//                                if (currentAnswerStatus.getValue(Boolean::class.java)) {
//                                    currentAssessment.child("assessment").child(currentCategory.key.toString()).children.forEach { currentAnswer ->
//                                        assessment.questions[assessment.category!!]!!.add(Answer(
//                                            currentAnswer.key?.toInt(),
//                                            currentAnswer.getValue(String::class.java)
//                                        ))
//                                    }
//                                }
//                            }
//                        }
//
//                        currentAssessment.child("assessment").child(assessment.category!!).children.forEach { currentAnswer ->
//                            assessment.questions[assessment.category!!]!!.add(Answer(
//                                currentAnswer.key?.toInt(),
//                                currentAnswer.getValue(String::class.java)
//                            ))
//                        }
//                        currentAssessment.child("assessment").child("common").children.forEach { currentAnswer ->
//                            assessment.questions[assessment.category!!]!!.add(Answer(
//                                currentAnswer.key?.toInt(),
//                                currentAnswer.getValue(String::class.java)
//                            ))
//                        }
                    }
                }


//                var assessmentLevel: String?
//                var examinee: String? = ""
//                var timestamp: String? = ""
//                val assessmentList = ArrayList<Assessment>()
//                val questionList = ArrayList<Question>()
//                val examinees = dataSnapshot.children
//                var savedAssessments: Iterable<DataSnapshot>
//                var savedQuestionAnswers: Iterable<DataSnapshot>
//
//                for (currentExaminee in examinees) {
//                    examinee = currentExaminee.child("name").getValue(String::class.java)
//                    savedAssessments = currentExaminee.child("savedAssessments").children
//                    for (currentSavedAssessment in savedAssessments) {
//                        timestamp = currentSavedAssessment.child("timestamp").getValue(String::class.java)
//                        assessmentLevel = currentSavedAssessment.child("assessmentLevel").getValue(String::class.java)
//                        when (assessmentLevel) {
//                            "12-month" -> {
//                                run {
//                                    savedQuestionAnswers = currentSavedAssessment.child("12-month").children
//                                    for (questionAnswer in savedQuestionAnswers) {
//                                        questionList.add(questionAnswer.getValue(Question::class.java)!!)
//                                    }
//                                }
//                                run {
//                                    savedQuestionAnswers = currentSavedAssessment.child("24-month").children
//                                    for (questionAnswer in savedQuestionAnswers) {
//                                        questionList.add(questionAnswer.getValue(Question::class.java)!!)
//                                    }
//                                }
//                                run {
//                                    savedQuestionAnswers = currentSavedAssessment.child("36-month").children
//                                    for (questionAnswer in savedQuestionAnswers) {
//                                        questionList.add(questionAnswer.getValue(Question::class.java)!!)
//                                    }
//                                }
//                                run {
//                                    savedQuestionAnswers = currentSavedAssessment.child("60-month").children
//                                    for (questionAnswer in savedQuestionAnswers) {
//                                        questionList.add(questionAnswer.getValue(Question::class.java)!!)
//                                    }
//                                }
//                            }
//                            "24-month" -> {
//                                run {
//                                    savedQuestionAnswers = currentSavedAssessment.child("24-month").children
//                                    for (questionAnswer in savedQuestionAnswers) {
//                                        questionList.add(questionAnswer.getValue(Question::class.java)!!)
//                                    }
//                                }
//                                run {
//                                    savedQuestionAnswers = currentSavedAssessment.child("36-month").children
//                                    for (questionAnswer in savedQuestionAnswers) {
//                                        questionList.add(questionAnswer.getValue(Question::class.java)!!)
//                                    }
//                                }
//                                run {
//                                    savedQuestionAnswers = currentSavedAssessment.child("60-month").children
//                                    for (questionAnswer in savedQuestionAnswers) {
//                                        questionList.add(questionAnswer.getValue(Question::class.java)!!)
//                                    }
//                                }
//                            }
//                            "36-month" -> {
//                                run {
//                                    savedQuestionAnswers = currentSavedAssessment.child("36-month").children
//                                    for (questionAnswer in savedQuestionAnswers) {
//                                        questionList.add(questionAnswer.getValue(Question::class.java)!!)
//                                    }
//                                }
//                                run {
//                                    savedQuestionAnswers = currentSavedAssessment.child("60-month").children
//                                    for (questionAnswer in savedQuestionAnswers) {
//                                        questionList.add(questionAnswer.getValue(Question::class.java)!!)
//                                    }
//                                }
//                            }
//                            "60-month" -> {
//                                savedQuestionAnswers = currentSavedAssessment.child("60-month").children
//                                for (questionAnswer in savedQuestionAnswers) {
//                                    questionList.add(questionAnswer.getValue(Question::class.java)!!)
//                                }
//                            }
//                        }
//
//                        savedQuestionAnswers = currentSavedAssessment.child("common").children
//                        for (questionAnswer in savedQuestionAnswers) {
//                            var map = HashMap<String, String>()
//                            map = questionAnswer.value as HashMap<String, String>
//                            val question = Question(
//                                map["id"]!!.toInt(),
//                                map["importance"],
//                                map["questionText"],
//
//                            )
//                            questionList.add(question)
//                        }
//                        //todo fix this
//                        val completed = true
//                        val result = 100
//                        //                        assessmentList.add(new Assessment(questionList, assessmentLevel, examinee, timestamp, completed, result));
//                    }
//                }

                view.setRecyclerViewAdapter(AssessmentRecyclerViewAdapter(assessments.toList()))
            }


            override fun onFailure(databaseError: DatabaseError) {

            }
        })
    }
}

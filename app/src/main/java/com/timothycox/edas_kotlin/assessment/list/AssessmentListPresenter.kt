package com.timothycox.edas_kotlin.assessment.list

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.timothycox.edas_kotlin.model.Answer
import com.timothycox.edas_kotlin.model.Response
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
                val responses = mutableListOf<MutableList<Response>>()
                dataSnapshot.children.forEach { existingExaminee ->
                    existingExaminee.child("responses").children.forEach { currentResponse ->
                        val response = Response(
                            currentResponse.child("category").value as String,
                            currentResponse.child("timestamp").value as String,
                            existingExaminee.child("name").value as String
                        )
                        response.isCompleted = currentResponse.child("isCompleted").value as Boolean
                        response.answers = mutableListOf()
                        if (response.isCompleted!!) {
                            response.result = currentResponse.child("result").value as Double
                            currentResponse.child("response").child(response.category!!).children.forEach { currentAnswer ->
                                response.answers!![0].add(Answer(
                                    currentAnswer.key?.toInt(),
                                    currentAnswer.value as Int
                                ))
                            }
                            currentResponse.child("response").child("common").children.forEach { currentAnswer ->
                                response.answers!![1].add(Answer(
                                    currentAnswer.key?.toInt(),
                                    // todo test if this getValue style works
                                    currentAnswer.value as Int
                                ))
                            }
                        }
                        else {
                            response.result = -1.0

                            currentResponse.child("answeredList").children.forEach { currentCategory ->
                                currentCategory.children.forEach { currentAnswerStatus ->
                                    if (currentAnswerStatus.value as Boolean) {
                                        currentResponse.child("response").child(currentCategory.key.toString()).children.forEach { currentAnswer ->
                                            response.answers!![0].add(Answer(
                                                currentAnswer.key?.toInt(),
                                                currentAnswer.value as Int
                                            ))
                                        }
                                    }
                                }
                            }

                            currentResponse.child("response").child(response.category!!).children.forEach { currentAnswer ->
                                response.answers!![0].add(Answer(
                                    currentAnswer.key?.toInt(),
                                    currentAnswer.value as Int
                                ))
                            }
                            currentResponse.child("response").child("common").children.forEach { currentAnswer ->
                                response.answers!![1].add(Answer(
                                    currentAnswer.key?.toInt(),
                                    currentAnswer.value as Int
                                ))
                            }
                        }

                    }
                }


//                var assessmentLevel: String?
//                var examinee: String? = ""
//                var timestamp: String? = ""
//                val responseList = ArrayList<Assessment>()
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
//                        //                        responseList.add(new Assessment(questionList, assessmentLevel, examinee, timestamp, completed, result));
//                    }
//                }

                view.setRecyclerViewAdapter(AssessmentRecyclerViewAdapter(responses.toList()))
            }


            override fun onFailure(databaseError: DatabaseError) {

            }
        })
    }
}

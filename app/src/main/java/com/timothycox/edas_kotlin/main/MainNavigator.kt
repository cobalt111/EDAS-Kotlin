package com.timothycox.edas_kotlin.main

import android.content.Context
import android.content.Intent
import android.os.Bundle

import com.timothycox.edas_kotlin.assessment.list.AssessmentListActivity
import com.timothycox.edas_kotlin.examinees.ExamineesListActivity

internal class MainNavigator(private val context: Context) : MainActivity.MainScreenEvents {

    override fun navigateTo(id: Int, bundle: Bundle?) {
        when (id) {
            EXAMINEES_LIST_ACTIVITY -> {
                val intent = Intent(context, ExamineesListActivity::class.java)
                if (bundle != null) intent.putExtra("mainBundle", bundle)
                context.startActivity(intent)
            }
            ASSESSMENT_LIST_ACTIVITY -> {
                val intent = Intent(context, AssessmentListActivity::class.java)
                if (bundle != null) intent.putExtra("mainBundle", bundle)
                context.startActivity(intent)
            }
        }
    }

    companion object {
        const val EXAMINEES_LIST_ACTIVITY = 2
        const val ASSESSMENT_LIST_ACTIVITY = 3
    }
}

package com.timothycox.edas_kotlin.main

import android.content.Context
import android.content.Intent
import android.os.Bundle

import com.timothycox.edas_kotlin.assessment.list.AssessmentListActivity
import com.timothycox.edas_kotlin.examinees.ExamineesActivity

internal class MainNavigator(private val context: Context) : MainActivity.MainScreenEvents {

    override fun itemClicked(id: Int, bundle: Bundle?) {
        when (id) {
            EXAMINEES_ACTIVITY -> {
                val intent = Intent(context, ExamineesActivity::class.java)
                if (bundle != null) intent.putExtra("userBundle", bundle)
                context.startActivity(intent)
            }
            ASSESSMENT_LIST_ACTIVITY -> {
                val intent = Intent(context, AssessmentListActivity::class.java)
                if (bundle != null) intent.putExtra("userBundle", bundle)
                context.startActivity(intent)
            }
        }
    }

    companion object {
        const val EXAMINEES_ACTIVITY = 2
        const val ASSESSMENT_LIST_ACTIVITY = 3
    }
}

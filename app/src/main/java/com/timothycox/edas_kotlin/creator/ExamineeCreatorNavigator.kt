package com.timothycox.edas_kotlin.creator

import android.content.Context
import android.content.Intent
import android.os.Bundle

import com.timothycox.edas_kotlin.assessment.AssessmentActivity

internal class ExamineeCreatorNavigator(private val context: Context) : ExamineeCreatorActivity.CreatorScreenEvents {

    override fun navigateTo(id: Int, bundle: Bundle?) {
        when (id) {
            ASSESSMENTS_ACTIVITY -> {
                val intent = Intent(context, AssessmentActivity::class.java)
                if (bundle != null) intent.putExtra("examineeCreatorBundle", bundle)
                context.startActivity(intent)
            }
        }
    }

    companion object {
        const val ASSESSMENTS_ACTIVITY = 1
    }
}

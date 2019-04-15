package com.timothycox.edas_kotlin.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.timothycox.edas_kotlin.assessment.AssessmentActivity
import com.timothycox.edas_kotlin.result.ResultActivity

internal class ExamineeProfileNavigator(private val context: Context) : ExamineeProfileActivity.ProfileClickEvents {

    override fun navigateTo(id: Int, bundle: Bundle?) {
        when (id) {
            ASSESSMENT_ACTIVITY -> {
                val intent = Intent(context, AssessmentActivity::class.java)
                if (bundle != null) intent.putExtra("examineeProfileBundle", bundle)
                context.startActivity(intent)
            }
            RESULT_ACTIVITY -> {
                val intent = Intent(context, ResultActivity::class.java)
                if (bundle != null) intent.putExtra("examineeProfileBundle", bundle)
                context.startActivity(intent)
            }
        }
    }

    companion object {
        const val ASSESSMENT_ACTIVITY = 1
        const val RESULT_ACTIVITY = 2
    }
}

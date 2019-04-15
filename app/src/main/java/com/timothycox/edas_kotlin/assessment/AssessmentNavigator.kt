package com.timothycox.edas_kotlin.assessment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.timothycox.edas_kotlin.result.ResultActivity

internal class AssessmentNavigator(private val context: Context) : AssessmentActivity.AssessmentScreenEvents {
    override fun navigateTo(id: Int, bundle: Bundle?) {
        when (id) {
            RESULTS_ACTIVITY -> {
                val intent = Intent(context, ResultActivity::class.java)
                if (bundle != null) intent.putExtra("assessmentBundle", bundle)
                context.startActivity(intent)
            }
        }
    }

    companion object {
        const val RESULTS_ACTIVITY = 1
    }
}

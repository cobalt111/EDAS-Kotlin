package com.timothycox.edas_kotlin.result

import android.content.Context
import android.content.Intent
import android.os.Bundle

import com.timothycox.edas_kotlin.assessment.AssessmentActivity
import com.timothycox.edas_kotlin.information.InformationActivity

internal class ResultNavigator(private val context: Context) : ResultActivity.ResultScreenEvents {

    override fun itemClicked(id: Int, bundle: Bundle?) {
        when (id) {
            ASSESSMENT_ACTIVITY -> {
                val intent = Intent(context, AssessmentActivity::class.java)
                if (bundle != null) intent.putExtra("userBundle", bundle)
                context.startActivity(intent)
            }
            INFORMATION_ACTIVITY -> {
                val intent = Intent(context, InformationActivity::class.java)
                if (bundle != null) intent.putExtra("userBundle", bundle)
                context.startActivity(intent)
            }
        }
    }

    companion object {
        const val ASSESSMENT_ACTIVITY = 1
        const val INFORMATION_ACTIVITY = 2
    }
}

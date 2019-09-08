package com.timothycox.edas_kotlin.examinees

import android.content.Context
import android.content.Intent
import android.os.Bundle

import com.timothycox.edas_kotlin.creator.ExamineeCreatorActivity
import com.timothycox.edas_kotlin.profile.ExamineeProfileActivity

internal class ExamineesListNavigator(private val context: Context) : ExamineesListActivity.ExamineesScreenEvents {

    override fun navigateTo(id: Int, bundle: Bundle?) {
        when (id) {
            EXAMINEE_CREATOR_ACTIVITY -> {
                val intent = Intent(context, ExamineeCreatorActivity::class.java)
                if (bundle != null) intent.putExtra("examineeListBundle", bundle)
                context.startActivity(intent)
            }
            EXAMINEE_PROFILE_ACTIVITY -> {
                val intent = Intent(context, ExamineeProfileActivity::class.java)
                if (bundle != null) intent.putExtra("examineeListBundle", bundle)
                context.startActivity(intent)
            }
        }
    }

    companion object {
        const val EXAMINEE_CREATOR_ACTIVITY = 1
        const val EXAMINEE_PROFILE_ACTIVITY = 2
    }
}

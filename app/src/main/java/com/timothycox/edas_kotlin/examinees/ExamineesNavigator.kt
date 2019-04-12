package com.timothycox.edas_kotlin.examinees

import android.content.Context
import android.content.Intent
import android.os.Bundle

import com.timothycox.edas_kotlin.creator.ExamineeCreatorActivity

internal class ExamineesNavigator(private val context: Context) : ExamineesActivity.ExamineesScreenEvents {

    override fun itemClicked(id: Int, bundle: Bundle?) {
        when (id) {
            EXAMINEE_CREATOR_ACTIVITY -> {
                val intent = Intent(context, ExamineeCreatorActivity::class.java)
                if (bundle != null) intent.putExtra("userBundle", bundle)
                context.startActivity(intent)
            }
        }
    }

    companion object {
        const val EXAMINEE_CREATOR_ACTIVITY = 1
    }
}

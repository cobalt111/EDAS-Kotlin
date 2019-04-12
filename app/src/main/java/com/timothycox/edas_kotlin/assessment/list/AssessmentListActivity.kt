package com.timothycox.edas_kotlin.assessment.list

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

import com.timothycox.edas_kotlin.R
import com.timothycox.edas_kotlin.model.User

class AssessmentListActivity : AppCompatActivity(), AssessmentListContract.View {

    private val adapter: AssessmentRecyclerViewAdapter? = null
    private val layoutManager: LinearLayoutManager? = null
    private var presenter: AssessmentListPresenter? = null
    private var navigator: AssessmentListNavigator? = null

    private var assessmentRecyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_assessment_list)

        presenter = AssessmentListPresenter(
            this,
            intent.getBundleExtra("userBundle").get("user") as User
        )
        navigator = AssessmentListNavigator(this)

        //        List<Question> questions = new ArrayList<>();
        //        List<Assessment> assessments = new ArrayList<>();
        //        Question sampleQuestion = new Question();
        //        sampleQuestion.setQuestionText("This is sample answer");
        //        sampleQuestion.setImportance("high");
        //        sampleQuestion.setId(4);
        //        questions.add(sampleQuestion);
        //        Assessment sampleAssessment = new Assessment(questions, "12-month", "Isaac", "11-2-18", true, 100);
        //        assessments.add(sampleAssessment);
        //
        //        sampleQuestion.setQuestionText("This is sample answer");
        //        sampleQuestion.setImportance("high");
        //        sampleQuestion.setId(4);
        //        questions.add(sampleQuestion);
        //        sampleAssessment = new Assessment(questions, "12-month", "Beth", "12-8-17", true, 100);
        //        assessments.add(sampleAssessment);
        //
        //        sampleQuestion.setQuestionText("This is sample answer");
        //        sampleQuestion.setImportance("high");
        //        sampleQuestion.setId(4);
        //        questions.add(sampleQuestion);
        //        sampleAssessment = new Assessment(questions, "12-month", "Oscar", "1-8-16", true, 100);
        //        assessments.add(sampleAssessment);
        //
        //        sampleQuestion.setQuestionText("This is sample answer");
        //        sampleQuestion.setImportance("high");
        //        sampleQuestion.setId(4);
        //        questions.add(sampleQuestion);
        //        sampleAssessment = new Assessment(questions, "12-month", "Penny", "3-8-18", true, 100);
        //        assessments.add(sampleAssessment);
        //
        //        sampleQuestion.setQuestionText("This is sample answer");
        //        sampleQuestion.setImportance("high");
        //        sampleQuestion.setId(4);
        //        questions.add(sampleQuestion);
        //        sampleAssessment = new Assessment(questions, "12-month", "Wilbur", "11-8-18", true, 100);
        //        assessments.add(sampleAssessment);
        //
        //        sampleQuestion.setQuestionText("This is sample answer");
        //        sampleQuestion.setImportance("high");
        //        sampleQuestion.setId(4);
        //        questions.add(sampleQuestion);
        //        sampleAssessment = new Assessment(questions, "12-month", "Rachel", "11-8-18", true, 100);
        //        assessments.add(sampleAssessment);
        //
        //        sampleQuestion.setQuestionText("This is sample answer");
        //        sampleQuestion.setImportance("high");
        //        sampleQuestion.setId(4);
        //        questions.add(sampleQuestion);
        //        sampleAssessment = new Assessment(questions, "12-month", "Harry", "11-8-18", true, 100);
        //        assessments.add(sampleAssessment);
        //
        //        sampleQuestion.setQuestionText("This is sample answer");
        //        sampleQuestion.setImportance("high");
        //        sampleQuestion.setId(4);
        //        questions.add(sampleQuestion);
        //        sampleAssessment = new Assessment(questions, "12-month", "Joe", "11-8-18", true, 100);
        //        assessments.add(sampleAssessment);
        //
        //        adapter = new AssessmentRecyclerViewAdapter(assessments);
        //        assessmentRecyclerView.setAdapter(adapter);
        //        assessmentRecyclerView.setHasFixedSize(true);
        //        layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        //        assessmentRecyclerView.setLayoutManager(layoutManager);
        //        assessmentRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //        assessmentRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(),
        //                assessmentRecyclerView, new RecyclerTouchListener.ClickListener() {
        //            @Override
        //            public void onClick(View view, int position) {
        //                Intent openAssessmentIntent = new Intent(getApplicationContext(), AssessmentActivity.class);
        //                startActivity(openAssessmentIntent);
        //            }
        //
        //            @Override
        //            public void onLongClick(View view, int position) {
        //
        //            }
        //        }));
    }

    override fun setRecyclerViewAdapter(adapter: AssessmentRecyclerViewAdapter) {
        assessmentRecyclerView!!.adapter = adapter
    }

    internal interface AssessmentListScreenEvents
}

package com.timothycox.edas_kotlin.assessment.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.timothycox.edas_kotlin.R
import com.timothycox.edas_kotlin.model.Assessment
import kotlinx.android.synthetic.main.fragment_assessment.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [AssessmentFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [AssessmentFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class AssessmentFragment : Fragment(), AssessmentFragmentContract.View {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    private var presenter: AssessmentFragmentPresenter? = null
    private var navigator: AssessmentFragmentNavigator? = null

    //<editor-fold defaultstate="collapsed" desc="Fragment Lifecycle">
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        // todo fix
//        presenter = AssessmentFragmentPresenter(this, )
        navigator = AssessmentFragmentNavigator(activity!!.applicationContext)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        assessmentFragmentAnswerOneButton.setOnClickListener { onClickAnswerOne() }
        assessmentFragmentAnswerTwoButton.setOnClickListener { onClickAnswerTwo() }
        assessmentFragmentAnswerThreeButton.setOnClickListener { onClickAnswerThree() }
        presenter?.create()
        return inflater.inflate(R.layout.fragment_assessment, container, false)
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Click Events">
    override fun onClickAnswerOne() {
        presenter?.onAnswer(1)
    }

    override fun onClickAnswerTwo() {
        presenter?.onAnswer(2)
    }

    override fun onClickAnswerThree() {
        presenter?.onAnswer(3)
    }
    //</editor-fold>

    override fun populateUIWithData(assessment: Assessment) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AssessmentFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AssessmentFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    internal interface AssessmentFragmentScreenEvents {
        fun itemClicked(id: Int)
    }
}

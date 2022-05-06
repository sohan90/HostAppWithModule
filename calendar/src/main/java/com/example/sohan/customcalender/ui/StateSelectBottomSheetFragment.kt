package com.example.sohan.customcalender.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sohan.customcalender.R
import com.example.sohan.customcalender.model.StateModelResponse
import com.example.sohan.customcalender.ui.InteractiveCalendarApp.saveStateId
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.cust_cal_fragment_state_selection.view.*


class StateSelectBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var adapter: StateListAdapter

    private val tagName = StateSelectBottomSheetFragment::class.java.simpleName

    companion object {

        fun newInstance(stateResponse: ArrayList<StateModelResponse>): StateSelectBottomSheetFragment {
            val fragment = StateSelectBottomSheetFragment()
            val bundle = Bundle()
            bundle.putParcelableArrayList("extra_state_list", stateResponse)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.MyBottomSheetDialogTheme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.cust_cal_fragment_state_selection, container, false)
        initRecyclerView(view)
        setStateIfExist(view)
        return view
    }

    private fun setStateIfExist(view: View) {
        val stateId = InteractiveCalendarApp.getStateId()
        if (!TextUtils.isEmpty(stateId)) {
            val stateModelResponse = getStateListFromBundle().firstOrNull { it.id == stateId }
            view.cus_cal_stateTxt.text = stateModelResponse?.name
        }
    }

    private fun initRecyclerView(view: View) {
        val stateListModel: List<StateModelResponse> = getStateListFromBundle()
        val linearLayoutManager = LinearLayoutManager(context)

        adapter = StateListAdapter(stateListModel) {
            val landingActivity: LandingActivity = activity as LandingActivity
            saveStateId(it.id)
            landingActivity.updateCalendarWithHolidays(it.id)
            dismiss()
        }

        view.cus_cal_recyl.layoutManager = linearLayoutManager
        view.cus_cal_recyl.adapter = adapter
    }

    private fun getStateListFromBundle(): List<StateModelResponse> {
        val stateListModel: List<StateModelResponse> = arguments?.getParcelableArrayList("extra_state_list")!!
        return stateListModel
    }


}
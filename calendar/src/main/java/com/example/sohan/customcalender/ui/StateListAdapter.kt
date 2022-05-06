package com.example.sohan.customcalender.ui

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sohan.customcalender.R
import com.example.sohan.customcalender.model.StateModelResponse
import com.example.sohan.customcalender.ui.InteractiveCalendarApp.getStateId

class StateListAdapter(private val stateList: List<StateModelResponse>,
                       callBack: (StateModelResponse) -> Unit) : RecyclerView.Adapter<StateListAdapter.StateListViewHolder>() {

    private var callBack: (StateModelResponse) -> Unit = {}

    init {
        this.callBack = callBack
    }

    inner class StateListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var stateTxt: TextView? = null
        var selectImg: ImageView? = null

        init {
            stateTxt = view.findViewById(R.id.cus_cal_state_label)
            selectImg = view.findViewById(R.id.cus_cal_select_img)
        }

        fun bind(stateModel: StateModelResponse) {
            stateTxt?.text = stateModel.name
            selectImg?.visibility = View.GONE
            stateTxt?.isEnabled = false

            val stateId = getStateId()
            if (!TextUtils.isEmpty(stateId)) {
                if (stateModel.id == stateId) {
                    stateTxt?.isEnabled = true
                    selectImg?.visibility = View.VISIBLE
                }
            }
            itemView.setOnClickListener {
                val stateMod = stateList[adapterPosition]
                callBack(stateMod)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StateListViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cust_cal_item_state, parent, false)
        return StateListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: StateListViewHolder, position: Int) {
        val stateModel = stateList[position]
        holder.bind(stateModel)
    }

    override fun getItemCount(): Int {
        return stateList.size
    }
}
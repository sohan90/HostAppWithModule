package com.example.sohan.customcalender.util

import android.content.Context
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu


fun showPopupWindow(
    anchorView: View,
    context: Context,
    listOfYear: List<Int>,
    callBack: (Int) -> Unit
) {
    val popupWindow = PopupMenu(context, anchorView)
    listOfYear.map { popupWindow.menu.add(it.toString()) }
    popupWindow.setOnMenuItemClickListener { menuItem ->
        val selectedYear = listOfYear.filter { it.toString() == menuItem.title }[0]
        callBack(selectedYear)
        true
    }
    popupWindow.show()

}
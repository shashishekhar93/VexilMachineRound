package com.tech.vexilmachineround.adapter

import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.tech.vexilmachineround.fragments.AuditTrailTimeline
import com.tech.vexilmachineround.fragments.Documents
import com.tech.vexilmachineround.fragments.K_Y_C
import com.tech.vexilmachineround.fragments.LoanSummary
import com.tech.vexilmachineround.fragments.Profile
import com.tech.vexilmachineround.fragments.VehicleInspection
import com.tech.vexilmachineround.presentation.HomeActivity

class HomePagerAdapter(homeActivity: HomeActivity) : FragmentStateAdapter(homeActivity) {

    private val fragmentList = listOf(
        Profile(),
        K_Y_C(),
        VehicleInspection(),
        LoanSummary(),
        Documents(),
        AuditTrailTimeline()
    )

    private val titles = listOf(
        "Profile",
        "KYC",
        "Vehicle Inspection",
        "Loan Summary",
        "Documents",
        "Audit Trail Timeline"
    )

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    fun getTitleOfFragment(position: Int): String{
        return titles[position]
    }
}
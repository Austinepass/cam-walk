package com.orgustine.camapp.data

import androidx.lifecycle.ViewModel
import com.orgustine.camapp.data.model.Project

class ProjectViewModel : ViewModel() {
    //Dummy ViewModel data, will fix this later
     var deviceList = arrayListOf(
        Project(1, "Hospital Construction Site"),
    )

    var cloudList = arrayListOf(
        Project(1, "Local Government Agency"),
        Project(2, "Stadium Renovation Project"),
        Project(3, "Market Construction Center")
    )

}
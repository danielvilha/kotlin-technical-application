package com.danielvilha.tds.util

import com.danielvilha.tds.data.Employer

/**
 * Created by danielvilha on 10/06/20
 */
fun smallerNumber(list : List<Employer>) : Int {
    var small = 0
    for (item in list) {
        // Verification if have employees under 18
        if (item.employee_age.toInt() < 18)
            small++
    }

    return small
}

fun middleNumber(list: List<Employer>) : Int {
    var medium = 0
    for (item in list) {
        // Verification if have employees between 18 and 60 years old
        if (item.employee_age.toInt() in 19..59) {
            medium++
        }
    }

    return medium
}

fun biggerNumber(list: List<Employer>) : Int {
    var big = 0
    for (item in list) {
        // Verification if have employees over 60 years old
        if (item.employee_age.toInt() > 60) {
            big++
        }
    }

    return big
}
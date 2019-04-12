package com.timothycox.edas_kotlin.model

import java.io.Serializable

data class Examinee(var name: String?, var age: Int, var gender: String?, var creatorUid: String?) : Serializable {
    var assessments: List<Assessment>? = null

    val ageAsHumanReadable: String
        get() {
            val years = age / 12
            val months = age % 12
            var monthTerm = ""
            var yearsTerm = ""

            if (months == 1)
                monthTerm = "month"
            else if (months > 1)
                monthTerm = "months"

            if (years == 1)
                yearsTerm = "year"
            else if (months > 1)
                yearsTerm = "years"

            if (years == 0)
                return StringBuilder(age)
                    .append(" ")
                    .append(monthTerm)
                    .toString()
            else {
                val builder = StringBuilder(years)
                    .append(" ")
                    .append(yearsTerm)
                if (months > 0) {
                    builder.append(", ")
                        .append(months)
                        .append(" ")
                        .append(monthTerm)
                }
                return builder.toString()
            }
        }
}

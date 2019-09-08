package com.timothycox.edas_kotlin.model

import java.io.Serializable

data class Examinee(var name: String?, var ageInMonths: Int, var gender: String?, var creatorUid: String?) : Serializable {

    var assessments: List<Assessment>? = null
    var category: String? = null

    init {
        assignCategory()
    }

    private fun assignCategory() {
        category = when (ageInMonths) {
            in 1..12 -> { "12-month" }
            in 13..24 -> { "24-month" }
            in 25..36 -> { "36-month" }
            in 37..Int.MAX_VALUE -> { "60-month" }
            else -> { "60-month" }
        }
    }

    val ageAsHumanReadable: String?
        get() {
            val ageStringBuilder : StringBuilder
            val years = ageInMonths / 12
            val months = ageInMonths % 12
            var monthTerm = ""
            var yearsTerm = ""

            if (months == 1)
                monthTerm = "month"
            else if (months > 1)
                monthTerm = "months"

            if (years == 1)
                yearsTerm = "year"
            else if (years > 1)
                yearsTerm = "years"

            if (years == 0)
                ageStringBuilder = StringBuilder(ageInMonths)
                    .append(" ")
                    .append(monthTerm)
            else {
                ageStringBuilder = StringBuilder(years)
                    .append(" ")
                    .append(yearsTerm)
                if (months > 0) {
                    ageStringBuilder.append(", ")
                        .append(months)
                        .append(" ")
                        .append(monthTerm)
                }
            }

            return ageStringBuilder.toString()
        }
}

package com.example.githubapp.helper

class PullRequestDetailVHUtil {
    companion object {
        fun getUserNameDisplayText(userName: String): String {
            return "Creator: $userName"
        }

        fun getCreatedDateDisplayText(createdDate: String): String {
            val formattedDate = DateTimeUtils.getLocalDateStringFromTimeZoneString(createdDate)
            return "Created On: $formattedDate"
        }

        fun getClosedDateDisplayText(closedDate: String): String {
            val formattedDate = DateTimeUtils.getLocalDateStringFromTimeZoneString(closedDate)
            return "Closed On: $formattedDate"
        }
    }
}
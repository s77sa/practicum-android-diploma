package ru.practicum.android.diploma.domain.sharing

interface ExternalNavigatorRepository {

    fun intentEmail(
        address: String,
        subject: String,
        text: String
    ): String?

    fun intentShare(
        text: String
    ): String?

    fun intentDial(
        number: String
    ): String?
}

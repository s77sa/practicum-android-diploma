package ru.practicum.android.diploma.data.sharing

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import ru.practicum.android.diploma.domain.api.ExternalNavigatorRepository

class ExternalNavigatorRepositoryImpl(private val context: Context) : ExternalNavigatorRepository {

    override fun intentEmail(address: String, subject: String, text: String): String? {
        return try {
            val shareIntent = Intent(Intent.ACTION_SENDTO)
            shareIntent.data = Uri.parse("mailto:")
            shareIntent.putExtra(Intent.EXTRA_EMAIL, address)
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
            shareIntent.putExtra(Intent.EXTRA_TEXT, text)
            shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(shareIntent)
            null
        } catch (e: ActivityNotFoundException) {
            e.message
        }
    }

    override fun intentShare(text: String): String? {
        return try {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, text)
            shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(shareIntent)
            null
        } catch (e: ActivityNotFoundException) {
            e.message
        }
    }

    override fun intentDial(number: String): String? {
        return try {
            val shareIntent = Intent(Intent.ACTION_DIAL)
            shareIntent.data = Uri.parse("tel:$number")
            shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(shareIntent)
            null
        } catch (e: ActivityNotFoundException) {
            e.message
        }
    }
}

import java.io.File

data class Message(val address: String?, val topic: String?, val sender: String?, val content: String?) {
    fun toHTML(): String {
        val bgColor = when (topic) {
            "Meeting Request" -> "#b2dfdb"
            "Important Announcement" -> "#ffcc80"
            else -> "#e6f7ff"
        }

        return buildString {
            append("<div style='background-color: #e6f7ff; padding: 20px; border: 1px solid #b3e0ff; border-radius: 8px; box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); max-width: 400px; margin: 0 auto;'>\n")
            append("<h2 style='background-color: #00796b; color: #fff; padding: 10px; border-top-left-radius: 8px; border-top-right-radius: 8px; margin: 0;'>Info</h2>\n")
            append("<table style='font-family: Arial; width: 100%; background-color: $bgColor; border-collapse: collapse;'>\n")

            fun addRow(label: String, value: String) {
                append("<tr style='border-bottom: 1px solid #b3e0ff;'>")
                    .append("<td style='padding: 10px; text-align: left;'>$label</td>")
                    .append("<td style='padding: 10px; text-align: left;'>$value</td>")
                    .append("</tr>\n")
            }

            address?.let { addRow("Email", it) }
            topic?.let { addRow("Topic", it) }
            sender?.let { addRow("Sender", it) }
            content?.let { addRow("Content", it) }

            append("</table>\n")
            append("</div>\n")
        }
    }
}

fun main() {
    val m = Message("egor@microsoft.com", "Meeting Request", "Egor Ezhov", "Hello!")
    File("email.html").writeText(m.toHTML())
}

package com.sayproj.core.usecase

import com.sayproj.core.data.Note

class GetWordCount {
    operator fun invoke(note: Note): Int  = getCount(note.title) + getCount(note.content)

    private fun getCount(text: String) =
        text.split(" ", "\n")
            .filter {
                it.contains(Regex(".*[a-zA-Z0-9].*"))
            }
            .count()

}

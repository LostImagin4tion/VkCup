package io.lostimagin4tion.vkcup.data.repositories

import io.lostimagin4tion.vkcup.domain.enitites.ConvertedQuizWithGaps
import io.lostimagin4tion.vkcup.domain.enitites.QuizWithGaps
import io.lostimagin4tion.vkcup.domain.repositories.IWatchPostRepository
import javax.inject.Inject

class WatchPostRepository @Inject constructor(): IWatchPostRepository {

    override fun convertQuizWithGaps(quizWithGaps: QuizWithGaps): ConvertedQuizWithGaps {
        val sortedGaps = quizWithGaps.gaps.sortedBy { it }
        val gapsWithIndex = mutableListOf<Pair<String, Int>>()
        val textItems = mutableListOf<Pair<String, Boolean>>()

        for (i in sortedGaps.indices) {
            val startIndex = if (i != 0 && sortedGaps[i] == sortedGaps[i - 1]) {
                quizWithGaps.text.indexOf(
                    string = sortedGaps[i],
                    startIndex = gapsWithIndex[i - 1].second + 1
                )
            } else {
                quizWithGaps.text.indexOf(string = sortedGaps[i])
            }

            gapsWithIndex.add(Pair(sortedGaps[i], startIndex))
        }
        gapsWithIndex.sortBy { it.second }

        var lastIndex = 0

        for (i in gapsWithIndex.indices) {
            if (i == 0) {
                lastIndex = if (gapsWithIndex[0].second == 0) {
                    gapsWithIndex[0].first.length
                } else {
                    textItems.add(
                        Pair(quizWithGaps.text.substring(0, gapsWithIndex[0].second), false)
                    )
                    gapsWithIndex[0].second + gapsWithIndex[0].first.length
                }
                textItems.add(
                    Pair(gapsWithIndex[0].first, true)
                )
            }
            else {
                textItems.add(
                    Pair(
                        quizWithGaps.text.substring(
                            startIndex = lastIndex,
                            endIndex = gapsWithIndex[i].second
                        ),
                        false
                    )
                )
                textItems.add(
                    Pair(gapsWithIndex[i].first, true)
                )
                lastIndex = gapsWithIndex[i].second + gapsWithIndex[i].first.length

                if (i == gapsWithIndex.lastIndex && lastIndex != quizWithGaps.text.length) {
                    textItems.add(
                        Pair(
                            quizWithGaps.text.substring(
                                startIndex = lastIndex,
                                endIndex = quizWithGaps.text.length
                            ),
                            false
                        )
                    )
                }
            }
        }

        val finalTextItems = mutableListOf<Pair<String, Boolean>>()

        for (i in textItems.indices) {
            if (textItems[i].second) {
                finalTextItems.add(textItems[i])
            } else {
                val split = textItems[i].first
                    .trim()
                    .split(" ")

                for (j in split.indices) {
                    if (split[j].isNotBlank()) {
                        finalTextItems.add(element = Pair(split[j], false))
                    }
                }
            }
        }

        return ConvertedQuizWithGaps(
            textItems = finalTextItems,
            gapsWithIndex = gapsWithIndex
        )
    }
}
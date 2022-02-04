package com.example.letsdart.utils

import com.example.letsdart.models.league.LeagueMatchup
import com.example.letsdart.models.series.SeriesPlayer


class GamesGenerator {
    companion object {
        fun generateGames(seriesPlayersList: List<SeriesPlayer>, rematch: Boolean): List<LeagueMatchup> {
            val gamesByPlayerList: MutableList<MutableList<LeagueMatchup>> =
                listOf<MutableList<LeagueMatchup>>().toMutableList()

            for (i in seriesPlayersList.indices) {
                gamesByPlayerList.add(listOf<LeagueMatchup>().toMutableList())
                for (j in i + 1 until seriesPlayersList.size) {
                    if (rematch) {
                        addGame(i, j, i, gamesByPlayerList, seriesPlayersList)
                        addGame(j, i, i, gamesByPlayerList, seriesPlayersList)
                    } else {
                        if (j % 2 == 0)
                            addGame(i, j, i, gamesByPlayerList, seriesPlayersList)
                        else
                            addGame(j, i, i, gamesByPlayerList, seriesPlayersList)
                    }
                }
            }
            return createList(gamesByPlayerList)
        }

        private fun addGame(
            firstIndex: Int,
            secondIndex: Int,
            gameListIndex: Int,
            gamesByPlayerList: MutableList<MutableList<LeagueMatchup>>,
            seriesPlayersList: List<SeriesPlayer>
        ) {
            gamesByPlayerList[gameListIndex].add(
                LeagueMatchup(
                    firstPlayer = seriesPlayersList[firstIndex],
                    secondPlayer = seriesPlayersList[secondIndex]
                )
            )
        }

        private fun createList(gamesByPlayerList: MutableList<MutableList<LeagueMatchup>>): List<LeagueMatchup> {
            val gamesList: MutableList<LeagueMatchup> = mutableListOf()
            for (i in 0 until gamesByPlayerList.size - 1)
                gamesList.addAll(gamesByPlayerList[i])
            gamesList.shuffle()

            return gamesList
        }
    }
}
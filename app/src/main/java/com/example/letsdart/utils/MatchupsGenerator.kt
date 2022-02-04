package com.example.letsdart.utils


import com.example.letsdart.models.general.Rules
import com.example.letsdart.models.series.SeriesPlayer
import com.example.letsdart.models.general.SavedPlayer
import com.example.letsdart.database.tournamentDatabase.TournamentMatchup

class MatchupsGenerator {

    companion object {
        fun generateFirstMatchups(playersList: List<SeriesPlayer>, rules: Rules): List<TournamentMatchup> {
            val localPlayerList = playersList.shuffled().toMutableList()
            return generate(localPlayerList, rules, 0)
        }

        fun generateNextMatchups(matchupsList: List<TournamentMatchup>, rules: Rules, level: Int): List<TournamentMatchup> {
            val localPlayerList = mutableListOf<SeriesPlayer>()
            for (elem in matchupsList)
                if (elem.firstPlayer.seriesPlayerId == elem.winnerId)
                    localPlayerList.add(elem.firstPlayer)
                else
                    localPlayerList.add(elem.secondPlayer)

            if (localPlayerList.size % 2 != 0)
                localPlayerList.shuffle()

            return generate(localPlayerList, rules, level)
        }

        private fun generate(localPlayerList: MutableList<SeriesPlayer>, rules: Rules, level: Int): List<TournamentMatchup> {
            val newMatchups = mutableListOf<TournamentMatchup>()
            var buyOut = -1
            val gamesPerMatchup = if (rules.rematch)
                2
            else 1

            if (localPlayerList.size % 2 != 0) {
                buyOut = localPlayerList.lastIndex
            }

            var counter = 0
            while (counter < localPlayerList.size - 1) {
                newMatchups.add(
                    TournamentMatchup(
                        firstPlayer = SeriesPlayer(
                            seriesPlayerId = localPlayerList[counter].seriesPlayerId,
                            savedPlayer = localPlayerList[counter].savedPlayer
                        ),
                        secondPlayer = SeriesPlayer(
                            seriesPlayerId = localPlayerList[counter + 1].seriesPlayerId,
                            savedPlayer = localPlayerList[counter + 1].savedPlayer
                        ),
                        remainingGames = gamesPerMatchup,
                        level = level
                    )
                )
                counter += 2
            }

            if (buyOut != -1) {
                val buyPlayer =
                    localPlayerList[buyOut]

                newMatchups.add(
                    TournamentMatchup(
                        firstPlayer = SeriesPlayer(
                            seriesPlayerId = buyPlayer.seriesPlayerId,
                            savedPlayer = buyPlayer.savedPlayer
                        ),
                        secondPlayer = SeriesPlayer(savedPlayer = SavedPlayer("BuyOut")),
                        remainingGames = gamesPerMatchup,
                        level = level,
                        winnerId = buyPlayer.seriesPlayerId
                    )
                )
            }
            return newMatchups
        }
    }
}
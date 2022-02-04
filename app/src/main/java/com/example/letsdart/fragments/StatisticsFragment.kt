package com.example.letsdart.fragments

import android.graphics.Color
import android.os.Bundle
import android.text.Layout
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.letsdart.R
import com.example.letsdart.databinding.StatisticsFragmentBinding
import org.apache.poi.xwpf.usermodel.TextAlignment
import java.math.BigDecimal
import java.math.RoundingMode

class StatisticsFragment : Fragment() {
    companion object {
        const val MAX_STANDARD_NUMBER = 20
    }

    private lateinit var binding: StatisticsFragmentBinding
    private lateinit var args: StatisticsFragmentArgs
    private val textViewMap = mutableMapOf<String, TextView>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.statistics_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        args = StatisticsFragmentArgs.fromBundle(requireArguments())

        setBasicStats()
        setDetailedStats()
        setOnPercentagesClickListener()
        setOnTotalsClickListener()
    }

    private fun setBasicStats() {
        binding.textPlayerName.text = args.player.playerName
        binding.textTotalGames.text = args.player.statistics.gamesPlayed.toString()
        binding.textTotalWins.text = args.player.statistics.wins.toString()
        binding.textTotalDefeats.text = args.player.statistics.defeats.toString()
        binding.textTotalWonLegs.text = args.player.statistics.wonLegs.toString()
        binding.textTotalLostLegs.text = args.player.statistics.lostLegs.toString()
        binding.textTotalPoints.text = args.player.statistics.scoreSum.toString()
        binding.textTotalDarts.text = args.player.statistics.scoreCount.toString()

        if (args.player.statistics.scoreCount != 0)
            binding.textAverage.text =
                BigDecimal((args.player.statistics.scoreSum.toDouble() / args.player.statistics.scoreCount) * 3).setScale(2, RoundingMode.HALF_DOWN)
                    .toString()
        else
            binding.textAverage.text = args.player.statistics.scoreCount.toString()
        binding.textTotalSingles.text = args.player.statistics.singles.toString()
        binding.textTotalDoubles.text = args.player.statistics.doubles.toString()
        binding.textTotalTriples.text = args.player.statistics.triples.toString()
    }

    private fun setOnPercentagesClickListener() {
        binding.buttonPercentages.setOnClickListener {
            fillDetailedStatsPercentages()
        }
    }

    private fun setOnTotalsClickListener() {
        binding.buttonTotals.setOnClickListener {
            fillDetailedStatsTotals()
        }
    }

    private fun setDetailedStats() {
        val firstRow = TableRow(requireContext())
        firstRow.weightSum = 6F

        val textParams = TableRow.LayoutParams()
        textParams.weight = 0.6F
        textParams.width = 0

        val valueParams = TableRow.LayoutParams()
        valueParams.weight = 1.5F
        valueParams.width = 0

        createRow(firstRow, textParams, valueParams, "0")
        binding.layoutTable.addView(firstRow)

        for (elem in 1..MAX_STANDARD_NUMBER) {
            val row = TableRow(requireContext())
            row.weightSum = 6F

            createRow(row, textParams, valueParams, "$elem")
            createRow(row, textParams, valueParams, "D$elem")
            createRow(row, textParams, valueParams, "T$elem")
            binding.layoutTable.addView(row)
        }
        val bullRow = TableRow(requireContext())
        bullRow.weightSum = 6F
        createRow(bullRow, textParams, valueParams, "25")
        createRow(bullRow, textParams, valueParams, "D25")

        binding.layoutTable.addView(bullRow)

        val otRow = TableRow(requireContext())
        otRow.weightSum = 6F
        createRow(otRow, textParams, valueParams, "OT")
        binding.layoutTable.addView(otRow)


        fillDetailedStatsPercentages()
    }

    private fun createRow(row: TableRow, textParams: TableRow.LayoutParams, valueParams: TableRow.LayoutParams, value: String) {
        val textHeader = TextView(requireContext())
        textHeader.layoutParams = textParams
        textHeader.textSize = 17f
        textHeader.text = value

        val textValue = TextView(requireContext())
        textValue.layoutParams = valueParams
        textValue.textSize = 17f
        textValue.setTextColor(Color.BLACK)
        textViewMap[value] = textValue

        row.addView(textHeader)
        row.addView(textValue)
    }

    private fun fillDetailedStatsPercentages() {
        for (elem in args.player.statistics.scoresMap) {
            textViewMap[elem.key]!!.text =
                if (args.player.statistics.scoreCount != 0)
                    "${BigDecimal(((elem.value).toDouble() / args.player.statistics.scoreCount) * 100).setScale(2, RoundingMode.HALF_DOWN)}%"
                else
                    "${args.player.statistics.scoreCount}.00%"
        }
    }

    private fun fillDetailedStatsTotals() {
        for (elem in args.player.statistics.scoresMap)
            textViewMap[elem.key]!!.text = elem.value.toString()
    }
}
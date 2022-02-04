package com.example.letsdart.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.letsdart.R
import com.example.letsdart.databinding.GameStatisticsFragmentBinding
import java.math.RoundingMode
import java.math.BigDecimal


class GameStatisticsFragment : Fragment() {
    companion object{
        const val MAX_STANDARD_NUMBER = 20
    }

    private lateinit var binding: GameStatisticsFragmentBinding
    private lateinit var args: GameStatisticsFragmentArgs
    private val textViewMap = mutableMapOf<String, TextView>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.game_statistics_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        args = GameStatisticsFragmentArgs.fromBundle(requireArguments())

        setBasicStats()
        setDetailedStats()
        setOnPercentagesClickListener()
        setOnTotalsClickListener()
    }

    private fun setBasicStats() {
        binding.textPlayerName.text = args.player.playerName
        binding.textTotalWonLegs.text = args.statistics.wonLegs.toString()
        binding.textTotalLostLegs.text = args.statistics.lostLegs.toString()
        binding.textTotalPoints.text = args.statistics.scoreSum.toString()
        binding.textTotalDarts.text = args.statistics.scoreCount.toString()
        binding.textAverage.text = BigDecimal((args.statistics.scoreSum.toDouble() / args.statistics.scoreCount) * 3).setScale(2, RoundingMode.HALF_DOWN).toString()
        binding.textTotalSingles.text = args.statistics.singles.toString()
        binding.textTotalDoubles.text = args.statistics.doubles.toString()
        binding.textTotalTriples.text = args.statistics.triples.toString()
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
        textParams.weight = 0.5F
        textParams.width = 0

        val valueParams = TableRow.LayoutParams()
        valueParams.weight = 1.5F
        valueParams.width = 0

        createRow(firstRow, textParams, valueParams, "0" )
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
        for (elem in args.statistics.scoresMap) {
            textViewMap[elem.key]!!.text =
                "${BigDecimal(((elem.value).toDouble() / args.statistics.scoreCount) * 100).setScale(2, RoundingMode.HALF_DOWN)}%"
        }
    }

    private fun fillDetailedStatsTotals() {
        for (elem in args.statistics.scoresMap)
            textViewMap[elem.key]!!.text = elem.value.toString()
    }
}
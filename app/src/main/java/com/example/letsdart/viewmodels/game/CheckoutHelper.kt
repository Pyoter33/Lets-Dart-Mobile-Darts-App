package com.example.letsdart.viewmodels.game

import android.content.res.Resources
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.DataFormatter
import java.io.IOException
import java.lang.Exception

class CheckoutHelper {
    fun readCheckout(resources: Resources, fileName: Int, checkout: Int): Map<Int, List<String>> {
        val inputStream = resources.openRawResource(fileName)
        val result = mutableMapOf<Int, MutableList<String>> ()
        try {
            val workbook = HSSFWorkbook(inputStream)

            val sheet = workbook.getSheetAt(checkout)

            val formatter = DataFormatter()
            for (row in sheet) {
                if (row.rowNum > 0) {
                    val cellIterator: Iterator<Cell> = row.cellIterator()

                    val firstCell = cellIterator.next()
                    result[firstCell.numericCellValue.toInt()] = mutableListOf()

                    while (cellIterator.hasNext()) {
                        val cell: Cell = cellIterator.next()
                        result[firstCell.numericCellValue.toInt()]!!.add(formatter.formatCellValue(cell))
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: Exception) {
          e.printStackTrace()
        } finally {
            try {
                inputStream.close()
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
        return result
    }
}

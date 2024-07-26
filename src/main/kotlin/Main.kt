import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.FileInputStream

@SpringBootApplication
class ApiExcelListApplication

fun main(args: Array<String>) {
    runApplication<ApiExcelListApplication>(*args)
}

@RestController
@RequestMapping("/api")
class FruitVeggieController {

    @GetMapping("/locations")
    fun getLocations(): List<Location> {
        val excelFile = FileInputStream("path/to/your/excel/file.xlsx")
        val workbook = XSSFWorkbook(excelFile)
        val sheet = workbook.getSheetAt(0)
        val locations = mutableListOf<Location>()

        for (row in sheet) {
            if (row.rowNum == 0) continue  // Skip header row
            val fruitVeggie = row.getCell(0).stringCellValue
            val x = row.getCell(1).numericCellValue
            val y = row.getCell(2).numericCellValue
            locations.add(Location(fruitVeggie, x, y))
        }

        workbook.close()
        return locations
    }
}

data class Location(val name: String, val x: Double, val y: Double)

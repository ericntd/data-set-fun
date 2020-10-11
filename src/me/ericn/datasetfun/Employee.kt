package me.ericn.datasetfun

import java.io.BufferedReader
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException
import java.lang.StringBuilder
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

val departments = arrayOf("Marketing", "Engineering", "UX", "Finance", "Other")
val titles = arrayOf("Analyst", "Engineer", "Manager", "Other")
val names = arrayOf("Syeda", "Fern", "Gabriella", "Troy", "Ana", "Khadija", "Amelia", "Victoria", "Rosa", "Lauren", "Billy", "Abby")


data class Employee(
        val name: String = names.random(),
        val department: String = departments.random(),
        val salary: Int = Random.nextInt(50_000, 100_000),
        val title: String = titles.random()
) {

    companion object {
        /**
         * Run the following function to create a new CSV file
         * with random data
         */
        fun generateTestData() {
            val str: String = generateTestDataStr()
            println("+++++ CSV content below:")
            println(str)
            println("----- CSV content above")
            println("")

            val scanner = Scanner(str)

            var fileWriter: FileWriter? = null

            try {
                fileWriter = FileWriter("employees.csv")

                while (scanner.hasNextLine()) {
                    fileWriter.append(scanner.nextLine())
                    fileWriter.append('\n')
                }

                println("Write CSV successfully!")
            } catch (e: Exception) {
                println("Writing CSV error!")
                e.printStackTrace()
            } finally {
                try {
                    fileWriter!!.flush()
                    fileWriter.close()
                } catch (e: IOException) {
                    println("Flushing/closing error!")
                    e.printStackTrace()
                }
            }
        }

        private fun generateTestDataStr(): String {
            val stringBuilder = StringBuilder()
            val CSV_HEADER = "Name,Department,Salary,Title"
            stringBuilder.append(CSV_HEADER)
            stringBuilder.append('\n')

            val employees = (1..12).map { Employee() }.toList()
            employees.forEach {
                stringBuilder.append(it.name)
                stringBuilder.append(',')
                stringBuilder.append(it.department)
                stringBuilder.append(',')
                stringBuilder.append(it.salary.toString())
                stringBuilder.append(',')
                stringBuilder.append(it.title.toString())
                stringBuilder.append('\n')
            }
            return stringBuilder.toString()
        }

        fun readTestDataFromStr(): List<Employee> {
            // You only need to run generateTestData() once
//            generateTestData()

            val COLUMN_ID_NAME = 0
            val COLUMN_ID_DEP = 1
            val COLUMN_ID_SALARY = 2
            val COLUMN_ID_TITLE = 3

            val employees = ArrayList<Employee>()
            val reader = Scanner(generateTestDataStr())

            // Read CSV header
            reader.nextLine()

            // Read the file line by line starting from the second line
            while (reader.hasNextLine()) {
                val tokens = reader.nextLine().split(",")
                if (tokens.isNotEmpty()) {
                    val customer = Employee(
                            tokens[COLUMN_ID_NAME],
                            tokens[COLUMN_ID_DEP],
                            Integer.parseInt(tokens[COLUMN_ID_SALARY]),
                            tokens[COLUMN_ID_TITLE]
                    )
                    employees.add(customer)
                }
            }

            return employees
        }

        fun readTestData(): List<Employee> {
            // You only need to run generateTestData() once
//            generateTestData()

            val COLUMN_ID_NAME = 0
            val COLUMN_ID_DEP = 1
            val COLUMN_ID_SALARY = 2
            val COLUMN_ID_TITLE = 3

            var fileReader: BufferedReader? = null

            try {
                val employees = ArrayList<Employee>()
                var line: String?

                fileReader = BufferedReader(FileReader("employees.csv"))

                // Read CSV header
                fileReader.readLine()

                // Read the file line by line starting from the second line
                line = fileReader.readLine()
                while (line != null) {
                    val tokens = line.split(",")
                    if (tokens.isNotEmpty()) {
                        val customer = Employee(
                                tokens[COLUMN_ID_NAME],
                                tokens[COLUMN_ID_DEP],
                                Integer.parseInt(tokens[COLUMN_ID_SALARY]),
                                tokens[COLUMN_ID_TITLE]
                        )
                        employees.add(customer)
                    }

                    line = fileReader.readLine()
                }

                return employees

            } catch (e: Exception) {
                println("Reading CSV Error!")
                e.printStackTrace()
                return emptyList()
            } finally {
                try {
                    fileReader!!.close()
                } catch (e: IOException) {
                    println("Closing fileReader Error!")
                    e.printStackTrace()
                }
            }
        }
    }
}
package me.ericn.datasetfun

fun main() {
    val tmp = emptyList<Employee>()
    val employees = Employee.readTestData()

    employees.sumBy { it.salary }.let {
        println("total salary: $it")
    }

    employees.groupingBy { it.department }.eachCount().forEach { (s, i) ->
        println("there are $i employees for department $s")
    }

    employees.groupBy { it.department }
        .map { group ->
            val departmentName = group.key
            val departmentEmployees = group.value
            val departmentTotalSalary = departmentEmployees.sumBy { it.salary }
            println("Total salary for department $departmentName is $departmentTotalSalary")
        }

    employees.groupingBy { it.department }
        .fold(initialValue = 0) { total, employee -> total + employee.salary }
        .map {
            println("Total salary for department ${it.key} is ${it.value}")
        }

    employees.groupingBy { it.department }
        .aggregate { key, acc: Int?, element, isFirstElement ->
            return@aggregate if (isFirstElement) {
                0 + element.salary
            } else {
                acc!! + element.salary
            }
        }
        .map {
            println("Total salary for department ${it.key} is ${it.value}")
        }

    var groupCount = 0
    employees.groupingBy { it.department }
        .aggregate { key, kAverage: Double?, element, isFirstElement ->
            // Reset the group count
            if (isFirstElement) {
                groupCount = 1
            } else {
                groupCount++
            }
            return@aggregate if (isFirstElement) {
                0.0 + element.salary
            } else {
                kAverage!! + (element.salary - kAverage) / (groupCount + 1)
            }
        }
        .map {
            println("Average salary for department ${it.key} is ${it.value}")
        }

    employees.groupBy { it.department }
        .map { group ->
            val departmentName = group.key
            val departmentEmployees = group.value
            val departmentAverageSalary = departmentEmployees.sumBy { it.salary } / departmentEmployees.size
            println("Average salary for department $departmentName is $departmentAverageSalary")
        }


}
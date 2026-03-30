package ferit.zadace.zadaca2

interface Gradable {
    fun averageGrade(): Double
    fun gradeDescription(): String
}

interface Describable {
    fun describe(): String
}

enum class ActivityType(val displayName: String) {
    DRAMA("Drama Club"),
    ROBOTICS("Robotics Club"),
    ART("Art Club"),
    SPORT("Sports"),
    MUSIC("Music Club"),
    CODING("Coding Club")
}

data class Subject(val name: String, val hoursPerWeek: Int)

data class Activity(val type: ActivityType, val supervisorName: String)

abstract class Person(
    val firstName: String,
    val lastName: String
) : Describable {
    val fullName: String get() = "$firstName $lastName"
    abstract fun getRole(): String
}

class Teacher(
    firstName: String,
    lastName: String,
    val subject: Subject,
    private var salary: Double
) : Person(firstName, lastName) {

    lateinit var office: String
    private val classrooms: MutableList<String> = mutableListOf()

    override fun getRole() = "Teacher"

    fun addClassroom(classroom: String) { classrooms.add(classroom) }

    fun gradeStudent(student: Student, mark: Int) {
        student.addGrade(subject, mark)
    }

    fun raiseSalary(percent: Double) {
        salary *= (1 + percent / 100)
        println("  New salary for $fullName: ${"%.2f".format(salary)} EUR")
    }

    protected fun calculateBonus(): Double = salary * 0.1

    fun showBonus() {
        println("  Bonus for $fullName: ${"%.2f".format(calculateBonus())} EUR")
    }

    override fun describe(): String =
        "Teacher: $fullName | Subject: ${subject.name} | Classes: ${classrooms.joinToString(", ")}"
}

class Student(
    firstName: String,
    lastName: String,
    val studentId: Int,
    val classroom: String,
    val subjects: List<Subject>,
    val activities: List<Activity> = emptyList()
) : Person(firstName, lastName), Gradable {

    private val grades: MutableMap<String, MutableList<Int>> = mutableMapOf()
    lateinit var homeRoomTeacher: Teacher

    override fun getRole() = "Student"

    fun addGrade(subject: Subject, mark: Int) {
        if (mark !in 1..5) { println("Grade must be between 1 and 5!"); return }
        grades.getOrPut(subject.name) { mutableListOf() }.add(mark)
    }

    override fun averageGrade(): Double {
        val all = grades.values.flatten()
        return if (all.isEmpty()) 0.0 else all.average()
    }

    override fun gradeDescription(): String = when {
        averageGrade() >= 4.5 -> "Excellent"
        averageGrade() >= 3.5 -> "Very Good"
        averageGrade() >= 2.5 -> "Good"
        averageGrade() >= 1.5 -> "Sufficient"
        else                  -> "Insufficient"
    }

    fun gradesForSubject(subject: Subject): List<Int> = grades[subject.name] ?: emptyList()

    fun printGrades() {
        println("  Grades for $fullName (ID: $studentId):")
        subjects.forEach { subject ->
            val sg  = gradesForSubject(subject)
            val avg = if (sg.isEmpty()) "-" else "%.2f".format(sg.average())
            println("    ${subject.name}: ${sg.joinToString(", ")} | Avg: $avg")
        }
        if (activities.isNotEmpty())
            println("    Activities: ${activities.joinToString(", ") { it.type.displayName }}")
    }

    override fun describe(): String {
        val act = if (activities.isNotEmpty())
            " | Activities: ${activities.joinToString(", ") { it.type.displayName }}" else ""
        return "Student: $fullName (ID: $studentId) | Class: $classroom | Avg: ${"%.2f".format(averageGrade())} | ${gradeDescription()}$act"
    }
}

class Classroom(val label: String, val homeRoomTeacher: Teacher) {
    private val students: MutableList<Student> = mutableListOf()

    fun enrollStudent(student: Student) {
        student.homeRoomTeacher = homeRoomTeacher
        students.add(student)
        println("${student.fullName} enrolled in class $label.")
    }

    fun studentCount(): Int = students.size
    fun bestStudent(): Student? = students.maxByOrNull { it.averageGrade() }
    fun getStudents(): List<Student> = students.toList()
    fun classAverage(): Double = if (students.isEmpty()) 0.0 else students.map { it.averageGrade() }.average()

    fun printClassroom() {
        println("\nClass $label (Home room teacher: ${homeRoomTeacher.fullName}) | Avg: ${"%.2f".format(classAverage())} ")
        students.sortedByDescending { it.averageGrade() }.forEach { student ->
            println("  ${student.describe()}")
            student.printGrades()
        }
    }
}

interface ClassroomManagement {
    fun addClassroom(classroom: Classroom)
    fun allClassrooms(): List<Classroom>
}

class ClassroomRegistry : ClassroomManagement {
    private val classrooms: MutableList<Classroom> = mutableListOf()
    override fun addClassroom(classroom: Classroom) { classrooms.add(classroom) }
    override fun allClassrooms(): List<Classroom> = classrooms.toList()
}

class School(
    val name: String,
    registry: ClassroomRegistry = ClassroomRegistry()
) : ClassroomManagement by registry {

    val description: String by lazy {
        "Welcome to '$name' with ${allClassrooms().size} classrooms."
    }

    fun allStudents(): List<Student> = allClassrooms().flatMap { it.getStudents() }
    fun totalStudentCount(): Int = allStudents().size

    fun bestStudentInSchool(): Student? = allStudents().maxByOrNull { it.averageGrade() }

    fun filterStudents(predicate: (Student) -> Boolean): List<Student> =
        allStudents().filter(predicate)

    fun groupByClassroom(): Map<String, List<Student>> =
        allStudents().groupBy { it.classroom }

    fun classAverageMap(): Map<String, Double> =
        allClassrooms().associate { it.label to it.classAverage() }
}

sealed class ExamResult {
    object Passed : ExamResult()
    data class Failed(val grade: Int, val reason: String) : ExamResult()
}

fun main() {
    val math     = Subject("Mathematics", 4)
    val physics  = Subject("Physics",     3)
    val croatian = Subject("Croatian",    4)
    val pe       = Subject("P.E.",        2)
    val history  = Subject("History",     2)

    val coreSubjects = listOf(math, physics, croatian, pe)
    val altSubjects  = listOf(math, croatian, history, pe)

    val drama    = Activity(ActivityType.DRAMA,    "Mr. Horvat")
    val robotics = Activity(ActivityType.ROBOTICS, "Ms. Novak")
    val art      = Activity(ActivityType.ART,      "Ms. Petrovic")
    val sport    = Activity(ActivityType.SPORT,    "Mr. Anic")
    val coding   = Activity(ActivityType.CODING,   "Mr. Kovac")

    val mathTeacher     = Teacher("Ivana", "Horvat", math,     1200.0)
    val physicsTeacher  = Teacher("Marko", "Novak",  physics,  1100.0)
    val croatianTeacher = Teacher("Ana",   "Kovac",  croatian, 1050.0)

    mathTeacher.addClassroom("3A")
    physicsTeacher.addClassroom("3A")
    physicsTeacher.addClassroom("3B")
    croatianTeacher.addClassroom("3B")

    val class3A = Classroom("3A", mathTeacher)
    val class3B = Classroom("3B", croatianTeacher)

    val student1 = Student("Luka",     "Peric",    100001,"3A", coreSubjects, listOf(robotics, sport))
    val student2 = Student("Marija",   "Maric",    100002,"3A", coreSubjects, listOf(drama, art))
    val student3 = Student("Ivan",     "Ivic",    100003,"3A", coreSubjects, listOf(coding))
    val student4 = Student("Petra",    "Petric",    100004,"3B", altSubjects,  listOf(art, drama))
    val student5 = Student("Tomislav", "Tomic", 100005,"3B", altSubjects,  listOf(sport, coding))

    listOf(student1, student2, student3).forEach    { class3A.enrollStudent(it) }
    listOf(student4, student5).forEach       { class3B.enrollStudent(it) }

    student1.addGrade(math, 5);     student1.addGrade(math, 4)
    student1.addGrade(physics, 5);  student1.addGrade(croatian, 3);  student1.addGrade(pe, 5)

    student2.addGrade(math, 3);   student2.addGrade(math, 4)
    student2.addGrade(physics, 2); student2.addGrade(croatian, 5); student2.addGrade(pe, 4)

    student3.addGrade(math, 2);     student3.addGrade(math, 1)
    student3.addGrade(physics, 3);  student3.addGrade(croatian, 4);  student3.addGrade(pe, 3)

    student4.addGrade(math, 5);    student4.addGrade(math, 5)
    student4.addGrade(croatian, 4); student4.addGrade(history, 5); student4.addGrade(pe, 5)

    student5.addGrade(math, 4)
    student5.addGrade(croatian, 3); student5.addGrade(history, 4); student5.addGrade(pe, 4)

    println("\nExam Results")
    fun evaluateStudentExam(student: Student, subject: Subject, grade: Int): ExamResult {
        println("Evaluating ${student.fullName} for ${subject.name}: grade $grade")
        return when {
            grade >= 2 -> ExamResult.Passed
            else -> ExamResult.Failed(grade, "Grade $grade is below passing (2)")
        }
    }

    val exam1 = evaluateStudentExam(student1, math, 5)
    val exam2 = evaluateStudentExam(student3, math, 2)
    val exam3 = evaluateStudentExam(student4, history, 1)

    listOf(exam1, exam2, exam3).forEachIndexed { index, result ->
        when (result) {
            is ExamResult.Passed -> println("  Exam ${index + 1}: PASSED - Student passed the exam!")
            is ExamResult.Failed -> println("  Exam ${index + 1}: FAILED - Reason: ${result.reason} (Grade: ${result.grade})")
        }
    }

    val school = School("High School")
    school.addClassroom(class3A)
    school.addClassroom(class3B)

    class3A.printClassroom()
    class3B.printClassroom()

    println("\n${school.description}")
    println("Total students: ${school.totalStudentCount()}")

    val best = school.bestStudentInSchool()
    println("\nBest student: ${best?.fullName} (${best?.gradeDescription()}, avg: ${"%.2f".format(best?.averageGrade())})")

    println("\nStudents with average >= 4.0:")
    school.filterStudents { it.averageGrade() >= 4.0 }
        .forEach { println("  ${it.fullName} - ${"%.2f".format(it.averageGrade())}") }

    println("\nClass averages:")
    school.classAverageMap().forEach { (label, avg) ->
        println("  Class $label: ${"%.2f".format(avg)}")
    }

    println("\nStudents grouped by classroom:")
    school.groupByClassroom().forEach { (classroom, studentList) ->
        println("  Class $classroom: ${studentList.joinToString(", ") { it.fullName }}")
    }

    println("\nTeachers:")
    listOf(mathTeacher, physicsTeacher, croatianTeacher).forEach { println("  ${it.describe()}") }

    println("\nRaising salaries by 10%:")
    listOf(mathTeacher, physicsTeacher, croatianTeacher).forEach { it.raiseSalary(10.0) }

    println("\nTeacher Bonuses")
    listOf(mathTeacher, physicsTeacher, croatianTeacher).forEach { it.showBonus() }
}
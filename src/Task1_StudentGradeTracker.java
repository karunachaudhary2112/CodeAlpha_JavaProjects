import java.util.ArrayList;
import java.util.Scanner;

class Student {
    String name;
    double marks;

    Student(String name, double marks) {
        this.name = name;
        this.marks = marks;
    }
}

public class Task1_StudentGradeTracker {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Student> students = new ArrayList<>();
        int choice;

        do {
            System.out.println("\n===== STUDENT GRADE TRACKER =====");
            System.out.println("1. Add Student");
            System.out.println("2. View Summary Report");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // clear leftover newline

            if (choice == 1) {
                System.out.print("Enter student name: ");
                String name = sc.nextLine();
                System.out.print("Enter marks: ");
                double marks = sc.nextDouble();
                students.add(new Student(name, marks));
                System.out.println("Student added!");

            } else if (choice == 2) {
                if (students.isEmpty()) {
                    System.out.println("No students yet.");
                    continue;
                }
                double sum = 0;
                Student highest = students.get(0);
                Student lowest = students.get(0);

                System.out.println("\n------------- SUMMARY REPORT -------------");
                for (Student s : students) {
                    System.out.println(s.name + "\t" + s.marks);
                    sum += s.marks;
                    if (s.marks > highest.marks) highest = s;
                    if (s.marks < lowest.marks) lowest = s;
                }
                System.out.println("Total Students : " + students.size());
                System.out.println("Average Score  : " + sum / students.size());
                System.out.println("Highest Score  : " + highest.marks + " (" + highest.name + ")");
                System.out.println("Lowest Score   : " + lowest.marks + " (" + lowest.name + ")");
            }
        } while (choice != 3);

        System.out.println("Goodbye!");
        sc.close();
    }
}
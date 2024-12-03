package network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MockData {

    // Mock tables
    protected static final Map<String, String[]> MOCK_USER_DB = new HashMap<>(); // user_id -> {password, name}
    protected static final List<String[]> MOCK_BOOKLIST = new ArrayList<>(); // {title, author}
    protected static final List<String[]> MOCK_BOOK_USER = new ArrayList<>(); // {user_id, book_name}
    protected static final List<String[]> MOCK_TASKLIST = new ArrayList<>(); // {user_id, task_name, task_deadline}
    protected static final Map<String, String[]> MOCK_TIMETABLE = new HashMap<>(); // user_id -> {slot1, slot2, slot3, slot4}

    protected static final Map<String, String[]> MOCK_TIMETABLE_MONDAY = new HashMap<>(); // user_id -> {slot1, slot2, slot3, slot4}
    protected static final Map<String, String[]> MOCK_TIMETABLE_TUESDAY= new HashMap<>();
    protected static final Map<String, String[]> MOCK_TIMETABLE_WEDNESDAY = new HashMap<>();
    protected static final Map<String, String[]> MOCK_TIMETABLE_THURSDAY = new HashMap<>();
    protected static final Map<String, String[]> MOCK_TIMETABLE_FRIDAY = new HashMap<>();
    protected static final Map<String, String[]> MOCK_TIMETABLE_SATURDAY = new HashMap<>();




    static {
        // Pre-fill the mock database with sample data

        // Users
        MOCK_USER_DB.put("U1", new String[]{"123", "John Doe"});
        MOCK_USER_DB.put("student2", new String[]{"mypassword", "Jane Smith"});

        // Books
        MOCK_BOOKLIST.add(new String[]{"1984", "George Orwell"});
        MOCK_BOOKLIST.add(new String[]{"To Kill a Mockingbird", "Harper Lee"});
        MOCK_BOOKLIST.add(new String[]{"Dune", "Frank Herbert"});

        // Book-User mapping
        MOCK_BOOK_USER.add(new String[]{"U1", "1984"});
        MOCK_BOOK_USER.add(new String[]{"student2", "Dune"});

        // Tasks
        MOCK_TASKLIST.add(new String[]{"U1", "Complete Assignment", "22.23.2311"});
        MOCK_TASKLIST.add(new String[]{"U1", "Dune", "22.23.2311"});
        MOCK_TASKLIST.add(new String[]{"student1", "Prepare for Exam", "22.23.2311"});
        MOCK_TASKLIST.add(new String[]{"student2", "Finish Project", "22.23.2311"});

        // Timetable
        MOCK_TIMETABLE.put("U1", new String[]{"Math;9:00", "Physics;12:00", "Chemistry;13:00", "Biology;16:00"});

        //timetabe with days
        MOCK_TIMETABLE_MONDAY.put("U1", new String[]{"Math;9:00", "Physics;12:00", "Chemistry;13:00", "Biology;16:00"});
        MOCK_TIMETABLE_TUESDAY.put("U1", new String[]{"Math;9:00", "Physics;12:00", "Chemistry;13:00", "Biology;16:00"});
        MOCK_TIMETABLE_THURSDAY.put("U1", new String[]{"Math;9:00", "Physics;12:00", "Chemistry;13:00", "Biology;16:00"});
        MOCK_TIMETABLE_WEDNESDAY.put("U1", new String[]{"Math;9:00", "Physics;12:00", "Chemistry;13:00", "Biology;16:00"});
        MOCK_TIMETABLE_FRIDAY.put("U1", new String[]{"Math;9:00", "Physics;12:00", "Chemistry;13:00", "Biology;16:00"});


    }



}

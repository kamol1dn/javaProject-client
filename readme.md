frontend/
│
├── src/
│   ├── Main.java               # Entry point for the app
│   ├── ClientConnection.java   # Handles network communication
│   ├── ui/
│   │   ├── MainMenu.java       # Main menu UI
│   │   ├── tasks/
│   │   │   ├── TasksMenu.java        # Handles tasks menu navigation
│   │   │   ├── TaskListUI.java       # View tasks
│   │   │   ├── TaskCreateUI.java     # Create new task
│   │   │   ├── TaskEditUI.java       # Edit task
│   │   │   └── TaskDeleteUI.java     # Delete all tasks
│   │   ├── library/
│   │   │   ├── LibraryMenu.java      # Handles library menu navigation
│   │   │   ├── BookListUI.java       # View booked books
│   │   │   ├── BookBorrowUI.java     # Borrow a book
│   │   │   ├── BookReturnUI.java     # Return a book
│   │   │   └── BookAddUI.java        # Add a new book
│   │   ├── student/
│   │   │   ├── StudentMenu.java      # Handles student menu navigation
│   │   │   ├── StudentInfoUI.java    # View student info
│   │   │   ├── TimetableUI.java      # View and edit timetable
│   │   └── settings/
│   │       ├── SettingsMenu.java     # Settings menu navigation
│   │       ├── EditNameUI.java       # Edit name
│   │       ├── EditIDUI.java         # Edit ID
│   │       ├── EditPasswordUI.java   # Edit password
│   │       ├── EditBirthdayUI.java   # Edit birthday
│   │       └── EditFacultyUI.java    # Edit faculty
│   ├── models/
│   │   ├── Task.java                # Task object
│   │   ├── Book.java                # Book object
│   │   └── User.java                # User object
│   └── utils/
│       ├── InputUtils.java          # Input validation
│       └── ScreenUtils.java         # Console formatting
│
├── run-client.bat                  # Run script for Windows
└── run-client.sh                   # Run script for Linux/Mac

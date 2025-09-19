#include <iostream>
#include <fstream>
#include <string>
#include <cstring>
using namespace std;

// Constants
const int MAX_COURSES = 1000;
const int MAX_USERS = 100;
const int MAX_STRING_LENGTH = 100;

// Global arrays for courses
string courseIds[MAX_COURSES];
string titles[MAX_COURSES];
int creditHours[MAX_COURSES];
int courseCount = 0;

// Global arrays for users
string usernames[MAX_USERS];
string passwords[MAX_USERS];
int userCount = 0;

// Function declarations
void loadUsers();
void loadCourses();
void saveUsers();
void saveCourses();
bool registerUser();
bool loginUser();
void mainMenu();
void addCourse();
void deleteCourse();
void searchCourse();
void updateCourse();
void listCourses();
void logout();

int main() {
    loadUsers();
    loadCourses();

    while (true) {
        cout << "\n=== Course Management System ===" << endl;
        cout << "1. Register" << endl;
        cout << "2. Login" << endl;
        cout << "3. Exit" << endl;
        cout << "Choose option: ";

        int choice;
        cin >> choice;
        cin.ignore(); // Clear newline

        switch (choice) {
            case 1:
                if (registerUser()) {
                    cout << "Registration successful!" << endl;
                } else {
                    cout << "Registration failed. Username may already exist." << endl;
                }
                break;
            case 2:
                if (loginUser()) {
                    cout << "Login successful!" << endl;
                    mainMenu();
                } else {
                    cout << "Login failed. Invalid credentials." << endl;
                }
                break;
            case 3:
                cout << "Goodbye!" << endl;
                return 0;
            default:
                cout << "Invalid choice. Try again." << endl;
        }
    }
}

void loadUsers() {
    ifstream file("users.csv");
    if (!file.is_open()) return;

    userCount = 0;
    string line;
    while (getline(file, line) && userCount < MAX_USERS) {
        size_t commaPos = line.find(',');
        if (commaPos != string::npos) {
            usernames[userCount] = line.substr(0, commaPos);
            passwords[userCount] = line.substr(commaPos + 1);
            userCount++;
        }
    }
    file.close();
}

void saveUsers() {
    ofstream file("users.csv");
    for (int i = 0; i < userCount; i++) {
        file << usernames[i] << "," << passwords[i] << endl;
    }
    file.close();
}

bool registerUser() {
    string username, password;
    cout << "Enter username: ";
    getline(cin, username);
    cout << "Enter password: ";
    getline(cin, password);

    // Check if username exists
    for (int i = 0; i < userCount; i++) {
        if (usernames[i] == username) {
            return false;
        }
    }

    if (userCount < MAX_USERS) {
        usernames[userCount] = username;
        passwords[userCount] = password;
        userCount++;
        saveUsers();
        return true;
    }
    return false;
}

bool loginUser() {
    string username, password;
    cout << "Enter username: ";
    getline(cin, username);
    cout << "Enter password: ";
    getline(cin, password);

    for (int i = 0; i < userCount; i++) {
        if (usernames[i] == username && passwords[i] == password) {
            return true;
        }
    }
    return false;
}

void loadCourses() {
    ifstream file("courses.csv");
    if (!file.is_open()) return;

    courseCount = 0;
    string line;
    while (getline(file, line) && courseCount < MAX_COURSES) {
        size_t firstComma = line.find(',');
        size_t secondComma = line.find(',', firstComma + 1);

        if (firstComma != string::npos && secondComma != string::npos) {
            courseIds[courseCount] = line.substr(0, firstComma);
            titles[courseCount] = line.substr(firstComma + 1, secondComma - firstComma - 1);
            creditHours[courseCount] = stoi(line.substr(secondComma + 1));
            courseCount++;
        }
    }
    file.close();
}

void saveCourses() {
    ofstream file("courses.csv");
    for (int i = 0; i < courseCount; i++) {
        file << courseIds[i] << "," << titles[i] << "," << creditHours[i] << endl;
    }
    file.close();
}

void mainMenu() {
    while (true) {
        cout << "\n=== Main Menu ===" << endl;
        cout << "1. Add Course" << endl;
        cout << "2. Delete Course" << endl;
        cout << "3. Search Course" << endl;
        cout << "4. Update Course" << endl;
        cout << "5. List All Courses" << endl;
        cout << "6. Logout" << endl;
        cout << "Choose option: ";

        int choice;
        cin >> choice;
        cin.ignore();

        switch (choice) {
            case 1: addCourse(); break;
            case 2: deleteCourse(); break;
            case 3: searchCourse(); break;
            case 4: updateCourse(); break;
            case 5: listCourses(); break;
            case 6:
                logout();
                return;
            default:
                cout << "Invalid choice. Try again." << endl;
        }
    }
}

void addCourse() {
    if (courseCount >= MAX_COURSES) {
        cout << "Maximum course limit reached!" << endl;
        return;
    }

    string courseId, title;
    int hours;

    cout << "Enter course ID: ";
    getline(cin, courseId);

    // Check for duplicate
    for (int i = 0; i < courseCount; i++) {
        if (courseIds[i] == courseId) {
            cout << "Course ID already exists!" << endl;
            return;
        }
    }

    cout << "Enter course title: ";
    getline(cin, title);

    cout << "Enter credit hours (1-6): ";
    cin >> hours;
    cin.ignore();

    if (hours < 1 || hours > 6) {
        cout << "Credit hours must be between 1 and 6!" << endl;
        return;
    }

    courseIds[courseCount] = courseId;
    titles[courseCount] = title;
    creditHours[courseCount] = hours;
    courseCount++;

    saveCourses();
    cout << "Course added successfully!" << endl;
}

void deleteCourse() {
    string courseId;
    cout << "Enter course ID to delete: ";
    getline(cin, courseId);

    for (int i = 0; i < courseCount; i++) {
        if (courseIds[i] == courseId) {
            // Shift all elements after the found position
            for (int j = i; j < courseCount - 1; j++) {
                courseIds[j] = courseIds[j + 1];
                titles[j] = titles[j + 1];
                creditHours[j] = creditHours[j + 1];
            }
            courseCount--;
            saveCourses();
            cout << "Course deleted successfully!" << endl;
            return;
        }
    }
    cout << "Course not found!" << endl;
}

void searchCourse() {
    cout << "Search by: 1. Course ID  2. Title substring: ";
    int choice;
    cin >> choice;
    cin.ignore();

    string searchTerm;
    bool found = false;

    if (choice == 1) {
        cout << "Enter course ID: ";
        getline(cin, searchTerm);

        for (int i = 0; i < courseCount; i++) {
            if (courseIds[i] == searchTerm) {
                cout << "Found: " << courseIds[i] << " - " << titles[i]
                     << " (" << creditHours[i] << " credits)" << endl;
                found = true;
                break;
            }
        }
    } else if (choice == 2) {
        cout << "Enter title substring: ";
        getline(cin, searchTerm);

        for (int i = 0; i < courseCount; i++) {
            if (titles[i].find(searchTerm) != string::npos) {
                cout << "Found: " << courseIds[i] << " - " << titles[i]
                     << " (" << creditHours[i] << " credits)" << endl;
                found = true;
            }
        }
    } else {
        cout << "Invalid choice!" << endl;
        return;
    }

    if (!found) {
        cout << "No courses found!" << endl;
    }
}

void updateCourse() {
    string courseId;
    cout << "Enter course ID to update: ";
    getline(cin, courseId);

    for (int i = 0; i < courseCount; i++) {
        if (courseIds[i] == courseId) {
            string newTitle;
            int newHours;

            cout << "Current title: " << titles[i] << endl;
            cout << "Enter new title (or press enter to keep current): ";
            getline(cin, newTitle);

            cout << "Current credit hours: " << creditHours[i] << endl;
            cout << "Enter new credit hours (1-6, or 0 to keep current): ";
            cin >> newHours;
            cin.ignore();

            if (!newTitle.empty()) {
                titles[i] = newTitle;
            }

            if (newHours >= 1 && newHours <= 6) {
                creditHours[i] = newHours;
            } else if (newHours != 0) {
                cout << "Invalid credit hours. Keeping current value." << endl;
            }

            saveCourses();
            cout << "Course updated successfully!" << endl;
            return;
        }
    }
    cout << "Course not found!" << endl;
}

void listCourses() {
    if (courseCount == 0) {
        cout << "No courses available!" << endl;
        return;
    }

    cout << "\n=== All Courses ===" << endl;
    for (int i = 0; i < courseCount; i++) {
        cout << (i + 1) << ". " << courseIds[i] << " - " << titles[i]
             << " (" << creditHours[i] << " credits)" << endl;
    }
    cout << "Total courses: " << courseCount << endl;
}

void logout() {
    cout << "Logged out successfully!" << endl;
}

# DIT400 – Course Management System (CMS)

## Assignment Title
Course Management System – C++ to Java Conversion

---

## Team Members

| Role                                | Name            | Student ID |
|-------------------------------------|-----------------|------------|
| Team Lead (Authentication & File I/O) | Meebelo Musole  | 2410523    |
| Member B (Course CRUD & Arrays)       | Olivia Lungu    | 2410474    |
| Member C (Search/Update & Java)       | Ngosa Ndlovu    | 2410524    |

---

## Build & Run Instructions

### C++ Version
1. Ensure you have `g++` installed.  
2. Compile:  
   ```bash
   g++ cms.cpp -o cms
   ```  
3. Run:  
   ```bash
   ./cms
   ```  

### Java Version
1. Ensure you have `javac` and `java` installed.  
2. Compile:  
   ```bash
   javac Main.java
   ```  
3. Package into JAR:  
   ```bash
   jar cfe cms_java.jar Main *.class
   ```  
4. Run JAR:  
   ```bash
   java -jar cms_java.jar
   ```

---

## Test Credentials
- **Username:** testuser  
- **Password:** 1234  

*(stored in `users.txt`)*

---

## Known Limitations
- Data is stored in plaintext files (`users.txt`, `courses.txt`), no encryption.  
- Arrays have fixed maximum size (1000 courses).  
- No GUI – only command-line interface.  
- No concurrency control (single user at a time).  

---

## Test Cases

1. **Login Success**  
   - Input: `testuser`, `1234`  
   - Expected: Successful login.  

2. **Add Course**  
   - Input: courseId=`CSC101`, title=`Intro to CS`, creditHours=`3`  
   - Expected: Course added successfully.  

3. **Add Duplicate Course**  
   - Input: courseId=`CSC101` again  
   - Expected: Error message (duplicate not allowed).  

4. **Search Course by ID**  
   - Input: `CSC101`  
   - Expected: Displays course details.  

5. **Update Course**  
   - Input: courseId=`CSC101`, new title=`Computer Basics`, new creditHours=`4`  
   - Expected: Course updated.  

6. **Delete Course**  
   - Input: courseId=`CSC101`  
   - Expected: Course deleted.  

7. **Logout**  
   - Input: `6` (Logout/Exit)  
   - Expected: Return to login screen / exit program.  

---

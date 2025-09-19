# 📄 docs/ai_prompts.md  

## AI Prompts & Usage Log  

### 1. Prompt:  
*"Convert the completed C++ Course Management System into Java using arrays only."*  

- **Accepted**: Using `Scanner` in Java instead of `cin` in C++.  
- **Rejected**: Suggestion to use `ArrayList` — requirement was arrays only.  
- **Reasoning**: Scanner is a direct replacement for input, but ArrayList breaks the assignment constraints.  

---

### 2. Prompt:  
*"Explain how to build an executable JAR for the Java program."*  

- **Accepted**: `javac Main.java` and `jar cfe cms_java.jar Main Main.class`.  
- **Rejected**: Using Maven/Gradle for builds — too advanced for this assignment.  
- **Reasoning**: Simple commands are enough; requirement was a single executable JAR.  

---

### 3. Prompt:  
*"Generate team roles and responsibilities for 3 members."*  

- **Accepted**: Splitting work into **Authentication & File I/O**, **Course CRUD**, **Search/Update & Java translation**.  
- **Rejected**: AI suggestion to add a “Testing Lead” as a 4th role — requirement was 3 members only.  
- **Reasoning**: Roles must align with the given instructions.  

---

### 4. Prompt:  
*"Produce a professional report with cover page, learning outcomes, and repository guidelines."*  

- **Accepted**: Adding learning outcomes, GitHub repo structure, and submission notes.  
- **Rejected**: Adding extra frameworks/tools (like MySQL or GUI) that were out of scope.  
- **Reasoning**: The assignment explicitly asked for arrays, text files, and CLI only.  

---

### 5. Prompt:  
*"Draft a 5-minute audio script where each team member discusses their role."*  

- **Accepted**: Conversational style script with intro, individual roles, and conclusion.  
- **Rejected**: Making it too formal like a presentation speech.  
- **Reasoning**: A discussion-style script sounds more natural for an audio submission.  

---

✅ This file documents how AI was used **responsibly**: we evaluated suggestions, kept what fit the assignment, and rejected what broke constraints.  

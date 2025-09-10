🌆 Smart City Management System

A modern, full-stack web application for managing and accessing municipal services, utilities, emergency contacts, tourist spots, and user feedback in a smart city environment.

✨ Features

🔐 User Authentication & Authorization – Secure login, registration, and role-based access (Admin, User).

🏛️ City Services Directory – Browse, search, and view details for hospitals, transport, police, utilities, education, government, and emergency services.

⚡ Utilities Management – Request, track, and manage utility services (electricity, water, etc.) with an admin dashboard for updates.

🚑 Emergency Contacts – Quick access to emergency numbers and details.

🗺️ Tourist Spots – Explore city attractions with details and contact info.

📝 User Feedback – Submit and manage feedback for city services (admins can review/manage).

🛠️ Admin Panel – Manage city services, utilities, users, and feedback with statistics & status counts.

📱 Responsive UI – Mobile-friendly design with custom CSS and smooth animations.

🗄️ Database Integration – MySQL backend with preloaded data via data.sql and reset_database.sql.

🚨 Error Handling – Custom error pages and robust exception handling.

🖥️ Thymeleaf Templating – Dynamic HTML rendering with Spring Boot.

🛠️ Tech Stack

Backend: Java, Spring Boot, Spring Data JPA, Spring Security

Frontend: Thymeleaf, HTML5, CSS3 (custom + utility classes)

Database: MySQL

Build Tool: Maven

Version Control: Git, GitHub



🚀 Setup & Run

Clone the repository

git clone https://github.com/Logesh-Murugan/smart-city-management-v2.git
cd smartcity-management


Configure Database

Update src/main/resources/application.properties with your MySQL credentials.

Build & Run

./mvnw spring-boot:run


Access the App
👉 Open http://localhost:8080
 in your browser.

🎨 Customization

➕ Add/Edit City Services: via admin panel → /admin/city-services

⚡ Manage Utilities: /admin/utility-requests

📞 Update Emergency Contacts: Admin panel or directly in DB

🎨 Styling: Edit styles.css or add utility classes

🤝 Contribution

Fork the repo

Create a new branch → git checkout -b feature/YourFeature

Commit changes → git commit -am 'Add new feature'

Push to branch → git push origin feature/YourFeature

Open a Pull Request 🎉

📜 License

This project is licensed under the MIT License.

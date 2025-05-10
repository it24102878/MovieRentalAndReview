
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Management System</title>
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        body {
            background-color: #1a1a1a;
            color: #ffffff;
            font-family: 'Arial', sans-serif;
        }
        .container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
        }
        .card {
            background-color: #2a2a2a;
            border-radius: 10px;
            padding: 20px;
            margin-bottom: 20px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.3);
            transition: transform 0.3s ease;
        }
        .card:hover {
            transform: translateY(-5px);
        }
        .btn {
            background-color: #e3342f;
            color: #ffffff;
            padding: 10px 20px;
            border-radius: 5px;
            text-decoration: none;
            transition: all 0.3s ease;
            border: none;
            cursor: pointer;
            display: inline-flex;
            align-items: center;
            justify-content: center;
        }
        .btn:hover {
            background-color: #cc1f1a;
            transform: translateY(-2px);
        }
        .btn-secondary {
            background-color: #4a5568;
        }
        .btn-secondary:hover {
            background-color: #2d3748;
        }
        .btn-success {
            background-color: #38a169;
        }
        .btn-success:hover {
            background-color: #2f855a;
        }
        .btn i {
            margin-right: 8px;
        }
        .form-input {
            background-color: #3a3a3a;
            color: #ffffff;
            border: 1px solid #4a4a4a;
            padding: 12px;
            border-radius: 5px;
            width: 100%;
            margin-bottom: 15px;
            transition: border-color 0.3s ease;
        }
        .form-input:focus {
            outline: none;
            border-color: #e3342f;
            box-shadow: 0 0 0 2px rgba(227, 52, 47, 0.2);
        }
        .nav-tabs {
            display: flex;
            border-bottom: 2px solid #e3342f;
            margin-bottom: 20px;
            flex-wrap: wrap;
        }
        .nav-tabs div {
            padding: 12px 20px;
            cursor: pointer;
            color: #ffffff;
            transition: all 0.3s ease;
            font-weight: 500;
        }
        .nav-tabs div.active {
            background-color: #e3342f;
            border-radius: 5px 5px 0 0;
        }
        .nav-tabs div:hover:not(.active) {
            background-color: #cc1f1a;
        }
        .hidden {
            display: none;
        }
        .table {
            width: 100%;
            border-collapse: collapse;
        }
        .table th, .table td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #4a4a4a;
        }
        .table th {
            background-color: #3a3a3a;
            font-weight: 600;
        }
        .table tr:hover {
            background-color: #3a3a3a;
        }
        .modal {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0, 0, 0, 0.8);
            display: flex;
            justify-content: center;
            align-items: center;
            z-index: 1000;
            opacity: 0;
            pointer-events: none;
            transition: opacity 0.3s ease;
        }
        .modal.active {
            opacity: 1;
            pointer-events: all;
        }
        .modal-content {
            background-color: #2a2a2a;
            padding: 25px;
            border-radius: 10px;
            width: 90%;
            max-width: 450px;
            transform: translateY(-20px);
            transition: transform 0.3s ease;
        }
        .modal.active .modal-content {
            transform: translateY(0);
        }
        .form-group {
            margin-bottom: 15px;
        }
        .form-label {
            display: block;
            margin-bottom: 8px;
            font-weight: 500;
        }
        .action-buttons {
            display: flex;
            gap: 10px;
        }
        .user-type-badge {
            padding: 4px 8px;
            border-radius: 4px;
            font-size: 12px;
            font-weight: 600;
        }
        .customer-badge {
            background-color: #4299e1;
        }
        .admin-badge {
            background-color: #9f7aea;
        }
        @media (max-width: 768px) {
            .nav-tabs div {
                padding: 10px 15px;
                font-size: 14px;
            }
            .container {
                padding: 15px;
            }
            .card {
                padding: 15px;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <h1 class="text-3xl font-bold mb-6 flex items-center">
            <i class="fas fa-users-cog mr-3"></i> User Management System
        </h1>

        <!-- Navigation Tabs -->
        <div class="nav-tabs">
            <div class="active" onclick="showTab('register')">
                <i class="fas fa-user-plus"></i> Register
            </div>
            <div onclick="showTab('login')">
                <i class="fas fa-sign-in-alt"></i> Login
            </div>
            <div onclick="showTab('update')">
                <i class="fas fa-user-edit"></i> Update Profile
            </div>
            <div onclick="showTab('list')">
                <i class="fas fa-list"></i> User List (Admin)
            </div>
        </div>

        <!-- Register Tab -->
        <div id="register" class="card">
            <h2 class="text-2xl font-semibold mb-4 flex items-center">
                <i class="fas fa-user-plus mr-2"></i> Register New User
            </h2>
            <form id="registerForm">
                <div class="form-group">
                    <label class="form-label">Name</label>
                    <input type="text" class="form-input" placeholder="Enter full name" required>
                </div>
                <div class="form-group">
                    <label class="form-label">Email</label>
                    <input type="email" class="form-input" placeholder="Enter email address" required>
                </div>
                <div class="form-group">
                    <label class="form-label">Password</label>
                    <input type="password" class="form-input" placeholder="Create password" required>
                </div>
                <div class="form-group">
                    <label class="form-label">User Type</label>
                    <select class="form-input" required>
                        <option value="" disabled selected>Select user type</option>
                        <option value="customer">Customer</option>
                        <option value="admin">Admin</option>
                    </select>
                </div>
                <button type="submit" class="btn">
                    <i class="fas fa-user-plus"></i> Register
                </button>
            </form>
        </div>

        <!-- Login Tab -->
        <div id="login" class="card hidden">
            <h2 class="text-2xl font-semibold mb-4 flex items-center">
                <i class="fas fa-sign-in-alt mr-2"></i> User Login
            </h2>
            <form id="loginForm">
                <div class="form-group">
                    <label class="form-label">Email</label>
                    <input type="email" class="form-input" placeholder="Enter your email" required>
                </div>
                <div class="form-group">
                    <label class="form-label">Password</label>
                    <input type="password" class="form-input" placeholder="Enter your password" required>
                </div>
                <button type="submit" class="btn">
                    <i class="fas fa-sign-in-alt"></i> Login
                </button>
            </form>
        </div>

        <!-- Update Profile Tab -->
        <div id="update" class="card hidden">
            <h2 class="text-2xl font-semibold mb-4 flex items-center">
                <i class="fas fa-user-edit mr-2"></i> Update User Profile
            </h2>
            <form id="updateForm">
                <div class="form-group">
                    <label class="form-label">User ID</label>
                    <input type="text" class="form-input" placeholder="Enter user ID" required>
                </div>
                <div class="form-group">
                    <label class="form-label">New Name (optional)</label>
                    <input type="text" class="form-input" placeholder="Enter new name">
                </div>
                <div class="form-group">
                    <label class="form-label">New Email (optional)</label>
                    <input type="email" class="form-input" placeholder="Enter new email">
                </div>
                <div class="form-group">
                    <label class="form-label">New Password (optional)</label>
                    <input type="password" class="form-input" placeholder="Enter new password">
                </div>
                <button type="submit" class="btn">
                    <i class="fas fa-save"></i> Update Profile
                </button>
            </form>
        </div>

        <!-- User List Tab (Admin) -->
        <div id="list" class="card hidden">
            <div class="flex justify-between items-center mb-4">
                <h2 class="text-2xl font-semibold flex items-center">
                    <i class="fas fa-list mr-2"></i> User Management
                </h2>
                <button class="btn btn-success" onclick="refreshUserList()">
                    <i class="fas fa-sync-alt"></i> Refresh
                </button>
            </div>
            
            <div class="mb-4 flex flex-col md:flex-row gap-4">
                <div class="flex-grow">
                    <input type="text" class="form-input" id="searchInput" placeholder="Search by ID, Name, or Email">
                </div>
                <button class="btn btn-secondary" onclick="clearSearch()">
                    <i class="fas fa-times"></i> Clear
                </button>
            </div>
            
            <div class="overflow-x-auto">
                <table class="table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Email</th>
                            <th>Type</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody id="userTableBody">
                        <!-- Users will be populated here -->
                    </tbody>
                </table>
            </div>
        </div>

        <!-- Modal for Alerts -->
        <div id="modal" class="modal">
            <div class="modal-content">
                <h3 id="modalTitle" class="text-xl font-semibold mb-4 flex items-center">
                    <i id="modalIcon" class="fas fa-info-circle mr-2"></i>
                    <span id="modalTitleText"></span>
                </h3>
                <p id="modalMessage" class="mb-4"></p>
                <div class="flex justify-end">
                    <button onclick="closeModal()" class="btn">
                        <i class="fas fa-times"></i> Close
                    </button>
                </div>
            </div>
        </div>
    </div>

    <script>
        
        let currentUser = null;
        
        
        let users = [
            { id: '1', name: 'John Doe', email: 'john@example.com', type: 'customer', password: 'password123' },
            { id: '2', name: 'Jane Admin', email: 'jane@example.com', type: 'admin', password: 'admin123' },
            { id: '3', name: 'Alice Smith', email: 'alice@example.com', type: 'customer', password: 'alice123' },
            { id: '4', name: 'Bob Johnson', email: 'bob@example.com', type: 'customer', password: 'bob123' }
        ];

        
        function showTab(tabId) {
            document.querySelectorAll('.card').forEach(card => card.classList.add('hidden'));
            document.getElementById(tabId).classList.remove('hidden');
            document.querySelectorAll('.nav-tabs div').forEach(tab => tab.classList.remove('active'));
            event.currentTarget.classList.add('active');
        }

    
        function showModal(title, message, type = 'info') {
            const modal = document.getElementById('modal');
            const modalTitle = document.getElementById('modalTitleText');
            const modalMessage = document.getElementById('modalMessage');
            const modalIcon = document.getElementById('modalIcon');
            
            modalTitle.textContent = title;
            modalMessage.textContent = message;
            
            
            switch(type) {
                case 'error':
                    modalIcon.className = 'fas fa-exclamation-circle mr-2 text-red-500';
                    break;
                case 'success':
                    modalIcon.className = 'fas fa-check-circle mr-2 text-green-500';
                    break;
                case 'warning':
                    modalIcon.className = 'fas fa-exclamation-triangle mr-2 text-yellow-500';
                    break;
                default:
                    modalIcon.className = 'fas fa-info-circle mr-2 text-blue-500';
            }
            
            modal.classList.add('active');
        }

        function closeModal() {
            document.getElementById('modal').classList.remove('active');
        }

        
        function generateId() {
            return Math.random().toString(36).substr(2, 9);
        }

        
        function populateUserTable(userList = users) {
            const tbody = document.getElementById('userTableBody');
            tbody.innerHTML = '';
            
            if (userList.length === 0) {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td colspan="5" class="text-center py-4 text-gray-400">
                        No users found
                    </td>
                `;
                tbody.appendChild(row);
                return;
            }
            
            userList.forEach(user => {
                const row = document.createElement('tr');
                const typeClass = user.type === 'admin' ? 'admin-badge' : 'customer-badge';
                
                row.innerHTML = `
                    <td>${user.id}</td>
                    <td>${user.name}</td>
                    <td>${user.email}</td>
                    <td><span class="user-type-badge ${typeClass}">${user.type}</span></td>
                    <td>
                        <div class="action-buttons">
                            <button class="btn btn-secondary" onclick="editUser('${user.id}')">
                                <i class="fas fa-edit"></i> Edit
                            </button>
                            <button class="btn" onclick="deleteUser('${user.id}')">
                                <i class="fas fa-trash"></i> Delete
                            </button>
                        </div>
                    </td>
                `;
                tbody.appendChild(row);
            });
        }

    
        document.getElementById('registerForm').addEventListener('submit', (e) => {
            e.preventDefault();
            const inputs = e.target.elements;
            const name = inputs[0].value.trim();
            const email = inputs[1].value.trim();
            const password = inputs[2].value;
            const type = inputs[3].value;
            
            
            if (!/^\S+@\S+\.\S+$/.test(email)) {
                showModal('Error', 'Please enter a valid email address', 'error');
                return;
            }
            
          
            if (users.some(u => u.email === email)) {
                showModal('Error', 'Email already registered', 'error');
                return;
            }
            
            const id = generateId();
            users.push({ id, name, email, password, type });
            showModal('Success', 'User registered successfully!', 'success');
            e.target.reset();
            populateUserTable(users);
        });

        
        document.getElementById('loginForm').addEventListener('submit', (e) => {
            e.preventDefault();
            const inputs = e.target.elements;
            const email = inputs[0].value.trim();
            const password = inputs[1].value;
            
            const user = users.find(u => u.email === email && u.password === password);
            
            if (user) {
                currentUser = user;
                showModal('Login Successful', `Welcome back, ${user.name}!`, 'success');
                
                
                if (user.type === 'admin') {
                    setTimeout(() => {
                        showTab('list');
                        document.querySelector('.nav-tabs div:nth-child(4)').click();
                    }, 1500);
                }
            } else {
                showModal('Login Failed', 'Invalid email or password', 'error');
            }
            
            e.target.reset();
        });

    
        document.getElementById('updateForm').addEventListener('submit', (e) => {
            e.preventDefault();
            const inputs = e.target.elements;
            const id = inputs[0].value.trim();
            const name = inputs[1].value.trim();
            const email = inputs[2].value.trim();
            const password = inputs[3].value;
            
            const userIndex = users.findIndex(u => u.id === id);
            
            if (userIndex === -1) {
                showModal('Error', 'User not found!', 'error');
                return;
            }
            
            
            if (email && !/^\S+@\S+\.\S+$/.test(email)) {
                showModal('Error', 'Please enter a valid email address', 'error');
                return;
            }
            
            
            if (email && users.some((u, index) => u.email === email && index !== userIndex)) {
                showModal('Error', 'Email already in use by another user', 'error');
                return;
            }
            
            
            if (name) users[userIndex].name = name;
            if (email) users[userIndex].email = email;
            if (password) users[userIndex].password = password;
            
            showModal('Success', 'Profile updated successfully!', 'success');
            populateUserTable(users);
            e.target.reset();
        });

        
        function deleteUser(id) {
            const user = users.find(u => u.id === id);
            
            if (!user) {
                showModal('Error', 'User not found!', 'error');
                return;
            }
            
            if (confirm(`Are you sure you want to delete ${user.name} (${user.email})?`)) {
                users = users.filter(u => u.id !== id);
                
                
                if (currentUser && currentUser.id === id) {
                    currentUser = null;
                }
                
                showModal('Success', 'User deleted successfully!', 'success');
                populateUserTable(users);
            }
        }

      
        function editUser(id) {
            const user = users.find(u => u.id === id);
            
            if (user) {
                const form = document.getElementById('updateForm');
                form.elements[0].value = user.id;
                form.elements[1].value = user.name;
                form.elements[2].value = user.email;
                
                showTab('update');
                document.querySelector('.nav-tabs div:nth-child(3)').click();
                
              
                document.getElementById('update').scrollIntoView({ behavior: 'smooth' });
            }
        }

        
        document.getElementById('searchInput').addEventListener('input', (e) => {
            const search = e.target.value.toLowerCase();
            const filteredUsers = users.filter(u => 
                u.id.toLowerCase().includes(search) || 
                u.name.toLowerCase().includes(search) ||
                u.email.toLowerCase().includes(search)
            );
            populateUserTable(filteredUsers);
        });

      
        function clearSearch() {
            document.getElementById('searchInput').value = '';
            populateUserTable(users);
        }

    
        function refreshUserList() {
            populateUserTable(users);
            showModal('Refreshed', 'User list has been refreshed', 'success');
        }

        
        document.addEventListener('DOMContentLoaded', () => {
            populateUserTable(users);
            
            
            document.getElementById('modal').addEventListener('click', (e) => {
                if (e.target === document.getElementById('modal')) {
                    closeModal();
                }
            });
            
        
            document.addEventListener('keydown', (e) => {
                if (e.key === 'Escape' && document.getElementById('modal').classList.contains('active')) {
                    closeModal();
                }
            });
        });
    </script>
</body>
</html>

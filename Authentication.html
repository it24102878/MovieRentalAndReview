<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Authentication Management</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Arial', sans-serif;
        }

        body {
            background-color: #1a1a1a;
            color: #ffffff;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            overflow-x: hidden;
        }

        .container {
            width: 100%;
            max-width: 400px;
            padding: 20px;
        }

        .auth-card {
            background: #2a2a2a;
            border-radius: 15px;
            padding: 30px;
            box-shadow: 0 8px 32px rgba(0, 0, 0, 0.5);
            backdrop-filter: blur(10px);
            animation: fadeIn 0.5s ease-in-out;
        }

        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(-20px); }
            to { opacity: 1; transform: translateY(0); }
        }

        h1 {
            text-align: center;
            margin-bottom: 20px;
            font-size: 24px;
            color: #ffffff;
        }

        .tab-buttons {
            display: flex;
            justify-content: space-between;
            margin-bottom: 20px;
        }

        .tab-btn {
            flex: 1;
            padding: 10px;
            background: #333;
            border: none;
            color: #fff;
            cursor: pointer;
            transition: background 0.3s;
            font-size: 14px;
        }

        .tab-btn.active, .tab-btn:hover {
            background: #ff0000;
        }

        .tab-btn:first-child {
            border-radius: 8px 0 0 0;
        }

        .tab-btn:last-child {
            border-radius: 0 8px 8px 0;
        }

        .tab-content {
            display: none;
        }

        .tab-content.active {
            display: block;
            animation: slideIn 0.3s ease-in-out;
        }

        @keyframes slideIn {
            from { opacity: 0; transform: translateX(20px); }
            to { opacity: 1; transform: translateX(0); }
        }

        .form-group {
            margin-bottom: 20px;
        }

        label {
            display: block;
            margin-bottom: 5px;
            font-size: 14px;
            color: #ccc;
        }

        input {
            width: 100%;
            padding: 12px;
            background: #333;
            border: none;
            border-radius: 8px;
            color: #fff;
            font-size: 16px;
            transition: background 0.3s;
        }

        input:focus {
            background: #444;
            outline: none;
        }

        .btn {
            width: 100%;
            padding: 12px;
            background: #ff0000;
            border: none;
            border-radius: 8px;
            color: #fff;
            font-size: 16px;
            cursor: pointer;
            transition: background 0.3s, transform 0.2s;
        }

        .btn:hover {
            background: #cc0000;
            transform: translateY(-2px);
        }

        .link {
            display: block;
            text-align: center;
            margin-top: 15px;
            color: #ff0000;
            text-decoration: none;
            font-size: 14px;
        }

        .link:hover {
            text-decoration: underline;
        }

        .oauth-buttons {
            display: flex;
            flex-direction: column;
            gap: 10px;
            margin-top: 20px;
        }

        .oauth-btn {
            background: #333;
            padding: 12px;
            border-radius: 8px;
            text-align: center;
            color: #fff;
            font-size: 14px;
            cursor: pointer;
            transition: background 0.3s;
        }

        .oauth-btn:hover {
            background: #444;
        }

        .admin-dashboard {
            background: #333;
            padding: 20px;
            border-radius: 8px;
            margin-top: 20px;
        }

        .session-list {
            list-style: none;
        }

        .session-item {
            padding: 10px;
            background: #444;
            margin-bottom: 10px;
            border-radius: 8px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .session-item button {
            background: #ff0000;
            border: none;
            padding: 8px 12px;
            border-radius: 8px;
            color: #fff;
            cursor: pointer;
        }

        .session-item button:hover {
            background: #cc0000;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="auth-card">
            <h1>Authentication Management</h1>
            <div class="tab-buttons">
                <button class="tab-btn active" data-tab="login">Login</button>
                <button class="tab-btn" data-tab="signup">Sign Up</button>
                <button class="tab-btn" data-tab="recovery">Recovery</button>
                <button class="tab-btn" data-tab="admin">Admin</button>
            </div>

            <div id="login" class="tab-content active">
                <form id="login-form">
                    <div class="form-group">
                        <label for="username">Username</label>
                        <input type="text" id="username" placeholder="Enter username" required>
                    </div>
                    <div class="form-group">
                        <label for="password">Password</label>
                        <input type="password" id="password" placeholder="Enter password" required>
                    </div>
                    <button type="submit" class="btn">Login</button>
                </form>
                <div class="oauth-buttons">
                    <div class="oauth-btn">Login with Google</div>
                    <div class="oauth-btn">Login with GitHub</div>
                </div>
                <a href="#" class="link" data-tab="recovery">Forgot Password?</a>
                <a href="#" class="link" data-tab="signup">Create an Account</a>
            </div>

            <div id="signup" class="tab-content">
                <form id="signup-form">
                    <div class="form-group">
                        <label for="signup-username">Username</label>
                        <input type="text" id="signup-username" placeholder="Choose a username" required>
                    </div>
                    <div class="form-group">
                        <label for="signup-email">Email</label>
                        <input type="email" id="signup-email" placeholder="Enter your email" required>
                    </div>
                    <div class="form-group">
                        <label for="signup-password">Password</label>
                        <input type="password" id="signup-password" placeholder="Create a password" required>
                    </div>
                    <div class="form-group">
                        <label for="confirm-password">Confirm Password</label>
                        <input type="password" id="confirm-password" placeholder="Confirm your password" required>
                    </div>
                    <button type="submit" class="btn">Sign Up</button>
                </form>
                <a href="#" class="link" data-tab="login">Already have an account? Login</a>
            </div>

            <div id="recovery" class="tab-content">
                <form id="recovery-form">
                    <div class="form-group">
                        <label for="email">Email</label>
                        <input type="email" id="email" placeholder="Enter your email" required>
                    </div>
                    <button type="submit" class="btn">Recover Password</button>
                </form>
                <a href="#" class="link" data-tab="login">Back to Login</a>
            </div>

            <div id="admin" class="tab-content">
                <div class="admin-dashboard">
                    <h2>Session Management</h2>
                    <ul class="session-list" id="session-list">
                        <!-- Sessions will be dynamically added here -->
                    </ul>
                </div>
                <a href="#" class="link" data-tab="login">Back to Login</a>
            </div>
        </div>
    </div>

    <script>
        // Tab switching functionality
        const tabButtons = document.querySelectorAll('.tab-btn');
        const tabContents = document.querySelectorAll('.tab-content');
        const links = document.querySelectorAll('.link');

        tabButtons.forEach(button => {
            button.addEventListener('click', () => {
                const tab = button.getAttribute('data-tab');
                switchTab(tab);
            });
        });

        links.forEach(link => {
            link.addEventListener('click', (e) => {
                e.preventDefault();
                const tab = link.getAttribute('data-tab');
                switchTab(tab);
            });
        });

        function switchTab(tab) {
            tabButtons.forEach(btn => btn.classList.remove('active'));
            tabContents.forEach(content => content.classList.remove('active'));

            document.querySelector(`.tab-btn[data-tab="${tab}"]`).classList.add('active');
            document.getElementById(tab).classList.add('active');
        }

        // Form submission handling (placeholder)
        document.getElementById('login-form').addEventListener('submit', (e) => {
            e.preventDefault();
            alert('Login submitted (Placeholder)');
        });

        document.getElementById('signup-form').addEventListener('submit', (e) => {
            e.preventDefault();
            const password = document.getElementById('signup-password').value;
            const confirmPassword = document.getElementById('confirm-password').value;
            if (password !== confirmPassword) {
                alert('Passwords do not match!');
                return;
            }
            alert('Sign Up submitted (Placeholder)');
        });

        document.getElementById('recovery-form').addEventListener('submit', (e) => {
            e.preventDefault();
            alert('Recovery request submitted (Placeholder)');
        });

        // Dynamic session list (placeholder)
        const sessionList = document.getElementById('session-list');
        const sessions = [
            { id: 1, user: 'user1', time: '2025-05-09 10:00' },
            { id: 2, user: 'user2', time: '2025-05-09 11:00' }
        ];

        sessions.forEach(session => {
            const li = document.createElement('li');
            li.classList.add('session-item');
            li.innerHTML = `
                <span>${session.user} - ${session.time}</span>
                <button onclick="endSession(${session.id})">End Session</button>
            `;
            sessionList.appendChild(li);
        });

        function endSession(id) {
            alert(`Ending session ${id} (Placeholder)`);
        }
    </script>
</body>
</html>
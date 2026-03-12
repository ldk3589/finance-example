#  Finance Manager Frontend

[English](README.md) | [中文](README.zh-CN.md)

A Vue 3 based frontend application for the Finance Manager project.

This module provides the user interface for login, registration, account viewing, category management, transaction recording, and financial report visualization. It communicates with the backend through REST APIs.

## Tech Stack

* Vue 3
* Vite
* Vue Router
* Pinia
* Axios
* Element Plus
* ECharts

## Project Structure

```
frontend/                             # Frontend folder
├── src/                              # Source code
│   ├── api/                          # Link to backend
│   │   ├── request.js                # Send data tool
│   │   ├── auth.js                   # Login link
│   │   ├── account.js                # Account link
│   │   ├── category.js               # Category link
│   │   └── transaction.js            # Money link
│   ├── layout/                       # Page layout
│   │   └── Layout.vue                # Main page box
│   ├── router/                       # Page links
│   │   └── index.js                  # Link rules
│   ├── stores/                       # Save data
│   │   └── auth.js                   # Save login info
│   ├── utils/                        # Helpful tools
│   │   └── auth.js                   # Login tool
│   ├── views/                        # Page views
│   │   ├── Login.vue                 # Login page
│   │   ├── Register.vue              # Sign up page
│   │   ├── Add.vue                   # Add data page
│   │   └── Report.vue                # Report page
│   ├── App.vue                       # Main Vue page
│   ├── main.js                       # Main JS file
│   └── style.css                     # Page styles
├── package.json                      # Tool list
└── vite.config.js                    # Vite tool settings
```

## Main Pages

### 1. Login Page

* User login form
* Username and password validation
* Save login state after successful login

### 2. Register Page

* User registration form
* Confirm password validation
* Jump to login after successful registration

### 3. Add Page

* Record income and expense data
* Select account and category
* Add new account
* Add new category
* View current account balances

### 4. Report Page

* View total income
* View total expense
* View current balance
* Expense pie chart
* Income and expense bar chart
* Transaction list

## API Communication

The frontend uses Axios and sends requests to backend APIs through `/api` proxy or base URL.

Main API files:

* `src/api/request.js`
* `src/api/auth.js`
* `src/api/account.js`
* `src/api/category.js`
* `src/api/transaction.js`

## Login State Management

The frontend uses:

* Pinia for auth store
* localStorage for persisted user information
* Vue Router guards for protected routes

Stored user information usually includes:

```
{
  "userId": 1,
  "username": "test",
  "token": "jwt-token"
}
```

## Route Design

Main routes:

* /login
* /register
* /add
* /report

Protected pages require a valid token.

## How to Run

### 1. Install dependencies

```
cd frontend
npm install
```

### 2. Start development server

```
npm run dev
```

### 3. Build for production

```
npm run build
```

## UI Design

The frontend uses Element Plus as the main UI component library.

Main design ideas:

* Clean dashboard layout
* Card-based content display
* Responsive page structure
* Unified colors, spacing, and rounded corners
* Better chart presentation for reports

## Notes

* Make sure the backend is running before using the frontend
* Check `request.js` if login expires or API requests fail
* The Authorization header should use Bearer token format
* If report charts are empty, first check whether backend report data is returned correctly

## Future Improvements

* Transaction edit and delete pages
* Better mobile adaptation
* Theme switching
* More detailed report filters
* Export and print support

## License

This module is for study and practice use.

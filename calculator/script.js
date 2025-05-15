// Store expenses in an array
let expenses = [
    { category: "Groceries", amount: 15000 },
    { category: "Rent", amount: 40000 },
    { category: "Transportation", amount: 5000 },
    { category: "Entertainment", amount: 10000 },
    { category: "Communication", amount: 2000 },
    { category: "Gym", amount: 3000 }
];

// Sorting state
let currentSort = {
    column: null,
    direction: 'asc'
};

// Get DOM elements
const expenseForm = document.getElementById('expenseForm');
const expensesList = document.getElementById('expensesList');
const totalExpensesElement = document.getElementById('totalExpenses');
const averageExpenseElement = document.getElementById('averageExpense');
const topExpensesElement = document.getElementById('topExpenses');

// Add event listeners for sorting
document.querySelectorAll('.sortable').forEach(header => {
    header.addEventListener('click', () => {
        const column = header.dataset.sort;
        if (currentSort.column === column) {
            currentSort.direction = currentSort.direction === 'asc' ? 'desc' : 'asc';
        } else {
            currentSort.column = column;
            currentSort.direction = 'asc';
        }
        
        // Update sort icons
        document.querySelectorAll('.sortable i').forEach(icon => {
            icon.className = 'fas fa-sort';
        });
        header.querySelector('i').className = `fas fa-sort-${currentSort.direction === 'asc' ? 'up' : 'down'}`;
        
        updateExpensesList();
    });
});

// Add event listener for form submission
expenseForm.addEventListener('submit', function(e) {
    e.preventDefault();
    
    // Get input values
    const category = document.getElementById('category').value;
    const amount = parseFloat(document.getElementById('amount').value);
    
    // Add expense to array
    expenses.push({ category, amount });
    
    // Update UI
    updateExpensesList();
    updateSummary();
    
    // Reset form
    expenseForm.reset();
});

// Function to delete expense
function deleteExpense(index) {
    expenses.splice(index, 1);
    updateExpensesList();
    updateSummary();
}

// Function to update expenses list
function updateExpensesList() {
    // Sort expenses if needed
    let sortedExpenses = [...expenses];
    if (currentSort.column) {
        sortedExpenses.sort((a, b) => {
            let comparison = 0;
            if (currentSort.column === 'category') {
                comparison = a.category.localeCompare(b.category);
            } else if (currentSort.column === 'amount') {
                comparison = a.amount - b.amount;
            }
            return currentSort.direction === 'asc' ? comparison : -comparison;
        });
    }

    expensesList.innerHTML = '';
    sortedExpenses.forEach((expense, index) => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${expense.category}</td>
            <td>$${expense.amount.toFixed(2)}</td>
            <td>
                <button class="delete-btn" onclick="deleteExpense(${index})">
                    <i class="fas fa-trash"></i>
                </button>
            </td>
        `;
        expensesList.appendChild(row);
    });
}

// Function to update summary
function updateSummary() {
    // Calculate total expenses
    const total = expenses.reduce((sum, expense) => sum + expense.amount, 0);
    totalExpensesElement.textContent = `$${total.toFixed(2)}`;
    
    // Calculate average daily expense (assuming 30 days per month)
    const average = total / 30;
    averageExpenseElement.textContent = `$${average.toFixed(2)}`;
    
    // Get top 3 expenses
    const topExpenses = [...expenses]
        .sort((a, b) => b.amount - a.amount)
        .slice(0, 3);
    
    // Update top expenses list
    topExpensesElement.innerHTML = '';
    topExpenses.forEach(expense => {
        const li = document.createElement('li');
        li.innerHTML = `
            <span>${expense.category}</span>
            <strong>$${expense.amount.toFixed(2)}</strong>
        `;
        topExpensesElement.appendChild(li);
    });
}

// Initialize the UI
updateExpensesList();
updateSummary(); 
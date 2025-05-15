-- Input
 TABLE orders (
    id INTEGER PRIMARY KEY,
    customer TEXT,
    amount REAL,
    order_date DATE
);

INSERT INTO orders (customer, amount, order_date) VALUES
('Alice', 5000, '2024-03-01'),
('Bob', 8000, '2024-03-05'),
('Alice', 3000, '2024-03-15'),
('Charlie', 7000, '2024-02-20'),
('Alice', 10000, '2024-02-28'),
('Bob', 4000, '2024-02-10'),
('Charlie', 9000, '2024-03-22'),
('Alice', 2000, '2024-03-30');

-- ChatGPT prompt
-- Let's say you're a BI engineer and you've got the following SQL table:
-- CREATE TABLE orders (
--     id INTEGER PRIMARY KEY,
--     customer TEXT,
--     amount REAL,
--     order_date DATE
-- );
-- I want you to implement a series of queries to analyse the data.
-- I want these queries to be portable (compatible across most databases).
-- 1. Calculate the total sales volume for March 2024.
-- 2. Find the customer who spent the most overall.
-- 3. Calculate the average order value for the last three months.

-- 1. Calculate the total sales volume for March 2024.
CREATE INDEX idx_orders_date ON orders(order_date);

SELECT SUM(amount) AS total_sales_march_2024
FROM orders
WHERE order_date >= '2024-03-01' AND order_date < '2024-04-01';

-- 2. Find the customer who spent the most overall.
SELECT customer, SUM(amount) AS total
FROM orders
GROUP BY customer
ORDER BY total DESC
LIMIT 1;

-- 3. Calculate the average order value for the last three months.
-- NOTE: compatible with SQLLite only
SELECT AVG(amount) AS avg_order_value_last_3_months
FROM orders
WHERE order_date >= DATE('now', '-3 months')
  AND order_date < DATE('now',);

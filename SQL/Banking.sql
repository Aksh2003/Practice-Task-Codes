CREATE TABLE Employee (
    RegisterId int PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100),
    address VARCHAR(255)
);

CREATE TABLE Customer (
    RegisterId int PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100),
    address VARCHAR(255)
);

CREATE TABLE User (
    RegisterId int PRIMARY KEY,
    password VARCHAR(100)
);

CREATE TABLE Account (
    accid INT PRIMARY KEY,
    custid int,
    bankid INT,
    branchid INT,
    balance DECIMAL(10,2),
    type VARCHAR(50),
    FOREIGN KEY (bankid) REFERENCES Bank(bank_id),
    FOREIGN KEY (branchid) REFERENCES Branch(branch_id),
    FOREIGN KEY (custid) REFERENCES Customer(RegisterId)
);

CREATE TABLE AccountType (
    branch_id INT,
    interest_rate DECIMAL(5,2),
    max_withdrawal_amount DECIMAL(10,2),
    min_balance DECIMAL(10,2),
    type VARCHAR(50),
    FOREIGN KEY (branch_id) REFERENCES Branch(branch_id),
    CONSTRAINT chk_account_type CHECK (type IN ('Savings', 'Current'))
);

CREATE TABLE Bank (
    bank_id INT PRIMARY KEY,
    bank_name VARCHAR(100)
);

CREATE TABLE Branch (
    branch_id INT PRIMARY KEY,
    bank_id INT,
    branch_name VARCHAR(100),
    address VARCHAR(255),
    FOREIGN KEY (bank_id) REFERENCES Bank(bank_id)
);

CREATE TABLE Transaction (
    tid INT PRIMARY KEY,
    accid INT,
    date DATE,
    amount DECIMAL(10,2),
    type VARCHAR(50),
    FOREIGN KEY (accid) REFERENCES account(accid),
    CONSTRAINT trns_type CHECK (type IN ('Withdrawal', 'Deposit'))

);

DROP TABLE IF EXISTS Transaction;


CREATE TABLE Transaction (
    tid INT AUTO_INCREMENT PRIMARY KEY,
    accid INT,
    date DATE,
    amount DECIMAL(10,2),
    type VARCHAR(50),
    FOREIGN KEY (accid) REFERENCES Account(accid),
    CONSTRAINT trns_type CHECK (type IN ('Withdrawal', 'Deposit'))
);





-- Insert into Employee table
INSERT INTO Employee (RegisterId, name, email, address)
VALUES (1, 'John Doe', 'john.doe@example.com', '123 Main St'),
       (2, 'Jane Smith', 'jane.smith@example.com', '456 Oak Ave');

-- Insert into Customer table
INSERT INTO Customer (RegisterId, name, email, address)
VALUES (101, 'Alice Johnson', 'alice.johnson@example.com', '789 Elm Rd'),
       (102, 'Bob Williams', 'bob.williams@example.com', '321 Pine Blvd');

-- Insert into User table
INSERT INTO User (RegisterId, password)
VALUES (101, 'password1'),
       (102, 'password2');

-- Insert into Bank table
INSERT INTO Bank (bank_id, bank_name)
VALUES (1, 'First Bank'),
       (2, 'Second Bank');

-- Insert into Branch table
INSERT INTO Branch (branch_id, bank_id, branch_name, address)
VALUES (201, 1, 'Main Branch', '10 Broad St'),
       (202, 1, 'West Branch', '20 Oak St'),
       (301, 2, 'Chennai', '30 Pine St');

-- Insert into AccountType table
INSERT INTO AccountType (branch_id, interest_rate, max_withdrawal_amount, min_balance, type)
VALUES (201, 1.5, 5000.00, 0, 'Savings'),
       (201, 2.0, 10000.00, 2000.00, 'Current'),
       (202, 1.4, 4000.00, 0, 'Savings'),
       (202, 1.3, 4000.00, 1500.00, 'Current'),
       (301, 1.7, 6000.00, 0, 'Savings'),
       (301, 1.8, 6000.00, 1200.00, 'Current');

-- Insert into Account table
INSERT INTO Account (accid, custid, bankid, branchid, balance, type)
VALUES (1, 101, 1, 201, 5000.00, 'Savings'),
       (2, 102, 1, 202, 8000.00, 'Current'),
       (3, 101, 2, 301, 7000.00, 'Savings'),
       (4, 102, 2, 301, 10000.00, 'Current');



-- Fetch the first three accounts with the highest balance
SELECT *
FROM Account
ORDER BY balance DESC
LIMIT 3;
-- Given five account_id, fetch their account details
SELECT *
FROM Account
WHERE accid IN (1, 2, 3, 4, 5);

-- Total number of accounts in Chennai branch
SELECT COUNT(*)
FROM Account a
JOIN Branch b ON a.branchid = b.branch_id
WHERE b.branch_name = 'Chennai';

-- Total number of accounts not in Chennai branch
SELECT COUNT(*)
FROM Account a
JOIN Branch b ON a.branchid = b.branch_id
WHERE b.branch_name != 'Chennai';

-- Non-duplicate result set of branch IDs that have an account
SELECT DISTINCT branchid
FROM Account;

-- For a given customer, get their interest rate and max withdrawal amount
SELECT at.interest_rate, at.max_withdrawal_amount,at.type
FROM Account a
JOIN AccountType at ON a.branchid = at.branch_id AND a.type = at.type
WHERE a.custid = 101;

-- SUM of amount in the accounts of people in each branch.

SELECT b.branch_id, b.branch_name, SUM(a.balance) AS total_balance
FROM Account a
JOIN Branch b ON a.branchid = b.branch_id
GROUP BY b.branch_id, b.branch_name;

-- Create a history table for account, whenever account is updated, an entry should be there in history table

 CREATE TABLE AccountHistory (
    history_id INT AUTO_INCREMENT PRIMARY KEY,
    accid INT,
    old_balance DECIMAL(10,2),
    new_balance DECIMAL(10,2),
    update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (accid) REFERENCES Account(accid)
);      
DELIMITER //

CREATE TRIGGER account_update_trigger
AFTER UPDATE ON Account
FOR EACH ROW
BEGIN
    INSERT INTO AccountHistory (accid, old_balance, new_balance)
    VALUES (OLD.accid, OLD.balance, NEW.balance);
END//

DELIMITER ;

-- Consider scenario where customer_id of 1 is transferring 500 Rs to customer_id 2. Write query for the same.
DELIMITER //

CREATE PROCEDURE TransferAmount(
    IN p_accid1 INT,
    IN p_accid2 INT,
    IN p_transfer_amount DECIMAL(10,2)
)
BEGIN
    DECLARE current_balance DECIMAL(10,2);
    DECLARE min_balance DECIMAL(10,2);
    
    
    SELECT  A.balance, AT.min_balance INTO current_balance, min_balance
    FROM Account A
	JOIN AccountType AT ON A.branchid = AT.branch_id AND A.type = AT.type
    WHERE A.accid = p_accid1;   

    
    START TRANSACTION;

    
    IF p_transfer_amount <= current_balance AND current_balance >= min_balance THEN
        
        UPDATE Account
        SET balance = balance - p_transfer_amount
        WHERE accid = p_accid1;

        
        UPDATE Account
        SET balance = balance + p_transfer_amount
        WHERE accid = p_accid2;

        
        INSERT INTO Transaction (accid, date, amount, type)
        VALUES (p_accid1, NOW(), p_transfer_amount, 'Withdrawal'),
               (p_accid2, NOW(), p_transfer_amount, 'Deposit');

        
        COMMIT;

        
        SELECT 'Transaction successful' AS result;
    ELSE

        ROLLBACK;

        
        SELECT 'Invalid withdrawal amount or insufficient balance' AS result;
    END IF;
    
END //

DELIMITER ;


CALL TransferAmount(1, 2, 500.00);


--- creating index

CREATE INDEX idx_customer_email ON Customer(email);
CREATE INDEX idx_employee_email ON Employee(email);


CREATE INDEX idx_account_customerID_branchID ON Account(custid,branch_id);
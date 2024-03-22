CREATE TABLE mca_configuration (
                      mca_id VARCHAR(36) PRIMARY KEY,
                      loan_id VARCHAR(36) NOT NULL,
                      factor_rate DECIMAL(13, 10) NOT NULL,
                      holdback_amount DECIMAL(32, 2) NOT NULL, # estimated hold back amount
                      holdback_percentage DECIMAL(13, 10) NOT NULL, # percentage for hold back based on variable income
                      holdback_type VARCHAR(36) NOT NULL, # prefer either AMOUNT or VARIABLE
);
